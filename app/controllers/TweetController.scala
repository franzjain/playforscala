package controllers

import play.api.mvc.{ Controller, RequestHeader, AnyContent }
import play.api.libs.ws.WS
import scala.concurrent.duration._
import scala.concurrent.{ Await }
import play.api.libs.json.Json
import models.Tweets._
import models._
import securesocial.core._
import play.api.cache.Cached
import play.api.Play.current
import play.api.libs.oauth._
import scala.concurrent.ExecutionContext.Implicits.global

object TweetController extends Controller with securesocial.core.SecureSocial {

  def tweetList() =
//    Cached(userIdCacheKey("twitter-"), 120) {
      SecuredAction.async { implicit request =>
        val (token, secret) = request.user.oAuth1Info.get match {
          case OAuth1Info(token, secret) => (token -> secret)
        }
        val info = request.user
        val oauthCalculator = OAuthCalculator(SecureSocial.serviceInfoFor(info).get.key, RequestToken(token, secret))
        println(s"request.user=${request.user}")
        val results = 3
        val query = """paperclip OR "paper clip""""
        val responseFuture = WS.url("https://api.twitter.com/1.1/search/tweets.json")
          .withQueryString("q" -> query, "rpp" -> results.toString())
          .sign(oauthCalculator).get()

        responseFuture.map(response => {
          println(s"response body=${response.body}")
          val tweets = Json.parse(response.body).\("statuses").as[Seq[Tweet]]
          Ok(views.html.twitterrest.tweetlist(tweets))
        })
      }
//    }

  def userIdCacheKey(prefix: String) = { (header: RequestHeader) =>
    prefix + header.session.get("userId").getOrElse("anonymous")
  }

}