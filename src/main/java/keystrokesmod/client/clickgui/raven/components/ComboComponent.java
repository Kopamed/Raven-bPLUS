package keystrokesmod.client.clickgui.raven.components;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class ComboComponent extends SettingComponent {

    private ComboSetting setting;

    public ComboComponent(Setting setting, ModuleComponent category) {
        super(setting, category);
        this.setting = (ComboSetting) setting;
    }

    @Override
    public void draw(int mouseX, int mouseY) {

        int x = this.x + 5;
        setDimensions(moduleComponent.getWidth(), 7);

        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GlStateManager.resetColor();
        int width = (int) (Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.setting.getName() + ": ") * 0.5);
        Minecraft.getMinecraft().fontRendererObj.drawString(
                setting.getName() + ":",
                (float) (x * 2),
                (float) ((y + (height/2)) * 2),
                0xFEFFFFFF,
                false);
        Minecraft.getMinecraft().fontRendererObj.drawString(
                setting.getMode().name(),
                (float) ((x + width) * 2) ,
                (float) ((y + (height/2)) * 2),
                0xFFA020F0,
                false);

        GL11.glPopMatrix();
    }

    @Override
    public void clicked(int mouseX, int mouseY, int button) {
        if(button == 0)
            setting.nextMode();
        else if(button == 1)
            setting.prevMode();
        moduleComponent.mod.guiButtonToggled(setting);
    }

}