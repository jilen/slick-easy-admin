package slick.admin

import slick.jdbc._
import slick.jdbc.StaticQuery._

class StaticQueryAction[P, R](query: StaticQuery[Unit, R])
    extends SimpleJdbcAction[R](StaticQueryAction.f(query)) {
}

object StaticQueryAction {

  def f[P, R](query: StaticQuery[Unit, R]) = (ctx: JdbcBackend#Context) => {
    query.first(ctx.session)
  }
}
