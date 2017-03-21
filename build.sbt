import org.scoverage.coveralls.Imports.CoverallsKeys._

name := """cronit-service"""
version := "1.0"
scalaVersion := "2.11.7"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

coverallsToken := Some("gc6Wo2u12Q9FgSDuZK82mG8FyFJMwzNTD")