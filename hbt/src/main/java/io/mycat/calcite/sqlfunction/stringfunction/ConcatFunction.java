package io.mycat.calcite.sqlfunction.stringfunction;

import org.apache.calcite.adapter.enumerable.RexImpTable;
import org.apache.calcite.adapter.enumerable.RexToLixTranslator;
import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.linq4j.tree.Expressions;
import org.apache.calcite.linq4j.tree.Types;
import org.apache.calcite.mycat.MycatSqlDefinedFunction;
import org.apache.calcite.rex.RexCall;
import org.apache.calcite.schema.ScalarFunction;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;
import org.apache.calcite.sql.SqlFunctionCategory;
import org.apache.calcite.sql.type.InferTypes;
import org.apache.calcite.sql.type.OperandTypes;
import org.apache.calcite.sql.type.ReturnTypes;

import java.lang.reflect.Method;

public class ConcatFunction extends MycatSqlDefinedFunction {

    public static ScalarFunction scalarFunction = ScalarFunctionImpl.create(ConcatFunction.class,
            "concat");
    public static final ConcatFunction INSTANCE = new ConcatFunction();

    public ConcatFunction() {
        super("concat", ReturnTypes.VARCHAR_2000, InferTypes.VARCHAR_1024, OperandTypes.SAME_VARIADIC, null, SqlFunctionCategory.STRING);
    }

    @Override
    public Expression implement(RexToLixTranslator translator, RexCall call, RexImpTable.NullAs nullAs) {
        Method method = Types.lookupMethod(ConcatFunction.class, "concat");
        return Expressions.call(method,translator.translateList(call.getOperands(),nullAs));
    }

    public static String concat(String... n) {
        StringBuilder sb = new StringBuilder();
        for (String s : n) {
            if (s == null) {
                return null;
            }
            sb.append(s);
        }

        return sb.toString();
    }
}