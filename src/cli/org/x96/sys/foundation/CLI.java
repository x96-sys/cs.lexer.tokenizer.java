package org.x96.sys.foundation;

import org.x96.sys.foundation.tokenizer.token.Kind;

public class CLI {
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String command = args[0];

        switch (command) {
            case "-v":
            case "--version":
                System.out.println(BuildInfo.VERSION);
                break;
            case "-b":
            case "--build-info":
                printBuildInfo();
                break;
            case "-h":
            case "--help":
                printUsage();
                break;
            default:
                // Comportamento original - processa argumentos como tokens
                for (String arg : args) {
                    for (byte b : arg.getBytes()) {
                        System.out.println(Kind.is(b));
                    }
                }
                break;
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java org.x96.sys.foundation.CLI [options] [text]");
        System.out.println("Options:");
        System.out.println("  -v, --version     Show version");
        System.out.println("  -b, --build-info  Show build information");
        System.out.println("  -h, --help        Show this help");
        System.out.println("  text              Process text as tokens (default behavior)");
    }

    private static void printBuildInfo() {
        System.out.println("Build Information:");
        System.out.println("  Version: " + BuildInfo.VERSION);
        System.out.println("  Build Date: " + BuildInfo.BUILD_DATE);
        System.out.println("  Build User: " + BuildInfo.BUILD_USER);
        System.out.println("  Major: " + BuildInfo.VERSION_MAJOR);
        System.out.println("  Minor: " + BuildInfo.VERSION_MINOR);
        System.out.println("  Patch: " + BuildInfo.VERSION_PATCH);
        System.out.println("  Full: " + BuildInfo.getFullVersion());
    }
}
