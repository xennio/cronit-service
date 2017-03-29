package io.cronit.actors

import akka.actor.{ActorSystem, Props}
import akka.http.javadsl.model.HttpEntities
import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, Uri}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.testkit.{TestActorRef, TestKit}
import io.cronit.builder.RestJobModelBuilder
import io.cronit.models.CronScheduler
import org.mockito.ArgumentCaptor
import org.mockito.Matchers._
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}
import org.specs2.mock.Mockito

class RestTaskActorTest extends TestKit(ActorSystem("CronitTestActorSystem"))
  with FlatSpecLike with Matchers with BeforeAndAfterAll with Mockito {
  implicit lazy val materializer = ActorMaterializer(ActorMaterializerSettings(system))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  it should "call http end point with given parameters when body is not present" in {
    val mockHttp = mock[HttpExt]

    val restActor = TestActorRef.create(system, Props(new RestTaskActor() {
      override val http: HttpExt = mockHttp
    }))

    val restJobModel = RestJobModelBuilder.sampleRestJobWithScheduleOnce()
    restActor ! restJobModel

    val argumentCaptor = ArgumentCaptor.forClass(classOf[HttpRequest])

    there was one(mockHttp).singleRequest(argumentCaptor.capture, anyObject(), anyObject(), anyObject())(anyObject())

    val expectedRequest = argumentCaptor.getValue

    expectedRequest.method shouldEqual HttpMethods.GET
    expectedRequest.uri shouldEqual Uri(restJobModel.url)
    expectedRequest.headers.size shouldEqual 1
    expectedRequest.headers.head shouldEqual RawHeader("foo", "bar")
  }

  it should "call http end point with given parameters when request body is exists" in {
    val mockHttp = mock[HttpExt]

    val restActor = TestActorRef.create(system, Props(new RestTaskActor() {
      override val http: HttpExt = mockHttp
    }))

    val restJobModel = RestJobModelBuilder.
      aRestJobModelBuilder().
      method("GET").
      url("http://www.google.com").
      addHeader("foo", "bar").
      name("CronJob").
      expectedStatus(200).
      body("httpBody").
      scheduleInfo(new CronScheduler("* * * * *")).
      build

    restActor ! restJobModel
    val argumentCaptor = ArgumentCaptor.forClass(classOf[HttpRequest])

    there was one(mockHttp).singleRequest(argumentCaptor.capture, anyObject(), anyObject(), anyObject())(anyObject())

    val expectedRequest = argumentCaptor.getValue

    expectedRequest.method shouldEqual HttpMethods.GET
    expectedRequest.uri shouldEqual Uri(restJobModel.url)
    expectedRequest.headers.size shouldEqual 1
    expectedRequest.headers.head shouldEqual RawHeader("foo", "bar")
    expectedRequest.entity shouldEqual  HttpEntity(`application/json`, restJobModel.body.get)

  }
}