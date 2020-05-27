package pf.task.waluty

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import cats.effect.Resource
import doobie.util.ExecutionContexts
import cats.effect.Blocker
import doobie.h2.H2Transactor


object Main extends IOApp {

  val transactor: Resource[IO, H2Transactor[IO]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
      be <- Blocker[IO]    // our blocking EC
      xa <- H2Transactor.newH2Transactor[IO](
              """jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;
                 INIT=runscript from '~/src/main/resources/create.sql';
                 """.stripMargin, // connect URL
              "sa",                                   // username
              "",                                     // password
              ce,                                     // await connection here
              be                                      // execute JDBC operations here
            )
    } yield xa


  def run(args: List[String]) =
    WalutyServer.stream[IO].compile.drain.as(ExitCode.Success)
}