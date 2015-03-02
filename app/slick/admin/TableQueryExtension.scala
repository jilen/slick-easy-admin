package slick.admin

import java.sql.Types._
import scala.language.existentials
import scala.util.Try
  import slick.ast._
import slick.dbio._
import slick.jdbc._
import slick.lifted._

trait TableQueryExtension extends SQLValueParser {

  implicit class TableQueryOps[E <: AbstractTable[_]](val query: TableQuery[E]) {

    lazy val table: E = {
      val ShapedValue(t, _)  = query.shaped
      t
    }

    lazy val tableName: String = {
      table.tableName
    }

    lazy val fieldInfos: Map[String, FieldSymbol] = {
      table.create_*.map { f =>
        f.name -> f
      }.toMap
    }

    lazy val primaryKeysFields: Map[String, FieldSymbol] = {
      val fs = for {
        PrimaryKey(_, colums) <- table.primaryKeys
        c <- colums
      } yield {
        val name = c.getDumpInfo.name
        val symbol = fieldInfos(name)
        name -> symbol
      }
      fs.toMap
    }

    def updateField(name: String)(pk: Map[String, String],  value: String):DBIOAction[Int, NoStream, Nothing] = {
      import StaticQuery._
      val part = u + s"update $tableName set " +? name + "=" +? value + " where "
      val query = parsePrimaryKeys(pk).foldLeft(part) { (q, f) =>
        appendCond(q, f) + ","
      }
      new StaticQueryAction(query)
    }

    def parsePrimaryKeys(pk: Map[String, String]): Map[String, Any] = {
      pk.map {
        case (k, v) =>
          k -> parseValue(primaryKeysFields(k).tpe, v)
      }
    }

    private def appendCond[P, R](q: StaticQuery[P, R], f: (String, Any)) = {
      val (name, value) = f
      value match {
        case v: BigDecimal =>
          q +? name + " = " +? v
        case v: Byte =>
          q +? name + " = " +? v
        case v: java.sql.Date =>
          q +? name + " = " +? v
        case v: Double =>
          q +? name + " = " +? v
        case v: Float =>
          q +? name + " = " +? v
        case v: Int =>
          q +? name + " = " +? v
        case v: Long =>
          q +? name + " = " +? v
        case v: Short =>
          q +? name + " = " +? v
        case v: String =>
          q +? name + " = " +? v
        case v: java.sql.Time =>
          q +? name + " = " +? v
        case v: java.sql.Timestamp =>
          q +? name + " = " +? v
      }
    }

  }

}
