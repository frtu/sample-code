package com.github.frtu.sample.bot.slack

import com.slack.api.app_backend.events.payload.EventsApiPayload
import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.bolt.context.builtin.EventContext
import com.slack.api.bolt.jetty.SlackAppServer
import com.slack.api.methods.request.views.ViewsPublishRequest.ViewsPublishRequestBuilder
import com.slack.api.model.block.Blocks.actions
import com.slack.api.model.block.Blocks.asBlocks
import com.slack.api.model.block.Blocks.divider
import com.slack.api.model.block.Blocks.section
import com.slack.api.model.block.SectionBlock
import com.slack.api.model.block.composition.BlockCompositions.markdownText
import com.slack.api.model.block.composition.BlockCompositions.plainText
import com.slack.api.model.block.element.BlockElements.asElements
import com.slack.api.model.block.element.BlockElements.button
import com.slack.api.model.block.element.ButtonElement
import com.slack.api.model.event.AppHomeOpenedEvent
import com.slack.api.model.event.MessageEvent
import com.slack.api.model.view.Views.view

fun main() {
    // Create a new Slack app
    val botToken = System.getenv("SLACK_BOT_TOKEN")
    val app = App(AppConfig.builder().singleTeamBotToken(botToken).build())

    // Event listener for messages
    app.event(MessageEvent::class.java) { payload, ctx ->
        val event = payload.event
        println("Received a message from user: ${event.user}, text: ${event.text}")

        // Respond to the message if needed
        ctx.say("Hello <@${event.user}>!")
        ctx.ack() // Acknowledge the event
    }

    app.event(AppHomeOpenedEvent::class.java) { payload: EventsApiPayload<AppHomeOpenedEvent>, ctx: EventContext ->
        val appHomeView = view { view ->
            view
                .type("home")
                .blocks(asBlocks(
                    section { section: SectionBlock.SectionBlockBuilder ->
                        section.text(markdownText { mt ->
                            mt.text(
                                "*Welcome to your _App's Home tab_* :tada:"
                            )
                        })
                    },
                    divider(),
                    section { section: SectionBlock.SectionBlockBuilder ->
                        section.text(markdownText { mt ->
                            mt.text(
                                "This button won't do much for now but you can set up a listener for it using the `actions()` method and passing its unique `action_id`. See an example on <https://slack.dev/java-slack-sdk/guides/interactive-components|slack.dev/java-slack-sdk>."
                            )
                        })
                    },
                    actions { actions ->
                        actions
                            .elements(
                                asElements(
                                    button { b: ButtonElement.ButtonElementBuilder ->
                                        b.text(plainText { pt ->
                                            pt.text("Click me!")
                                        }).value("button1").actionId("button_1")
                                    }
                                )
                            )
                    }
                ))
        }
        val res = ctx.client().viewsPublish { r: ViewsPublishRequestBuilder ->
            r.userId(payload.event.user)
                .view(appHomeView)
        }
        println("Response ok:${res.isOk}")
        ctx.ack()
    }

    val server = SlackAppServer(app)
    server.start() // http://localhost:3000/slack/events
}