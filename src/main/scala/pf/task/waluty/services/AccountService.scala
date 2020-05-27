package pf.task.waluty.services

import pf.task.waluty.model.UserAccount
import pf.task.waluty.model.OperationStatus
import pf.task.waluty.model.Currency

trait AccountService[F[_]] {
  def createAccount(acc:UserAccount):F[OperationStatus]
  def getAccount(accountId:String):F[Option[UserAccount]]
  def transfer(accountId:String,curFrom:Currency,curTo:Currency):F[OperationStatus]
}
