echo "-> Run bash individually - Type 'workflow-activiti-event-drivenbuild' to build the project"
workflow-activiti-event-drivenbuild() {
  echo "mvn clean package"
  mvn clean package
}
echo "-> Run bash individually - Type 'workflow-activiti-event-drivenk8s' to run the executable"
workflow-activiti-event-drivenk8s() {
  echo "Running executable > mvn k8s:resource k8s:build"
  mvn k8s:resource k8s:build
}
echo "-> Run bash individually - Type 'workflow-activiti-event-drivenregistryk8s' to run the executable"
workflow-activiti-event-drivenk8sregistry() {
  echo "Running executable > mvn k8s:resource k8s:build k8s:push -Pregistry-k8s"
  mvn k8s:resource k8s:build k8s:push -Pregistry-k8s
}
echo "-> Run bash individually - Type 'workflow-activiti-event-drivenrun' to run the executable"
workflow-activiti-event-drivenrun() {
  echo "Running executable > mvn spring-boot:run"
  mvn spring-boot:run
}
