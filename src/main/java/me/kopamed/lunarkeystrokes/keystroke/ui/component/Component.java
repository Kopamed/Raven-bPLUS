package me.kopamed.lunarkeystrokes.keystroke.ui.component;

import me.kopamed.lunarkeystrokes.keystroke.ui.KeyStrokeConfigGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class Component {
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int height;
    private int width;
    private int x, y;

    public double getTextSize() {
        return textSize;
    }

    public void setTextSize(double textSize) {
        this.textSize = textSize;
    }

    private double textSize;
    private net.minecraft.client.gui.FontRenderer fontRenderer;

    public boolean isMouseDown() {
        return mouseDown;
    }

    public void setMouseDown(boolean mouseDown) {
        this.mouseDown = mouseDown;
    }

    public boolean mouseDown;

    public static int maxStringLen = 65;

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    private boolean hovered;

    public void draw(){

    }

    public boolean isMouseOver(int x, int y){
        if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.width)
                return true;
        return false;
    }

    public void backgroundProcess(int x, int y){
        // following line might be resource intensive so I might have to change that later if that is an issue
        setHovered(isMouseOver(x, y)); // setting our "hovered" boolean to true if the mouse is over our component, and false if it is not.
    }

    public Component(int x, int y, int width, int height, double textSize) {
        this.height = height;
        this.width = width;
        this.y = y;
        this.x = x;
        this.textSize = textSize;
        this.fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    }

    public FontRenderer getFontRender(){
        return this.fontRenderer;
    }
}
