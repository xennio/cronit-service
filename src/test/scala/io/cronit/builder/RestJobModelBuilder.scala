package io.cronit.builder

import io.cronit.models.{CronScheduler, RestJobModel, ScheduleInfo, ScheduleOnce}
import io.cronit.utils.Clock

class RestJobModelBuilder {

  var method: String = ""
  var url: String = ""
  var body: String = ""
  var expectedStatus: Int = 200
  var headers: Map[String, String] = Map[String, String]()
  var name: String = ""
  var id: String = ""
  var scheduleInfo: ScheduleInfo = new ScheduleInfo {}

  def addHeader(key: String, value: String): RestJobModelBuilder = {
    headers + (key -> value)
    this
  }

  def method(method: String): RestJobModelBuilder = {
    this.method = method
    this
  }

  def url(url: String): RestJobModelBuilder = {
    this.url = url
    this
  }

  def scheduleInfo(scheduleInfo: ScheduleInfo): RestJobModelBuilder = {
    this.scheduleInfo = scheduleInfo
    this
  }

  def expectedStatus(expectedStatus: Int): RestJobModelBuilder = {
    this.expectedStatus = expectedStatus
    this
  }

  def name(name: String): RestJobModelBuilder = {
    this.name = name
    this
  }

  def body(body: String): RestJobModelBuilder = {
    this.body = body
    this
  }

  def id(id: String): RestJobModelBuilder = {
    this.id = id
    this
  }


  def build: RestJobModel = {
    val builder = this
    new RestJobModel {
      override def url = builder.url

      override def method = builder.method

      override def body = Some(builder.body)

      override def expectedStatus = builder.expectedStatus

      override def headers = Some(builder.headers)

      override def scheduleInfo = builder.scheduleInfo

      override def name = builder.name

      override def id = builder.id
    }
  }

}

object RestJobModelBuilder {
  def aRestJobModelBuilder(): RestJobModelBuilder = new RestJobModelBuilder()

  def sampleRestJobWithCronScheduler(): RestJobModel = RestJobModelBuilder.
    aRestJobModelBuilder().
    method("GET").
    url("http://www.google.com").
    addHeader("foo", "bar").
    name("CronJob").
    expectedStatus(200).
    scheduleInfo(new CronScheduler("* * * * *")).
    build

  def sampleRestJobWithScheduleOnce(): RestJobModel = RestJobModelBuilder.
    aRestJobModelBuilder().
    method("GET").
    url("http://www.google.com").
    addHeader("foo", "bar").
    name("CronJob").
    expectedStatus(200).
    scheduleInfo(new ScheduleOnce(Clock.now())).
    build

}
