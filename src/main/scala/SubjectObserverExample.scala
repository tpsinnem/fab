trait SubjectObserver[F <: SubjectObserver[F]]
  extends Fab2[F, Subject[F], Observer[F]] {
  self: F =>
}

trait Subject[F <: SubjectObserver[F]] extends FabM1[F, Subject[F]] {
  self: F#M1 =>
  private var observers:List[F#M2] = List()
  def subscribe(obs:F#M2) = 
    observers = obs :: observers
  def publish = 
    for (obs <- observers) obs.notify(this:F#M1)
}
trait Observer[F <: SubjectObserver[F]] extends FabM2[F, Observer[F]] {
  self: F#M2 =>
  def notify(sub:F#M1):Unit
}

trait SensorReader extends SubjectObserver[SensorReader]
  with Fab2[SensorReader, Sensor, Display] {
  type M1 = Sensor
  type M2 = Display
}
trait Sensor extends Subject[SensorReader] {
  val label:String
  var value:Double = 0.0
  def changeValue(v:Double) = {
    value = v
    publish
  }
}
class Display extends Observer[SensorReader] {
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
