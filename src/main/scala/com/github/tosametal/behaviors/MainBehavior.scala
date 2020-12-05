package com.github.tosametal.behaviors

import akka.actor.typed.{Behavior, ChildFailed, MailboxSelector}
import akka.actor.typed.scaladsl.Behaviors

object MainBehavior {
  sealed trait Command
  case class LifecycleHello(numString: String) extends Command
  case object LifecycleShutdown extends Command
  case object MailBoxSleepAndPrint extends Command
  case object MailBoxPrior extends Command
  case object HelloFaultTolerance extends Command
  case object FailFaultTolerance extends Command

  def apply: Behavior[Command] = Behaviors.setup { context =>
    val lifecycleActor = context.spawn(LifecycleBehavior.apply, "lifecycle-actor")
    val mailBoxBehavior = context.spawn(
      MailBoxBehavior.apply,
      "mailbox-behavior",
      MailboxSelector.fromConfig("prior")
    )
    val faultToleranceActor = context.spawn(FaultToleranceBehavior.apply, "fault-tolerance-actor")

    context.watch(lifecycleActor)
    context.watch(faultToleranceActor)

    Behaviors
      .receiveMessage[Command] {
        case LifecycleHello(numString) =>
          lifecycleActor ! LifecycleBehavior.Hello(numString)
          Behaviors.same
        case LifecycleShutdown =>
          lifecycleActor ! LifecycleBehavior.Shutdown
          Behaviors.same
        case MailBoxSleepAndPrint =>
          mailBoxBehavior ! MailBoxBehavior.SleepAndPrint
          Behaviors.same
        case MailBoxPrior =>
          mailBoxBehavior ! MailBoxBehavior.PriorPrint
          Behaviors.same
        case HelloFaultTolerance =>
          faultToleranceActor ! FaultToleranceBehavior.Hello
          Behaviors.same
        case FailFaultTolerance =>
          faultToleranceActor ! FaultToleranceBehavior.Fail
          Behaviors.same
      }
      .receiveSignal { case (ctx, ChildFailed((ref, throwable))) =>
        ctx.log.error(s"Failed child actor: ${ref.path}", throwable.getCause)
        Behaviors.same
      }
  }
}
