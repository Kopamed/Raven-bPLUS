package keystrokesmod.client.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.*;

public class Config {
    public final File file;
    public final long creationDate;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Config(File pathToFile) {
        long creationDate1;
        this.file = pathToFile;

        if (!file.exists()) {
            creationDate1 = System.currentTimeMillis();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                creationDate1 = getData().get("creationTime").getAsLong();
            } catch (NullPointerException e) {
                creationDate1 = 0L;
            }
        }
        this.creationDate = creationDate1;
    }

    public String getName() {
        return file.getName().replace(".bplus", "");
    }

    public JsonObject getData() {
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(file)) {
            JsonElement obj = jsonParser.parse(reader);
            return obj.isJsonNull() ? null : obj.getAsJsonObject();
        } catch (JsonSyntaxException | ClassCastException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(JsonObject data) {
        data.addProperty("creationTime", creationDate);
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.write(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
