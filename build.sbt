name := "slick-admin"

version := "1.0"

scalaVersion := "2.11.4"


libraryDependencies ++= Seq(
  cache, jdbc, ws,
  "com.typesafe.slick" %% "slick" % "3.0.0-RC1"
)

scalacOptions ++= Seq(
  "-feature",
  "-language:higherKinds",
  "-language:reflectiveCalls"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)
