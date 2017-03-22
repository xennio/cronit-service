package io.cronit.utils

import org.joda.time.DateTimeZone
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class ConfigurationTest extends FlatSpec with Matchers with BeforeAndAfter {

  after {
    System.setProperty("env", "development")
  }

  "It" should " load 'development' environment variables when env did not defined " in {
    val configuration = new Configuration {}
    configuration.config.getString("environment") shouldEqual "development"
  }

  "It" should " load 'specific' environment variables when env is defined " in {
    System.setProperty("env", "qa")
    val configuration = new Configuration {}
    configuration.config.getString("environment") shouldEqual "qa"
  }

  "It" should " return GMT timezone from config" in {
    System.setProperty("env", "qa")
    val configuration = new Configuration {}
    configuration.jodaTimeZone shouldEqual DateTimeZone.forID("GMT")
  }

  "It" should " return path with root" in {
    val configuration = new Configuration {}
    configuration.urlFor("/path") shouldEqual "http://localhost:8888/path"
  }
}