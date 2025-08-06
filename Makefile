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

JACOCO_VERSION = 0.8.13
JACOCO_CLI     = $(TOOL_DIR)/jacococli.jar
JACOCO_AGENT   = $(TOOL_DIR)/jacocoagent-runtime.jar
JACOCO_BASE    = https://repo1.maven.org/maven2/org/jacoco

build:
	mkdir -p $(MAIN_BUILD)
	javac -d $(MAIN_BUILD) \
		$(SRC_MAIN)/org/x96/sys/foundation/tokenizer/token/Kind.java

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

coverage-run: build-test tools/jacoco
	java -javaagent:$(JACOCO_AGENT)=destfile=$(BUILD_DIR)/jacoco.exec \
	     -jar $(JUNIT_JAR) \
	     execute \
	     --class-path $(TEST_BUILD):$(MAIN_BUILD) \
	     --scan-class-path

coverage-report: tools/jacoco
	java -jar $(JACOCO_CLI) report \
		$(BUILD_DIR)/jacoco.exec \
		--classfiles $(MAIN_BUILD) \
		--sourcefiles $(SRC_MAIN) \
		--html $(BUILD_DIR)/coverage \
		--name "Coverage Report"

coverage: coverage-run coverage-report
	@echo "✅ Relatório de cobertura disponível em: build/coverage/index.html"
	@echo "🌐 Abrir com: open build/coverage/index.html"

tools:
	mkdir -p tools

tools/junit: tools
	@if [ ! -f $(JUNIT_JAR) ]; then \
		echo "📦 Baixando JUnit..."; \
		curl -L -o $(JUNIT_JAR) $(JUNIT_URL); \
	else \
		echo "✅ JUnit já está em $(JUNIT_JAR)"; \
	fi

tools/jacoco: tools
	@if [ ! -f $(JACOCO_CLI) ] || [ ! -f $(JACOCO_AGENT) ]; then \
		echo "📦 Baixando JaCoCo..."; \
		curl -L -o $(JACOCO_CLI) $(JACOCO_BASE)/org.jacoco.cli/$(JACOCO_VERSION)/org.jacoco.cli-$(JACOCO_VERSION)-nodeps.jar && \
		curl -L -o $(JACOCO_AGENT) $(JACOCO_BASE)/org.jacoco.agent/$(JACOCO_VERSION)/org.jacoco.agent-$(JACOCO_VERSION)-runtime.jar; \
	else \
		echo "✅ JaCoCo já está em tools/"; \
	fi

clean:
	rm -rf $(BUILD_DIR) $(TOOL_DIR)
