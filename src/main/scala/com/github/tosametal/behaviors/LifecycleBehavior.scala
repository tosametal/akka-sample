package com.github.tosametal.behaviors

import akka.actor.typed.{Behavior, PostStop}
import akka.actor.typed.scaladsl.Behaviors

object LifecycleBehavior {
  sealed trait Command
  case class Hello(numString: String) extends Command
  case object Shutdown extends Command

  def apply: Behavior[Command] = Behaviors.setup { context =>
    context.log.info("lifecycle actor is created.")

    Behaviors
      .receiveMessage[Command] {
        case Hello(numString) =>
          context.log.info("num is " + numString.toInt)
          Behaviors.same
        case Shutdown =>
          Behaviors.stopped { () => context.log.error("shutdown") }
      }
      .receiveSignal { case (ctx, PostStop) =>
        ctx.log.error(s"Receive signal: $PostStop")
        Behaviors.same
      }
  }
}
