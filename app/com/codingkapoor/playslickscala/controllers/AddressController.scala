package com.codingkapoor.playslickscala.controllers

import com.codingkapoor.playslickscala.models.Address
import com.codingkapoor.playslickscala.services.{ AddressService, AddressServiceImpl }

import javax.inject.Inject
import com.google.inject.ImplementedBy

import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.mvc.{ Action, Controller }

import play.api.libs.json.{ Json, JsPath, Reads, Writes, JsSuccess, JsError }
import play.api.libs.json.Reads._

import play.api.libs.functional.syntax._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AddressController @Inject() (addressService: AddressService) extends Controller {

  implicit val deserializer: Reads[Address] = (
    (JsPath \ "city").read[String] and
    (JsPath \ "state").read[String] and
    (JsPath \ "zip").read[Long] and
    (JsPath \ "userId").readNullable[Long])(Address.apply(_: String, _: String, _: Long, _: Option[Long]))

  implicit val userWrites = new Writes[Address] {
    def writes(address: Address) = Json.obj(
      "id" -> address.id,
      "city" -> address.city,
      "state" -> address.state,
      "zip" -> address.zip)
  }

  def getAllUserAddresses(userId: Long) = Action.async {
    addressService.getAllUserAddresses(userId) map { address =>
      Ok(Json.toJson(address))
    }
  }

  def getUserAddress(userId: Long, addressId: Long) = Action.async {
    addressService.getUserAddress(addressId) map {
      case Some(address) => Ok(Json.toJson(address))
      case None          => NotFound
    }
  }

  def createAddress(userId: Long) = Action.async(parse.json) {
    implicit request =>
      request.body.validate[Address] match {
        case JsSuccess(createAddress, _) =>
          addressService.createAddress(createAddress.city, createAddress.state, createAddress.zip, createAddress.userId) map {
            res => Created
          }
        case JsError(errors) => Future(BadRequest)
      }
  }

  def deleteAddress(userId: Long, addressId: Long) = Action.async {
    addressService.deleteAddress(addressId) map {
      res => Ok(Json.toJson(res))
    }
  }
}
