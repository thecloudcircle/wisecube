ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.wisecube"
ThisBuild / scalaVersion := "2.12.14"

lazy val root = (project in file("."))
  .settings(
    name := "wisecube"
  )

lazy val global = project
  .in(file("."))
  .settings(settings: _*)
  .disablePlugins(AssemblyPlugin)
  .aggregate(
    core,
    text,
    query,
    ai
  )

lazy val core = project
  .settings(
    name := "wisecube-core",
    settings,
    libraryDependencies ++= coreDependencies
  )

lazy val text = project
  .settings(
    name := "wisecube-text",
    settings,
    libraryDependencies ++= textDependencies
  )

lazy val query = project
  .settings(
    name := "wisecube-query",
    settings,
    libraryDependencies ++= coreDependencies
  )

lazy val ai = project
  .settings(
    name := "wisecube-ai",
    settings,
    libraryDependencies ++= coreDependencies
  )

lazy val dependencies = new {
  val combinators = "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
  val spark = "org.apache.spark" %% "spark-mllib" % "3.2.1" % "provided"
  val jena = ("org.apache.jena" % "jena-arq" % "3.17.0")
    .exclude("com.fasterxml.jackson.core", "jackson-databind")
  val scalatest = ("org.scalatest" %% "scalatest" % "3.2.11" % "test")
    .exclude("org.scala-lang.modules", "scala-xml")
}

lazy val coreDependencies = Seq(
  dependencies.combinators,
  dependencies.spark,
  dependencies.jena,
  dependencies.scalatest
)

lazy val textDependencies = Seq(
  dependencies.spark,
  dependencies.scalatest
)

lazy val queryDependencies = Seq(
  dependencies.spark,
  dependencies.jena,
  dependencies.scalatest
)

lazy val settings = commonSettings ++ assemblySettings

lazy val commonSettings = Seq(
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

lazy val assemblySettings = Seq(
  ThisBuild / assemblyShadeRules := Seq(
    ShadeRule.rename("org.apache.commons.logging.**" -> "shadelogging.@1").inAll
  ),
  ThisBuild / assemblyJarName := name.value + ".jar",
  ThisBuild / assemblyMergeStrategy := {
    case PathList("shadelogging", xs @ _*) => MergeStrategy.first
    case x if Assembly.isConfigFile(x) =>
      MergeStrategy.concat
    case PathList(ps @ _*) if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
      MergeStrategy.rename
    case PathList("META-INF", xs @ _*) =>
      (xs map {_.toLowerCase}) match {
        case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
          MergeStrategy.discard
        case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
          MergeStrategy.discard
        case "plexus" :: xs =>
          MergeStrategy.discard
        case "services" :: xs =>
          MergeStrategy.filterDistinctLines
        case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
          MergeStrategy.filterDistinctLines
        case _ => MergeStrategy.deduplicate
      }
    case _ => MergeStrategy.deduplicate
  }
)
