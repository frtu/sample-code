echo "-> Run bash individually - Type 'microservice-kotlinbuild' to build the project"
microservice-kotlinbuild() {
  echo "mvn clean package"
  mvn clean package
}
echo "-> Run bash individually - Type 'microservice-kotlinrun' to run the executable"
microservice-kotlinrun() {
  echo "Running executable > mvn spring-boot:run"
  mvn spring-boot:run
}

microservice-kotlinbuild
microservice-kotlinrun