package me.kopamed.lunarkeystrokes.keystroke.setting;

public class Setting {
    private String name;
    private Type type;

    public Setting(String name, Type type){
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return this.name;
    }

    public static enum Type {
        COLOUR,
        TICK,
        SLIDER;
    }
}
