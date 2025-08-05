BUILD_DIR     = build
MAIN_BUILD    = $(BUILD_DIR)/main
CLI_BUILD     = $(BUILD_DIR)/cli
TEST_BUILD    = $(BUILD_DIR)/test

SRC_MAIN      = src/main
SRC_CLI       = src/cli
SRC_TEST      = src/test

TOOL_DIR      = tools
JUNIT_VERSION = 1.13.4
JUNIT_JAR     = $(TOOL_DIR)/junit-platform-console-standalone.jar
JUNIT_URL     = https://maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$(JUNIT_VERSION)/junit-platform-console-standalone-$(JUNIT_VERSION).jar

build:
	mkdir -p $(MAIN_BUILD)
	javac -d $(MAIN_BUILD) \
		$(SRC_MAIN)/org/x96/sys/foundation/tokenizer/token/Kind.java \
		$(SRC_MAIN)/org/x96/sys/foundation/Hello.java

build-cli: build
	mkdir -p $(CLI_BUILD)
	javac -cp $(MAIN_BUILD) -d $(CLI_BUILD) \
		$(SRC_CLI)/org/x96/sys/foundation/CLI.java

cli: build-cli
	java -cp $(MAIN_BUILD):$(CLI_BUILD) org.x96.sys.foundation.CLI $(ARGS)

build-test: build tools/junit
	javac -cp $(MAIN_BUILD):$(JUNIT_JAR) -d $(TEST_BUILD) \
		src/test/org/x96/sys/foundation/tokenizer/token/KindTest.java

test: build-test
	java -jar $(JUNIT_JAR) \
		execute \
		--class-path $(TEST_BUILD):$(MAIN_BUILD) \
		--scan-class-path

tools:
	mkdir -p tools

tools/junit: tools
	@if [ ! -f $(JUNIT_JAR) ]; then \
		echo "📦 Baixando JUnit..."; \
		curl -L -o $(JUNIT_JAR) $(JUNIT_URL); \
	else \
		echo "✅ JUnit já está em $(JUNIT_JAR)"; \
	fi

clean:
	rm -rf $(BUILD_DIR) $(TOOL_DIR)
