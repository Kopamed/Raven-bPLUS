package keystrokesmod.client.module.modules.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.Utils;

public class CursorTrail extends Module {

	private List<CursorTrailPoint> trailPoints = new ArrayList<CursorTrailPoint>();
	private SliderSetting length;

	//help me unfuck this sigma
	//will implement new hud colors for this later
	public CursorTrail() {
		super("Cursor trail", ModuleCategory.render);
		this.registerSetting(new DescriptionSetting("DO NOT USE"));
		this.registerSetting(length = new SliderSetting("length (ms)", 200, 1, 1000, 1));
	}

	public void draw(int x, int y) {
		trailPoints.add(new CursorTrailPoint(Utils.Client.rainbowDraw(5, 0), x, y));

		GL11.glPushAttrib(0);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		//setColor(color);
		GL11.glEnable(2848);
		GL11.glLineWidth(5f);
		GL11.glBegin(2);
		GL11.glVertex2d(x, y);
		for(CursorTrailPoint tp : trailPoints)
			tp.draw();
		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glDisable(3042);
		GL11.glEnable(3553);
		GL11.glPopAttrib();
		GL11.glLineWidth(1.0f);
		//GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	}



	public class CursorTrailPoint {
		private final int color;
		private final CoolDown cd = new CoolDown((long) length.getInput());
		private final int x, y;

		public CursorTrailPoint(int color, int x, int y) {
			this.y = y;
			this.x = x;
			this.color = color;
			cd.setCooldown((long) length.getInput());
			Utils.Player.sendMessageToSelf(length.getInput() + " " + cd.getCooldownTime());
			cd.start();
		}

		public void draw() {
			GL11.glVertex2d(x, y);
			RenderUtils.setColor(color);
			//Utils.Player.sendMessageToSelf(x + " " + y);
			if(cd.hasFinished()) {
				Utils.Player.sendMessageToSelf("removed");
				Utils.Player.sendMessageToSelf(trailPoints.size() + "");
				trailPoints.remove(this);
				return;
			}
		}
	}
}
