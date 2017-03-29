package io.cronit.services

import akka.actor.Props
import io.cronit.actors.RestTaskActor
import io.cronit.models.{CronScheduler, JobModel, ScheduleOnce}
import io.cronit.utils.Clock
import org.joda.time.DateTime

import scala.concurrent.ExecutionContext.Implicits.global

trait JobSchedulerComponent {
  this: ActorSystemComponent with CronExpressionComponent =>
  val jobSchedulerService: JobSchedulerService

  class JobSchedulerService {
    val restTaskActor = actorSystemService.actorSystem.actorOf(Props[RestTaskActor])

    def scheduleTask(jobModel: JobModel): Unit = {
      var executionDate: DateTime = Clock.now()
      jobModel.scheduleInfo match {
        case cs: CronScheduler => {
          executionDate = cronExpressionService.getNextExecutionDate(cs.expression)
        }
        case so: ScheduleOnce => {
          executionDate = so.runAt
        }
      }
      val delay = cronExpressionService.getFiniteDurationFromNow(executionDate)
      actorSystemService.scheduler.scheduleOnce(delay, restTaskActor, jobModel)
    }
  }

}