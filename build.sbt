name := "tagless-final"

version := "0.1"

scalaVersion := "2.12.6"

scalacOptions ++= Seq(
  "-Ypartial-unification",
  "-language:higherKinds"
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.2.0",
  "org.typelevel" %% "cats-effect" % "1.0.0-RC2"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")