package slick.admin

import java.sql.Types._
import scala.util.Try

import slick.ast.Type
import slick.jdbc.JdbcType

trait SQLValueParser {

  val dateForamt: String

  def parseValue(jdbcType: Type, value: String): Try[Any] = {
    val sqlType = jdbcType.asInstanceOf[JdbcType[_]].sqlType
    sqlType match {
      case TINYINT => Try(value.toByte)
      case SMALLINT => Try(value.toShort)
      case INTEGER => Try(value.toInt)
      case BIGINT => Try(value.toLong)
      case FLOAT => Try(value.toFloat)
      case DOUBLE => Try(value.toDouble)
      case NUMERIC | DECIMAL => Try(BigDecimal(value))
      case CHAR | VARCHAR | LONGVARCHAR => Try(value)
      case DATE | TIMESTAMP => Try(parseDate(value))
      case BIT | BOOLEAN => Try(value.toBoolean)
    }
  }

  private def parseDate(v: String) = {
    new java.text.SimpleDateFormat(dateForamt).parse(v)
  }
}
