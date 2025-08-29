BUILD_DIR     = out
MAIN_BUILD    = $(BUILD_DIR)/main
CLI_BUILD     = $(BUILD_DIR)/cli
TEST_BUILD    = $(BUILD_DIR)/test

SRC_MAIN      = src/main
SRC_CLI       = src/cli
SRC_TEST      = src/test

TOOLS_DIR      = tools
LIB_DIR       = lib

IO_VERSION = 1.1.0
IO_JAR     = $(LIB_DIR)/org.x96.sys.io.jar
IO_URL     = https://github.com/x96-sys/io.java/releases/download/v$(IO_VERSION)/org.x96.sys.io.jar
IO_SHA256  = e18d2fdb894386bd24bb08f178e4a6566d7feadaaf8e96d32bd6d9c5dc63c474

TOKEN_VERSION = 1.0.0
TOKEN_JAR     = $(LIB_DIR)/org.x96.sys.lexer.token.jar
TOKEN_URL     = https://github.com/x96-sys/cs.lexer.token.java/releases/download/v$(TOKEN_VERSION)/org.x96.sys.lexer.token.jar
TOKEN_SHA256  = b58fa314148954ec78d3ead11a434da2670d6d64837807087d2b541190fcf40d

KIND_VERSION = 1.0.0
KIND_JAR     = $(LIB_DIR)/org.x96.sys.lexer.token.kind.jar
KIND_URL     = https://github.com/x96-sys/lexer.token.kind.java/releases/download/v$(KIND_VERSION)/org.x96.sys.lexer.token.kind.jar
KIND_SHA256  = 55d12618cd548099d138cbc1e7beda2b78e6a09382ec725523e82f7eb5a31c69

BUZZ_VERSION = 1.0.0
BUZZ_JAR     = $(LIB_DIR)/org.x96.sys.buzz.jar
BUZZ_URL     = https://github.com/x96-sys/buzz.java/releases/download/v$(BUZZ_VERSION)/org.x96.sys.buzz.jar
BUZZ_SHA256  = c4f30d580a9dea5db83f0dd0256de247ca217e62f401e5c06392c5b61909efa1

JUNIT_VERSION = 1.13.4
JUNIT_JAR     = $(TOOLS_DIR)/junit-platform-console-standalone.jar
JUNIT_URL     = https://maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$(JUNIT_VERSION)/junit-platform-console-standalone-$(JUNIT_VERSION).jar
JUNIT_SHA256  = 3fdfc37e29744a9a67dd5365e81467e26fbde0b7aa204e6f8bbe79eeaa7ae892

JACOCO_VERSION = 0.8.13
JACOCO_BASE    = https://maven.org/maven2/org/jacoco

JACOCO_CLI_VERSION = $(JACOCO_VERSION)
JACOCO_CLI_JAR     = $(TOOLS_DIR)/jacococli.jar
JACOCO_CLI_URL     = $(JACOCO_BASE)/org.jacoco.cli/$(JACOCO_CLI_VERSION)/org.jacoco.cli-$(JACOCO_CLI_VERSION)-nodeps.jar
JACOCO_CLI_SHA256  = 8f748683833d4dc4d72cea5d6b43f49344687b831e0582c97bcb9b984e3de0a3

JACOCO_AGENT_VERSION = $(JACOCO_VERSION)
JACOCO_AGENT_JAR     = $(TOOLS_DIR)/jacocoagent-runtime.jar
JACOCO_AGENT_URL     = $(JACOCO_BASE)/org.jacoco.agent/$(JACOCO_AGENT_VERSION)/org.jacoco.agent-$(JACOCO_AGENT_VERSION)-runtime.jar
JACOCO_AGENT_SHA256  = 47e700ccb0fdb9e27c5241353f8161938f4e53c3561dd35e063c5fe88dc3349b

GJF_VERSION = 1.28.0
GJF_JAR     = $(TOOLS_DIR)/gjf.jar
GJF_URL     = https://maven.org/maven2/com/google/googlejavaformat/google-java-format/$(GJF_VERSION)/google-java-format-$(GJF_VERSION)-all-deps.jar
GJF_SHA256  = 32342e7c1b4600f80df3471da46aee8012d3e1445d5ea1be1fb71289b07cc735

BUILD_INFO = https://gist.githubusercontent.com/tfs91/d8a380974ee7f640e0692855b643ec01/raw/4d3958befd1f77e4d62a4be8133878316e43a061/generate_build_info.rb

JAVA_SOURCES      = $(shell find $(SRC_MAIN) -name "*.java")
JAVA_TEST_SOURCES = $(shell find $(SRC_TEST) -name "*.java")

DISTRO_JAR     = org.x96.sys.lexer.tokenizer.jar
DISTRO_CLI_JAR = org.x96.sys.lexer.tokenizer.cli.jar
CLI_PATH      = org/x96/sys/lexer/tokenizer/CLI.java
CLI_CLASS     = org.x96.sys.lexer.tokenizer.CLI

CP  = $(IO_JAR):$(TOKEN_JAR):$(KIND_JAR)
CPT = $(IO_JAR):$(TOKEN_JAR):$(KIND_JAR):$(BUZZ_JAR):$(JUNIT_JAR)

build/info:
	@curl -sSL $(BUILD_INFO) | ruby - src/main/ org.x96.sys.lexer.tokenizer

build: libs clean/build/main build/info
	@javac -d $(MAIN_BUILD) -cp $(CP) $(JAVA_SOURCES)
	@echo "[🦾] [compiled] [$(MAIN_BUILD)]"

build/cli: build
	@mkdir -p $(CLI_BUILD)
	@javac -cp $(MAIN_BUILD):$(CP) -d $(CLI_BUILD) \
	    $(SRC_CLI)/$(CLI_PATH)

build/test: kit build build/cli
	@javac -cp $(MAIN_BUILD):$(CLI_BUILD):$(CPT) -d $(TEST_BUILD) \
     $(JAVA_TEST_SOURCES)

cli: build/cli
	java -cp $(MAIN_BUILD):$(CLI_BUILD):$(CP) ? $(ARGS)

test: build/test
	@java -jar $(JUNIT_JAR) \
     execute \
     --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CLI_BUILD):$(CPT) \
     --scan-class-path

coverage-run: build/test
	@java -javaagent:$(JACOCO_AGENT)=destfile=$(BUILD_DIR)/jacoco.exec \
       -jar $(JUNIT_JAR) \
       execute \
       --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CLI_BUILD):$(CPT) \
       --scan-class-path

coverage-report:
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

test-method: build/test ## Executa teste específico (METHOD="Classe#método")
	@echo "🧪 Executando teste: $(METHOD)"
	@java -jar $(JUNIT_JAR) --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CP) --select "method:$(METHOD)"

test-class: build/test ## Executa classe de teste (CLASS="nome.da.Classe")
	@echo "🧪 Executando classe: $(CLASS)"
	@java -jar $(JUNIT_JAR) --class-path $(TEST_BUILD):$(MAIN_BUILD):$(CP) --select "class:$(CLASS)"

format: kit
	@find src -name "*.java" -print0 | xargs -0 java -jar $(GJF_JAR) --aosp --replace
	@echo "✅ Formatação concluída com sucesso!"

distro:
	@jar cf $(DISTRO_JAR) -C $(MAIN_BUILD) .
	@echo "[📦] [lib] criada com sucesso! $(DISTRO_JAR)"

distro/cli:
	@echo "Main-Class: $(CLI_CLASS)" > manifest.txt
	@jar cfm $(DISTRO_CLI_JAR) manifest.txt -C $(CLI_BUILD) . -C $(MAIN_BUILD) .
	@rm manifest.txt
	@echo "[📦] [cli] Empacotado com sucesso em $(DISTRO_CLI_JAR)"


define deps
$1/$2: $1
	@expected="$($3_SHA256)"; \
	jar="$($3_JAR)"; \
	url="$($3_URL)"; \
	tmp="$$$$(mktemp)"; \
	if [ ! -f "$$$$jar" ]; then \
		echo "[📦] [🚛] [$($3_VERSION)] [$2]"; \
		curl -sSL -o "$$$$tmp" "$$$$url"; \
		actual="$$$$(shasum -a 256 $$$$tmp | awk '{print $$$$1}')"; \
		if [ "$$$$expected" = "$$$$actual" ]; then mv "$$$$tmp" "$$$$jar"; \
		echo "[📦] [📍] [$($3_VERSION)] [$2] [🐚]"; else rm "$$$$tmp"; \
		echo "[❌] [hash mismatch] [$2]"; exit 1; fi; \
	else \
		actual="$$$$(shasum -a 256 $$$$jar | awk '{print $$$$1}')"; \
		if [ "$$$$expected" = "$$$$actual" ]; \
		then echo "[📦] [📍] [$($3_VERSION)] [🐚] [$2]"; \
		else \
			echo "[❌] [hash mismatch] [$2]"; \
			curl -sSL -o "$$$$tmp" "$$$$url"; \
			actual="$$$$(shasum -a 256 $$$$tmp | awk '{print $$$$1}')"; \
			if [ "$$$$expected" = "$$$$actual" ]; then mv "$$$$tmp" "$$$$jar"; \
			echo "[📦] [♻️] [$($3_VERSION)] [🐚] [$2]"; else rm "$$$$tmp"; \
			echo "[❌] [download failed] [$2]"; exit 1; fi; \
		fi; \
	fi
endef

libs: \
	$(LIB_DIR)/io \
	$(LIB_DIR)/token \
	$(LIB_DIR)/kind \
	$(LIB_DIR)/buzz

$(eval $(call deps,$(LIB_DIR),io,IO))
$(eval $(call deps,$(LIB_DIR),token,TOKEN))
$(eval $(call deps,$(LIB_DIR),kind,KIND))
$(eval $(call deps,$(LIB_DIR),buzz,BUZZ))

kit: \
	$(TOOLS_DIR)/junit \
	$(TOOLS_DIR)/gjf \
	$(TOOLS_DIR)/jacoco_cli \
	$(TOOLS_DIR)/jacoco_agent

$(eval $(call deps,$(TOOLS_DIR),junit,JUNIT))
$(eval $(call deps,$(TOOLS_DIR),gjf,GJF))
$(eval $(call deps,$(TOOLS_DIR),jacoco_cli,JACOCO_CLI))
$(eval $(call deps,$(TOOLS_DIR),jacoco_agent,JACOCO_AGENT))

$(BUILD_DIR) $(MAIN_BUILD) $(TEST_BUILD) $(TOOLS_DIR) $(LIB_DIR):
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
	@rm -rf $(TOOLS_DIR)
	@echo "[🛀] [clean] [$(TOOLS_DIR)]"

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
