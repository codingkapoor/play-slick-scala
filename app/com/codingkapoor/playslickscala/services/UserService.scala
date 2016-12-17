package com.codingkapoor.playslickscala.services

import scala.concurrent.Future

import com.codingkapoor.playslickscala.models.User
import com.codingkapoor.playslickscala.repositories.UserRepository
import com.google.inject.ImplementedBy

import javax.inject.Inject

@ImplementedBy(classOf[UserServiceImpl])
trait UserService {
  def getAllUsers(): Future[Seq[User]]
  def getUser(id: Long): Future[Option[User]]
  def createUser(firstName: String, lastName: String, mobile: Long, email: String): Future[String]
  def deleteUser(id: Long): Future[Int]
}

class UserServiceImpl @Inject()(userRepository: UserRepository) extends UserService  {

  def getAllUsers(): Future[Seq[User]] = {
    userRepository.getAllUsers()
  }

  def getUser(id: Long): Future[Option[User]] = {
    userRepository.getUser(id)
  }

  def createUser(firstName: String, lastName: String, mobile: Long, email: String): Future[String] = {
    userRepository.createUser(User(firstName = firstName, lastName = lastName, mobile = mobile, email = email))
  }

  def deleteUser(id: Long): Future[Int] = {
    userRepository.deleteUser(id)
  }

}
