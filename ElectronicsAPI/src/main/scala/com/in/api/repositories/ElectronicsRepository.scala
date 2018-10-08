package com.in.api.repositories

import com.in.api.{EProducts, ProductsFilter}

import scala.concurrent.Future

trait ElectronicsRepository {

  def products(productsFilter: ProductsFilter): Future[EProducts]

}
