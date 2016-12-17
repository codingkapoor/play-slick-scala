package com.codingkapoor.playslickscala.controllers

import com.codingkapoor.playslickscala.models.User
import com.codingkapoor.playslickscala.services.UserService

import javax.inject.Inject

import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.{ Json, JsPath, Reads, Writes, JsSuccess, JsError }
import play.api.libs.json.Reads._

import play.api.mvc.{ Action, Controller }
import play.api.libs.functional.syntax._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserController @Inject() (userService: UserService) extends Controller {

  implicit val userFromat = Json.format[User]

  implicit val userWrites: Writes[User] = (
    (JsPath \ "id").write[Option[Long]] and
    (JsPath \ "firstName").write[String] and
    (JsPath \ "lastName").write[String] and
    (JsPath \ "mobile").write[Long] and
    (JsPath \ "email").write[String])(unlift(User.unapply))

  def getAllUsers() = Action.async {
    userService.getAllUsers() map { user =>
      Ok(Json.toJson(user))
    }
  }

  def getUser(id: Long) = Action.async {
    userService.getUser(id) map {
      case Some(user) => Ok(Json.toJson(user))
      case None       => NotFound
    }
  }

  def createUser() = Action.async(parse.json) {
    implicit request =>
      request.body.validate[User] match {
        case JsSuccess(createUser, _) =>
          userService.createUser(createUser.firstName, createUser.lastName, createUser.mobile, createUser.email) map {
            res => Created
          }
        case JsError(errors) => Future(BadRequest)
      }
  }

  def deleteUser(id: Long) = Action.async {
    userService.deleteUser(id) map { res =>
      Ok(Json.toJson(res))
    }
  }

}
