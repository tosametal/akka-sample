package com.github.tosametal

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.tosametal.behaviors.MainBehavior
import com.github.tosametal.behaviors.MainBehavior.Command

trait HttpRoute {
  val actorSystem: ActorSystem[Command]

  def route: Route =
    path("hello") {
      get {
        parameter("num".as[String]) { numString =>
          actorSystem ! MainBehavior.LifecycleHello(numString)
          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "OK"))
        }
      }
    } ~ path("shutdown") {
      get {
        actorSystem ! MainBehavior.LifecycleShutdown
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "OK"))
      }
    } ~ path("sleep") {
      get {
        actorSystem ! MainBehavior.MailBoxSleepAndPrint
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "OK"))
      }
    } ~ path("prior") {
      get {
        actorSystem ! MainBehavior.MailBoxPrior
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "OK"))
      }
    }
}
