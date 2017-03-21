package io.cronit.services

import io.cronit.common.SchedulerServiceException
import io.cronit.models.{CronScheduler, JobModel, RestJobModel, ScheduleOnce}
import io.cronit.utils.Configuration
import org.joda.time.DateTime
import org.mockito.Mockito.when
import org.scalatest.{FlatSpec, Matchers}
import org.specs2.mock.Mockito

class JobModelBuilderServiceTest extends FlatSpec with Matchers
  with JobModelBuilderComponent
  with JsonServiceComponent
  with Configuration
  with Mockito {

  override val jobModelBuilderService = new JobModelBuilderService()
  override val jsonService = mock[JsonService]

  behavior of "Job Model builder service"

  it should "create rest job model with CronScheduler from given Map when job type is rest" in {

    val jobMapRepresentation = Map("id" -> "jobId", "name" -> "jobName", "jobType" -> "Rest",
      "group" -> "executionGroup", "schedulerInfo" -> Map("expression" -> "* * * * *", "type" -> "CronScheduler"),
      "url" -> "http://cronscheduler.it", "method" -> "POST", "body" -> "jobBody", "expectedStatus" -> 301D,
      "headers" -> List(Map("foo" -> "bar")))

    when(jsonService.deserializeAsMap("valid job content json")).thenReturn(jobMapRepresentation)


    val jobModel: JobModel = jobModelBuilderService.from("valid job content json")

    jobModel shouldBe a[RestJobModel]
    jobModel.id shouldEqual "jobId"
    jobModel.name shouldEqual "jobName"
    jobModel.group shouldEqual "executionGroup"
    jobModel.scheduleInfo shouldBe a[CronScheduler]
    jobModel.scheduleInfo.asInstanceOf[CronScheduler].expression shouldEqual "* * * * *"

    val restJobModel = jobModel.asInstanceOf[RestJobModel]
    restJobModel.url shouldEqual "http://cronscheduler.it"
    restJobModel.body shouldEqual Some("jobBody")
    restJobModel.method shouldEqual "POST"
    restJobModel.expectedStatus shouldEqual 301
    restJobModel.headers shouldEqual Some(Map("foo" -> "bar"))
  }

  it should "create rest job model with ScheduleOnce with Default execution group from given Map when job type is rest and group is not defined" in {

    val jobMapRepresentation = Map("id" -> "jobId", "name" -> "jobName", "jobType" -> "Rest",
      "schedulerInfo" -> Map("runAt" -> "2016-10-30T00:00:00.000Z", "type" -> "ScheduleOnce"),
      "url" -> "http://cronscheduler.it", "method" -> "POST", "body" -> "jobBody", "expectedStatus" -> 301D,
      "headers" -> List(Map("foo" -> "bar")))

    when(jsonService.deserializeAsMap("valid job content json")).thenReturn(jobMapRepresentation)


    val jobModel: JobModel = jobModelBuilderService.from("valid job content json")


    jobModel shouldBe a[RestJobModel]
    jobModel.id shouldEqual "jobId"
    jobModel.name shouldEqual "jobName"
    jobModel.group shouldEqual "Default"
    jobModel.scheduleInfo shouldBe a[ScheduleOnce]
    jobModel.scheduleInfo.asInstanceOf[ScheduleOnce].runAt shouldEqual DateTime.parse("2016-10-30T00:00:00.000Z").toDate

    val restJobModel = jobModel.asInstanceOf[RestJobModel]
    restJobModel.url shouldEqual "http://cronscheduler.it"
    restJobModel.body shouldEqual Some("jobBody")
    restJobModel.method shouldEqual "POST"
    restJobModel.expectedStatus shouldEqual 301
    restJobModel.headers shouldEqual Some(Map("foo" -> "bar"))
  }


  it should "throw SchedulerServiceException when job type is not known" in {

    val jobMapRepresentation = Map("id" -> "jobId", "name" -> "jobName", "jobType" -> "UnknownType",
      "group" -> "executionGroup", "schedulerInfo" -> Map("runAt" -> "2016-10-30T00:00:00.000Z", "type" -> "ScheduleOnce"),
      "url" -> "http://cronscheduler.it", "method" -> "POST", "body" -> "jobBody", "expectedStatus" -> 301D,
      "headers" -> List(Map("foo" -> "bar")))

    when(jsonService.deserializeAsMap("not valid job type job content json")).thenReturn(jobMapRepresentation)

    intercept[SchedulerServiceException] {
      jobModelBuilderService.from("not valid job type job content json")
    }
  }
}
