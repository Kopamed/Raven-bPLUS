package me.kopamed.lunarkeystrokes.keystroke.setting.settings;

import me.kopamed.lunarkeystrokes.keystroke.setting.Setting;

import java.awt.*;

public class ColourSetting extends Setting {
    private int red;
    private int green;
    private int blue;

    public int maxPos = 255;

    public ColourSetting(String name, int defaultRed, int defaultGreen, int defaultBlue){
        super(name, Type.COLOUR);
        this.red = defaultRed;
        this.green = defaultGreen;
        this.blue = defaultBlue;
    }

    public ColourSetting(String name, Color color){
        super(name, Type.COLOUR);
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public Color getRGB(){
        return new Color(this.red, this.green, this.blue);
    }
}
