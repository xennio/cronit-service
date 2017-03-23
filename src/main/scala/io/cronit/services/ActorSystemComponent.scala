package io.cronit.services

import akka.actor.{ActorSystem, Props}
import io.cronit.actors.RestTaskActor


trait ActorSystemComponent {
  val actorSystemService: ActorSystemService

  class ActorSystemService {
    implicit lazy val actorSystem = ActorSystem("CronitActorSystem")
    val scheduler = actorSystem.scheduler
    val restTaskActor = actorSystem.actorOf(Props[RestTaskActor])
  }
}
