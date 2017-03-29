package io.cronit.actors

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model.{HttpMethods, HttpRequest}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.testkit.{TestActorRef, TestKit}
import io.cronit.builder.RestJobModelBuilder
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}
import org.specs2.mock.Mockito

class RestTaskActorTest extends TestKit(ActorSystem("CronitTestActorSystem"))
  with FlatSpecLike with Matchers with BeforeAndAfterAll with Mockito {
  implicit lazy val materializer = ActorMaterializer(ActorMaterializerSettings(system))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  it should "call http end point with given parameters " in {
    val mockHttp = mock[HttpExt]

    val restActor = TestActorRef.create(system, Props(new RestTaskActor() {
      override val http: HttpExt = mockHttp
    }))

    val restJobModel = RestJobModelBuilder.sampleRestJobWithScheduleOnce()
    restActor ! restJobModel

    verify(mockHttp).singleRequest(HttpRequest(HttpMethods.GET, restJobModel.url))
  }
}