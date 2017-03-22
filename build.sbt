name := """cronit-service"""
version := "1.0"
scalaVersion := "2.11.8"
sbtPlugin := true


lazy val versions = new {
  val akka = "2.4.11"
  val logback = "1.1.7"
  val akkaHttp = "10.0.4"
  val joda = "2.9.4"

  val mockitoCore = "1.9.5"
  val scalaTest = "2.2.3"
  val specs2 = "2.3.12"
  val scalaMockTestSupport = "3.2.2"
  val cronUtils = "5.0.5"
}


libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % versions.akka,
    "com.typesafe.akka" %% "akka-slf4j" % versions.akka,
    "com.typesafe.akka" %% "akka-http" % versions.akkaHttp,

    "ch.qos.logback" % "logback-classic" % versions.logback,

    "joda-time" % "joda-time" % versions.joda,

    "com.cronutils" % "cron-utils" % versions.cronUtils,

    "org.mockito" % "mockito-core" % versions.mockitoCore % "test",
    "org.scalatest" %% "scalatest" % versions.scalaTest % "test",
    "org.specs2" %% "specs2" % versions.specs2 % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % versions.scalaMockTestSupport % "test",
    "com.typesafe.akka" %% "akka-testkit" % versions.akka % "test"
  )
}

