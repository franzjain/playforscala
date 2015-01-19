package utils

import securesocial.core.{ UserService, SocialUser, IdentityId, Identity, UserServicePlugin }
import securesocial.core.providers.Token
import play.api.{ Application }


class SimpleUserService(val app: Application) extends UserServicePlugin(app) {

  var users: Map[IdentityId, SocialUser] = Map()
  var tokens: Map[String, Token] = Map()
  
  def find(id: IdentityId): Option[SocialUser] = {
    users.get(id)
  }
  
  def findByEmailAndProvider(email: String, providerId: String) = {
    users.values.find { user =>
      user.identityId.providerId == providerId && user.email == Some(email)
    }
  }
  
  def save(user: Identity): Identity = {
    val socialUser: SocialUser = SocialUser(user)
    users = users + (user.identityId -> socialUser)
    socialUser
  }
  
  def save(token: Token) = {
    tokens = tokens + (token.uuid -> token)
  }
  
  def findToken(token: String) = {
    tokens.get(token)
  }
  
  def deleteToken(uuid: String) {
    tokens = tokens - uuid
  }
  
  def deleteExpiredTokens(){
    tokens = tokens.filter( !_._2.isExpired )
  }
  
  
  
  
  
}