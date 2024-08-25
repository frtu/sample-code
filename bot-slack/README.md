# Project - bot-slack

## About

Allow to connect to Slack & receive message.

## Enablement

This project intend to use [socket mode](https://slack.dev/java-slack-sdk/guides/socket-mode) which rely on Websocket &
avoid the need to open HTTP webhook callback.

When creating and installing your app, don't forget to 

* Go to Settings > Basic Information > Add a new App-Level Token with the `connections:write` scope
* Get the generated token value that starts with xapp-

* Go to Settings > Socket Mode > Turn on `Enable Socket Mode`
* Configure the features (without setting Request URLs)

* Install the app to receive bot/user tokens (bot: xoxb-, user: xoxp-)

## Features

Events :

* Listen to event when bot is being called : `AppMentionEvent`

Settings > Features > [Slash commands](https://slack.dev/java-slack-sdk/guides/slash-commands)

* Respond to commands : `/hello`


## Release notes

### 0.0.1-SNAPSHOT - Current version

* Initial version with Bolt in Socket Mode