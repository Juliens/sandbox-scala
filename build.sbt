import AssemblyKeys._

assemblySettings

jarName in assembly := "test.jar"

name := "My TEST"

version := "0.1"

scalaVersion := "2.10.3"

libraryDependencies += "org.mongodb" %% "casbah" % "2.7.0-RC2"

libraryDependencies += "net.glxn" % "qrgen" % "1.3"
