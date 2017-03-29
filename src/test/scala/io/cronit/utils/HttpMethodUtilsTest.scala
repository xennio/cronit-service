package io.cronit.utils

import akka.http.scaladsl.model.HttpMethods
import io.cronit.utils.HttpMethodUtils.HttpMethodConverter
import org.scalatest.{FlatSpec, Matchers}

class HttpMethodUtilsTest extends FlatSpec with Matchers {

  it should "convert http method string to akka http method type" in {
    "GET".toHttpMethod shouldEqual HttpMethods.GET
    "POST".toHttpMethod shouldEqual HttpMethods.POST
    "PUT".toHttpMethod shouldEqual HttpMethods.PUT
    "DELETE".toHttpMethod shouldEqual HttpMethods.DELETE
    "PATCH".toHttpMethod shouldEqual HttpMethods.PATCH
  }
}