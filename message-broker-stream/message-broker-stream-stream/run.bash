echo "-> Run bash individually - Type 'message-broker-streambuild' to build the project"
message-broker-streambuild() {
  echo "mvn clean package"
  mvn clean package
}
echo "-> Run bash individually - Type 'message-broker-streamrun' to run the executable"
message-broker-streamrun() {
  echo "Running executable > mvn spring-boot:run"
  mvn spring-boot:run
}

message-broker-streambuild
message-broker-streamrun