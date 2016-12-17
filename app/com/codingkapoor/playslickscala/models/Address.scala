package com.codingkapoor.playslickscala.models

import slick.driver.MySQLDriver.api._

case class Address(id: Option[Long], city: String, state: String, zip: Long, userId: Option[Long])

object Address {

  def apply(city: String, state: String, zip: Long, userId: Option[Long]): Address =
    Address(None, city, state, zip, userId)

  def mapperTo(id: Option[Long], city: String, state: String, zip: Long, userId: Option[Long]) =
    apply(id, city, state, zip, userId)
}

class AddressTableDef(tag: Tag) extends Table[Address](tag, "ADDRESS") {

  def id = column[Option[Long]]("ID", O.PrimaryKey, O.AutoInc)
  def city = column[String]("CITY")
  def state = column[String]("STATE")
  def zip = column[Long]("ZIP")
  def userId = column[Option[Long]]("USER_ID")

  def user = foreignKey("USER_ID_FK", userId, UserTableDef.users)(_.id)

  override def * =
    (id, city, state, zip, userId) <> ((Address.mapperTo _).tupled, Address.unapply)
}

object AddressTableDef {
  val addresses = TableQuery[AddressTableDef]
}
