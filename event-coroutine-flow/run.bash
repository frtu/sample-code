echo "-> Run bash individually - Type 'event-coroutine-flowbuild' to build the project"
event-coroutine-flowbuild() {
  echo "mvn clean package"
  mvn clean package
}
echo "-> Run bash individually - Type 'event-coroutine-flowrun' to run the executable"
event-coroutine-flowrun() {
  echo "Running executable > mvn spring-boot:run"
  mvn spring-boot:run
}

event-coroutine-flowbuild
event-coroutine-flowrun