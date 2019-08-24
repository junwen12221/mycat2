package cn.lightfish.sql.ast.numberExpr;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LongConstExpr implements LongExpr {

  final Long value;

  public Long getValue() {
    return value;
  }
}