package pf.task.waluty.repositories

import pf.task.waluty.model.UserAccount
import pf.task.waluty.TypeAliases._
import doobie.util.transactor.Transactor
import doobie.implicits._
import cats.implicits._
import doobie._
import cats.Monad
import cats.effect.Bracket
import pf.task.waluty.model.Currency
import doobie.util.update.Update

trait AccountRepository[F[_]] {
  def getAccount(regno: String): F[Option[UserAccount]]
  def updateAccount(acc: UserAccount): F[Int]
  def createAccount(acc: UserAccount): F[Int]
}

class LiveAccountRepository[F[_]: Monad: ThrowableBracket](xa: Transactor[F])
    extends AccountRepository[F] {

  override def getAccount(regno: String): F[Option[UserAccount]] = {
    val acc: ConnectionIO[Option[UserAccount]] = for {
      acc <- sql"select regno, name from users where regno = ${regno}"
        .query[(String, String)]
        .option
      balance <- sql"select  currency, amount from balance where regno = ${regno}"
        .query[(String, BigDecimal)]
        .to[List]
    } yield acc match {
      case Some((regno, name)) =>
        UserAccount(name, regno, balance.map(x => Currency(x._1, x._2))).some
      case _ => None
    }
    acc.transact(xa)
  }

  override def updateAccount(acc: UserAccount): F[Int] = {
    val update = for {
      insAcc <- sql"update users set name = ${acc.name} where regno = ${acc.regno})".update.run
      insDel <- sql"delete from balance where regno = ${acc.regno}".update.run
      insBal <- Update[(String, BigDecimal)](
        s"insert into balance(regno,currency,amount) values(${acc.regno},?,?)"
      ).updateMany(acc.balance.map(Currency.currency2Tuple(_)))
    } yield insAcc + insBal + insDel
    update.transact(xa)
  }

  override def createAccount(acc: UserAccount): F[Int] = {
    val insert = for {
      insAcc <- sql"insert into users(regno,name) values (${acc.regno},${acc.name})".update.run
      insBal <- Update[(String, BigDecimal)](
        s"insert into balance(regno,currency,amount) values('${acc.regno}',?,?)"
      ).updateMany(acc.balance.map(Currency.currency2Tuple(_)))
    } yield insAcc + insBal
    insert.transact(xa)
  }

}


object LiveAccountRepository{
  def make[F[_]: Monad: ThrowableBracket](xa: Transactor[F]) = new LiveAccountRepository(xa)
}