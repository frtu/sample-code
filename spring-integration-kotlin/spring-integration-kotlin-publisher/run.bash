echo "-> Run bash individually - Type 'spring-integration-kotlinbuild' to build the project"
spring-integration-kotlinbuild() {
  echo "mvn clean package"
  mvn clean package
}
echo "-> Run bash individually - Type 'spring-integration-kotlinrun' to run the executable"
spring-integration-kotlinrun() {
  echo "Running executable > java -jar target/spring-integration-kotlin-0.0.1-SNAPSHOT.jar"
  java -jar target/spring-integration-kotlin-0.0.1-SNAPSHOT.jar
}

spring-integration-kotlinbuild
spring-integration-kotlinrun