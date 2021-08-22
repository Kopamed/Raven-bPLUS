package keystrokesmod;



import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import keystrokesmod.main.Ravenbplus;
import org.apache.http.client.methods.HttpPost;
import org.lwjgl.Sys;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.*;

public class URLUtils {
   public static String hypixelApiKey = "";
   public static String pasteApiKey = "";
   public static final String base_url = "https://api.paste.ee/v1/pastes/";
   public static final String base_paste = "{\"description\":\"Raven B+ Config\",\"expiration\":\"never\",\"sections\":[{\"name\":\"TitleGoesHere\",\"syntax\":\"text\",\"contents\":\"BodyGoesHere\"}]}";

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

   //public static getInfoFromPastee(String link){

   //}

   public static boolean isLink(String string){
      if(string.startsWith("http") && string.contains(".") && string.contains("://")) return true;
      return false;
   }

   public static boolean isPasteeLink(String link){
      if(isLink(link) && link.contains("paste.ee")) return true;
      return false;
   }

   public static String makeRawPasteePaste(String arg) {
      // https://api.paste.ee/v1/pastes/<id>
      // https://paste.ee/p/XZKFL

      StringBuilder rawLink = new StringBuilder();
      rawLink.append(base_url);
      rawLink.append(arg.split("/")[arg.split("/").length - 1]);
      return rawLink.toString();
   }

   public static String createPaste(String name, String content){


      try {
         HttpURLConnection request = (HttpURLConnection)(new URL(base_url)).openConnection();
         request.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
         request.setRequestProperty("X-Auth-Token", pasteApiKey);
         request.setRequestMethod("POST");
         request.setDoOutput(true);
         request.connect();
         OutputStream outputStream = request.getOutputStream();
         Throwable occuredErrors = null;
         String payload = base_paste.replace("TitleGoesHere", name).replace("BodyGoesHere", content).replace("\\", "");
         //System.out.println(payload);
         try {
            // sending data
            outputStream.write(payload.getBytes("UTF-8"));
            outputStream.flush();
         } catch (Throwable microsoftMoment) {
            occuredErrors = microsoftMoment;
            throw microsoftMoment;
         } finally {
            if (outputStream != null) {
               if (occuredErrors != null) {
                  try {
                     outputStream.close();
                  } catch (Throwable var48) {
                     occuredErrors.addSuppressed(var48);
                  }
               } else {
                  outputStream.close();
               }
            }

         }

         request.disconnect();
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
         JsonParser parser = new JsonParser();
         JsonObject json = (JsonObject) parser.parse(bufferedReader.readLine());
         //System.out.println(json);
         //System.out.println(json.get("link"));
         //System.out.println(pasteApiKey);
         return json.get("link").toString().replace("\"", "");
      } catch (Exception var51) {
         //System.out.println(pasteApiKey);
      }
      return "";
   }

   public static List<String> getConfigFromPastee(String link) {
      try {
         //System.out.println(link);
         HttpURLConnection request = (HttpURLConnection)(new URL(link)).openConnection();
         request.setRequestProperty("X-Auth-Token", pasteApiKey);
         request.setRequestMethod("GET");
         request.setDoOutput(true);
         //System.out.println(request.getResponseMessage());
         request.connect();

         //System.out.println(request.getResponseMessage());
         //System.out.println(request.getResponseCode());
         List<String> finall = new ArrayList<>();
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
         JsonParser parser = new JsonParser();
         JsonObject json = (JsonObject) parser.parse(bufferedReader.readLine());
         //System.out.println(json);
         JsonObject json2 = json.getAsJsonObject("paste");
         finall.add(true + "");
         JsonObject json3 = (JsonObject)  json2.getAsJsonArray("sections").get(0);
         finall.add(json3.get("name") + "");
         finall.add(json3.get("contents") + "");

         //System.out.println(json2.getAsJsonArray("sections"));
         //System.out.println(pasteApiKey);
         request.disconnect();
         return finall;
      } catch (Exception var51) {
         //System.out.println(pasteApiKey + "FUUUUUUU");
         var51.printStackTrace();
      }
      List<String> welp = new ArrayList<>();
      welp.add("false");
      return welp;
   }
}
