MVN := docker compose -f docker-compose-mvn.yml run --rm mvn

docker-clean-install:
	$(MVN) clean install

docker-test:
	$(MVN) test

run:
	java -jar target/Assignment-01-1.0.0.jar