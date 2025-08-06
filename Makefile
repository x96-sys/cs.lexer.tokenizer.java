BUILD_DIR     = build
MAIN_BUILD    = $(BUILD_DIR)/main
CLI_BUILD     = $(BUILD_DIR)/cli
TEST_BUILD    = $(BUILD_DIR)/test

SRC_MAIN      = src/main
SRC_CLI       = src/cli
SRC_TEST      = src/test

TOOL_DIR      = tools
LIB_DIR       = lib

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

FLUX_VERSION  = 1.0.0
FLUX_JAR      = $(LIB_DIR)/org.x96.sys.foundation.io.jar
FLUX_URL      = https://github.com/x96-sys/flux.java/releases/download/v$(FLUX_VERSION)/org.x96.sys.foundation.io.jar

JAVA_SOURCES := $(shell find $(SRC_MAIN) -name "*.java")

# Target principal que depende dos arquivos .class
build: generate-build-info lib/flux compile-all

compile-all: | $(MAIN_BUILD)
	javac -d $(MAIN_BUILD) -cp $(FLUX_JAR) $(JAVA_SOURCES)


# Cria o diretório de build
$(MAIN_BUILD):
	mkdir -p $(MAIN_BUILD)

build-cli: build
	mkdir -p $(CLI_BUILD)
	javac -cp $(MAIN_BUILD):$(FLUX_JAR) -d $(CLI_BUILD) \
	   $(SRC_CLI)/org/x96/sys/foundation/CLI.java

cli: build-cli
	java -cp $(MAIN_BUILD):$(CLI_BUILD):$(FLUX_JAR) org.x96.sys.foundation.CLI $(ARGS)

$(TEST_BUILD):
	mkdir -p $(TEST_BUILD)

build-test: build build-cli tools/junit | $(TEST_BUILD)
	javac -cp $(MAIN_BUILD):$(CLI_BUILD):$(FLUX_JAR):$(JUNIT_JAR) -d $(TEST_BUILD) \
	   $(shell find $(SRC_TEST) -name "*.java")

test: build-test
	java -jar $(JUNIT_JAR) \
	   execute \
	   --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CLI_BUILD):$(FLUX_JAR) \
	   --scan-class-path

coverage-run: build-test tools/jacoco
	java -javaagent:$(JACOCO_AGENT)=destfile=$(BUILD_DIR)/jacoco.exec \
	     -jar $(JUNIT_JAR) \
	     execute \
	     --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CLI_BUILD):$(FLUX_JAR) \
	     --scan-class-path

coverage-report: tools/jacoco
	java -jar $(JACOCO_CLI) report \
	   $(BUILD_DIR)/jacoco.exec \
	   --classfiles $(MAIN_BUILD) \
	   --classfiles $(CLI_BUILD) \
	   --sourcefiles $(SRC_MAIN) \
	   --sourcefiles $(SRC_CLI) \
	   --html $(BUILD_DIR)/coverage \
	   --name "Coverage Report"

coverage: coverage-run coverage-report
	@echo "✅ Relatório de cobertura disponível em: build/coverage/index.html"
	@echo "🌐 Abrir com: open build/coverage/index.html"

test-method: build-test ## Executa teste específico (METHOD="Classe#método")
	@echo "🧪 Executando teste: $(METHOD)"
	@java -jar $(JUNIT_JAR) --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CLI_BUILD):$(FLUX_JAR) --select "method:$(METHOD)"

test-class: build-test ## Executa classe de teste (CLASS="nome.da.Classe")
	@echo "🧪 Executando classe: $(CLASS)"
	@java -jar $(JUNIT_JAR) --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CLI_BUILD):$(FLUX_JAR) --select "class:$(CLASS)"

format: tools/gjf ## Formata todo o código fonte Java com google-java-format
	find src -name "*.java" -print0 | xargs -0 java -jar $(GJF_JAR) --aosp --replace

build-info: generate-build-info ## Força a regeneração do BuildInfo

lib:
	mkdir -p lib

lib/flux: lib
	@if [ ! -f $(FLUX_JAR) ]; then \
		echo "📦 Baixando FLUX..."; \
		curl -L -o $(FLUX_JAR) $(FLUX_URL); \
	else \
		echo "✅ FLUX já está em $(FLUX_JAR)"; \
	fi

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

# Gera automaticamente o arquivo BuildInfo.java
generate-build-info:
	@echo "🔧 Gerando BuildInfo..."
	@mkdir -p $(SRC_MAIN)/org/x96/sys/foundation
	@printf 'package org.x96.sys.foundation;\n\n' > $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java
	@printf '/**\n * Informações de build geradas automaticamente pelo Makefile\n * Não edite este arquivo manualmente!\n */\n' >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java
	@printf 'public final class BuildInfo {\n' >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java
	@if command -v git >/dev/null 2>&1 && git rev-parse --git-dir >/dev/null 2>&1; then \
		VERSION=$$(git describe --tags --always --dirty 2>/dev/null || echo "v0.1.0-unknown"); \
		if echo "$$VERSION" | grep -q "^v[0-9]"; then \
			MAJOR=$$(echo "$$VERSION" | sed 's/^v\([0-9]*\)\..*/\1/'); \
			MINOR=$$(echo "$$VERSION" | sed 's/^v[0-9]*\.\([0-9]*\)\..*/\1/'); \
			PATCH=$$(echo "$$VERSION" | sed 's/^v[0-9]*\.[0-9]*\.\([0-9]*\).*/\1/'); \
		else \
			MAJOR="0"; MINOR="1"; PATCH="0"; \
		fi; \
	else \
		VERSION="v0.1.0-no-git"; \
		MAJOR="0"; MINOR="1"; PATCH="0"; \
	fi; \
	BUILD_DATE=$$(date '+%Y-%m-%d %H:%M:%S'); \
	BUILD_USER=$$(whoami); \
	printf '    public static final String VERSION = "%s";\n' "$$VERSION" >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java; \
	printf '    public static final String BUILD_DATE = "%s";\n' "$$BUILD_DATE" >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java; \
	printf '    public static final String BUILD_USER = "%s";\n' "$$BUILD_USER" >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java; \
	printf '    public static final String VERSION_MAJOR = "%s";\n' "$$MAJOR" >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java; \
	printf '    public static final String VERSION_MINOR = "%s";\n' "$$MINOR" >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java; \
	printf '    public static final String VERSION_PATCH = "%s";\n' "$$PATCH" >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java
	@printf '\n    private BuildInfo() {\n        // Classe utilitária - não deve ser instanciada\n    }\n\n' >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java
	@printf '    public static String getFullVersion() {\n' >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java
	@printf '        return VERSION + " (built on " + BUILD_DATE + " by " + BUILD_USER + ")";\n' >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java
	@printf '    }\n}\n' >> $(SRC_MAIN)/org/x96/sys/foundation/BuildInfo.java
	@echo "✅ BuildInfo gerado com sucesso!"



clean:
	rm -rf $(BUILD_DIR) $(TOOL_DIR) $(LIB_DIR)

.PHONY: build-cli cli build-test test coverage-run coverage-report coverage test-method test-class tools lib clean generate-build-info build-info compile-all
