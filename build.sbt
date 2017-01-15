name := "StarterBot"
version := "0.1"

scalaVersion := "2.11.8"

resolvers += "jitpack" at "https://jitpack.io"
scalacOptions ++= Seq("-feature")

val akkaVersion = "2.4.8"

libraryDependencies ++= Seq(
  // Scala-ish Telegram Bot API
  "com.github.mukel" %% "telegrambot4s" % "v1.2.0",

  // Useful to schedule tasks
  "com.github.philcali" %% "cronish" % "0.1.3",

  // Akka Http goodies
  "com.typesafe.akka" %% "akka-http-core" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion
)
