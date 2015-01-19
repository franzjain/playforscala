name := """productsocial"""

version := "1.0-SNAPSHOT"

play.Project.playScalaSettings

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.webjars" %% "webjars-play" % "2.2.2-1",
  "org.webjars" % "bootstrap" % "2.1.1",
  "net.sf.barcode4j" % "barcode4j" % "2.0",
  "ws.securesocial" %% "securesocial" % "2.1.4"
)

resolvers += Resolver.sonatypeRepo("releases")