fab
===

An attempt at F-bounded family polymorphism

---------------------------------

Here are the traits, larger arities probably to come. I haven't used Scala macros yet, 
but if there'd be some nice way of using them to create these automatically, clues 
are welcome.

trait Fab2[+F <: Fab2[F,M1,M2], +M1 <: FabM1[F,M1], +M2 <: FabM2[F,M2]] extends FabR1[F, M1] with FabR2[F, M2] {
  self: F =>
}
trait FabR1[+F <: FabR1[F,M], +M <: FabM1[F,M]] {
  self: F =>
  type M1 <: M
}
trait FabR2[+F <: FabR2[F,M], +M <: FabM2[F,M]] {
  self: F =>
  type M2 <: M
}
trait FabM1[+F <: FabR1[F,M], +M <: FabM1[F,M]] {
  self: F#M1 =>
}
trait FabM2[+F <: FabR2[F,M], +M <: FabM2[F,M]] {
  self: F#M2 =>
}

-------------------------------------

An example, adapted from Martin Odersky's well-known Cake pattern example:


trait SubjectObserver[F <: SubjectObserver[F]]
  extends Fab2[F, Subject[F], Observer[F]] with FabR1[F, Subject[F]] with FabR2[F, Observer[F]] {
  self: F =>
  type M1 <: Subject[F]
  type M2 <: Observer[F]
}
trait Subject[F <: SubjectObserver[F]] extends FabM1[F, Subject[F]] {
  self: F#M1 =>
  private var observers:List[F#M2] = List()
  def subscribe(obs:F#M2) = 
    observers = obs :: observers
  def publish = 
    for (obs <- observers) obs.notify(this)
}
trait Observer[F <: SubjectObserver[F]] extends FabM2[F, Observer[F]] {
  self: F#M2 =>
  def notify(sub:F#M1):Unit
}
trait SensorReader extends SubjectObserver[SensorReader] with Fab2[SensorReader, Sensor, Display] {
  type M1 = Sensor
  type M2 = Display
}
trait Sensor extends Subject[SensorReader] with FabM1[SensorReader, Sensor] {
  val label:String
  var value:Double = 0.0
  def changeValue(v:Double) = {
    value = v
    publish
  }
}
class Display extends Observer[SensorReader] with FabM2[SensorReader, Display] {
  def printlnZ(s:String) = println(s:String)
  def notify(sub:Sensor) =
    printlnZ(sub.label + " has value " + sub.value)
}
object Test {
  val s1 = new Sensor { val label = "sensor1" }
  val s2 = new Sensor { val label = "sensor2" }
  def main(args:Array[String]) = {
    val d1 = new Display; val d2 = new Display
    s1.subscribe(d1); s1.subscribe(d2)
    s2.subscribe(d1)
    s1.changeValue(2); s2.changeValue(3)
  }
}


