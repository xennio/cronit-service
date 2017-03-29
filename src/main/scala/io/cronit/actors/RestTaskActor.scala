package io.cronit.actors

import akka.actor.Actor
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest}
import io.cronit.models.RestJobModel
import io.cronit.services.ActorSystemService

class RestTaskActor() extends Actor {
  implicit lazy val materializer = ActorSystemService.materializer

  val http = Http(context.system)

  override def receive: Receive = {
    case rjm: RestJobModel => {
      val req = HttpRequest(HttpMethods.GET, rjm.url)
      http.singleRequest(req)
    }
  }
}
