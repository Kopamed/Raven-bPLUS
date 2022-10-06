package keystrokesmod.client.clickgui.kv;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.kv.components.KvCategoryComponent;
import keystrokesmod.client.clickgui.kv.components.KvModuleComponent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.Module.ModuleCategory;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class KvCompactGui extends GuiScreen {

    private int containerX, containerY, containerWidth, containerHeight;
    private final List<KvCategoryComponent> topCategories;
    private final int padding = 5;
    public KvCategoryComponent currentCategory;

    public KvCompactGui() {
        topCategories = new ArrayList<>();
        Module.ModuleCategory[] values = Module.ModuleCategory.values();
        for (ModuleCategory moduleCategory : values) {
            if(moduleCategory.getParentCategory() == ModuleCategory.category) {
                topCategories.add(new KvCategoryComponent(moduleCategory));
            }
        }
        currentCategory = topCategories.get(2);
    }

    public void initMain() {

    }
    
    public void onGuiOpen() {
        renderModules();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //drawing container
        containerWidth = (int) (this.width/1.5);
        containerHeight = (int) (this.height/1.5);
        containerX = this.width/2 - containerWidth/2;
        containerY = this.height/2 - containerHeight/2;
        
        drawBorderedRoundedRect(
                containerX,
                containerY,
                containerX + containerWidth,
                containerY + containerHeight,
                7,
                3,
                0x80413C39,
                0x80413C39);
        //drawing the boarders
        //horizontal boarder
        Gui.drawRect( 
                containerX,
                containerY + containerHeight/6,
                containerX + containerWidth,
                containerY + containerHeight/6 + 1,
                0xFFFF3C39);
        //vertical boarder
        Gui.drawRect(
                containerX + containerWidth/4,
                containerY + containerHeight/6,
                containerX + containerWidth/4 + 1,
                containerY + containerHeight,
                0xFFFF3C39);
        
        //drawing categories
        // note i need to do this
        // note i hate scissor
        //GL11.glPushMatrix();
        //GL11.glEnable(GL11.GL_SCISSOR_TEST);
        //GL11.glScissor(mouseY, mouseY, mouseY, mouseY);
        for(KvCategoryComponent categoryComponent : topCategories) {
            categoryComponent.draw(mouseX, mouseY);
        }
        
        //drawing modules
        for(KvModuleComponent module : currentCategory.getModules())   {
            module.draw(mouseX, mouseY);
        }
    }

    public static void drawRoundedRect(int x, int y, int x1, int y1, int radius, int color) {

        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2.0D;
        y *= 2.0D;
        x1 *= 2.0D;
        y1 *= 2.0D;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        color(color);
        GL11.glEnable(2848);
        GL11.glBegin(9);

        int i;
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
    }



    public static void color(int color) {
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GL11.glColor4d(r, g, b, alpha);
    }

    public static void drawBorderedRoundedRect(int x, int y, int x1, int y1, int borderSize, int borderC, int insideC) {
        drawRoundedRect(x, y, x1, y1, borderSize, borderC);
        drawRoundedRect((x + 1), (y + 1), (x1 - 1), (y1 - 1), borderSize, insideC);
    }

    public static void drawBorderedRoundedRect(int x, int y, int x1, int y1, int radius, int borderSize, int borderC, int insideC) {
        drawRoundedRect((x - borderSize), (y - borderSize), (x1 + borderSize), (y1 + borderSize), radius, insideC);
        drawRoundedRect(x, y, x1, y1, radius + borderSize, borderC);
    }

    public static void resetColor() {
        GlStateManager.color(1, 1, 1, 1);
    }
    
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for(KvCategoryComponent component : topCategories) {
            if(component.mouseDown(mouseX, mouseY, mouseButton)) {
                return;
            }
        }
    }
    
    public void setCurrentCategory(KvCategoryComponent cc) {
        currentCategory = cc;
        renderModules();
    }
    
    private void renderModules() {
        int xOffSet = 0;
        int yOffSet = 0;
        int iterations = 0;
        for(KvModuleComponent module : currentCategory.getModules())   {
            iterations++;
            module.setCoords(containerX + containerWidth/4 + padding + xOffSet, containerY + containerHeight/6 + padding + yOffSet);
            module.setDimensions((containerWidth - containerWidth/4 - 6*padding)/3, (containerWidth - containerWidth/4 - 4*padding)/3);
            xOffSet += (containerWidth - containerWidth/4)/3;
            if(iterations == 3) {
                iterations = 0;
                xOffSet = 0;
                yOffSet += (containerWidth - containerWidth/4)/3;
            }
        }
        
        int categoryHeight = 0;
        for(KvCategoryComponent categoryComponent : topCategories) {
            categoryComponent.setCoords(
                    containerX + padding,
                    containerY + containerHeight/6 + padding + categoryHeight);
            
            categoryComponent.setDimensions(containerWidth/4, containerHeight/12);
            categoryHeight += categoryComponent.getHeight();
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }


}
