import zio.*
import zio.http.*
import zio.http.ChannelEvent.Read
import zio.http.codec.PathCodec.string

object BackendMain extends ZIOAppDefault {
  private val socketApp: WebSocketApp[Any] =
    Handler.webSocket { channel =>
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
      Method.GET / "subscriptions" -> handler(socketApp.toResponse)
    ).toHttpApp

  override val run = Server.serve(app).provide(Server.defaultWithPort(4003))
}
