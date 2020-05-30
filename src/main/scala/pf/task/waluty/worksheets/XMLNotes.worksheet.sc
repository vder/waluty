import com.lucidchart.open.xtract.XmlReader
import scala.xml.Elem
import pf.task.waluty.worksheets.ExchangeRate
import pf.task.waluty.services.LiveExchangeService
import cats.effect.Timer
import cats.effect.ContextShift
import cats.effect.IO
import org.http4s.client.blaze._
import org.http4s.client._
import org.http4s.scalaxml._
import cats.implicits._
import scala.concurrent.ExecutionContext.Implicits.global
implicit val cs: ContextShift[IO] = IO.contextShift(global)
implicit val timer: Timer[IO] = IO.timer(global)

import cats.effect.Blocker
import java.util.concurrent._

val blockingPool = Executors.newFixedThreadPool(5)
val blocker = Blocker.liftExecutorService(blockingPool)
val httpClient: Client[IO] = JavaNetClientBuilder[IO](blocker).create

val respXML = httpClient.expect[Elem]("http://api.nbp.pl/api/exchangerates/rates/a/chf/")



val exService = LiveExchangeService.make(httpClient)


exService.calculate("PLN","USD",10).unsafeRunSync()
exService.calculate("PLN","USD",20).unsafeRunSync()


val xml = respXML.unsafeRunSync()

val exRate = XmlReader.of[ExchangeRate].read(xml).toOption




