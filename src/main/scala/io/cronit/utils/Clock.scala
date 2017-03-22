package io.cronit.utils

import java.time.{ZoneId, ZonedDateTime}
import java.util.Date

import org.joda.time.{DateTime, DateTimeZone}

object ClockUtils {

  implicit class ZonedDateToJodaDateTime(zonedDateTime: ZonedDateTime) {
    def toJoda: DateTime = {
      val zone = DateTimeZone.forID(zonedDateTime.getZone().getId());
      new DateTime(zonedDateTime.toInstant().toEpochMilli(), zone);
    }
  }

}

object Clock {

  var isFrozen = false

  var setTime: Option[DateTime] = None


  def freeze(dateTime: DateTime): Unit = {
    isFrozen = true
    setTime = Some(dateTime)
  }

  def unfreeze() = {
    isFrozen = false
    setTime = None
  }

  def now(): Date = {
    if (isFrozen) setTime match {
      case Some(d) => d.toDate
      case None => DateTime.now().toLocalDateTime.toDate
    } else DateTime.now().toLocalDateTime.toDate
  }

  def asJodaTime: DateTime = {
    new DateTime(now)
  }

  def asZonedDateTime: ZonedDateTime = {
    val nowAsJoda = asJodaTime
    ZonedDateTime.of(nowAsJoda.getYear, nowAsJoda.getMonthOfYear, nowAsJoda.getDayOfMonth,
      nowAsJoda.getHourOfDay, nowAsJoda.getMinuteOfDay, nowAsJoda.getSecondOfMinute,
      nowAsJoda.getMillisOfSecond * 1000000, ZoneId.of(nowAsJoda.getZone.getID, ZoneId.SHORT_IDS))
  }
}
