package keystrokesmod.client.clickgui.kv;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.font.FontUtil;

public class KvSection {

	private final String name;
	private int sectionX, sectionY, height;
	private int width;
	protected static int containerX, containerY, containerWidth, containerHeight;

	public KvSection(String name) {
		this.name = name;
		this.width = (int) FontUtil.normal.getStringWidth(name) + 8;
		this.height = (int) FontUtil.normal.getHeight() + 8;
	}

	public static void initGui(int containerX, int containerY, int containerWidth, int containerHeight) {
		KvSection.containerX = containerX;
		KvSection.containerY = containerY;
		KvSection.containerWidth = containerWidth;
		KvSection.containerHeight = containerHeight;
	}

	public void refresh() {

	}

	public String getName() {
		return name;
	}

	public void setSectionCoords(int x, int y) {
		sectionX = x;
		sectionY = y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void drawSection(int mouseX, int mouseY, float partialTicks) {
		RenderUtils.drawBorderedRoundedRect(sectionX, sectionY, (float) (sectionX + width), sectionY + height, 6, 2, Utils.Client.rainbowDraw(1, 0), Raven.kvCompactGui.getCurrentSection() == this ? 0xFF000000: isMouseOver(mouseX, mouseY) ? 0xC0FFFFFF : 0x40FFFFFF);
		FontUtil.normal.drawCenteredString(name, (float) (sectionX + (width/2)), (sectionY + (height/2)) -1, 0xFFFFFFFF);
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

	}

	public boolean mouseClicked(int x, int y, int button) {
		if ((button == 0) && isMouseOver(x, y)) {
			Raven.kvCompactGui.setCurrentSection(this);
			return true;
		}
		return false;
	}

    public void mouseReleased(int x, int y, int button) {

    }

	public boolean isMouseOver(int x, int y) {
		return ((x > sectionX) && (x < (sectionX + width)) && (y > sectionY) && (y < (sectionY + height)));
	}

	public void keyTyped(char t, int k) {

	}

	public void scroll(float i) {

	}

}
