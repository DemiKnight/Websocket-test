import com.raquo.airstream.web.DomEventStream
import com.raquo.laminar.api.L.{*, given}
import com.raquo.airstream.{*, given}
import org.scalajs.dom
import org.scalajs.dom.{MessageEvent, WebSocket}

import scala.scalajs.js
import scala.scalajs.js.annotation.*

// import javascriptLogo from "/javascript.svg"
@js.native @JSImport("/javascript.svg", JSImport.Default)
val javascriptLogo: String = js.native

@main
def LiveChart(): Unit =
  dom.document.querySelector("#app").innerHTML = s"""
    <div>
      <a href="https://vitejs.dev" target="_blank">
        <img src="/vite.svg" class="logo" alt="Vite logo" />
      </a>
      <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank">
        <img src="$javascriptLogo" class="logo vanilla" alt="JavaScript logo" />
      </a>
      <h1>Hello Scala.js!</h1>
      <div class="card">
        <button id="counter" type="button"></button>
      </div>
      <p class="read-the-docs">
        Click on the Vite logo to learn more
      </p>
    </div>
  """

  setupCounter(dom.document.getElementById("counter"))
  val websocket = new WebSocket("ws:localhost:80/api/subscriptions")
//  val stream: EventStream[MessageEvent] = DomEventStream[dom.MessageEvent](websocket,"onopen")

  websocket.onopen = (event) => {
    websocket.send("FOO")
  }

end LiveChart

def setupCounter(element: dom.Element): Unit =
  var counter = 0

  def setCounter(count: Int): Unit =
    counter = count
    element.innerHTML = s"count is $counter"

  element.addEventListener("click", e => setCounter(counter + 1))
  setCounter(0)
end setupCounter
