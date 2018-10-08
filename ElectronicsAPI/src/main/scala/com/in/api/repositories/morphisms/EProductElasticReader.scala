package com.in.api.repositories.morphisms

import com.in.api.EProduct
import com.sksamuel.elastic4s.{Hit, HitReader}

import scala.util.{Success, Try}

object EProductMorpshisms {

  implicit object EProductElasticReader extends HitReader[EProduct] {
    override def read(hit: Hit): Try[EProduct] = Success(hit.sourceAsMap.mapValues { _.toString } )
  }

}
