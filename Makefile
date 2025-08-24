BUILD_DIR     = out
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

CS_FLUX_VERSION  = 1.0.1
CS_FLUX_JAR      = $(LIB_DIR)/org.x96.sys.foundation.io.jar
CS_FLUX_URL      = https://github.com/x96-sys/flux.java/releases/download/v$(CS_FLUX_VERSION)/org.x96.sys.foundation.io.jar

CS_TOKEN_VERSION = 0.1.3
CS_TOKEN_JAR     = $(LIB_DIR)/org.x96.sys.foundation.cs.lexer.token.jar
CS_TOKEN_URL     = https://github.com/x96-sys/cs.lexer.token.java/releases/download/v$(CS_TOKEN_VERSION)/org.x96.sys.foundation.cs.lexer.token.jar

CS_KIND_VERSION = 0.1.3
CS_KIND_JAR     = $(LIB_DIR)/org.x96.sys.foundation.cs.lexer.token.kind.jar
CS_KIND_URL     = https://github.com/x96-sys/cs.lexer.token.kind.java/releases/download/$(CS_KIND_VERSION)/org.x96.sys.foundation.cs.lexer.token.kind.jar

CS_VISITOR_VERSION = 0.1.5
CS_VISITOR_JAR     = $(LIB_DIR)/org.x96.sys.foundation.cs.lexer.visitor.jar
CS_VISITOR_URL     = https://github.com/x96-sys/cs.lexer.visitor.java/releases/download/v$(CS_VISITOR_VERSION)/org.x96.sys.foundation.cs.lexer.visitor.jar


JAVA_SOURCES = $(shell find $(SRC_MAIN) -name "*.java")

DISTRO_JAR = org.x96.sys.foundation.cs.lexer.tokenizer.jar

CP = $(CS_FLUX_JAR):$(CS_TOKEN_JAR):$(CS_KIND_JAR):$(CS_VISITOR_JAR)

build: libs clean/build/main generate-build-info
	@javac -d $(MAIN_BUILD) -cp $(CP) $(JAVA_SOURCES)
	@echo "[🦾] [compiled] [$(MAIN_BUILD)]"

build/cli: build
	@mkdir -p $(CLI_BUILD)
	@javac -cp $(MAIN_BUILD):$(CP) -d $(CLI_BUILD) \
	    $(SRC_CLI)/org/x96/sys/foundation/CLI.java

build/test: build build/cli tools/junit
	@javac -cp $(JUNIT_JAR):$(MAIN_BUILD):$(CLI_BUILD):$(CP) -d $(TEST_BUILD) \
     $(shell find $(SRC_TEST) -name "*.java")



cli: build/cli
	java -cp $(MAIN_BUILD):$(CLI_BUILD):$(CP) org.x96.sys.foundation.CLI $(ARGS)


test: build/test
	@java -jar $(JUNIT_JAR) \
     execute \
     --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CLI_BUILD):$(CP) \
     --scan-class-path

coverage-run: build-test tools/jacoco
	@java -javaagent:$(JACOCO_AGENT)=destfile=$(BUILD_DIR)/jacoco.exec \
       -jar $(JUNIT_JAR) \
       execute \
       --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CLI_BUILD):$(CP) \
       --scan-class-path

coverage-report: tools/jacoco
	@java -jar $(JACOCO_CLI) report \
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
	@java -jar $(JUNIT_JAR) --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CP) --select "method:$(METHOD)"

test-class: build-test ## Executa classe de teste (CLASS="nome.da.Classe")
	@echo "🧪 Executando classe: $(CLASS)"
	@java -jar $(JUNIT_JAR) --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CP) --select "class:$(CLASS)"

format: kit
	@find src -name "*.java" -print0 | xargs -0 java -jar $(GJF_JAR) --aosp --replace
	@echo "✅ Formatação concluída com sucesso!"



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

distro: lib
	@jar cf $(DISTRO_JAR) -C $(MAIN_BUILD) .
	@echo "✅ Distribuição criada com sucesso! $(DISTRO_JAR)"

define deps
$1/$2: $1
	@if [ ! -f "$$($3_JAR)" ]; then \
		echo "[📦] [🚛] [$$($3_VERSION)] [$2]"; \
		curl -sSL -o $$($3_JAR) $$($3_URL); \
	else \
		echo "[📦] [📍] [$$($3_VERSION)] [$2]"; \
	fi
endef

libs: \
	$(LIB_DIR)/flux \
	$(LIB_DIR)/token \
	$(LIB_DIR)/kind \
	$(LIB_DIR)/visitor

$(eval $(call deps,$(LIB_DIR),flux,CS_FLUX))
$(eval $(call deps,$(LIB_DIR),token,CS_TOKEN))
$(eval $(call deps,$(LIB_DIR),kind,CS_KIND))
$(eval $(call deps,$(LIB_DIR),visitor,CS_VISITOR))

$(eval $(call deps,tools,junit,JUNIT))


$(BUILD_DIR) $(MAIN_BUILD) $(TEST_BUILD) $(TOOL_DIR) $(LIB_DIR):
	mkdir -p $@

clean/build:
	@rm -rf $(BUILD_DIR)
	@echo "[🧽] [clean] [$(BUILD_DIR)]"

clean/build/main:
	@rm -rf $(MAIN_BUILD)
	@echo "[🧼] [clean] [$(MAIN_BUILD)]"

clean/build/test:
	@rm -rf $(TEST_BUILD)
	@echo "[🧹] [clean] [$(TEST_BUILD)]"

clean/build/cli:
	@rm -rf $(CLI_BUILD)
	@echo "[🧹] [clean] [$(CLI_BUILD)]"

clean/kit:
	@rm -rf $(TOOL_DIR)
	@echo "[🛀] [clean] [$(TOOL_DIR)]"

clean/libs:
	@rm -rf $(LIB_DIR)
	@echo "[🥽] [clean] [$(LIB_DIR)]"

clean: \
	clean/build \
	clean/build/main \
	clean/build/test \
	clean/kit \
	clean/libs
	@echo "[🔬] [clean]"
