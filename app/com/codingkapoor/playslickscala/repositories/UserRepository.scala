package com.codingkapoor.playslickscala.repositories

import com.codingkapoor.playslickscala.models.{ User, UserTableDef }

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider

import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._

import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[UserRepositoryImpl])
trait UserRepository {
  def getAllUsers(): Future[Seq[User]]
  def getUser(id: Long): Future[Option[User]]
  def createUser(user: User): Future[String]
  def deleteUser(id: Long): Future[Int]
}

class UserRepositoryImpl @Inject() (dbConfigProvider: DatabaseConfigProvider) extends UserRepository {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  val users = UserTableDef.users

  lazy val usersCompiled = Compiled(users)

  def usersFilteredById(id: Rep[Long]) = users.filter(_.id === id)
  lazy val usersFilteredByIdCompiled = Compiled(usersFilteredById _)

  def getAllUsers(): Future[Seq[User]] = {
    dbConfig.db.run(usersCompiled.result)
  }

  def getUser(id: Long): Future[Option[User]] = {
    dbConfig.db.run(usersFilteredByIdCompiled(id).result.headOption)
  }

  def createUser(user: User): Future[String] = {
    dbConfig.db.run(usersCompiled += user).map(result => "User created successfully").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def deleteUser(id: Long): Future[Int] = {
    dbConfig.db.run(usersFilteredByIdCompiled(id).delete)
  }

}
