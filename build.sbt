name := "playscala"
version := "1.0"


lazy val playscala = (project in file("."))
  .enablePlugins(PlayScala)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala).settings(
  watchSources ++= (baseDirectory.value / "ui/src" ** "*").get
)

      
//resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
//resolvers += Resolver.sonatypeRepo("snapshots")
//resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

scalaVersion := "2.13.6"
val scalatestVersion = "3.2.9"

//libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
libraryDependencies ++= Seq(
  "org.reactivemongo" %% "play2-reactivemongo" % "1.0.4-play28", // Enable reactive mongo for Play 2.8
  "org.reactivemongo" %% "reactivemongo-akkastream" % "1.0.4", // ReactiveMongo now supports the streaming of documents. It processes the data without loading the entire documents into memory
  "org.reactivemongo" %% "reactivemongo-akkastream" % "0.20.13",
  "com.typesafe.play" %% "play-json-joda" % "2.9.1", // Provide JSON serialization for Joda-Time
  "org.reactivemongo" %% "reactivemongo-play-json-compat" % "1.0.4-play28", // Provide JSON serialization for reactive mongo
  "org.reactivemongo" %% "reactivemongo-play-json" % "0.20.13-play28",
  "org.reactivemongo" %% "reactivemongo-bson-compat" % "0.20.13", // Provide BSON serialization for reactive mongo
  "org.reactivemongo" %% "reactivemongo-bson-macros" % "0.20.13",
  "org.reactivemongo" %% "reactivemongo-bson" % "0.20.13",
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalatestVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % "test",
)

libraryDependencies += guice
//libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test