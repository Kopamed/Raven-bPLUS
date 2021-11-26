package me.kopamed.raven.bplus.client.visual.clickgui.plus.component;

import me.kopamed.raven.bplus.client.feature.setting.SelectorRunnable;
import me.superblaubeere27.client.utils.fontRenderer.GlyphPageFontRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

public abstract class Component {
    private ArrayList<Component> components = new ArrayList<Component>();

    private boolean locationSet = false;
    private double x;
    private double y;

    private boolean sizeSet = false;
    private double width;
    private double height;

    private boolean bgSet = false;
    private Color background;
    protected boolean
            mouseDown = false;

    protected boolean draggable = false;
    protected boolean dragging = false;
    protected double windowStartDragX;
    protected double windowStartDragY;
    protected double mouseStartDragX;
    protected double mouseStartDragY;
    private boolean visible = true;

    public double getX() {
        if(!locationSet)
            throw new NullPointerException("You havent set the location retard");
        return x;
    }

    public double getY() {
        if(!locationSet)
            throw new NullPointerException("You havent set the location retard");
        return y;
    }

    public double getWidth() {
        if(!sizeSet)
            throw new NullPointerException("You havent set the size retard");
        return width;
    }

    public double getHeight() {
        if(!sizeSet)
            throw new NullPointerException("You havent set the size retard");
        return height;
    }

    public void setLocation(double x, double y){
        this.locationSet = true;
        this.x = x;
        this.y = y;
    }

    public void setSize(double width, double height){
        this.sizeSet = true;
        this.width = width;
        this.height = height;
    }

    public Color getColor() {
        if(!bgSet)
            throw new NullPointerException("You havent set the color retard");
        return background;
    }

    public void setColor(Color background) {
        this.bgSet = true;
        this.background = background;
    }

    public void paint(GlyphPageFontRenderer fr){
        Gui.drawRect((int)this.getX(), (int)this.getY(), (int)(this.getX() + this.getWidth()), (int)(this.getY() + this.getHeight()), this.getColor().getRGB());
        for(Component component: this.getComponents()){
            component.paint(fr);
        }
    }

    public void add(Component component){
        this.components.add(component);
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> c){
        this.components = c;
    }

    public void clearComponents(){
        this.components.clear();
    }

    public void update(int x, int y){
        if(draggable && dragging){
                this.x = windowStartDragX + (x - mouseStartDragX);
                this.y = windowStartDragX + (x - mouseStartDragX);
        }

        for(Component component : components){
            component.update(x, y);
        }
    }

    public boolean isMouseDown() {
        return mouseDown;
    }

    public void mouseDown(int x, int y, int mb) {
        this.mouseDown = true;

        if(draggable && mouseOver(x, y) && Keyboard.isKeyDown(29)){
            this.dragging = true;
            this.mouseStartDragX = x;
            this.mouseStartDragY = y;
            this.windowStartDragX = getX();
            this.windowStartDragY = getY();
        }

        for(Component component : components){
            component.mouseDown(x, y, mb);
        }
    }

    public void mouseReleased(int x, int y, int mb) {
        this.mouseDown = false;
        if(draggable)
            this.dragging = false;
        for(Component component : components){
            component.mouseReleased(x, y, mb);
        }
    }

    public boolean mouseOver(int x, int y){
        return x >= this.getX() && x <= this.getX() + this.getWidth() && y >= this.getY() && y <= this.getY() + this.getHeight();
    }

    public boolean isOpened() {
        return false;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public void stopDragging(){
        this.dragging = false;
    }

    public void onResize(){
        for (Component component : components){
            component.onResize();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void keyTyped(char typedChar, int keyCode){
        for(Component component : components)
            component.keyTyped(typedChar,keyCode);
    }
}

