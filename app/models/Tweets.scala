package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.json.util._
import play.api.libs.functional.syntax._

object Tweets {

  implicit val tweetReads: Reads[Tweet] = (
    (JsPath \ "user" \ "name").read[String] ~
    (JsPath \ "text").read[String]
  )(Tweet.apply _)
}

case class Tweet(from: String, text: String)