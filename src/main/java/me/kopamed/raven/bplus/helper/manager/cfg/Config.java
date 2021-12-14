package me.kopamed.raven.bplus.helper.manager.cfg;

import com.google.gson.JsonObject;
import me.kopamed.raven.bplus.helper.manager.version.Version;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private File        file;
    private String      configName;
    private JsonObject  jsonObject;

    private String      authorName;
    private String      notes;
    private String      intendedServer;
    private Date        creationDate;
    private Date        lastModifiedDate;
    private long        usedFor;
    private Version     intendedVersion;

    private boolean     readOnly;

    public Config(File file){
        this(file, false);
    }

    public Config(File file, boolean readOnly){
        if(!file.exists()) {  // shouldnt really be a thing fix this in the future pls
            try {
                file.createNewFile();
                System.out.println("MADE FILE HFUH UregresgsregsregsergsergsreergEHF HOEW FHEOP");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.readOnly = readOnly;
        this.file = file;
        this.configName = file.getName();
        getJson();
    }

    private void getJson() {/*
        Object obj = parser.parse(new FileReader("//cdn.crunchify.com/Users/Shared/crunchify.json"));

        // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
        JSONObject jsonObject = (JSONObject) obj;

        // A JSON array. JSONObject supports java.util.List interface.
        JSONArray companyList = (JSONArray) jsonObject.get("Company List");

        // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
        // Iterators differ from enumerations in two ways:
        // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
        // 2. Method names have been improved.
        Iterator<JSONObject> iterator = companyList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }*/
    }

    // todo create read and write opperations

    public File getFile(){
        return file;
    }

    public Map getHeaders() {
        Map<String, String> bruh = new HashMap<>();
        bruh.put("author", x(authorName));
        bruh.put("notes", x(notes));
        bruh.put("server", x(intendedServer));
        bruh.put("version", x(intendedVersion != null ? intendedVersion.toString() : ""));
        return bruh;
    }

    private String x(String s){
        return (s == null || s.isEmpty()) ? "" : s;
    }

    public boolean isReadOnly() {
        return readOnly;
    }
}
