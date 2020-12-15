import sbt._

object Dependencies {
  private val akkaVersion = "2.6.10"
  val akkaActorTyped = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
  val akkaStreamTyped = "com.typesafe.akka" %% "akka-stream-typed" % akkaVersion
  val akkaClusterTyped = "com.typesafe.akka" %% "akka-cluster-typed" % akkaVersion
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.1.11"
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val akkaSerializationJackson = "com.typesafe.akka" %% "akka-serialization-jackson" % akkaVersion
  val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
}