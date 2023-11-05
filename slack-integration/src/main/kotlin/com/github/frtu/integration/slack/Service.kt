import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.serialization.Serializable

const val SLACK_BOT_TOKEN = "TOKEN"

fun main() {
    embeddedServer(Netty, port = 8080) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    routing {
        post("/slack/events") {
            println("Receiving slack event")
            val event = call.receive<SlackEventWrapper>()
            when (event.type) {
                "url_verification" -> call.respond(HttpStatusCode.OK, mapOf("challenge" to event.challenge))
                "event_callback" -> {
                    val messageEvent = event.event
                    if (messageEvent?.type == "message" && messageEvent.subtype != "bot_message") {
                        // Your logic here, e.g., check if the message mentions the bot
                        val responseText = "Hello! How can I help you?"

                        client.post<String>("https://slack.com/api/chat.postMessage") {
                            header("Authorization", "Bearer $SLACK_BOT_TOKEN")
                            contentType(ContentType.Application.Json)
                            body = mapOf(
                                "channel" to messageEvent.channel,
                                "text" to responseText
                            )
                        }
                    }
                    call.respond(HttpStatusCode.OK)
                }
                else -> call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

@Serializable
data class SlackEventWrapper(
    val type: String,
    val event: SlackMessageEvent? = null,
    val challenge: String? = null
)

@Serializable
data class SlackMessageEvent(
    val type: String,
    val user: String,
    val text: String,
    val channel: String,
    val subtype: String? = null
)
