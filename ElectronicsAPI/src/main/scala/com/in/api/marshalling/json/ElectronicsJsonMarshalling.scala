package com.in.api.marshalling.json

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.in.api.ProductsResponse
import spray.json.DefaultJsonProtocol

/**
  * Provides marshalling of models to/from json
  */
trait ElectronicsJsonMarshalling extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val productsResponseFormat = jsonFormat1(ProductsResponse)

}
