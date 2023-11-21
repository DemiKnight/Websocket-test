name := "backend"
scalaVersion := "3.3.1"

val zioVersion = "2.0.19"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-streams" % zioVersion,
  "dev.zio" %% "zio-http" % "3.0.0-RC3"
)