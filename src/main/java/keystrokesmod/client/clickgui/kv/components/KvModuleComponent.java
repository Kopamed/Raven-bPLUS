package keystrokesmod.client.clickgui.kv.components;

import keystrokesmod.client.clickgui.kv.KvCompactGui;
import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.gui.Gui;

public class KvModuleComponent extends KvComponent{
    
    private Module module;
    
    private int toggleX, toggleY, toggleWidth, toggleHeight;
    
    public KvModuleComponent(Module module) {
        this.module = module;
    }
    
    
    
    @Override
    public void draw(int mouseX, int mouseY) {
        KvCompactGui.drawRoundedRect(x, y, x + width, y + height, 12, 0xFF00FFFF);
        
        Gui.drawRect(x , y + (int) (3*height/3.8), x + width, y+ (int) (3*height/3.8) + 1, 0xFFFFFFFF);
       
        Gui.drawRect(x + width - (int) (width/3.8),
                y + (int) (3*height/3.8),
                x + width - (int) (width/3.8) + 1,
                y + height,
                0xFFFFFFFF);
       
        FontUtil.normal.drawCenteredStringWithShadow(module.getName(), x + width/2, y + height - (int) ((3*height/3.8)/2), 0xFF000000);
        FontUtil.two.drawNoBSCenteredString(module.isEnabled() ? "Disabled" : "Enabled", toggleX + toggleWidth/2, toggleY, 0xFF000000);
    }

    @Override
    public boolean mouseDown(int x, int y, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
        toggleX = x;
        toggleY = y + (height - (int) ((height/3.8)/2));
        toggleWidth = width - (int) (width/3.8);
        toggleHeight = height - toggleY;
    }

    @Override
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

}
