echo "-> Run bash individually - Type 'spring-integration-kotlinbuild' to build the project"
spring-integration-kotlinbuild() {
  echo "mvn clean package"
  mvn clean package
}
echo "-> Run bash individually - Type 'spring-integration-kotlinrun' to run the executable"
spring-integration-kotlinrun() {
  echo "Running executable > mvn spring-boot:run"
  mvn spring-boot:run
}

spring-integration-kotlinbuild
spring-integration-kotlinrun