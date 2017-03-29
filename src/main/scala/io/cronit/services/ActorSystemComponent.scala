package io.cronit.services

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}


trait ActorSystemComponent {
  val actorSystemService: ActorSystemService

  class ActorSystemService {
    implicit lazy val actorSystem = ActorSystemService.actorSystem
    implicit lazy val materializer = ActorSystemService.materializer
    val scheduler = actorSystem.scheduler
  }

}

object ActorSystemService {
  implicit lazy val actorSystem = ActorSystem("CronitActorSystem")
  implicit lazy val materializer = ActorMaterializer(ActorMaterializerSettings(actorSystem))
}

