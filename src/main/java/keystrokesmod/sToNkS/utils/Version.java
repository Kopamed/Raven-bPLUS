package keystrokesmod.sToNkS.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Version {
    public static final String versionFileName = "/assets/keystrokes/version";
    public static final String branchFileName = "/assets/keystrokes/branch";

    public static String currentVersion = "";
    public static String latestVersion = "";

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

        ArrayList<Integer> currentVersionSplited = new ArrayList<>();
        ArrayList<Integer> latestVersionSplited = new ArrayList<>();

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

        if (latestVersionSplited.get(0) > currentVersionSplited.get(0)) return true;
        if (latestVersionSplited.get(1) > currentVersionSplited.get(1)) return true;

        return latestVersionSplited.get(2) > currentVersionSplited.get(2);
    }

    public static String getCurrentVersion() {
        if (currentVersion != null) /* cached version */ return currentVersion;

        try (InputStream input = Version.class.getResourceAsStream(versionFileName)) {
            if (input == null) throw new NullPointerException("input == null !");

            Scanner scanner = new Scanner(input);
            currentVersion = scanner.nextLine();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }

        return currentVersion;

    }

    public static String getLatestVersion() {
        if (latestVersion != null) /* cached version */ return latestVersion;

        try (
            InputStream is = new URL("https://raw.githubusercontent.com/Kopamed/Raven-bPLUS/main/src/main/resources" + versionFileName).openStream()
        ) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Scanner versionLook = new Scanner(br);
            latestVersion = versionLook.nextLine();
        } catch (IOException klojangamingmoment) {
            klojangamingmoment.printStackTrace();
            return "";
        }

        return latestVersion;
    }

    public static  String getSelfBranch() {
        if (branch != null) return branch;

        try (
            InputStream is = new URL("https://raw.githubusercontent.com/Kopamed/Raven-bPLUS/main/src/main/resources" + branchFileName).openStream()
        ){
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Scanner versionLook = new Scanner(br);
            branch = versionLook.nextLine().split("-")[0];
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return branch;
    }

    public static boolean isBeta() {
        if (beta != null) {
            return beta.equals("beta");
        }

        try (
            InputStream input = Version.class.getResourceAsStream(branchFileName)
        ){
            if (input == null) throw new NullPointerException("input == null !");

            Scanner scanner = new Scanner(input);
            if (scanner.nextLine().split("-")[0].equalsIgnoreCase("beta")) {
                beta = "beta";
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        currentVersion = getCurrentVersion();
        latestVersion = getLatestVersion();

        ArrayList<Integer> currentVersionSplited = new ArrayList<>();
        ArrayList<Integer> latestVersionSplited = new ArrayList<>();

        for (String whatHelp : currentVersion.split("-"))
            currentVersionSplited.add(Integer.parseInt(whatHelp));

        for (String whatHelp : latestVersion.split("-"))
            latestVersionSplited.add(Integer.parseInt(whatHelp));

        if (latestVersionSplited.get(0) < currentVersionSplited.get(0)) return true;
        if (latestVersionSplited.get(1) < currentVersionSplited.get(1)) return true;

        return latestVersionSplited.get(2) < currentVersionSplited.get(2);
    }

    public static int getSelfBetaVersion() {
        if (betaSelfVersion != -1) return betaSelfVersion;

        try (
            InputStream input = Version.class.getResourceAsStream(branchFileName)
        ){
            if (input == null) throw new NullPointerException("input == null !");

            Scanner scanner = new Scanner(input);
            String meinkfragt = scanner.nextLine();
            if (meinkfragt.split("-")[0].equalsIgnoreCase("beta")) {
                betaSelfVersion = Integer.parseInt(meinkfragt.split("-")[1]);
                return betaSelfVersion;
            }
        } catch (Exception var47) {
            var47.printStackTrace();
        }

        return -1;
    }

    public static int getLatestBetaVersion() {
        if (betaLatestVersion != -1) return betaLatestVersion;

        try (
            InputStream is = new URL("https://raw.githubusercontent.com/Kopamed/Raven-bPLUS/main/src/main/resources" + branchFileName).openStream()
        ){
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            Scanner scanner = new Scanner(br);
            String sigma_monero_miner = scanner.nextLine();
            if (sigma_monero_miner.split("-")[0].equalsIgnoreCase("beta")) {
                betaLatestVersion = Integer.parseInt(sigma_monero_miner.split("-")[1]);
                return betaLatestVersion;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        return -1;
    }

    public static String getFullVersion() {
        return getCurrentVersion().replace("-", ".") + "." + getReadBranch().replace("-", ".");
    }

    public static String getReadBranch() {
        if (readBranch != null) return readBranch;

        InputStream input = Version.class.getResourceAsStream(branchFileName);
        Scanner scanner = new Scanner(input);

        readBranch = scanner.nextLine();
        return readBranch;
    }
}
