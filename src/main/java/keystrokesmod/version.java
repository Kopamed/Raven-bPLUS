package keystrokesmod;

import keystrokesmod.main.Ravenb3;
import scala.reflect.internal.pickling.UnPickler;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class version {
    public static final String versionFileName = "/assets/keystrokes/version";
    public static String currentVersion;
    public static String latestVersion;
    public static boolean outdated() {
        return true;
        /*InputStream input = version.class.getResourceAsStream(versionFileName);
        Scanner scanner = new Scanner(input);
        try {
            currentVersion = scanner.nextLine();
        } catch (Exception var47) {
            var47.printStackTrace();
            return false;
        }

        URL url = null;
        try {
            url = new URL("https://raw.githubusercontent.com/Kopamed/Raven-bPLUS/main/src/main/resources/assets/keystrokes/version");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            Scanner versionLook = new Scanner(br);
            latestVersion = versionLook.nextLine();
        } catch (Exception var48) {
            var48.printStackTrace();
            return false;
        }
        String[] currentVersionSplited = currentVersion.split(".");
        String[] latestVersionSplited = latestVersion.split(".");
        if (Integer.parseInt(latestVersionSplited[0]) > Integer.parseInt(currentVersionSplited[0])) {
            return true;
        }
        if (Integer.parseInt(latestVersionSplited[1]) > Integer.parseInt(currentVersionSplited[1])) {
            return true;
        }
        if (Integer.parseInt(latestVersionSplited[2]) > Integer.parseInt(currentVersionSplited[2])) {
            return true;
        }
        return false;*/
    }
}
