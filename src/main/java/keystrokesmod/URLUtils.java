package keystrokesmod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

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
}
