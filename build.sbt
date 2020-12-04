name := "akka-sample"

version := "0.1"

scalaVersion := "2.13.4"

ThisBuild / scalafmtOnCompile := true

libraryDependencies ++= Seq(
  Dependencies.akkaActorTyped,
  Dependencies.akkaStreamTyped,
  Dependencies.akkaHttp,
  Dependencies.akkaSlf4j,
  Dependencies.logback
)