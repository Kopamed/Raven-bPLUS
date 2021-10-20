package me.kopamed.lunarkeystrokes.keystroke.ui.component.components;

import me.kopamed.lunarkeystrokes.keystroke.setting.settings.TickSetting;
import me.kopamed.lunarkeystrokes.keystroke.ui.component.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Tick extends Component {
    private int unTickedSize = 4;
    private Color unTickedColour = new Color(183, 183, 183);

    private int tickedSize = 6;
    private Color tickedColour = new Color(73, 99, 246);

    private int tickMargin = (tickedSize - unTickedSize)/2;

    private boolean actionPerformed = false;

    private TickSetting tickSetting;

    public Tick(int x, int y, int width, int height, TickSetting tickSetting, double textSize){
        super(x, y, width, height, textSize);
        this.tickSetting = tickSetting;
    }

    public void draw(){
        int baseX = this.getX() + this.getWidth() - this.tickedSize;
        int baseY = this.getY();
        if(this.tickSetting.isTicked()){
            Gui.drawRect(baseX, baseY + (this.getHeight() - tickedSize)/2, baseX + tickedSize, baseY + (this.getHeight() - tickedSize)/2 + this.tickedSize, tickedColour.getRGB());
        } else {
            baseX += tickMargin;
            Gui.drawRect(baseX, this.getY() + (this.getHeight() - unTickedSize)/2, baseX + unTickedSize, this.getY() + (this.getHeight() - unTickedSize)/2 + unTickedSize, unTickedColour.getRGB());
            //System.out.println("drew no");
        }

        GL11.glPushMatrix();
        GL11.glScaled(this.getTextSize(), this.getTextSize(), this.getTextSize());
        double fontHeight = getFontRender().FONT_HEIGHT * this.getTextSize();
        Minecraft.getMinecraft().fontRendererObj.drawString(this.tickSetting.getName(), (int)(this.getX() * (1/this.getTextSize())), (int)((this.getY() + (this.getHeight() - fontHeight)/2) * (1/this.getTextSize())), 0xffffffff);
        GL11.glPopMatrix();
    }

    public void backgroundProcess(int x, int y){
        super.backgroundProcess(x, y);


        //System.out.println(mouseDown + " " + overTickBox(x,y) + actionPerformed);
        if(mouseDown && overTickBox(x, y) && !actionPerformed){
            this.tickSetting.toggle();
            actionPerformed = true;
    } else if(!mouseDown && actionPerformed){
            actionPerformed = false;
        }
    }

    private boolean overTickBox(int x, int y) {
        int baseX = this.getX() + this.getWidth() - this.tickedSize;
        int baseY = this.getY() + (this.getHeight() - tickedSize)/2;
        return (x >= baseY && x <= baseX + tickedSize && y>= baseY && y <= baseY + this.tickedSize);
    }
}
