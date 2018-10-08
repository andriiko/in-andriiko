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
            'titleKeyword.as[String].?
          ) {
            (priceGreaterThan, titleKeyword) =>
              complete(OK, ctrl.products(
                ProductsFilter(
                  priceGreaterThan,
                  titleKeyword
                )
              ))
          }
        }
      }
    }
  }

}
