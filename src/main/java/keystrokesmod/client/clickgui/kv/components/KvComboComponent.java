package keystrokesmod.client.clickgui.kv.components;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import net.minecraft.client.Minecraft;

public class KvComboComponent extends KvComponent {

	private ComboSetting setting;

	public KvComboComponent(Setting setting) {
		this.setting = (ComboSetting) setting;
	}

    @Override
	public void draw(int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
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
    public void clicked(int button, int mouseX, int mouseY) {
    	if(button == 0)
        setting.nextMode();
    	else if(button == 1)
    	setting.prevMode();
    }
}
