# Project - workflow-temporal

## About

Demontrating a complex multi microservice arch using Temporal.

## Structure

[subscription-service](workflows%2Fsubscription-service) is the service listening to Kafka & bridge to Temporal
using `SubscriptionHandler` :

* [subscription-workflow](workflows%2Fsubscription-workflow) is a module that host
  internal `SubscriptionWorkflow`. `SubscriptionWorkflowImpl` create async calls to 2 child
  workflows `ReminderWorkflow.startReminder`
* [reminder-workflow](workflows%2Freminder-workflow) is a module that host
  internal `ReminderWorkflow` that calls `EmailSinkActivity` and awaits, until an event is received that change
  ReminderStatus to DONE

[email-activity](activities%2Femail-activity) is separate service that expose `EmailSinkActivity` that is suppose to
send a kafka event to configured `application.topic.domain-source`.

## About

## Release notes

### 0.0.1-SNAPSHOT - Current version

* Feature list