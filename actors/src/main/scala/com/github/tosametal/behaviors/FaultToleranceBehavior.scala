package com.github.tosametal.behaviors

import akka.actor.typed.{Behavior, PostStop, PreRestart, SupervisorStrategy}
import akka.actor.typed.scaladsl.Behaviors
import com.github.tosametal.Counter

object FaultToleranceBehavior {
  sealed trait Command
  case object Hello extends Command
  case object Fail extends Command

  def apply(counter: Counter): Behavior[Command] = Behaviors.setup { context =>
    context.log.info("fault-tolerance-actor is created")
    Behaviors.supervise(ft(counter)).onFailure[RuntimeException](SupervisorStrategy.restart)
  }

  private def ft(counter: Counter): Behavior[Command] =
    Behaviors
      .receiveMessage[Command] {
        case Hello =>
          println(s"Hello! ${counter.getCount}")
          counter.increment()
          ft(counter)
        case Fail =>
          throw new RuntimeException("fault-tolerance-actor received `Fail` message")
          Behaviors.same
      }
      .receiveSignal {
        case (ctx, PreRestart) =>
          ctx.log.error("fault-tolerance-actor received PreRestart signal")
          Behaviors.same
        case (ctx, PostStop) =>
          ctx.log.error("fault-tolerance-actor received PostStop signal")
          Behaviors.same
      }
}
