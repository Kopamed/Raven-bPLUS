package keystrokesmod.client.clickgui.kv.components;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import net.minecraft.client.Minecraft;

public class KvTickComponent extends KvComponent {

	private TickSetting setting;

	public KvTickComponent(Setting setting) {
		this.setting = (TickSetting) setting;
	}

    @Override
	public void draw(int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        Minecraft.getMinecraft().fontRendererObj.drawString(
                (setting.isToggled() ? "[+]  " : "[-]  ") + setting.getName(),
                (float) (x * 2),
                (float) ((y + (height/2)) * 2),
                setting.isToggled() ? 0xFF00FF00 : 0xFEFFFFFF,
                false);
        GL11.glPopMatrix();
    }

    @Override
	public void clicked(int button, int x, int y) {
    	setting.toggle();
	}
}
