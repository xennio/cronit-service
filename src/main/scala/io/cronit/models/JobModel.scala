package io.cronit.models

trait JobModel {
  def id: String

  def name: String

  def group: String = "Default"

  def jobType: JobType.Value

  def scheduleInfo: ScheduleInfo
}