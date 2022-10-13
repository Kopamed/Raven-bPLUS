package keystrokesmod.client.clickgui.kv.components;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;

public class KvDoubleSliderComponent extends KvComponent {

    private DoubleSliderSetting setting;
    private boolean mouseDown;
    private Helping helping;

    public KvDoubleSliderComponent(Setting setting) {
        this.setting = (DoubleSliderSetting) setting;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        //moving the bars
        if(mouseDown) {
        	float value = (mouseX - x) / (float) (width);
        	value = (float) ((value * (setting.getMax())) + (setting.getMin() * (1 - value)));
            if(helping == Helping.Max)
                setting.setValueMax(value);
            else if(helping == Helping.Min)
                setting.setValueMin(value);
        }

        //name + input
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        Minecraft.getMinecraft().fontRendererObj.drawString(
                setting.getName() + ": " + setting.getInputMin() + " - " + setting.getInputMax(),
                (float) (x * 2),
                (float) (y * 2),
                0xFEFFFFFF,
                false);
        GL11.glPopMatrix();

        //bars
        int percentMinOffSet = (int) (width * ((setting.getInputMin() - setting.getMin())/(setting.getMax() - setting.getMin())));
        int percentMaxOffSet = (int) (width * ((setting.getInputMax() - setting.getMin())/(setting.getMax() - setting.getMin())));
        int offSetDiff = percentMaxOffSet - percentMinOffSet;
        int boxY = y + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT/2) + 1;
        int boxHeight = height - (boxY - y);
        RenderUtils.drawBorderedRoundedRect(x, boxY, x + width, (boxY + boxHeight) , 4, 2, Utils.Client.rainbowDraw(1, 0), 0x20FFFFFF);
        RenderUtils.drawBorderedRoundedRect(
                x + percentMinOffSet,
                boxY,
                x + percentMaxOffSet,
                (boxY + boxHeight),
                offSetDiff > 4  ? 4 : offSetDiff,
                2,
                0xFFA020F0,
                0x90FFFFFF);
    }

    @Override
    public void clicked(int button, int mouseX, int mouseY) {
        mouseDown = true;
        float percentageAcross = (mouseX - x) / (float) width;
        //percentageAcross = (float) ((percentageAcross * (setting.getMax())) + (setting.getMin() * (1 - percentageAcross)));
        float percentangeMax = (float) ((setting.getInputMax() - setting.getMin())/(setting.getMax() - setting.getMin())) + 0.01f;
        float percentangeMin = (float) ((setting.getInputMin() - setting.getMin())/(setting.getMax() - setting.getMin()));
        helping = Math.abs(percentageAcross - percentangeMax) < Math.abs(percentageAcross - percentangeMin) ? Helping.Max : Helping.Min;
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        mouseDown = false;
    }

    public enum Helping {
        Min,
        Max;
    }
}
