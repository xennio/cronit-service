package io.cronit.utils

import akka.http.scaladsl.model.{HttpMethod, HttpMethods}

object HttpMethodUtils {

  implicit class HttpMethodConverter(method: String) {
    def toHttpMethod: HttpMethod = {
      val methodToUpper = method.toUpperCase
      methodToUpper match {
        case "GET" => HttpMethods.GET
        case "POST" => HttpMethods.POST
        case "PUT" => HttpMethods.PUT
        case "DELETE" => HttpMethods.DELETE
        case "PATCH" => HttpMethods.PATCH
      }
    }
  }

}
