package keystrokesmod;



import org.apache.http.client.methods.HttpPost;
import org.lwjgl.Sys;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class URLUtils {
   public static String hypixelApiKey = "";
   public static String pasteApiKey = "";

   public static boolean isHypixelKeyValid(String ak) {
      String c = getTextFromURL("https://api.hypixel.net/key?key=" + ak);
      return !c.isEmpty() && !c.contains("Invalid");
   }

   public static String getTextFromURL(String _url) {
      String r = "";
      HttpURLConnection con = null;

      try {
         URL url = new URL(_url);
         con = (HttpURLConnection)url.openConnection();
         r = getTextFromConnection(con);
      } catch (IOException ignored) {
      } finally {
         if (con != null) {
            con.disconnect();
         }

      }

      return r;
   }

   private static String getTextFromConnection(HttpURLConnection connection) {
      if (connection != null) {
         try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String result;
            try {
               StringBuilder stringBuilder = new StringBuilder();

               String input;
               while((input = bufferedReader.readLine()) != null) {
                  stringBuilder.append(input);
               }

               String res = stringBuilder.toString();
               connection.disconnect();

               result = res;
            } finally {
               bufferedReader.close();
            }

            return result;
         } catch (Exception ignored) {}
      }

      return "";
   }

   public static List<String> getConfigFromURL(String _url){
      List<String> r = new ArrayList<String>();
      HttpURLConnection con = null;

      try {
         URL url = new URL(_url);
         con = (HttpURLConnection)url.openConnection();
         r = getTextListFromConnection(con);
      } catch (IOException ignored) {
      } finally {
         if (con != null) {
            con.disconnect();
         }

      }

      return r;
   }

   private static List<String> getTextListFromConnection(HttpURLConnection connection) {
      if (connection != null) {
         try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            List<String> result;
            try {
               List<String> stringBuilder = new ArrayList<String>();

               String input;
               while((input = bufferedReader.readLine()) != null) {
                  stringBuilder.add(input);
               }
               connection.disconnect();

               result = stringBuilder;
            } finally {
               bufferedReader.close();
            }

            return result;
         } catch (Exception ignored) {}
      }

      return new ArrayList<String>();
   }

   public static boolean isLink(String string){
      if(string.startsWith("http") && string.contains(".") && string.contains("://")) return true;
      return false;
   }

   public static boolean isPastebinLink(String link){
      if(isLink(link) && link.contains("://pastebin.com")) return true;
      return false;
   }

   public static String makeRawPastebinPaste(String arg) {
      https://pastebin.com/fwfwefew
      if(arg.contains("raw")) return arg;

      StringBuilder rawLink = new StringBuilder();
      rawLink.append(arg.substring(0, arg.lastIndexOf("/")));
      rawLink.append("/raw");
      rawLink.append(arg.substring(arg.lastIndexOf("/")));
      return rawLink.toString();
   }

   public static String createPaste() throws IOException {

      String paste_api = "https://pastebin.com/api/api_post.php";
      /*
      HttpURLConnection con5 = (HttpURLConnection)(new URL(paste_api)).openConnection();
      con5.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
      con5.setRequestProperty("api_dev_key", "jRCgqXVQswMFxePS_2XkL5W-wsT6PyMg");
      con5.setRequestProperty("api_option", "paste");
      con5.setRequestProperty("api_paste_code", "yes" + System.currentTimeMillis());
      con5.setRequestMethod("POST");
      con5.setDoOutput(true);
      con5.connect();
      OutputStream os = con5.getOutputStream();
      os.flush();
      System.out.println(con5.getResponseMessage());
      con5.disconnect();

      URL url = new URL(paste_api);
      URLConnection con = url.openConnection();
      HttpURLConnection http = (HttpURLConnection)con;
      http.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
      http.setRequestProperty("api_dev_key", "jRCgqXVQswMFxePS_2XkL5W-wsT6PyMg");
      http.setRequestProperty("api_option", "paste");
      http.setRequestProperty("api_paste_code", "yes" + System.currentTimeMillis());
      http.setRequestMethod("POST"); // PUT is another valid option
      http.setDoOutput(true);
      try(OutputStream oss = http.getOutputStream()){

      }
      http.connect();
      System.out.println(http.getResponseMessage());

*/
      URL url = new URL(paste_api);

// Open a connection(?) on the URL(??) and cast the response(???)
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

// Now it's "open", we can set the request method, headers etc.
      connection.setRequestProperty("accept", "application/json");
      connection.setRequestProperty("api_dev_key", "nopeeking");
      connection.setRequestProperty("api_option", "paste");
      connection.setRequestProperty("api_paste_code", "yes" + System.currentTimeMillis());
      connection.setRequestMethod("POST");
      System.out.println(connection.getResponseCode());
      connection.connect();
      System.out.println(connection.getResponseCode());
// This line makes the request
      InputStream responseStream = connection.getInputStream();
      connection.disconnect();
// Manually converting the response body InputStream to APOD using Jackson
      Scanner scanner = new Scanner(responseStream);
      while(scanner.hasNextLine()){
         System.out.println(scanner.nextLine());
      }

      return "";
   }
}
