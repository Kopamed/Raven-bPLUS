package me.kopamed.raven.bplus.helper.manager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class VersionManager {
    private final String versionFilePath = "/assets/raven/version/version";
    private final String branchFilePath = "/assets/raven/version/branch";
    private final String sourceURL = "https://github.com/Kopamed/Raven-bPLUS";
    private final String discord = "https://discord.gg/N4zn4FwPcz";

    private boolean error = false;

    private boolean outdated = false;
    private boolean beta = false;
    private String currentVersion;
    private String latestVersion;

    public VersionManager() {
        setUpVersions();
        setUpBeta();
        setUpOutdated();
    }

    private void setUpBeta() {
        InputStream input = VersionManager.class.getResourceAsStream(branchFilePath);
        Scanner scanner = new Scanner(input);
        try {
            if(scanner.nextLine().split("-")[0].equalsIgnoreCase("beta")){
                this.beta = true;
            }
        } catch (Exception var47) {
            var47.printStackTrace();
        }
    }

    private void setUpOutdated() {
        if(error)
            return;


        ArrayList<Integer> currentVersionSplited = new ArrayList<Integer>();
        ArrayList<Integer> latestVersionSplited = new ArrayList<Integer>();

        for (String whatHelp : currentVersion.split("-")) {
            currentVersionSplited.add(Integer.parseInt(whatHelp));
        }
        for (String whatHelp : latestVersion.split("-")) {
            latestVersionSplited.add(Integer.parseInt(whatHelp));
        }

        if (latestVersionSplited.get(0) < currentVersionSplited.get(0)) {
            this.outdated = true;
        }
        if (latestVersionSplited.get(1) < currentVersionSplited.get(1)) {
            this.outdated = true;
        }
        if(latestVersionSplited.get(2) < currentVersionSplited.get(2)){
            this.outdated = true;
        }
    }

    private void setUpVersions() {
        // setting up currentVersion
        InputStream input = VersionManager.class.getResourceAsStream(versionFilePath);
        Scanner scanner = new Scanner(input);
        try {
            this.currentVersion = scanner.nextLine();
        } catch (Exception var47) {
            var47.printStackTrace();
            this.error = true;
        }

        // setting up latest version
        URL url = null;
        try {
            url = new URL("https://raw.githubusercontent.com/Kopamed/Raven-bPLUS/main/src/main/resources/assets/keystrokes/version");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            this.error = true;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (Exception koljanisaskidwhotokenlogspeoplefuckhim) {
            koljanisaskidwhotokenlogspeoplefuckhim.printStackTrace();
            this.error = true;
        }

        try {
            Scanner versionLook = new Scanner(br);
            this.latestVersion = versionLook.nextLine();
        } catch (Exception var48) {
            var48.printStackTrace();
            this.error = true;
        }
    }

    public boolean isError() {
        return error;
    }

    public boolean isOutdated() {
        return outdated;
    }

    public boolean isBeta() {
        return beta;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public String getDiscord() {
        return discord;
    }
}
