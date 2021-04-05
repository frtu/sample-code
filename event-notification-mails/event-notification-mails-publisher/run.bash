echo "-> Run bash individually - Type 'event-notification-mailsbuild' to build the project"
event-notification-mailsbuild() {
  echo "mvn clean package"
  mvn clean package
}
echo "-> Run bash individually - Type 'event-notification-mailsrun' to run the executable"
event-notification-mailsrun() {
  echo "Running executable > java -jar target/event-notification-mails-0.0.1-SNAPSHOT.jar"
  java -jar target/event-notification-mails-0.0.1-SNAPSHOT.jar
}

event-notification-mailsbuild
event-notification-mailsrun