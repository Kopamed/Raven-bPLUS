package me.kopamed.lunarkeystrokes.keystroke.ui;


import me.kopamed.lunarkeystrokes.keystroke.setting.settings.ColourSetting;
import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.utils.CoolDown;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;

import java.awt.*;

public class KeystrokeKey {
    public String name;
    public KeyBinding keyBinding;
    public float width;
    public float height;
    public boolean buttonPressRegistered;
    float transitionTimeLasts = 75.0f;
    private CoolDown transition = new CoolDown(0);
    private Color backgroundColour;
    private Color backgroundPressedColour;

    private TransitionMode transitionMode = TransitionMode.NONE;

    public KeystrokeKey(String string, KeyBinding keyBindingBridge, float width, float height) {
        this.name = string;
        this.keyBinding = keyBindingBridge;
        this.width = width;
        this.height = height;
    }

    public void draw(float x, float y, Color textColour, Color textPressedColour, Color backgroundColours, Color backgroundPressedColour, boolean textShaddow) {
        /*ColourSetting currentTextColourSetting;
        boolean buttonPressed;
        Minecraft mc = Minecraft.getMinecraft();
        buttonPressed = keyBinding.isKeyDown();
        if (buttonPressed && !this.buttonPressRegistered) {
            this.transitionMode = TransitionMode.TOPRESSED;
            this.buttonPressRegistered = true;
            this.transition.setCooldown((int)transitionTimeLasts);
            this.transition.start();
            this.backgroundColour = backgroundColour;
            this.backgroundPressedColour = backgroundPressedColour;
        } else if (this.buttonPressRegistered && !buttonPressed) {
            this.transitionMode = TransitionMode.TORELEASED;
            this.buttonPressRegistered = false;
            this.transition.setCooldown((int)transitionTimeLasts);
            this.transition.start();
            this.backgroundColour = backgroundPressedColour;
            this.backgroundPressedColour = backgroundColour;
        } else if(this.buttonPressRegistered == buttonPressed && this.transition.hasTimeElapsed()){
            this.transitionMode = TransitionMode.NONE;
        }




        if (!transition.hasTimeElapsed()) {
            float timeElpasedSinceChange = transition.getElapsedTime();
            float transitionProgress = timeElpasedSinceChange / transitionTimeLasts;
            if(this.transitionMode == TransitionMode.TOPRESSED){

            } else if(this.transitionMode == TransitionMode.TORELEASED){

            }
            int transitionTotalRed =(int)((this.backgroundColour.getRed() - this.backgroundPressedColour.getRed()) / transitionProgress);
            int transitionTotalGreen =(int)((this.backgroundColour.getGreen() - this.backgroundPressedColour.getGreen()) / transitionProgress);
            int transitionTotalBlue =(int)((this.backgroundColour.getBlue() - this.backgroundPressedColour.getRed()) / transitionProgress);

            // ILILILIL is baciacally getting the chroma cycle
            hue = transitionProgress * this.backgroundPressedColour.(theta) + (1.0f - transitionProgress) * this.backgroundColour.IlllIIIIIIlllIlIIlllIlIIl(theta);
            float red = transitionProgress * this.backgroundPressedColour.getRed()  + (1.0f - transitionProgress) * this.backgroundColour.lIllIlIIIlIIIIIIIlllIlIll(theta);
            float green = transitionProgress * this.backgroundPressedColour.llIlllIIIllllIIlllIllIIIl(theta) + (1.0f - transitionProgress) * this.backgroundColour.llIlllIIIllllIIlllIllIIIl(theta);
            blue = transitionProgress * this.backgroundPressedColour.llllIIlIIlIIlIIllIIlIIllI(theta) + (1.0f - transitionProgress) * this.backgroundColour.llllIIlIIlIIlIIllIIlIIllI(theta);
            Gui.drawRect(x, y, this.width, this.height, ColorUtil.isKeyDown(hue, red, green, blue)); // drawing the rect
        } else {
            ColorSetting colorSetting6 = buttonPressed ? backgroundPressedColourSetting : backgroundColourSettings;
            colorSetting6.isKeyDown(x, y, this.width, this.height);
        }
        currentTextColourSetting = buttonPressed ? textPressedColourSetting : textColourSetting;
        if (this.keyBinding.getKey() == mc.getGameSettings().keyBindJump().getKey()) {
            currentTextColourSetting.isKeyDown(x + this.width / 2.0f - this.width / 6.0f, y + 3.0f, this.width / 3.0f, 1.0f, textShaddow);
        } else {
            boolean bl4;
            KeyStrokesMod keyStrokesMod = Ref.getLC().lllllIllIllIllllIlIllllII().llIIIlllIIlllIllllIlIllIl();
            f5 = x + this.width / 2.0f;
            f4 = y + this.height / 2.0f - (float)(Ref.getFontRenderer().getFontHeight() / 2) + 1.0f;
            boolean bl5 = this.keyBinding.getKey() == mc.getGameSettings().keyBindAttack().getKey() && Ravenbplus.getKeyStroke().showLMBCPS != false;
            boolean bl6 = bl4 = this.keyBinding.getKey() == mc.gameSettings.keyBindUseItem().getKey() && keyStrokesMod.getRightCPSSetting().getInput() != false;
            if (bl5 || bl4) {
                GL11().pushMatrix();
                f3 = 0.6f;
                GL11().scale(f3, f3, 0.0);
                f4 = y + this.height - (float)Ref.getFontRenderer().getFontHeight() + 2.0f;
                String string = Ref.getLC().getMouseEventHandler().isKeyDown(bl5) + " CPS";
                if (textShaddow) {
                    Ref.getFontRenderer().drawCenteredStringWithShadow(string, f5 / f3, f4 / f3, currentTextColourSetting.isKeyDown(f5 + f4));
                } else {
                    Ref.getFontRenderer().drawCenteredString(string, f5 / f3, f4 / f3, currentTextColourSetting.isKeyDown(f5 + f4));
                }
                GL11().scale(1.0, 1.0, 0.0);
                GL11().popMatrix();
                f4 = y + this.height / 4.0f - (float)(Ref.getFontRenderer().getFontHeight() / 4);
            }
            if (textShaddow) {
                currentTextColourSetting.isKeyDown(this.name, f5, f4);
            } else {
                currentTextColourSetting.lIllIlIIIlIIIIIIIlllIlIll(this.name, f5, f4);
            }
        }*/
    }

    public void setKeyBinding(KeyBinding keyBindingBridge) {
        this.keyBinding = keyBindingBridge;
    }

    public String getName() {
        return this.name;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public KeyBinding getKeyBinding() {
        return this.keyBinding;
    }

    public static enum TransitionMode{
        TOPRESSED,
        TORELEASED,
        NONE;
    }
}

