package keystrokesmod.client.clickgui.raven.components;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.module.modules.client.GuiModule;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;

public class SliderComponent extends SettingComponent {

    private SliderSetting setting;
    private boolean mouseDown;

    public SliderComponent(Setting setting, ModuleComponent category) {
        super(setting, category);
        this.setting = (SliderSetting) setting;
    }

    @Override
    public void draw(int mouseX, int mouseY) {

        setDimensions(moduleComponent.getWidth() - 10, 14);
        int x = this.x + 5;

        //moving the bars
        if(mouseDown) {
            float p = (mouseX - x) / (float) (width);
            setting.setValue((float) ((p * (setting.getMax())) + (setting.getMin() * (1 - p))));
        }

        //name + input
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        Minecraft.getMinecraft().fontRendererObj.drawString(
                setting.getName() + ": " + setting.getInput(),
                (float) (x * 2),
                (float) (y * 2),
                0xFEFFFFFF,
                false);
        GL11.glPopMatrix();

        //bars
        int percentWidth = (int) (width * ((setting.getInput() - setting.getMin())/(setting.getMax() - setting.getMin())));
        int boxY = y + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT/2) + 1;
        int boxHeight = height - (boxY - y);
        RenderUtils.drawBorderedRoundedRect(x, boxY , x + width, (boxY + boxHeight) , 7, 2, GuiModule.getBoarderColour(), 0x20FFFFFF);
        RenderUtils.drawBorderedRoundedRect(x, boxY , x + percentWidth, (boxY + boxHeight), percentWidth > 7 ? 7 : percentWidth, 2, 0xFFA020F0, 0x90FFFFFF);
    }

    @Override
    public void clicked(int button, int x, int y) {
        mouseDown = true;
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        mouseDown = false;
    }

}
