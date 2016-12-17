package com.codingkapoor.playslickscala.repositories

import com.codingkapoor.playslickscala.models.{ Address, AddressTableDef }

import play.api.db.slick.DatabaseConfigProvider

import slick.driver.MySQLDriver.api._
import slick.driver.JdbcProfile

import javax.inject.Inject
import com.google.inject.ImplementedBy

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@ImplementedBy(classOf[AddressRepositoryImpl])
trait AddressRepository {
  def getAllUserAddresses(id: Long): Future[Seq[Address]] 
  def getAddress(id: Long): Future[Option[Address]]
  def createAddress(address: Address): Future[String]
  def deleteAddress(id: Long): Future[Int]
}

class AddressRepositoryImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends AddressRepository {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  val addresses = AddressTableDef.addresses

  def getAllUserAddresses(id: Long): Future[Seq[Address]] = {
    dbConfig.db.run(addresses.filter(_.userId === id).result)
  }

  def getAddress(id: Long): Future[Option[Address]] = {
    dbConfig.db.run(addresses.filter(_.id === id).result.headOption)
  }

  def createAddress(address: Address): Future[String] = {
    dbConfig.db.run(addresses += address).map(result => "Address created successfully").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def deleteAddress(id: Long): Future[Int] = {
    dbConfig.db.run(addresses.filter(_.id === id).delete)
  }
}
