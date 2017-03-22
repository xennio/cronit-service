package io.cronit.models

import org.scalatest.{FlatSpec, Matchers}

class RestJobModelTest extends FlatSpec with Matchers {

  it should "Rest job Type when instance is RestJobModel " in {
    val restJobModel = new RestJobModel {
      override def url = ???

      override def method = ???

      override def body = ???

      override def expectedStatus = ???

      override def headers = ???

      override def scheduleInfo = ???

      override def name = ???

      override def id = ???
    }

    restJobModel.jobType shouldEqual JobType.Rest

  }

}
