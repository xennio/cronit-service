package io.cronit.utils

import com.typesafe.config.ConfigFactory
import org.joda.time.DateTimeZone

import scala.util.Properties

trait Configuration {

  val env = Properties.propOrElse("env", "development")
  val config = ConfigFactory.load().getConfig(env)
  val jodaTimeZone = DateTimeZone.forID(config.getString("timezone"))

  def urlFor(path: String): String = {
    s"${config.getString("rootPath")}${path}"
  }
}

