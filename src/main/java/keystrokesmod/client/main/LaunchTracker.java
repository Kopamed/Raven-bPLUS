package keystrokesmod.client.main;

import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class LaunchTracker {
    static void registerLaunch() throws IOException {
        HttpURLConnection pathsConnection = (HttpURLConnection) (new URL(
                "https://launchtracker.raventeam.repl.co/paths").openConnection());
        String pathsText = getTextFromConnection(pathsConnection);
        pathsConnection.disconnect();

        StringBuilder fullURL = new StringBuilder();
        fullURL.append("https://launchtracker.raventeam.repl.co");
        for (String line : pathsText.split("\n")) {
            if (line.startsWith("RavenB+")) {
                String[] splitLine = line.split(" ~ ");
                fullURL.append(splitLine[splitLine.length - 1]);
            }
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(fullURL.toString());

// Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        String mac = getMac();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] encodedhash = digest.digest(mac.getBytes(StandardCharsets.UTF_8));
        mac = bytesToHex(encodedhash);
        params.add(new BasicNameValuePair("hashedMacAddr", mac));
        params.add(new BasicNameValuePair("clientVersion", Raven.versionManager.getClientVersion().toString()));
        params.add(new BasicNameValuePair("latestVersion", Raven.versionManager.getLatestVersion().toString()));
        params.add(new BasicNameValuePair("config", Raven.configManager.getConfig().getData().toString()));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

//Execute and get the response.
        HttpResponse response = (HttpResponse) httpclient.execute(httppost);
        HttpResponseStatus entity = response.getStatus();
    }

    static String getMac() {
        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface ni = networkInterfaces.nextElement();
            byte[] hardwareAddress = new byte[0];
            try {
                hardwareAddress = ni.getHardwareAddress();
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
            if (hardwareAddress != null) {
                String[] hexadecimalFormat = new String[hardwareAddress.length];
                for (int i = 0; i < hardwareAddress.length; i++) {
                    hexadecimalFormat[i] = String.format("%02X", hardwareAddress[i]);
                }
                return String.join(":", hexadecimalFormat).toLowerCase();
            }
        }
        return "UNKNOWN";
    }

    private static String getTextFromConnection(HttpURLConnection connection) {
        if (connection != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String result;
                try {
                    StringBuilder stringBuilder = new StringBuilder();

                    String input;
                    while ((input = bufferedReader.readLine()) != null) {
                        stringBuilder.append(input).append("\n");
                    }

                    String res = stringBuilder.toString();
                    connection.disconnect();

                    result = res;
                } finally {
                    bufferedReader.close();
                }

                return result;
            } catch (Exception ignored) {
            }
        }

        return "";
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
