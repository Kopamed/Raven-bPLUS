package me.kopamed.lunarkeystrokes.keystroke;

import me.kopamed.lunarkeystrokes.keystroke.setting.settings.SliderSetting;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;

public class KeyStroke {
   public static int x;
   public static int y;

   // public  because we need to be able to access them easily in other classes using the Ravenbplus.getKeystrokes func
   public Color textColour, textColourPressed, backgroundColour, backgroundColourPressed;
   public double scale, boxSize, transparency, transition;
   public boolean showMouseButtons, showRMBCPS, showSpaceBar, textShadow, showLMBCPS, showMovementKeys, replaceNamesWithArrows, chromaText, chromaBg;

   public static InputStream lunarXCross;
   public static ResourceLocation lunarXCrossResource;

   public final int margin;


   public KeyStroke() {
      // I have to load config later (after this init step)
      // if the config is not found, these will be the default values
      this.textColour = new Color(255, 255, 255);
      this.textColourPressed = new Color(23, 23, 23); // fuuuck thats dark
      this.backgroundColour = new Color(77,77,77, 130); //  yessir
      this.backgroundColourPressed = new Color(180, 180, 180);

      this.scale = 1; // idfk what this does, but i will inspect lunar code later and find out
      this.boxSize = 10; // actual boxSize (pixels) = boxSize*screenwidth*0.05
      this.transparency = 170;
      this.transition = 75f;

      this.showMouseButtons = true; // uhh the mouse buttons
      this.showLMBCPS = true;
      this.showRMBCPS = true;
      this.showSpaceBar = false;
      this.textShadow = true;
      this.showMovementKeys = true; //wasd keys
      this.replaceNamesWithArrows = false;
      this.chromaText = false;
      this.chromaBg = false;
      this.margin = 2;
      // keystoke general x and y
      x = 0;
      y = 0;

      // remake ClientConfig.loadKeystrokeSettingsFromFile()
      //configLoad();
   }
}
