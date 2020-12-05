package com.github.tosametal.behaviors

import akka.actor.typed.{Behavior, PostStop, PreRestart, SupervisorStrategy}
import akka.actor.typed.scaladsl.Behaviors

object FaultToleranceBehavior {
  sealed trait Command
  case object Hello extends Command
  case object Fail extends Command

  def apply: Behavior[Command] = Behaviors.setup { context =>
    context.log.info("fault-tolerance-actor is created")
    Behaviors.supervise(ft).onFailure[RuntimeException](SupervisorStrategy.restart)
  }

  private def ft: Behavior[Command] =
    Behaviors.receiveMessage[Command] {
      case Hello =>
        println("Hello!")
        Behaviors.same
      case Fail =>
        throw new RuntimeException("fault-tolerance-actor received `Fail` message")
        Behaviors.same
    }.receiveSignal {
      case (ctx, PreRestart) =>
        ctx.log.error("fault-tolerance-actor received PreRestart signal")
        Behaviors.same
      case (ctx, PostStop) =>
        ctx.log.error("fault-tolerance-actor received PostStop signal")
        Behaviors.same
    }
}
