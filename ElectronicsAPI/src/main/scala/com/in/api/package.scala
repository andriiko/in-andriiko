package com.in

package object api {

  /**
    * Electronics product, which is a collection of it's attributes.
    * Didn't call it just ''Product'' to avoid ambiguity with Scala's Product
    */
  type EProduct = Map[String, String]

  /**
    * Collection of Products.
    */
  type EProducts = Seq[Map[String, String]]

}
