package com.in.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.in.api.controllers.ConcreteElectronicsController
import com.in.api.repositories.ElectronicsElasticRepository

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.io.StdIn
import scala.util.{Failure, Success, Try}

object APIWebServer extends App {

  override def main(args: Array[String]) {
    implicit val system = ActorSystem("api-actor-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val config = APIConfig

    // Assembly concrete controllers and repositories (elastic):
    val futureRoutes: Future[Route] =
      ElectronicsElasticRepository(APIConfig.BootstrapFileName).map { repo =>
        ElectronicsRoutes(
          ConcreteElectronicsController(repo)
        )
      }

    println(s"Initialising the server...")
    Await.ready(futureRoutes, Duration.Inf).value.get match {
      case Success(routes) => {
        val bindingFuture = Http().bindAndHandle(routes, config.Interface, config.Port)
        println(s"Server online at http://${config.Interface}:${config.Port}/\nPress RETURN to stop...")
        StdIn.readLine() // let it run until user presses return
        bindingFuture
          .flatMap(_.unbind()) // trigger unbinding from the port
          .onComplete(_ => system.terminate()) // and shutdown when done
      }
      case Failure(ex) => {
        println(s"Server couldn't start due to the following error:\n${ex.getMessage}")
      }
    }


  }

}
