package io.cronit.services

import io.cronit.utils.{Clock, DateUtils}
import org.scalatest.{FlatSpec, Matchers}

class CronExpressionServiceTest extends FlatSpec with Matchers {

  val cronExpressionComponent = new CronExpressionComponent() {
    override val cronExpressionService: CronExpressionService = new CronExpressionService
  }

  it should "get next execution time " in {
    Clock.freeze(DateUtils.localDate("10021981"))

    val nextRunningDate = cronExpressionComponent.cronExpressionService.getNextExecutionDate("* 1 * * *")
    nextRunningDate shouldBe Clock.asJodaTime.plusHours(1)

    Clock.unfreeze()
  }

  it should "get next monday execution date" in {

    Clock.freeze(DateUtils.localDate("22032017"))

    val nextRunningDate = cronExpressionComponent.cronExpressionService.getNextExecutionDate("30 1 * * 1")
    nextRunningDate shouldBe DateUtils.localDate("27032017").plusHours(1).plusMinutes(30)

    Clock.unfreeze()

  }

}
