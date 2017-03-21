package io.cronit.models

import scala.collection.Map

trait RestJobModel extends JobModel {

  override def jobType = JobType.Rest

  def method: String

  def url: String

  def headers: Option[Map[String, String]]

  def body: Option[String]

  def expectedStatus: Int

}
