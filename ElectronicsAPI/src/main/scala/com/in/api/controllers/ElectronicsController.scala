package com.in.api.controllers

import com.in.api.{EProducts, ProductsFilter, ProductsOrder, ProductsResponse}

import scala.concurrent.Future

/**
  * Controller of electronics related requests
  */
trait ElectronicsController {

  def products(filter: ProductsFilter, order: ProductsOrder, limit: Option[Int]): Future[ProductsResponse]

}
