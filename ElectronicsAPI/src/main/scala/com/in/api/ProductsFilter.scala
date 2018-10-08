package com.in.api

/**
  * Describes how products should be filtered.
  * N.B. Certainly more sophisticated filter should be developed, which could nicely handle >, >= <, <= conditions.
  * */
case class ProductsFilter(
  priceGreaterThan: Option[Int]
)
