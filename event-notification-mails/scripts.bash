CONTAINER_NAME='postgres_database_1'

echo "Type 'startpostgres' to start postgres server"
startpostgres() {
  echo "Make sure you have ** docker-compose ** installed !!"
  (cd docker/storage/ && exec docker-compose up)
}
echo "Type 'postgresrm' to delete docker image & remove data"
postgresrm() {
    echo "docker rm ${CONTAINER_NAME}"
    docker rm ${CONTAINER_NAME}

    rm -Rf docker/storage/db/data/
}

# Copied from https://github.com/frtu/governance-toolbox/tree/master/schema-registries/docker/confluentinc-schema-registry
echo "Type 'startkafka' to start kafka server"
startkafka() {
  echo "Make sure you have ** docker-compose ** installed !!"
  (cd docker/kafka/ && exec docker-compose up)
}

startpostgres