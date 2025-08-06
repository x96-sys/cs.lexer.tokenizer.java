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
JACOCO_BASE    = https://maven.org/maven2/org/jacoco

GJF_VERSION   = 1.28.0
GJF_JAR       = $(TOOL_DIR)/google-java-format.jar
GJF_URL       = https://maven.org/maven2/com/google/googlejavaformat/google-java-format/$(GJF_VERSION)/google-java-format-$(GJF_VERSION)-all-deps.jar

JAVA_SOURCES := $(shell find $(SRC_MAIN) -name "*.java")
CLASS_FILES := $(patsubst $(SRC_MAIN)/%.java,$(MAIN_BUILD)/%.class,$(JAVA_SOURCES))

# Target principal que depende dos arquivos .class
build: $(CLASS_FILES)

# Regra para compilar arquivos .class individuais
$(MAIN_BUILD)/%.class: $(SRC_MAIN)/%.java | $(MAIN_BUILD)
	javac -d $(MAIN_BUILD) -cp $(MAIN_BUILD) $<

# Cria o diretório de build
$(MAIN_BUILD):
	mkdir -p $(MAIN_BUILD)

build-cli: build
	mkdir -p $(CLI_BUILD)
	javac -cp $(MAIN_BUILD) -d $(CLI_BUILD) \
	   $(SRC_CLI)/org/x96/sys/foundation/CLI.java

cli: build-cli
	java -cp $(MAIN_BUILD):$(CLI_BUILD) org.x96.sys.foundation.CLI $(ARGS)

$(TEST_BUILD):
	mkdir -p $(TEST_BUILD)

build-test: build tools/junit | $(TEST_BUILD)
	javac -cp $(MAIN_BUILD):$(JUNIT_JAR) -d $(TEST_BUILD) \
	   $(shell find $(SRC_TEST) -name "*.java")

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

test-method: build-test ## Executa teste específico (METHOD="Classe#método")
	@echo "🧪 Executando teste: $(METHOD)"
	@java -jar $(JUNIT_JAR) --class-path $(TEST_BUILD):$(MAIN_BUILD) --select "method:$(METHOD)"

test-class: build-test ## Executa classe de teste (CLASS="nome.da.Classe")
	@echo "🧪 Executando classe: $(CLASS)"
	@java -jar $(JUNIT_JAR) --class-path $(TEST_BUILD):$(MAIN_BUILD) --select "class:$(CLASS)"

format: tools/gjf ## Formata todo o código fonte Java com google-java-format
	find src -name "*.java" -print0 | xargs -0 java -jar $(GJF_JAR) --aosp --replace

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

tools/gjf: tools
	@if [ ! -f $(GJF_JAR) ]; then \
		echo "📦 Baixando Google Java Format..."; \
		curl -L -o $(GJF_JAR) $(GJF_URL); \
	else \
		echo "✅ Google Java Format já está em $(GJF_JAR)"; \
	fi

clean:
	rm -rf $(BUILD_DIR) $(TOOL_DIR)

.PHONY: build-cli cli build-test test coverage-run coverage-report coverage test-method test-class tools clean