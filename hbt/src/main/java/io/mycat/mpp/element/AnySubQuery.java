package io.mycat.mpp.element;

import com.alibaba.fastsql.sql.ast.SQLObject;
import com.alibaba.fastsql.sql.ast.expr.SQLBinaryOperator;
import io.mycat.mpp.DataContext;
import io.mycat.mpp.SqlValue;
import io.mycat.mpp.plan.DataAccessor;
import io.mycat.mpp.plan.QueryPlan;
import io.mycat.mpp.plan.RowType;
import io.mycat.mpp.runtime.Type;


public class AnySubQuery implements SqlValue {
    public AnySubQuery(SqlValue left, SQLBinaryOperator operator, QueryPlan sqlExpr) {
        super();
    }

    @Override
    public Object getValue(RowType type, DataAccessor dataAccessor, DataContext context) {
        return null;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public SQLObject toParseTree() {
        return null;
    }
}