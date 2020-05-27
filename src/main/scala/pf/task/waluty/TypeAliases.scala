package pf.task.waluty


import cats.effect.Bracket
import cats.ApplicativeError

object TypeAliases
{
    type ThrowableBracket[A[_]] = Bracket[A,Throwable] 
    type ApplicativeThrowable[A[_]] = ApplicativeError[A,Throwable]

}