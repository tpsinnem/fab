trait XTypes[F <: XTypes[F]] extends Fab2[F, XAlpha[F], XBeta[F]] { self: F => }
trait XAlpha[F <: XTypes[F]] extends FabM1[F, XAlpha[F]] { self: F#M1 => }
trait XBeta[F <: XTypes[F]] extends FabM2[F, XBeta[F]] { self: F#M2 => }

trait FooTypes extends XTypes[FooTypes] {
  type M1 = FooAlpha
  type M2 = FooBeta
}
class FooAlpha extends XAlpha[FooTypes]
object FooAlpha extends FooAlpha

class FooBeta extends XBeta[FooTypes]
object FooBeta extends FooBeta
