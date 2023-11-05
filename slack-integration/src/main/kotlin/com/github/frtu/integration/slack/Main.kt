import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.webSocket
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.send
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
data class RtmStartResponse(val ok: Boolean, val url: String)

// https://api.slack.com/methods/rtm.start
fun main() {
    val slackBotToken = "xoxb-Your-Bot-Token-Here"
    val apiUrl = "https://slack.com/api/rtm.start"

    val client = HttpClient(CIO) {
        install(WebSockets)
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    runBlocking {
        // Start RTM connection and retrieve WebSocket URL
        val rtmStartResponse: RtmStartResponse = client.get(apiUrl) {
            headers {
                append("Authorization", "Bearer $slackBotToken")
            }
        }

        if (!rtmStartResponse.ok) {
            println("Failed to connect to Slack RTM API.")
            return@runBlocking
        }

        // Connect to WebSocket URL
        client.webSocket(method = HttpMethod.Get, host = "", port = 0, path = "", request = {
            url(rtmStartResponse.url)
        }) {
            println("Connected to Slack RTM API.")

            // Listen for incoming messages
            launch {
                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()
                            println("Received: $text")

                            // Here you can parse the message and check for questions
                            // Respond by sending an answer back to Slack
                            val question = "what's your name?"
                            val answer = "I am a helpful bot."

                            if (text.contains(question, ignoreCase = true)) {
                                send("""{"id": 1, "type": "message", "channel": "C1234567890", "text": "$answer"}""")
                            }
                        }
                    }
                }
            }
        }
    }
}
