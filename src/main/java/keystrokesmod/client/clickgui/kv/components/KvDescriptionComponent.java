package keystrokesmod.client.clickgui.kv.components;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import net.minecraft.client.Minecraft;

public class KvDescriptionComponent extends KvComponent {

	private DescriptionSetting setting;

	public KvDescriptionComponent(Setting setting) {
		this.setting = (DescriptionSetting) setting;
	}

    @Override
	public void draw(int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        Minecraft.getMinecraft().fontRendererObj.drawString(
                setting.getDesc(),
                (float) (x * 2),
                (float) ((y + (height/2)) * 2),
                0xFFA020F0,
                false);
        GL11.glPopMatrix();
    }
}
