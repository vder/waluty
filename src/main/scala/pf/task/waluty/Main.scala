package pf.task.waluty

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {
  def run(args: List[String]) =
    WalutyServer.stream[IO].compile.drain.as(ExitCode.Success)
}