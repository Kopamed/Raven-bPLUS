package me.kopamed.raven.bplus.helper.manager;

public class DebugManager {
    private boolean debugging = false;

    public DebugManager(){

    }

    public boolean isDebugging() {
        return debugging;
    }

    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }
}
