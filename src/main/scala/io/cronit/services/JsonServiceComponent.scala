package io.cronit.services

import io.cronit.common.SchedulerServiceException
import io.cronit.utils.Configuration

import scala.util.parsing.json.JSON

trait JsonServiceComponent {
  this: Configuration =>
  val jsonService: JsonService

  class JsonService {

    def deserializeAsMap(content: String): Map[String, Any] = {
      try {
        JSON.parseFull(content).get.asInstanceOf[Map[String, Any]]
      } catch {
        case t: Throwable => throw new SchedulerServiceException(s"Deserialize error ${content} cannot deserialize to Json", t)
      }
    }
  }

}