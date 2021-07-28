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
    public static String currentVersion;
    public static String latestVersion;
    public static boolean outdated() {
        InputStream input = version.class.getResourceAsStream(versionFileName);
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
            System.out.println(latestVersion);
            System.out.println(currentVersion);
        } catch (Exception var48) {
            var48.printStackTrace();
            return false;
        }

        ArrayList<Integer> currentVersionSplited = new ArrayList<Integer>();
        ArrayList<Integer> latestVersionSplited = new ArrayList<Integer>();

        for (String whatHelp : currentVersion.split("-")) {
            currentVersionSplited.add(Integer.parseInt(whatHelp));
        }
        for (String whatHelp : latestVersion.split("-")) {
            latestVersionSplited.add(Integer.parseInt(whatHelp));
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
}
