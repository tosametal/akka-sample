package com.github.tosametal.behaviors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.dispatch.ControlMessage

object MailBoxBehavior {
  sealed trait Command
  case object SleepAndPrint extends Command
  case object PriorPrint extends Command with ControlMessage

  def apply: Behavior[Command] = Behaviors.setup { _ =>
    Behaviors.receiveMessage {
      case SleepAndPrint =>
        Thread.sleep(3000)
        println(SleepAndPrint)
        Behaviors.same
      case PriorPrint =>
        println(PriorPrint)
        Behaviors.same
    }
  }
}
