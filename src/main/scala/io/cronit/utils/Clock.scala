package io.cronit.utils

import java.time.{ZoneId, ZonedDateTime}

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

  def freeze(): Unit = {
    isFrozen = true
  }

  def freeze(dateTime: DateTime): Unit = {
    isFrozen = true
    setTime = Some(dateTime)
  }

  def unfreeze() = {
    isFrozen = false
    setTime = None
  }

  def now(): DateTime = {
    if (isFrozen) setTime match {
      case Some(d) => d
      case None => DateTime.now()
    } else DateTime.now()
  }

  def asZonedDateTime: ZonedDateTime = {
    ZonedDateTime.of(now.getYear, now.getMonthOfYear, now.getDayOfMonth,
      now.getHourOfDay, now.getMinuteOfDay, now.getSecondOfMinute,
      now.getMillisOfSecond * 1000000, ZoneId.of(now.getZone.getID, ZoneId.SHORT_IDS))
  }
}
