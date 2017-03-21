package io.cronit.services

import io.cronit.common.SchedulerServiceException
import io.cronit.utils.Configuration
import org.scalatest.{FlatSpec, Matchers}
import org.specs2.mock.Mockito

class JsonServiceTest extends FlatSpec with Matchers
  with JsonServiceComponent
  with Configuration
  with Mockito {

  behavior of "Json Service"


  override val jsonService: JsonService = new JsonService

  it should "deserialize as Map[String,Any] when content is valid Json string" in {
    val jsonToConvert = "{\"foo\":\"bar\",\"foo2\":2,\"foo3\":[{\"key\":\"value\"}]}"
    val jsonMap = new JsonService().deserializeAsMap(jsonToConvert)

    jsonMap("foo").toString shouldEqual "bar"
    jsonMap("foo2").asInstanceOf[Double] shouldEqual 2D
    jsonMap("foo3").asInstanceOf[List[Map[String, Any]]] shouldEqual List(Map("key" -> "value"))
  }

  it should "throw SchedulerServiceException when content is not valid Json string" in {
    val jsonToConvert = "not valid json"
    intercept[SchedulerServiceException] {
      new JsonService().deserializeAsMap(jsonToConvert)
    }
  }

}
