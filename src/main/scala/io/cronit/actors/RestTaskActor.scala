package io.cronit.actors

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.MediaTypes._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpEntity, HttpRequest}
import io.cronit.models.RestJobModel
import io.cronit.services.ActorSystemService
import io.cronit.utils.HttpMethodUtils.HttpMethodConverter

class RestTaskActor() extends Actor with ActorLogging {
  implicit lazy val materializer = ActorSystemService.materializer

  val http = Http(context.system)

  override def receive: Receive = {

    case rjm: RestJobModel => {

      val headers = rjm.headers.getOrElse(Map[String, String]()).map { each => RawHeader(each._1, each._2) }.to[List]

      rjm.body match {
        case Some(s) => http.singleRequest(HttpRequest(rjm.method.toHttpMethod, rjm.url, headers, HttpEntity(`application/json`, s)))
        case _ => http.singleRequest(HttpRequest(rjm.method.toHttpMethod, rjm.url, headers))
      }

    }
  }
}
