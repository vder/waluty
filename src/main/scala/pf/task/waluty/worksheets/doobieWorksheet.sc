
import java.sql.Connection
import java.sql.DriverManager
import scala.concurrent.ExecutionContext.Implicits.global
import doobie.implicits._
import doobie.util.ExecutionContexts
import java.sql.Connection
import doobie._
import cats._
import cats.effect._
import cats.implicits._



implicit val cs: ContextShift[IO] = IO.contextShift(global)
implicit val timer: Timer[IO] = IO.timer(global)


  val tx = Transactor.fromDriverManager[IO](
   "org.h2.Driver", // driver classname
      """jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;INIT=runscript from './src/main/resources/create.sql';"""
  )

  //DB_CLOSE_DELAY=-1;


  sql"select count(*) from balance"

