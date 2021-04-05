echo "-> Run bash individually - Type 'event-notification-mailsbuild' to build the project"
event-notification-mailsbuild() {
  echo "mvn clean package"
  mvn clean package
}
echo "-> Run bash individually - Type 'event-notification-mailsrun' to run the executable"
event-notification-mailsrun() {
  echo "Running executable > mvn spring-boot:run"
  mvn spring-boot:run
}

event-notification-mailsbuild
event-notification-mailsrun