#### Small prototypes and exercises for Tagless Final.

Tagless Final is a specific implementation of the Interpreter pattern that is conceptually
similar to Free Monad but has important differences.

Each service is represented by a trait which is parameterized by a target monad `F[_]`. Operations
for the service are represented as methods in the trait. A business logic case that uses the
service is implemented as an expression that represents an instance of `F`. An interpreter
evaluates this expression for a given concrete monad, which means that the implicit instance 
of this monad has to be in scope.

This approach seems to create much less boilerplate than the Free Monad approach. It also feels
much more natural. Combining different services is trivial, especially compared to the Free
Monad approach, and it also does not create excessive objects on the heap as in the case of
Free Monad when combining multiple services results in chaining multiple `Coproduct`s of different
algebras.

---

`softwaremill` Variation of 
[https://softwaremill.com/free-tagless-compared-how-not-to-commit-to-monad-too-early/](https://softwaremill.com/free-tagless-compared-how-not-to-commit-to-monad-too-early/)