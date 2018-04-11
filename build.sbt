name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"


libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.1"



