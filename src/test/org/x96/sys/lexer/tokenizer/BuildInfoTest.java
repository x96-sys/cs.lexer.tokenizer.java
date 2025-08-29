package org.x96.sys.lexer.tokenizer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class BuildInfoTest {

    @Test
    public void testVersionNotEmpty() {
        assertNotNull(BuildInfo.VERSION, "VERSION should not be null");
        assertFalse(BuildInfo.VERSION.isEmpty(), "VERSION should not be empty");
    }

    @Test
    public void testVersionFormat() {
        // Pode conter tag, commit curto e -dirty
        assertTrue(
                BuildInfo.VERSION.matches(".*[0-9a-f]{6,}-dirty?|v?[0-9.]+-[0-9a-f]{6,}-dirty?"),
                "VERSION format should include commit or tag+commit, optionally -dirty");
    }

    @Test
    public void testTimestampNotEmpty() {
        assertNotNull(BuildInfo.BUILD_TIMESTAMP);
        assertFalse(BuildInfo.BUILD_TIMESTAMP.isEmpty());
    }

    @Test
    public void testHostUserOSJava() {
        assertNotNull(BuildInfo.BUILD_HOST);
        assertNotNull(BuildInfo.BUILD_USER);
        assertNotNull(BuildInfo.BUILD_OS);
        assertNotNull(BuildInfo.JAVA_VERSION);

        assertFalse(BuildInfo.BUILD_HOST.isEmpty());
        assertFalse(BuildInfo.BUILD_USER.isEmpty());
        assertFalse(BuildInfo.BUILD_OS.isEmpty());
        assertFalse(BuildInfo.JAVA_VERSION.isEmpty());
    }

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<BuildInfo> ctor = BuildInfo.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(ctor.getModifiers()), "Constructor should be private");
    }
}
