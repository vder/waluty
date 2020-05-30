import pf.task.waluty.model.UserAccount
import pf.task.waluty.repositories.LiveAccountRepository
import java.sql.Connection
import java.sql.DriverManager
import scala.concurrent.ExecutionContext.Implicits.global
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie._
import cats._
import cats.effect._
import cats.implicits._
import pf.task.waluty.model._



implicit val cs: ContextShift[IO] = IO.contextShift(global)
implicit val timer: Timer[IO] = IO.timer(global)


 /*val tx = Transactor.fromDriverManager[IO](
    "org.h2.Driver", // driver classname
    """jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;INIT=runscript from './src/main/resources/create.sql';"""
   )
*/
 
 println("test")


  val repo = LiveAccountRepository.make(tx)

  
repo.getAccount("1").unsafeRunSync()



val usrAccount = UserAccount("Ja","moj pesel",List(USD(30),PLN(1000)))

repo.createAccount(usrAccount)
