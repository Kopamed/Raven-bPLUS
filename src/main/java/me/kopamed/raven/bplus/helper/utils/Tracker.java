package me.kopamed.raven.bplus.helper.utils;

public class Tracker {
    private final String osName = System.getProperty("os.name").toLowerCase();
    private final String osArch = System.getProperty("os.arch").toLowerCase();

    public Tracker(){
        // dafaq Runtime.getRuntime().addShutdownHook(new Thread(ex::shutdown));
    }

    public void registerLaunch() {
        // todo
        //ex.execute(() -> Utils.URLS.getTextFromURL(numberOfUseTracker));
    }

    public String getOsArch() {
        return osArch;
    }

    public String getOsName() {
        return osName;
    }
}
