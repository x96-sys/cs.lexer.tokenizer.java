package org.x96.sys.foundation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CLITest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void testVersionOption() {
    CLI.main(new String[] { "-v" });
    String output = outContent.toString().trim();
    assertEquals(BuildInfo.VERSION, output);
  }

  @Test
  public void testVersionLongOption() {
    CLI.main(new String[] { "--version" });
    String output = outContent.toString().trim();
    assertEquals(BuildInfo.VERSION, output);
  }

  @Test
  public void testBuildInfoOption() {
    CLI.main(new String[] { "-b" });
    String output = outContent.toString();
    assertTrue(output.contains("Build Information:"));
    assertTrue(output.contains("Version: " + BuildInfo.VERSION));
    assertTrue(output.contains("Build Date: " + BuildInfo.BUILD_DATE));
    assertTrue(output.contains("Build User: " + BuildInfo.BUILD_USER));
    assertTrue(output.contains("Major: " + BuildInfo.VERSION_MAJOR));
    assertTrue(output.contains("Minor: " + BuildInfo.VERSION_MINOR));
    assertTrue(output.contains("Patch: " + BuildInfo.VERSION_PATCH));
    assertTrue(output.contains("Full: " + BuildInfo.getFullVersion()));
  }

  @Test
  public void testBuildInfoLongOption() {
    CLI.main(new String[] { "--build-info" });
    String output = outContent.toString();
    assertTrue(output.contains("Build Information:"));
    assertTrue(output.contains("Version: " + BuildInfo.VERSION));
  }

  @Test
  public void testHelpOption() {
    CLI.main(new String[] { "-h" });
    String output = outContent.toString();
    assertTrue(output.contains("Usage:"));
    assertTrue(output.contains("Options:"));
    assertTrue(output.contains("-v, --version"));
    assertTrue(output.contains("-b, --build-info"));
    assertTrue(output.contains("-h, --help"));
  }

  @Test
  public void testHelpLongOption() {
    CLI.main(new String[] { "--help" });
    String output = outContent.toString();
    assertTrue(output.contains("Usage:"));
    assertTrue(output.contains("Options:"));
  }

  @Test
  public void testNoArguments() {
    CLI.main(new String[] {});
    String output = outContent.toString();
    assertTrue(output.contains("Usage:"));
    assertTrue(output.contains("Options:"));
  }

  @Test
  public void testDefaultBehavior() {
    // Testa o comportamento original - processa texto como tokens
    CLI.main(new String[] { "a" });
    String output = outContent.toString();
    assertFalse(output.isEmpty());
    // Deve conter informações sobre o token 'a'
    assertTrue(output.length() > 0);
  }
}
