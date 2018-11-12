import com.alibaba.fastjson.JSON
object fastjson {

  def getOrDefault(i:Integer): Int={
    if(i==null) 0 else i
  }

  def main(args: Array[String]): Unit = {
    val str2 = "{\"et\":\"kanqiu_client_join\",\"vtm\":1435898329434,\"body\":{\"client\":\"866963024862254\",\"client_type\":\"android\",\"room\":\"NBA_HOME\",\"gid\":\"\",\"type\":\"\",\"roomid\":\"\"},\"time\":1435898329}"
    val json = JSON.parseObject(str2)
    //获取成员
    val fet = json.get("et")
    //返回字符串成员
    val etString = json.getString("et")
    //返回整形成员
    val vtm = json.getInteger("vtm")
    println(vtm)
    //返回多级成员
    val client = json.getJSONObject("body").get("client")
    println(client)

    println(json.getInteger("a"))
    println(s"get with default = ${getOrDefault(json.getInteger("a"))}")

    println(s"get with default = ${getOrDefault(json.getJSONObject("a").getInteger("b"))}")
  }
}
