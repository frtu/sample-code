echo "-> Run bash individually - Type 'event-notification-web-coroutine-flowbuild' to build the project"
event-notification-web-coroutine-flowbuild() {
  echo "mvn clean package"
  mvn clean package
}
echo "-> Run bash individually - Type 'event-notification-web-coroutine-flowrun' to run the executable"
event-notification-web-coroutine-flowrun() {
  echo "Running executable > mvn spring-boot:run"
  mvn spring-boot:run
}

event-notification-web-coroutine-flowbuild
event-notification-web-coroutine-flowrun