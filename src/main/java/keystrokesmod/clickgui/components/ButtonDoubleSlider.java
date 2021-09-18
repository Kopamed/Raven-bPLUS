package keystrokesmod.clickgui.components;

import keystrokesmod.clickgui.RenderComponent;
import keystrokesmod.module.ModuleSettingDoubleSlider;
import keystrokesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ButtonDoubleSlider extends RenderComponent {
    private final ModuleSettingDoubleSlider settingDoubleSlider;
    private final ButtonModule module;
    private int o;
    private int x;
    private int y;
    private boolean visible = false;
    private double slidersWidthToDraw;
    private final int msl = 84;

    public ButtonDoubleSlider(ModuleSettingDoubleSlider settingDoubleSlider, ButtonModule module, int o) {
        this.settingDoubleSlider = settingDoubleSlider;
        this.module = module;
        this.x = module.category.getX() + module.category.getWidth();
        this.y = module.category.getY() + module.o;
        this.o = o;
    }

    public void r3nd3r() {
        net.minecraft.client.gui.Gui.drawRect(this.module.category.getX() + 4, this.module.category.getY() + this.o + 11, this.module.category.getX() + 4 + this.module.category.getWidth() - 8, this.module.category.getY() + this.o + 15, -12302777);
        int l = this.module.category.getX() + 4 + (int)settingDoubleSlider.getInputMin();
        int r = this.module.category.getX() + 4 + (int)this.slidersWidthToDraw;
        if (r - l > 84) {
            r = l + 84;
        }

        if(l > r){
            this.slidersWidthToDraw = 0;
            r = l;
        }

        net.minecraft.client.gui.Gui.drawRect(l, this.module.category.getY() + this.o + 11, r, this.module.category.getY() + this.o + 15, Utils.Client.astolfoColorsDraw(14, 10));
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.settingDoubleSlider.getName() + ": " + this.settingDoubleSlider.getInputMin() + ", " + this.settingDoubleSlider.getInputMax(), (float)((int)((float)(this.module.category.getX() + 4) * 2.0F)), (float)((int)((float)(this.module.category.getY() + this.o + 3) * 2.0F)), -1);
        GL11.glPopMatrix();
    }

    public void so(int n) {
        this.o = n;
    }

    public void render(int x, int y) {
        this.y = this.module.category.getY() + this.o;
        this.x = this.module.category.getX();
        double d = Math.min(this.module.category.getWidth() - 8, Math.max(0, x - this.x));
        System.out.println(d);
        this.slidersWidthToDraw = (double)(this.module.category.getWidth() - 8) * (this.settingDoubleSlider.getInputMax() - this.settingDoubleSlider.getMin()) / (this.settingDoubleSlider.getMax() - this.settingDoubleSlider.getMin());
        if (this.visible) {
            if (d == this.settingDoubleSlider.getInputMin()) {
                System.out.println("1");
                this.settingDoubleSlider.setValueMax(this.settingDoubleSlider.getInputMin());
            } else {
                double n = round(d / (double)(this.module.category.getWidth() - 8) * (this.settingDoubleSlider.getMax() - this.settingDoubleSlider.getMin()) + this.settingDoubleSlider.getMin(), 2);
                //fix following linex
                System.out.println("2");
                this.settingDoubleSlider.setValueMax(n);
            }
        }

    }

    private static double round(double v, int p) {
        if (p < 0) {
            return 0.0D;
        } else {
            BigDecimal bd = new BigDecimal(v);
            bd = bd.setScale(p, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }

    public void onCl1ck(int x, int y, int mouseButton) {
        System.out.println("Clicked");
        if (this.u(x, y) && mouseButton == 0 && this.module.po) {
            this.visible = true;
        }

        if (this.i(x, y) && mouseButton == 0 && this.module.po) {
            this.visible = true;
        }

    }

    public void mr(int x, int y, int m) {
        this.visible = false;
    }

    public boolean u(int x, int y) {
        return x > this.x && x < this.x + this.module.category.getWidth() / 2 + 1 && y > this.y && y < this.y + 16;
    }

    public boolean i(int x, int y) {
        return x > this.x + this.module.category.getWidth() / 2 && x < this.x + this.module.category.getWidth() && y > this.y && y < this.y + 16;
    }
}
