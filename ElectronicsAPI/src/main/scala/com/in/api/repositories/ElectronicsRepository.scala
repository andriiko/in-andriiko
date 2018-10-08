package com.in.api.repositories

import com.in.api.{EProducts, ProductsFilter, ProductsOrder}

import scala.concurrent.Future

trait ElectronicsRepository {

  def products(productsFilter: ProductsFilter, order: ProductsOrder, limit: Option[Int]): Future[EProducts]

}
