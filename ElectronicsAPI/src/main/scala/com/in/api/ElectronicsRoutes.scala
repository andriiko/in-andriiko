package com.in.api

import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.in.api.controllers.ElectronicsController
import com.in.api.marshalling.json.ElectronicsJsonMarshalling

/**
  * Factory of routes
  */
object ElectronicsRoutes extends ElectronicsJsonMarshalling {

  def apply(ctrl: ElectronicsController): Route = {
    pathPrefix("electronics") {
      get {
        path("products") {
          parameters(
            'priceGreaterThan.as[Int].?,
            'titleKeyword.as[String].?,
            'asc.as[String].?,
            'desc.as[String].?,
            'primaryOrder.as[String].?,
            'limit.as[Int].?
          ) {
            (priceGreaterThan, titleKeyword, asc, desc, primaryOrder, limit) =>
              complete(OK, ctrl.products(
                ProductsFilter(
                  priceGreaterThan,
                  titleKeyword
                ),
                ProductsOrder(
                  asc,
                  desc,
                  OrderType(primaryOrder)
                ),
                limit
              ))
          }
        }
      }
    }
  }

}
