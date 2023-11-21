scalaVersion := "3.3.1"

name := "wt-test"

lazy val frontend = (project in file("frontend"))

lazy val backend = (project in file("backend"))

lazy val root = (project in file("."))
  .aggregate(frontend, backend)
