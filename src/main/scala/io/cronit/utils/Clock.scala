package io.cronit.utils

import java.util.Date

import org.joda.time.DateTime

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

  def time(): Date = {
    if (isFrozen) setTime match {
      case Some(d) => d.toDate
      case None => DateTime.now().toLocalDateTime.toDate
    } else DateTime.now().toLocalDateTime.toDate
  }

  def asJodaTime: DateTime = {
    new DateTime(time)
  }
}
