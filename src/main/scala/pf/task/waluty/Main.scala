package pf.task.waluty

import cats.effect.{ExitCode, IO, IOApp}
import cats.effect._
import cats.implicits._
import cats.effect.Resource
import doobie.util.ExecutionContexts
import cats.effect.Blocker
import doobie.h2.H2Transactor
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import scala.concurrent.ExecutionContext.global
import pf.task.waluty.routes.AccountRoutes
import pf.task.waluty.repositories.LiveAccountRepository
import org.http4s.server.blaze.BlazeServerBuilder


object Main extends IOApp {

  val resources =
    for {
      client <- BlazeClientBuilder[IO](global).resource
      ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
      be <- Blocker[IO]    // our blocking EC
      xa <- H2Transactor.newH2Transactor[IO](
              "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;INIT=runscript from './src/main/resources/create.sql';", // connect URL
              "sa",                                   // username
              "",                                     // password
              ce,                                     // await connection here
              be                                      // execute JDBC operations here
            )
    } yield (xa,client)


  def run(args: List[String]) =

   resources.use{
     case (xa,client) =>
     for{
       accRepo <- LiveAccountRepository.make[IO](xa)
       routes <- AccountRoutes.make[IO](accRepo)
       httpApp = routes.routes.orNotFound
        _ <- BlazeServerBuilder[IO]
                  .bindHttp(
                   9000,
                    "localhost"
                  )
                  .withHttpApp(httpApp)
                  .serve
                  .compile
                  .drain
          } yield ExitCode.Success
     }
   



}