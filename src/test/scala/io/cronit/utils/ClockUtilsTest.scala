package io.cronit.utils

import java.time.{ZoneId, ZonedDateTime}

import io.cronit.utils.ClockUtils._
import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}

class ClockUtilsTest extends FlatSpec with Matchers {

  it should "convert zonedDate to JodaDate " in {
    val zonedDateTime = ZonedDateTime.of(2017, 1, 16, 8, 31, 22, 0, ZoneId.of("UTC"))
    zonedDateTime.toJoda shouldEqual DateTime.parse("2017-01-16T08:31:22+00:00")
  }
}
