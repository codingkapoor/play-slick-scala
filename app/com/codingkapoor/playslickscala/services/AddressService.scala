package com.codingkapoor.playslickscala.services

import com.google.inject.ImplementedBy

import scala.concurrent.Future
import javax.inject.Inject

import com.codingkapoor.playslickscala.models.Address
import com.codingkapoor.playslickscala.repositories.AddressRepository

@ImplementedBy(classOf[AddressServiceImpl])
trait AddressService {
  def getAllUserAddresses(id: Long): Future[Seq[Address]]
  def getUserAddress(userId: Long, id: Long): Future[Option[Address]]
  def createAddress(city: String, state: String, zip: Long, userId: Option[Long]): Future[String]
  def deleteAddress(userId: Long, id: Long): Future[Int]
}

class AddressServiceImpl @Inject() (addressRepository: AddressRepository) extends AddressService {

  def getAllUserAddresses(id: Long): Future[Seq[Address]] = {
    addressRepository.getAllUserAddresses(id)
  }
  
  // TODO Verify if the `userId` specified as the path param is same as `userId` in the `Address`  
  def getUserAddress(userId: Long, id: Long): Future[Option[Address]] = {
    addressRepository.getAddress(id)
  }

  def createAddress(city: String, state: String, zip: Long, userId: Option[Long]): Future[String] = {
    addressRepository.createAddress(Address(city, state, zip, userId))
  }

  // TODO Verify if the `userId` specified as the path param is same as `userId` in the `Address`  
  def deleteAddress(userId: Long, id: Long): Future[Int] = {
    addressRepository.deleteAddress(id)
  }
}
