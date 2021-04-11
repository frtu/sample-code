CONTAINER_NAME='postgres_database_1'

echo "Type 'startpostgres' to start postgres server"
startpostgres() {
  echo "Make sure you have ** docker-compose ** installed !!"
  (cd docker/ && exec docker-compose up)
}
echo "Type 'postgresrm' to delete docker image & remove data"
postgresrm() {
    echo "docker rm ${CONTAINER_NAME}"
    docker rm ${CONTAINER_NAME}

    rm -Rf docker/db/data/
}

startpostgres