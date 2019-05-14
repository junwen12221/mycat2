package io.mycat.proxy.executer;

import io.mycat.proxy.session.MycatSession;
import io.mycat.router.ResultRoute;
import io.mycat.router.routeResult.GlobalTableWriteResultRoute;
import io.mycat.router.routeResult.MySQLCommandRouteResultRoute;
import io.mycat.router.routeResult.OneServerResultRoute;
import io.mycat.router.routeResult.SubTableResultRoute;
import io.mycat.router.routeResult.dbResultSet.DbResultSet;
import java.io.IOException;

/**
 * @author jamie12221
 * @date 2019-05-07 13:58
 *
 * 根据不同的路由结果处理
 **/
public enum RouteResultExecuter implements ResultRoute.Executer<MycatSession> {
  INSTANCE;

  @Override
  public void run(DbResultSet dbResultSet, MycatSession mycatSession) {

  }

  @Override
  public void run(OneServerResultRoute dbResultSet, MycatSession mycatSession)
      throws IOException {
    mycatSession.switchDataNode(dbResultSet.getDataNode());
//    MySQLPacketExchanger.INSTANCE.handle(mycatSession, false);
  }

  @Override
  public void run(GlobalTableWriteResultRoute dbResultSet, MycatSession mycatSession) {

  }

  @Override
  public void run(MySQLCommandRouteResultRoute dbResultSet, MycatSession mycatSession) {

  }

  @Override
  public void run(SubTableResultRoute dbResultSet, MycatSession mycatSession) {

  }
}
