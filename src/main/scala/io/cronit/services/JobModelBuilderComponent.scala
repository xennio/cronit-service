package io.cronit.services

import io.cronit.common.SchedulerServiceException
import io.cronit.models._
import org.joda.time.DateTime

import scala.collection.Map

trait JobModelBuilderComponent {
  this: JsonServiceComponent =>
  val jobModelBuilderService: JobModelBuilderService

  class JobModelBuilderService {

    def from(content: String): JobModel = {
      val from: Map[String, Any] = jsonService.deserializeAsMap(content)
      val schedulerInfoMap = from("schedulerInfo").asInstanceOf[Map[String, String]]

      val schedulerInfo = schedulerInfoMap("type") match {
        case "CronScheduler" => CronScheduler(schedulerInfoMap("expression"))
        case "ScheduleOnce" => ScheduleOnce(DateTime.parse(schedulerInfoMap("runAt")).toDate)
      }

      val jobType = from("jobType")

      jobType match {
        case "Rest" => {
          new RestJobModel {
            override def url: String = from("url").toString

            override def method: String = from("method").toString

            override def body: Option[String] = from.get("body").asInstanceOf[Option[String]]

            override def headers: Option[Map[String, String]] = Some(from.get("headers").asInstanceOf[Option[List[Map[String, String]]]].
              getOrElse(List(Map[String, String]())).flatten.toMap)

            override def scheduleInfo: ScheduleInfo = schedulerInfo

            override def name: String = from("name").toString

            override def id: String = from("id").toString

            override def expectedStatus: Int = from("expectedStatus").asInstanceOf[Double].toInt

            override def group: String = from.get("group") match {
              case Some(g: String) => g
              case _ => super.group
            }

          }
        }
        case _ => throw new SchedulerServiceException(s"unsupported jobType : ${jobType}")
      }
    }
  }

}
