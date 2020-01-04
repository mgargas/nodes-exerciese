name := "nodes-exercise"

version := "0.1"

scalaVersion := "2.13.1"

val akkaVersion     = "2.5.25"
val akkaHttpVersion = "10.1.10"

libraryDependencies ++= Seq(
  "org.apache.poi"    % "poi"                % "3.17",
  "org.apache.poi"    % "poi-ooxml"          % "3.17",
  "com.typesafe.akka" %% "akka-http"         % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor"        % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit"      % akkaVersion % "test",
  "de.heikoseeberger" %% "akka-http-jackson" % "1.27.0",
  "org.scalatest"     %% "scalatest"         % "3.0.8" % "test",
  "org.scalamock"     %% "scalamock"         % "4.4.0" % "test"
)
