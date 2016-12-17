package com.codingkapoor.playslickscala.models

import slick.driver.MySQLDriver.api._

case class User(id: Option[Long] = None, firstName: String, lastName: String, mobile: Long, email: String)

class UserTableDef(tag: Tag) extends Table[User](tag, "USER") {

  def id = column[Option[Long]]("ID", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("FIRST_NAME")
  def lastName = column[String]("LAST_NAME")
  def mobile = column[Long]("MOBILE")
  def email = column[String]("EMAIL")

  override def * =
    (id, firstName, lastName, mobile, email) <> (User.tupled, User.unapply)
}

object UserTableDef {
  val users = TableQuery[UserTableDef]
}
