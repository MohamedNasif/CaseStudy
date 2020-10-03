package droidefense.cli;

import droidefense.log4j.Log;
import droidefense.log4j.LoggerType;
import droidefense.sdk.util.InternalConstant;
import droidefense.sdk.util.Util;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.IOException;

public class DroidefenseOptions extends Options {

    public DroidefenseOptions() {
        this.addOption("d", "debug", false, "print debugging information");
        this.addOption("p", "profile", false, "Wait for JVM profiler");
        this.addOption("v", "verbose", false, "be verbose");
        this.addOption("V", "version", false, "show current version information");
        this.addOption("h", "help", false, "print this message");
        this.addOption("s", "show", false, "show generated report after scan");

        this.addOption(
                Option.builder("u")
                        .longOpt("unpacker")
                        .desc("select prefered unpacker: [zip, apktool, memapktool]")
                        .hasArg()
                        .argName("unpacker")
                        .build()
        );

        this.addOption(
                Option.builder("i")
                        .longOpt("input")
                        .desc("input .apk to be analyzed")
                        .hasArg()
                        .argName("apk")
                        .build()
        );

        this.addOption(
                Option.builder("o")
                        .longOpt("output")
                        .desc("select prefered output: [json, json.min]")
                        .hasArg()
                        .argName("format")
                        .build()
        );
    }

    public void showHelp() {
        // automatically generate the help statement
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("droidefense", this);
        System.out.println();
        System.out.println("For additional info, please visit: https://github.com/droidefense/engine");
        System.out.println();
    }

    public void showVersion() {
        System.out.println(" > Current version:       " + InternalConstant.ENGINE_VERSION);
        System.out.println(" > Current build:         " + readVersion());
        System.out.println(" > Check out on Github:   " + InternalConstant.REPO_URL);
        System.out.println(" > Report your issue:     " + InternalConstant.ISSUES_URL);
        System.out.println(" > Lead developer:        " + InternalConstant.LEAD_DEVELOPER);
        System.out.println();
    }

    private String readVersion() {
        try {
            return Util.readFileFromInternalResourcesAsString(
                    DroidefenseOptions.class.getClassLoader(),
                    "/lastbuild");
        } catch (IOException e) {
            Log.write(LoggerType.ERROR, "Could not get version information");
            Log.write(LoggerType.ERROR, e.getLocalizedMessage());
            return "unknown";
        }
    }

    public void showAsciiBanner() {
        System.out.println();
        System.out.println();
        System.out.println("________               .__    .___      _____                            ");
        System.out.println("\\______ \\_______  ____ |__| __| _/_____/ ____\\____   ____   ______ ____  ");
        System.out.println(" |    |  \\_  __ \\/  _ \\|  |/ __ |/ __ \\   __\\/ __ \\ /    \\ /  ___// __ \\ ");
        System.out.println(" |    `   \\  | \\(  <_> )  / /_/ \\  ___/|  | \\  ___/|   |  \\\\___ \\\\  ___/ ");
        System.out.println("/_______  /__|   \\____/|__\\____ |\\___  >__|  \\___  >___|  /____  >\\___  >");
        System.out.println("        \\/                     \\/    \\/          \\/     \\/     \\/     \\/ ");
        System.out.println();
        System.out.println();
        System.out.println("droidefense v" + InternalConstant.ENGINE_VERSION + " - a tool for automated reverse engineering of Android apk files.");
        System.out.println("specially focused on Android Malware detection, feature extraction and reporting");
        System.out.println();
        System.out.println("Copyright 2017 Droidefense <droidefense@gmail.com>");
        System.out.println();
    }

    public void readKeyBoard() {
        try { System.in.read(); } catch (Exception e) {}
    }
}