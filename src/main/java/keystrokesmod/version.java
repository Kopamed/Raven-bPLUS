package keystrokesmod;

import keystrokesmod.main.Ravenb3;
import org.lwjgl.Sys;
import scala.Int;
import scala.reflect.internal.pickling.UnPickler;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class version {
    public static final String versionFileName = "/assets/keystrokes/version";
    public static final String branchFileName = "/assets/keystrokes/branch";
    public static String currentVersion = null;
    public static String latestVersion = null;
    public static String branch = null;

    public static boolean outdated() {
        currentVersion = getCurrentVersion();
        latestVersion = getLatestVersion();
        if (latestVersion.isEmpty())
            return false;

        ArrayList<Integer> currentVersionSplited = new ArrayList<Integer>();
        ArrayList<Integer> latestVersionSplited = new ArrayList<Integer>();

        for (String whatHelp : currentVersion.split("-")) {
            currentVersionSplited.add(Integer.parseInt(whatHelp));
        }
        for (String whatHelp : latestVersion.split("-")) {
            try {
                latestVersionSplited.add(Integer.parseInt(whatHelp));
            } catch (Exception klojaanPlayingMinecraftBeLike) {
                klojaanPlayingMinecraftBeLike.printStackTrace();
                return false;
            }
        }

        if (latestVersionSplited.get(0) > currentVersionSplited.get(0)) {
            return true;
        }
        if (latestVersionSplited.get(1) > currentVersionSplited.get(1)) {
            return true;
        }
        if (latestVersionSplited.get(2) > currentVersionSplited.get(2)) {
            return true;
        }
        return false;
    }

    public static String getCurrentVersion() {
        if (currentVersion != null) {
            //////System.out.println("Fast return");
            return currentVersion;
        }
        InputStream input = version.class.getResourceAsStream(versionFileName);
        Scanner scanner = new Scanner(input);
        try {
            currentVersion = scanner.nextLine();
        } catch (Exception var47) {
            var47.printStackTrace();
            return "";
        }
        return currentVersion;
    }

    public static  String getLatestVersion() {
        if (latestVersion != null) {
            //////System.out.println("Fast return");
            return latestVersion;
        }

        URL url = null;
        try {
            url = new URL("https://raw.githubusercontent.com/Kopamed/Raven-bPLUS/main/src/main/resources/assets/keystrokes/version");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (Exception klojangamingmoment) {
            klojangamingmoment.printStackTrace();
            return "";
        }

        try {
            Scanner versionLook = new Scanner(br);
            latestVersion = versionLook.nextLine();
        } catch (Exception var48) {
            var48.printStackTrace();
            return "";
        }
        return latestVersion;
    }

    public static  String getBranch() {
        if (branch != null) {
            //////System.out.println("Fast return");
            return branch;
        }

        URL url = null;
        try {
            url = new URL("https://raw.githubusercontent.com/Kopamed/Raven-bPLUS/main/src/main/resources/assets/keystrokes/branch");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (Exception klojangamingmoment) {
            klojangamingmoment.printStackTrace();
            return "";
        }

        try {
            Scanner versionLook = new Scanner(br);
            branch = versionLook.nextLine();
        } catch (Exception var48) {
            var48.printStackTrace();
            return "";
        }
        return branch;
    }

    public static boolean isBeta() {
        InputStream input = version.class.getResourceAsStream(branchFileName);
        Scanner scanner = new Scanner(input);
        try {
            if(scanner.nextLine().equalsIgnoreCase("beta")){
                return true;
            }
        } catch (Exception var47) {
            var47.printStackTrace();
        }


        currentVersion = getCurrentVersion();
        latestVersion = getLatestVersion();

        ArrayList<Integer> currentVersionSplited = new ArrayList<Integer>();
        ArrayList<Integer> latestVersionSplited = new ArrayList<Integer>();

        for (String whatHelp : currentVersion.split("-")) {
            currentVersionSplited.add(Integer.parseInt(whatHelp));
        }
        for (String whatHelp : latestVersion.split("-")) {
            latestVersionSplited.add(Integer.parseInt(whatHelp));
        }

        if (latestVersionSplited.get(0) < currentVersionSplited.get(0)) {
            return true;
        }
        if (latestVersionSplited.get(1) < currentVersionSplited.get(1)) {
            return true;
        }
        if (latestVersionSplited.get(2) < currentVersionSplited.get(2)) {
            return true;
        }
        return false;
    }
}
