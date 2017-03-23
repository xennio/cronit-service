package io.cronit.utils

import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}

class ClockTest extends FlatSpec with Matchers {

  it should " freeze time to specific datetime" in {

    Clock.freeze(DateTime.parse("10021981T12:30"))

    Clock.now() shouldEqual DateTime.parse("10021981T12:30")

    Clock.unfreeze()

  }

  it should " return current time when it is not frozen" in {
    val oldDate = DateTime.now().minusMinutes(1)
    Clock.now().isAfter(oldDate) shouldBe true
  }

  it should " return current time when is frozen but no specific date value set" in {
    Clock.freeze()

    val oldDate = DateTime.now().minusMinutes(1)
    Clock.now().isAfter(oldDate) shouldBe true

    Clock.unfreeze()
  }


}
