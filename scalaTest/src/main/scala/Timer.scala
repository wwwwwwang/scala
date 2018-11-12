/**
  * Created by SNOW on 2017/5/19.
  */

import akka.actor.{ActorSystem, Props, _}
import scala.math.random

import scala.concurrent.duration._

class MyActor extends Actor {
  def receive: PartialFunction[Any, Unit] = {
    case 0 =>
      //val r = if (random - 0.5 > 0) true else false
      val r = true
      Constant.value = r
      //test.value = true
      println(s"Constant.value has been changed to $r by timer, time = ${System.currentTimeMillis()}")
    case para: String => println(para)
    case _ => ()
  }
}

object Timer {
  val Actorsystem: ActorSystem = ActorSystem.create("mstest")
  val act: ActorRef = Actorsystem.actorOf(Props[MyActor], "changeValue")
  //implicit val time = Timeout(5 seconds)

  def changeValue(): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    println(s"Constant.value = ${Constant.value} before changed")
    val cancellable = Actorsystem.scheduler.schedule(0 milliseconds, 5 seconds, act, 0)
  }

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    //1.system.scheduler.scheduleOnce(2 seconds, act1, "foo")
    /*2.system.scheduler.scheduleOnce(2 seconds){
        act1 ? "Hello"
    }*/
    //3.这将会计划0ms后每50ms向tickActor发送 Tick-消息
    val cancellable = Actorsystem.scheduler.schedule(0 milliseconds, 5 seconds, act, 0)
    //这会取消未来的Tick发送
    //cancellable.cancel()
  }

}
