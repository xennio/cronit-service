package io.cronit.actors

import akka.actor.Actor
import io.cronit.models.Start

class RestTaskActor extends Actor {
  override def receive: Receive = {
    case Start => {

    }
  }
}
