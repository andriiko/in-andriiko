package com.in.api.controllers

import com.in.api.{EProducts, ProductsFilter, ProductsResponse}
import com.in.api.repositories.ElectronicsRepository
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Here controller just proxies calls to a 'repo', but
  * in reality it holds other responsibilities, such as checking user rights etc.
  */
class ConcreteElectronicsController private(repo: ElectronicsRepository) extends ElectronicsController {

  def products(filter: ProductsFilter): Future[ProductsResponse] = repo.products(filter) map ProductsResponse

}

object ConcreteElectronicsController {

  def apply(repo: ElectronicsRepository): ConcreteElectronicsController = new ConcreteElectronicsController(repo)

}
