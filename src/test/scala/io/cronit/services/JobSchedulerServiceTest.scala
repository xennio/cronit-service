package io.cronit.services

import akka.actor.{ActorRef, ActorSystem, Scheduler}
import akka.testkit.{TestKit, TestProbe}
import io.cronit.builder.RestJobModelBuilder
import io.cronit.utils.{Clock, DateUtils}
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}
import org.specs2.mock.Mockito

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class JobSchedulerServiceTest extends TestKit(ActorSystem("CronitTestActorSystem")) with ActorSystemComponent with FlatSpecLike
  with Matchers
  with Mockito
  with JobSchedulerComponent
  with CronExpressionComponent
  with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  override val actorSystemService: ActorSystemService = new ActorSystemService {
    override val scheduler: Scheduler = mock[Scheduler]
  }
  override val jobSchedulerService: JobSchedulerService = new JobSchedulerService {
    override val restTaskActor: ActorRef = TestProbe().ref
  }
  override val cronExpressionService: CronExpressionService = mock[CronExpressionService]

  it should "calculate next execution delay when scheduling a task" in {
    Clock.freeze(DateUtils.toDate("20180202"))

    val restJob = RestJobModelBuilder.sampleRestJobWithCronScheduler
    when(cronExpressionService.getNextExecutionDate("* * * * *")).thenReturn(Clock.now)
    when(cronExpressionService.getFiniteDurationFromNow(Clock.now())).thenReturn(5 minutes)

    jobSchedulerService.scheduleTask(restJob)

    verify(actorSystemService.scheduler).scheduleOnce(5 minutes, jobSchedulerService.restTaskActor, restJob)
    Clock.unfreeze()
  }

  it should "calculate next execution delay when schedule once task" in {
    Clock.freeze(DateUtils.toDate("20180202"))

    val restJob = RestJobModelBuilder.sampleRestJobWithScheduleOnce
    when(cronExpressionService.getFiniteDurationFromNow(Clock.now())).thenReturn(15 minutes)

    jobSchedulerService.scheduleTask(restJob)

    verify(actorSystemService.scheduler).scheduleOnce(15 minutes, jobSchedulerService.restTaskActor, restJob)
    Clock.unfreeze()
  }
}