package com.github.tosametal

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.Http
import com.github.tosametal.behaviors.MainBehavior

object Main extends App with HttpRoute {
  override val actorSystem: ActorSystem[MainBehavior.Command] = ActorSystem(MainBehavior.apply, "akka-sample")
  implicit val classicActorSystem = actorSystem.toClassic
  implicit val ec = classicActorSystem.dispatcher
  private val bindingFuture = Http().bindAndHandle(route, "localhost", 8765)
  sys.addShutdownHook {
    bindingFuture.flatMap(_.unbind()).onComplete(_ => actorSystem.terminate())
  }
}
