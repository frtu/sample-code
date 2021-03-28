echo "-> Run bash individually - Type 'message-broker-streambuild' to build the project"
message-broker-streambuild() {
  echo "mvn clean package"
  mvn clean package
}
echo "-> Run bash individually - Type 'message-broker-streamrun' to run the executable"
message-broker-streamrun() {
  echo "Running executable > java -jar target/message-broker-stream-0.0.1-SNAPSHOT.jar"
  java -jar target/message-broker-stream-0.0.1-SNAPSHOT.jar
}

message-broker-streambuild
message-broker-streamrun