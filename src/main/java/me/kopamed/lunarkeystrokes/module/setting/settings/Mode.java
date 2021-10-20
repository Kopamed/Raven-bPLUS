package me.kopamed.lunarkeystrokes.module.setting.settings;

import me.kopamed.lunarkeystrokes.module.setting.Setting;

import java.util.Arrays;
import java.util.List;

public class Mode extends Setting {
    private List<String> options;
    private int currentPos =0;
    private String currentMode;
    static String settingType = "mode";

    public Mode(String settingName, String[] modes, String defaultPos){
        super(settingName, settingType);

        this.options = Arrays.asList(modes);
        this.currentPos = this.options.indexOf(defaultPos);

        this.currentMode = this.options.get(currentPos);
    }


    public Mode(String settingName, String[] modes, int defaultPos){
        super(settingName, settingType);

        this.options = Arrays.asList(modes);
        this.currentPos = defaultPos;

        this.currentMode = this.options.get(currentPos);
    }

    public String getMode(){
        return this.currentMode;
    }

    public void setMode(String val){
        this.currentMode = val;
        this.currentPos = this.options.indexOf(val);
    }

    public void nextMode(){
        if(currentPos < this.options.size() - 1){
            this.currentPos++;
            this.currentMode = this.options.get(currentPos);
        }else {
            this.currentPos = 0;
            this.currentMode = this.options.get(currentPos);
        }
    }
}
