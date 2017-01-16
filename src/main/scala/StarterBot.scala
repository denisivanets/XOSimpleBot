import java.util
import java.util.concurrent.ConcurrentHashMap
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import info.mukel.telegrambot4s._, api._, methods._, models._, Implicits._
import scala.collection.JavaConverters._

object XOBot extends App with TelegramBot with Polling with Commands with ChatActions {
  val games = new ConcurrentHashMap[Long, XOGame]().asScala
  override def token: String = 
    scala.io.Source.fromFile("bot.token").getLines().next

  on("/start") { implicit message => args =>
    val info = "Hello!Let's start\n" +
    "Type /newgame for start new game\n" +
    "Type /step [number of area](1-9)\n(example:/step 5) for make step" +
    "\nYour char is X"
    reply(info)
  }

  on("/newgame") { implicit message => args =>
    val newGame = new XOGame
    games put (message.sender,newGame)
    reply("New game was created")
    val photo = InputFile.FromContents("game.png",newGame.getPicture)
    uploadingPhoto
    api.request(SendPhoto(message.sender,photo))
  }

  on("/step") { implicit message => args =>
    val isGameExists = if(games.contains(message.sender)) true else false
    val argsIsValid = MessageHolder.checkValid(args.mkString)
    if(argsIsValid && isGameExists){
    val game =games(message.sender)
    val isGameDone = if(game.winner >= 0) true else false
    if(isGameDone){ reply("Game over\n" +
      "Type /newgame for start new game")}
    else{
    game.makeStep(args.mkString)
    val photo = InputFile.FromContents("game.png", game.getPicture)
    uploadingPhoto
    api.request(SendPhoto(message.sender,photo))}
    }
    else if(!argsIsValid){
      reply("Wrong arguments\n" +
        "Type like this: /step [number of area](1-9)\n" +
        "Example: /step 5")}
    else if(!isGameExists){
      reply("Game doesn't exist\n" +
        "Type /newgame for create new game" +
        "")
    }
  }

  run()

}

