name := "ElectronicsAPI"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.5"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.5"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.12"
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.1.5" % Test
libraryDependencies += "com.jsuereth" % "scala-arm_2.12" % "2.0"

val elastic4sVersion = "6.3.7"
libraryDependencies ++= Seq(
  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,

  "com.sksamuel.elastic4s" %% "elastic4s-embedded" % elastic4sVersion,

// for the http client
  "com.sksamuel.elastic4s" %% "elastic4s-http" % elastic4sVersion,

  // if you want to use reactive streams
  "com.sksamuel.elastic4s" %% "elastic4s-http-streams" % elastic4sVersion,

  "com.sksamuel.elastic4s" %% "elastic4s-jackson" % elastic4sVersion,

// testing
  "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elastic4sVersion % "test",
  "com.sksamuel.elastic4s" %% "elastic4s-embedded" % elastic4sVersion % "test",

  "org.apache.logging.log4j" % "log4j-api" % "2.9.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.9.1"
)

