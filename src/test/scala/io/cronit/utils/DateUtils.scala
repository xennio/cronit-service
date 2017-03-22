package io.cronit.utils

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, ISODateTimeFormat}

object DateUtils {
  val localDateFormat = DateTimeFormat.forPattern("ddMMyyyy")

  def localDate(date: String): DateTime = {
    localDateFormat.parseDateTime(date)
  }

  def toDate(date: String): DateTime = {
    ISODateTimeFormat.dateParser().parseDateTime(date)
  }


}
