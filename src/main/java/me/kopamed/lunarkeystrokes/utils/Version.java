package me.kopamed.lunarkeystrokes.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Version {
    public static final String versionFileName = "/assets/keystrokes/version";
    public static final String branchFileName = "/assets/keystrokes/branch";
    public static String currentVersion = null;
    public static String latestVersion = null;
    public static String branch = null;
    public static String beta = null;
    public static int betaSelfVersion = -1;
    public static int betaLatestVersion = -1;
    public static String readBranch = null;

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
        return latestVersionSplited.get(2) > currentVersionSplited.get(2);
    }

    public static String getCurrentVersion() {
        if (currentVersion != null) {
            //////////System.out.println("Fast return");
            return currentVersion;
        }
        InputStream input = Version.class.getResourceAsStream(versionFileName);
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
            //////////System.out.println("Fast return");
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

    public static  String getSelfBranch() {
        if (branch != null) {
            //////////System.out.println("Fast return");
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
            branch = versionLook.nextLine().split("-")[0];
        } catch (Exception var48) {
            var48.printStackTrace();
            return "";
        }
        return branch;
    }

    public static boolean isBeta() {
        if(beta != null) {
            return beta == "beta";
        }

        InputStream input = Version.class.getResourceAsStream(branchFileName);
        Scanner scanner = new Scanner(input);
        try {
            if(scanner.nextLine().split("-")[0].equalsIgnoreCase("beta")){
                beta = "beta";
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
        return latestVersionSplited.get(2) < currentVersionSplited.get(2);
    }

    public static int getSelfBetaVersion() {
        if(betaSelfVersion != -1){
            return betaSelfVersion;
        }

        InputStream input = Version.class.getResourceAsStream(branchFileName);
        Scanner scanner = new Scanner(input);
        try {
            String meinkfragt = scanner.nextLine();
            if(meinkfragt.split("-")[0].equalsIgnoreCase("beta")) {
                betaSelfVersion = Integer.parseInt(meinkfragt.split("-")[1]);
                return betaSelfVersion;
            }
        } catch (Exception var47) {
            var47.printStackTrace();
        }
        return -1;
    }

    public static int getLatestBetaVersion() {
        if(betaLatestVersion != -1) {
            return betaLatestVersion;
        }

        URL url = null;
        try {
            url = new URL("https://raw.githubusercontent.com/Kopamed/Raven-bPLUS/main/src/main/resources/assets/keystrokes/branch");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return -1;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (Exception klojangamingmoment) {
            klojangamingmoment.printStackTrace();
            return -1;
        }

        try {
            Scanner scanner = new Scanner(br);
            String sigma_monero_miner = scanner.nextLine();
            if(sigma_monero_miner.split("-")[0].equalsIgnoreCase("beta")) {
                betaLatestVersion = Integer.parseInt(sigma_monero_miner.split("-")[1]);
                return betaLatestVersion;
            }
        } catch (Exception var48) {
            var48.printStackTrace();
            return -1;
        }
        return -1;
    }

    public static String getFullversion(){
        return getCurrentVersion().replace("-", ".") + "." + getReadBranch().replace("-", ".");
    }

    public static String getReadBranch(){
        if(readBranch != null)
            return readBranch;

        InputStream input = Version.class.getResourceAsStream(branchFileName);
        Scanner scanner = new Scanner(input);

        readBranch = scanner.nextLine();
        return readBranch;
    }
}
