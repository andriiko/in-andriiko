package com.in.api.controllers

import com.in.api.{EProducts, ProductsFilter, ProductsResponse}

import scala.concurrent.Future

/**
  * Controller of electronics related requests
  */
trait ElectronicsController {

  def products(filter: ProductsFilter): Future[ProductsResponse]

}
