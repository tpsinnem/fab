trait Fab2[F <: Fab2[F,M1,M2], M1 <: FabM1[F,M1], M2 <: FabM2[F,M2]] extends FabR1[F, M1] with FabR2[F, M2] {
  self: F =>
}
trait FabR1[F <: FabR1[F,M], M <: FabM1[F,M]] {
  self: F =>
  type M1 <: M
}
trait FabR2[F <: FabR2[F,M], M <: FabM2[F,M]] {
  self: F =>
  type M2 <: M
}
trait FabM1[F <: FabR1[F,M], M <: FabM1[F,M]] {
  self: F#M1 =>
}
trait FabM2[F <: FabR2[F,M], M <: FabM2[F,M]] {
  self: F#M2 =>
}
