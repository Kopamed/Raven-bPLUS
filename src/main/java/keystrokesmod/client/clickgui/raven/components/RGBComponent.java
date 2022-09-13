package keystrokesmod.client.clickgui.raven.components;

import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.module.setting.impl.RGBSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class RGBComponent implements Component {

    private final RGBSetting setting;
    private final ModuleComponent module;
    private int moduleStartY;

    private final int boxMargin = 4;
    private final int boxHeight = 4;
    private final int textSize = 11;
    private final int x;

    private double barWidth;

    private boolean mouseDown;
    private Helping mode;
    private static RGBComponent helping;

    public RGBComponent(RGBSetting setting, ModuleComponent module, int moduleStartY) {
        this.setting = setting;
        this.module = module;
        this.moduleStartY = moduleStartY;
        this.x = module.category.getX() + module.category.getWidth();
    }

    @Override
    public void draw() {
        Gui.drawRect(this.module.category.getX() + boxMargin,
                this.module.category.getY() + this.moduleStartY + textSize,
                this.module.category.getX() - boxMargin + this.module.category.getWidth(),
                this.module.category.getY() + this.moduleStartY + textSize + boxHeight, -12302777);
        int[] drawColor = { 0xffff0000, 0xff00ff00, 0xff0000ff };
        for (int i = 0; i < 3; i++) {
            int color = (int) ((this.barWidth * this.setting.getColor(i) / 255f) + this.module.category.getX()
                    + boxMargin);
            Gui.drawRect(color, this.module.category.getY() + this.moduleStartY + textSize - 1,
                    color + (color % 2 == 0 ? 2 : 1),
                    this.module.category.getY() + this.moduleStartY + textSize + boxHeight + 1, drawColor[i]);
        }
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(
                this.setting.getName() + ": " + this.setting.getRed() + ", " + this.setting.getGreen() + ", "
                        + this.setting.getBlue(),
                (float) ((int) ((float) (this.module.category.getX() + 4) * 2.0F)),
                (float) ((int) ((float) (this.module.category.getY() + this.moduleStartY + 3) * 2.0F)), -1);
        GL11.glPopMatrix();
    }

    @Override
    public void update(int mousePosX, int mousePosY) {
        this.barWidth = this.module.category.getWidth() - boxMargin * 2;
        if (helping != null && helping != this)
            return;
        if ((this.mouseDown) && ((mousePosX > this.module.category.getX() + boxMargin
                && mousePosX < (this.module.category.getX() + this.module.category.getWidth() - boxMargin)
                && mousePosY > this.module.category.getY() + this.moduleStartY
                && mousePosY < this.module.category.getY() + this.moduleStartY + textSize + boxHeight + 1)
                || mode != Helping.NONE)) {
            float mouseP = (mousePosX - this.module.category.getX() - boxMargin) / (float) barWidth;
            mouseP = mouseP > 0 ? mouseP < 1 ? mouseP : 1 : 0;
            // Utils.Player.sendMessageToSelf(mode.name());
            if (mode != Helping.NONE) {
                this.setting.setColor(mode.id, (int) (mouseP * 255));
                return;
            }

            // Utils.Player.sendMessageToSelf(getTick(mouseP) + " ");
            switch (getTick(mouseP)) {
            case 0:
                mode = Helping.RED;
                break;
            case 1:
                mode = Helping.GREEN;
                break;
            case 2:
                mode = Helping.BLUE;
                break;
            }
            this.setting.setColor(mode.id, (int) (mouseP * 255));
            helping = this;

        } else {
            mode = Helping.NONE;
            if (helping == this) {
                helping = null;
            }
        }
    }

    @Override
    public void mouseDown(int x, int y, int b) {
        if (this.u(x, y) && b == 0 && this.module.po) {
            this.mouseDown = true;
        }

        if (this.i(x, y) && b == 0 && this.module.po) {
            this.mouseDown = true;
        }
    }

    @Override
    public void mouseReleased(int x, int y, int b) {
        if (b == 0)
            this.mouseDown = false;
    }

    @Override
    public void keyTyped(char t, int k) {

    }

    @Override
    public void setComponentStartAt(int moduleStartY) {
        this.moduleStartY = moduleStartY;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    private int getTick(float p) {
        int r = 0;
        float c = 1;
        for (int i = 0; i < 3; i++) {
            if (Math.abs((this.setting.getColor(i) / 255f) - p) < c) {
                r = i;
                c = Math.abs((this.setting.getColor(i) / 255f) - p);
            }
        }
        return r;
    }

    public enum Helping {
        RED(0), GREEN(1), BLUE(2), NONE(-1);

        private final int id;

        Helping(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
    
    @Override
    public int getY() {
        return moduleStartY;
    }
    
    public boolean u(int x, int y) {
        return x > this.x && x < this.x + this.module.category.getWidth() / 2 + 1 && y > this.moduleStartY && y < this.moduleStartY + 16;
    }

    public boolean i(int x, int y) {
        return x > this.x + this.module.category.getWidth() / 2 && x < this.x + this.module.category.getWidth() && y > this.moduleStartY
                && y < this.moduleStartY + 16;
    }

}
