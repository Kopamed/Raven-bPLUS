//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.keystroke;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.utils.MouseManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class KeyStrokeConfigGui extends GuiScreen {
    private static final String[] colors = { "White", "Red", "Green", "Blue", "Yellow", "Purple", "Rainbow" };
    private GuiButton modeBtn;
    private GuiButton textColorBtn;
    private GuiButton showMouseBtn;
    private GuiButton outlineBtn;
    private boolean d;
    private int lx;
    private int ly;

    public void initGui() {
        KeyStroke st = KeyStrokeMod.getKeyStroke();
        this.buttonList.add(this.modeBtn = new GuiButton(0, this.width / 2 - 70, this.height / 2 - 28, 140, 20,
                "Mod: " + (KeyStroke.enabled ? "Enabled" : "Disabled")));
        this.buttonList.add(this.textColorBtn = new GuiButton(1, this.width / 2 - 70, this.height / 2 - 6, 140, 20,
                "Text color: " + colors[KeyStroke.currentColorNumber]));
        this.buttonList.add(this.showMouseBtn = new GuiButton(2, this.width / 2 - 70, this.height / 2 + 16, 140, 20,
                "Show mouse buttons: " + (KeyStroke.showMouseButtons ? "On" : "Off")));
        this.buttonList.add(this.outlineBtn = new GuiButton(3, this.width / 2 - 70, this.height / 2 + 38, 140, 20,
                "Outline: " + (KeyStroke.outline ? "On" : "Off")));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        KeyStrokeMod.getKeyStrokeRenderer().renderKeystrokes();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void actionPerformed(GuiButton button) {
        KeyStroke st = KeyStrokeMod.getKeyStroke();
        if (button == this.modeBtn) {
            KeyStroke.enabled = !KeyStroke.enabled;
            this.modeBtn.displayString = "Mod: " + (KeyStroke.enabled ? "Enabled" : "Disabled");
        } else if (button == this.textColorBtn) {
            KeyStroke.currentColorNumber = KeyStroke.currentColorNumber == 6 ? 0 : KeyStroke.currentColorNumber + 1;
            this.textColorBtn.displayString = "Text color: " + colors[KeyStroke.currentColorNumber];
        } else if (button == this.showMouseBtn) {
            KeyStroke.showMouseButtons = !KeyStroke.showMouseButtons;
            this.showMouseBtn.displayString = "Show mouse buttons: " + (KeyStroke.showMouseButtons ? "On" : "Off");
        } else if (button == this.outlineBtn) {
            KeyStroke.outline = !KeyStroke.outline;
            this.outlineBtn.displayString = "Outline: " + (KeyStroke.outline ? "On" : "Off");
        }

    }

    protected void mouseClicked(int mouseX, int mouseY, int button) {
        try {
            super.mouseClicked(mouseX, mouseY, button);
        } catch (IOException var9) {
        }

        if (button == 0) {
            MouseManager.addLeftClick();
            KeyStroke st = KeyStrokeMod.getKeyStroke();
            int startX = KeyStroke.x;
            int startY = KeyStroke.y;
            int endX = startX + 74;
            int endY = startY + (KeyStroke.showMouseButtons ? 74 : 50);
            if (mouseX >= startX && mouseX <= endX && mouseY >= startY && mouseY <= endY) {
                this.d = true;
                this.lx = mouseX;
                this.ly = mouseY;
            }
        } else if (button == 1) {
            MouseManager.addRightClick();
        }

    }

    protected void mouseReleased(int mouseX, int mouseY, int action) {
        super.mouseReleased(mouseX, mouseY, action);
        this.d = false;
    }

    protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick) {
        super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
        if (this.d) {
            KeyStroke st = KeyStrokeMod.getKeyStroke();
            KeyStroke.x = KeyStroke.x + mouseX - this.lx;
            KeyStroke.y = KeyStroke.y + mouseY - this.ly;
            this.lx = mouseX;
            this.ly = mouseY;
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void onGuiClosed() {
        Raven.clientConfig.updateKeyStrokesSettings();
    }
}
