.DEFAULT_GOAL := compile-run
compile-run: compile run
clean:
	rm -rf ./target
compile: clean
	mkdir -p ./target/classes
	javac -d ./target/classes ./src/main/java/games/Slot.java
run:
	java -cp ./target/classes games.Slot