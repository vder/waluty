val Http4sVersion = "0.21.3"
val CirceVersion = "0.13.0"
val Specs2Version = "4.9.3"
val LogbackVersion = "1.2.3"
val doobieVersion = "0.8.8"
val h2Version = "1.3.148"

lazy val root = (project in file("."))
  .settings(
    organization := "pf.task",
    name := "waluty",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.2",
    libraryDependencies ++= Seq(
      "com.h2database" % "h2" % h2Version,
      "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-scala-xml" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "org.specs2" %% "specs2-core" % Specs2Version % "test",
      "ch.qos.logback" % "logback-classic" % LogbackVersion,
      "org.tpolecat" %% "doobie-core" % doobieVersion,
      "org.scala-lang.modules" %% "scala-xml" % "2.0.0-M1",
      "com.h2database" % "h2" % "1.4.200",
      // And add any of these as needed
      "org.tpolecat" %% "doobie-h2" % doobieVersion, // H2 driver 1.4.200 + type mappings.
      "org.tpolecat" %% "doobie-hikari" % doobieVersion // HikariCP transactor.
      //"org.tpolecat" %% "doobie-postgres"  % doobieVersion,          // Postgres driver 42.2.9 + type mappings.
      //"org.tpolecat" %% "doobie-quill"     % doobieVersion,          // Support for Quill 3.4.10
      // "org.tpolecat" %% "doobie-specs2"    % doobieVersion % "test", // Specs2 support for typechecking statements.
      // "org.tpolecat" %% "doobie-scalatest" % doobieVersion % "test"  // ScalaTest support for typechecking statements.
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature"//,
  //"-Xfatal-warnings"
)
