.DEFAULT_GOAL := build-run
build-run: verify run
clean:
	./mvnw clean
build: clean
	./mvnw package
verify: build
	./mvnw verify
run:
	java -jar ./target/casino-1.0-SNAPSHOT-jar-with-dependencies.jar