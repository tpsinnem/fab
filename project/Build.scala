import sbt._
import Keys._

object FabBuild extends Build {
  lazy val FabProject = Project("Fab", file(".")).settings(
    scalaVersion := "2.10-RC3"
  )
}
