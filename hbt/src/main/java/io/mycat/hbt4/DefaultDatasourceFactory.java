package io.mycat.hbt4;

import com.alibaba.fastsql.util.JdbcUtils;
import com.google.common.collect.ImmutableList;
import io.mycat.*;
import io.mycat.datasource.jdbc.datasource.JdbcConnectionManager;
import io.mycat.datasource.jdbc.datasource.JdbcDataSource;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.*;

public class DefaultDatasourceFactory implements DatasourceFactory {
    final MycatDataContext context;
    final List<String> targets = new ArrayList<>();
    private Map<String, Deque<MycatConnection>> connectionMap;
    private LinkedList<MycatConnection> autoCloseables = new LinkedList<>();
    public DefaultDatasourceFactory(MycatDataContext context) {
        this.context = context;
    }

    @Override
    public void close() throws Exception {
        context.getTransactionSession().clearJdbcConnection();
    }

    @Override
    public void open() {
        this.connectionMap = context.getTransactionSession().getConnection(targets);
    }

    @Override
    public void createTableIfNotExisted(String targetName, String createTableSql) {

    }


    @Override
    public Map<String, MycatConnection> getConnections(List<String> targets) {
        HashMap<String,MycatConnection> connectionHashMap = new HashMap<>();
        Map<String, Deque<MycatConnection>> connection = context.getTransactionSession().getConnection(targets);
        for (Map.Entry<String, Deque<MycatConnection>> stringDequeEntry : connection.entrySet()) {
            connectionHashMap.put(stringDequeEntry.getKey(),stringDequeEntry.getValue().getFirst());
        }

        return connectionHashMap;
    }

    @Override
    public void regist(ImmutableList<String> asList) {
        targets.addAll(asList);
    }

    @Override
    public MycatConnection getConnection(String key) {
        Deque<MycatConnection> mycatConnections = connectionMap.get(key);
        MycatConnection pop = mycatConnections.pop();
        autoCloseables.add(pop);
        return pop;
    }

    /**
     * @todo check dead lock
     * @param targets
     * @return
     */
    @Override
    @SneakyThrows
    public List<Connection> getTmpConnections(List<String> targets) {
        List<Connection> res = new ArrayList<>();
        JdbcConnectionManager connectionManager = MetaClusterCurrent.wrapper(JdbcConnectionManager.class);
        Map<String, JdbcDataSource> datasourceInfo = connectionManager.getDatasourceInfo();
        synchronized (connectionManager) {
            for (String jdbcDataSource : targets) {
                JdbcDataSource dataSource = datasourceInfo.get(jdbcDataSource);
                res.add(  dataSource.getDataSource().getConnection());
            }
            return res;
        }
    }

    @Override
    @SneakyThrows
    public void recycleTmpConnections(List<Connection> connections) {
        for (Connection connection : connections) {
            JdbcUtils.close(connection);
        }

    }
}