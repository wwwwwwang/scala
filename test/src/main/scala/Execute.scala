/**
  * Created by SNOW on 2017/5/19.
  */
object Execute {
  def main(args: Array[String]): Unit = {
    new Thread(new ThreadDemo("test")).start()
    /*for (i <- 1 to 1000) {
      if (test.value) {
        println(s"value = ${test.value} when i = $i, time = ${System.currentTimeMillis()}")
        test.value = false
        println(s"value = ${test.value} has changed to false in main thread! ")
      }else{
        println(s"value = ${test.value} in else switch! ")
      }
      Thread.sleep(1000)
    }*/
    test.run()
  }
}
