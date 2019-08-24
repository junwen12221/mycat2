package cn.lightfish.sql.ast;

import io.mycat.schema.MycatColumnDefinition;
import java.util.List;

public abstract class AbsractExecutor implements Executor {

  protected final MycatColumnDefinition[] columnList;

  public AbsractExecutor(MycatColumnDefinition[] columnList) {
    this.columnList = columnList;
  }

  @Override
  public MycatColumnDefinition[] columnDefList() {
    return columnList;
  }
}