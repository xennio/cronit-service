package io.cronit.services

import com.cronutils.model.CronType
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import io.cronit.utils.Clock
import io.cronit.utils.ClockUtils._

trait CronExpressionComponent {

  val cronExpressionService: CronExpressionService
  val cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX)

  class CronExpressionService {
    def getNextExecutionDate(expression: String) = {
      val cronParser = new CronParser(cronDefinition)
      val cron = cronParser.parse(expression)
      ExecutionTime.forCron(cron).nextExecution(Clock.asZonedDateTime).toJoda
    }
  }

}
