package controllers

import play.api.mvc.{ Action, Controller, WebSocket }
import java.lang.management.ManagementFactory
import play.api.libs.iteratee.{ Iteratee, Enumerator }
import play.api.libs.concurrent.Promise
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.duration.DurationInt

object WebSockets extends Controller {

  def statusPage() = Action { implicit request => 
    Ok(views.html.websockets.statusPage(request))  
  }
  
  def statusFeed() = WebSocket.using[String] { implicit request =>
    def getLoadAverage = { 
      val value = ManagementFactory.getOperatingSystemMXBean.getSystemLoadAverage
      println(s"getLoadAverage was invoked: $value")
      "%1.2f" format value
    }
    
    def getRandom() = { 
      val random = scala.util.Random.nextDouble()
      println(s"getRandom was invoked: $random")
      "%1.2f" format random
    }
    
    val in = Iteratee.ignore[String]
    val out = Enumerator.repeatM{ 
      Promise.timeout(getRandom, 3 seconds)
    }
    
    (in, out)
  }
}