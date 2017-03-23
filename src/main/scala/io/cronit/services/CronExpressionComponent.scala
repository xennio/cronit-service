package io.cronit.services

import com.cronutils.model.CronType
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import io.cronit.utils.Clock
import io.cronit.utils.ClockUtils._
import org.joda.time.DateTime

import scala.concurrent.duration.{Duration, _}

trait CronExpressionComponent {

  val cronExpressionService: CronExpressionService
  val cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX)
  val cronParser = new CronParser(cronDefinition)

  class CronExpressionService {
    def getNextExecutionDate(expression: String) = {
      val cron = cronParser.parse(expression)
      ExecutionTime.forCron(cron).nextExecution(Clock.asZonedDateTime).toJoda
    }

    def getFiniteDurationFromNow(dateTime: DateTime) = {
      val now = Clock.now()
      val diff = dateTime.getMillis - now.getMillis
      Duration(diff, MILLISECONDS)
    }
  }

}