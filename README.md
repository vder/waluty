# waluty
```
import cats._
import cats.data._
import cats.implicits._


def fun[F[_]: ApplicativeError[*[_],Throwable] :FlatMap] = 
for{
   x <- "100".toIntOption.toRight(new Throwable("Not an Int")).liftTo[F]
   y <- "10".toIntOption.toRight(new Throwable("Not an Int")).liftTo[F]
} yield x
```
