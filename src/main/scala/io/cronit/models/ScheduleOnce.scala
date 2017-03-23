package io.cronit.models

import org.joda.time.DateTime

case class ScheduleOnce(runAt: DateTime) extends ScheduleInfo
