package controllers

import play.api._
import play.api.mvc._
import slick.lifted.{TableQuery, AbstractTable}

class TableAdmin[E <: AbstractTable[_]](a: TableQuery[E]) extends Controller {

}
