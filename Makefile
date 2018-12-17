.DEFAULT_GOAL := build-run
build:
	./mvnw package
build-run: clean build run
clean:
	./mvnw clean
run:
	java -jar ./target/casino-1.0-SNAPSHOT-jar-with-dependencies.jar