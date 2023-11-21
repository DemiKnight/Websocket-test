package uk.co.alexknight.wstest.backend

import zio.*
import zio.http.*
import zio.http.ChannelEvent.Read
import zio.http.codec.PathCodec.string

object BackendMain extends ZIOAppDefault {
  private val socketApp: WebSocketApp[Any] =
    Handler.webSocket { (channel: WebSocketChannel) =>
      channel.receiveAll {
        case Read(WebSocketFrame.Text("FOO")) =>
          channel.send(Read(WebSocketFrame.Text("BAR")))
        case Read(WebSocketFrame.Text("BAR")) =>
          channel.send(Read(WebSocketFrame.Text("FOO")))
        case Read(WebSocketFrame.Text(text)) =>
          channel.send(Read(WebSocketFrame.Text(text))).repeatN(10)
        case _ =>
          ZIO.unit
      }
    }

  private val app: HttpApp[Any] =
    Routes(
      Method.GET / "greet" / string("name") -> handler {
        (name: String, req: Request) =>
          Response.text(s"Greetings {$name}!")
      },
      Method.POST / "boardcast" / string("message") -> handler {
        (message: String, req: Request) =>
          // todo send all websockets a message
          Response.ok
      },
      Method.GET / "subscriptions" -> handler(socketApp.toResponse)
    ).toHttpApp

  override val run = Server.serve(app).provide(Server.defaultWithPort(4003))
}
