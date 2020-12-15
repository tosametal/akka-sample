name := "akka-sample"

version := "0.1"

scalaVersion := "2.13.4"

ThisBuild / scalafmtOnCompile := true

lazy val actors = (project in file("actors"))
  .settings(
    libraryDependencies ++= Seq(
      Dependencies.akkaActorTyped,
      Dependencies.akkaStreamTyped,
      Dependencies.akkaHttp,
      Dependencies.akkaSlf4j,
      Dependencies.logback
    ),
    name := "actors-sample"
  )

lazy val streams = (project in file("streams"))
  .settings(
    name := "streams-sample"
  )

lazy val clusters = (project in file("clusters"))
  .settings(
    name := "clusters-sample"
  )