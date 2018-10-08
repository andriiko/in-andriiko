package com.in.api.repositories.morphisms

import com.in.api.{ASC, DESC, EProduct, ProductsOrder}
import com.sksamuel.elastic4s.searches.sort.{FieldSort, Sort}
import com.sksamuel.elastic4s.{Hit, HitReader}

import scala.util.{Success, Try}

object EProductMorpshisms {

  implicit object EProductElasticReader extends HitReader[EProduct] {
    override def read(hit: Hit): Try[EProduct] = Success(hit.sourceAsMap.mapValues { _.toString } )
  }

  implicit def OrderToSearchRequest(o: ProductsOrder): Iterable[Sort] = {
    o match {
      case ProductsOrder(Some(asc), None, _) => FieldSort(asc) :: Nil
      case ProductsOrder(None, Some(desc), _) => FieldSort(desc).desc() :: Nil
      case ProductsOrder(Some(asc), Some(desc), Some(ASC)) => FieldSort(asc) :: FieldSort(desc).desc() :: Nil
      case ProductsOrder(Some(asc), Some(desc), Some(DESC)) => FieldSort(desc).desc() :: FieldSort(asc) :: Nil
      case _ => Nil
    }
  }

}
