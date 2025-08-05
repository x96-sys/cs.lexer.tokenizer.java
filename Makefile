build:
	mkdir -p build
	javac -d build \
		src/main/org/x96/sys/foundation/Hello.java \
		src/cli/org/x96/sys/foundation/CLI.java

run:
	java -cp build org.x96.sys.foundation.CLI

clean:
	rm -rf build
