name := "ConfigTest"

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies += "com.typesafe" % "config"  % "1.3.1"
libraryDependencies += "commons-logging" % "commons-logging" % "1.1.1"
libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.0.0-mr1-cdh4.7.0"  % Provided

//enablePlugins(JavaServerAppPackaging)
