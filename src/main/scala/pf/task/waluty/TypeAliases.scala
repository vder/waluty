package pf.task.waluty


import cats.effect.Bracket
import cats.ApplicativeError
import cats.MonadError

object TypeAliases
{
    type ThrowableBracket[A[_]] = Bracket[A,Throwable] 
    type ApplicativeThrowable[A[_]] = ApplicativeError[A,Throwable]
    type MonadThrowable[A[_]] = MonadError[A,Throwable]

}