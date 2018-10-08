package com.in.api.repositories

import com.in.api.{EProduct, EProducts, ProductsFilter, ProductsOrder}
import com.sksamuel.elastic4s.embedded.LocalNode
import resource.managed

import scala.concurrent.Future
import com.sksamuel.elastic4s.http._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.annotation.tailrec
import scala.io.Codec
import com.in.api.repositories.morphisms.EProductMorpshisms._


class ElectronicsElasticRepository private(client: ElasticClient) extends ElectronicsRepository {
  import ElectronicsElasticRepository._

  private val defaultLimit = 100

  def products(productsFilter: ProductsFilter, order: ProductsOrder, limit: Option[Int]): Future[EProducts] = {
    import com.sksamuel.elastic4s.http.ElasticDsl._

    productsFilter match {
      case ProductsFilter(Some(priceGreaterThan), None) =>
        client.execute {
          search(idx).query { rangeQuery(Fields.ListPrice).gt(priceGreaterThan) }.sortBy(order).size(limit.getOrElse(defaultLimit))
        }.map { _.result.to[EProduct] }
      case ProductsFilter(None, Some(titleKeyword)) =>
        client.execute {
          search(idx).query { termQuery(Fields.Title, titleKeyword) }.sortBy(order).size(limit.getOrElse(defaultLimit))
        }.map { _.result.to[EProduct] }
      case _ => client.execute {
        search(idx).sortBy(order).size(limit.getOrElse(defaultLimit))
      }.map { _.result.to[EProduct] }
    }
  }
}

object ElectronicsElasticRepository {

  private val idx = "electronics"
  private val tpe = "product"

  private object Fields {
    val Title = "Title"
    val ListPrice = "ListPrice"
    val EAN = "EAN"
  }

  def apply(bootstrapFileName: String): Future[ElectronicsElasticRepository] = {

    // Should be able to point to custom host:port, but don't bother for now.
    // Create temp elastic node just for the demo.
    val localNode = LocalNode(idx, "/tmp/datapath")
    implicit val client = localNode.client(shutdownNodeOnClose = true)

    bootstrapElastic(bootstrapFileName) map { _ => new ElectronicsElasticRepository(client) }

  }

  private def bootstrapElastic(bootstrapFileName: String)(implicit client: ElasticClient): Future[Unit] = {
    for {
      _ <- prepareElasticIndex()
      products <- readFromFile(bootstrapFileName)
      _ <- writeToElastic(products)
    } yield ()
  }

  private def prepareElasticIndex()(implicit client: ElasticClient): Future[Unit] = {
    import com.sksamuel.elastic4s.http.ElasticDsl._
    client.execute {
      createIndex(idx).mappings(
        mapping(tpe).fields(
          floatField(Fields.ListPrice),
          longField(Fields.EAN)
        )
      )
    } map { _ => () }
  }

  private def readFromFile(fileName: String)(implicit client: ElasticClient): Future[EProducts] = {
    val managedResult = for (bufferedSource <- managed(scala.io.Source.fromResource(fileName)(Codec("ISO-8859-1")))) yield {
      collectProducts(bufferedSource.getLines(), Nil)
    }

    managedResult.toFuture.flatten
  }

  @tailrec
  private def collectProducts(lines: Iterator[String], productsAcc: EProducts): Future[EProducts] = {
    if(!lines.hasNext) {
      Future.successful(productsAcc)
    } else {

      val product = Map(
        lines
          .dropWhile(line => line.isEmpty || line.startsWith("ITEM"))
          .takeWhile(line => !line.isEmpty)
          .filter { _.contains("=") }
          .map { line => {
            val kv = line.split("=").map { _.trim() }
            kv(0) -> (if(kv(0) == "ListPrice") kv(1).substring(kv(1).lastIndexOf('$')+1) else kv(1))
          }
          }.toList:_*)
      collectProducts(lines, productsAcc :+ product)
    }
  }

  private def writeToElastic(products: EProducts)(implicit client: ElasticClient): Future[Unit] = Future {
    import com.sksamuel.elastic4s.http.ElasticDsl._
    products.foreach { product =>
      client.execute {
        indexInto(idx / tpe)
          .fields(product)
      }
    }
  }

}
