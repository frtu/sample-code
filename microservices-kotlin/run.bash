echo "-> Run bash individually - Type 'microservices-kotlinbuild' to build the project"
microservices-kotlinbuild() {
  echo "mvn clean package"
  mvn clean package
}
echo "-> Run bash individually - Type 'microservices-kotlinrun' to run the executable"
microservices-kotlinrun() {
  echo "Running executable > mvn spring-boot:run"
  mvn spring-boot:run
}

microservices-kotlinbuild
microservices-kotlinrun