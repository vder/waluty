
import scala.xml.Elem
import pf.task.waluty.worksheets.ExchangeRate
import pf.task.waluty.services.LiveExchangeService
import io.circe.Json
import io.circe.syntax._

import cats.effect.Timer
import cats.effect.ContextShift
import cats.effect.IO
import org.http4s.client.blaze._
import org.http4s.client._
import org.http4s.circe._
import cats.implicits._
import scala.concurrent.ExecutionContext.Implicits.global

implicit val cs: ContextShift[IO] = IO.contextShift(global)
implicit val timer: Timer[IO] = IO.timer(global)



BlazeClientBuilder[IO](global).resource.use { client =>
  // use `client` here and return an `IO`.
  // the client will be acquired and shut down
  // automatically each time the `IO` is run.
  IO.unit
}


import cats.effect.Blocker
import java.util.concurrent._

val blockingPool = Executors.newFixedThreadPool(5)
val blocker = Blocker.liftExecutorService(blockingPool)
val httpClient: Client[IO] = JavaNetClientBuilder[IO](blocker).create


val respA = httpClient.expect[Json]("http://api.nbp.pl/api/exchangerates/rates/a/chf/")
val respB = httpClient.expect[String]("http://api.nbp.pl/api/exchangerates/rates/b/chf/")

import org.http4s.scalaxml._

val respXML = httpClient.expect[Elem]("http://api.nbp.pl/api/exchangerates/rates/a/chf/")

val respJson = respA.unsafeRunSync

respJson.spaces2

respJson.as[ExchangeRate]



val exService = LiveExchangeService.make(httpClient)


exService.calculate("PLN","USD",10).unsafeRunSync()
exService.calculate("PLN","USD",20).unsafeRunSync()


val c = respJson.hcursor

c.get[List[BigDecimal]]("mid")
