package com.in.api

/**
  * Describes how products should be ordered.
  * */
case class ProductsOrder (

  ascField: Option[String],

  descField: Option[String],

  primaryOrder: Option[OrderType]

)

sealed trait OrderType
object ASC extends OrderType
object DESC extends OrderType

object OrderType {
  def apply(tpe: Option[String]): Option[OrderType] = {
    tpe match {
      case Some("asc") => Some(ASC)
      case Some("desc") => Some(DESC)
      case _ => None
    }
  }
}
