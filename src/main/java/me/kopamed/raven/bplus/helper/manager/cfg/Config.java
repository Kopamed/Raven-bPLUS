package me.kopamed.raven.bplus.helper.manager.cfg;

import com.google.gson.JsonObject;
import me.kopamed.raven.bplus.helper.manager.version.Version;

import java.io.File;
import java.util.Date;

public class Config {
    private File        file;
    private String      configName;
    private JsonObject  jsonObject;

    private String      authorName;
    private String      notes;
    private Date        creationDate;
    private Date        lastModifiedDate;
    private long        usedFor;
    private Version     intendedVersion;


    private boolean readOnly;

    public Config(File file){
        this(file, false);
    }

    public Config(File file, boolean readOnly){
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
}
