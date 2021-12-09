package me.kopamed.raven.bplus.client.feature.module.modules.client;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.ComboSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Gui extends Module {
   public static ComboSetting onHover;

   public Gui() {
      super("Gui", "The display you are currently looking out", ModuleCategory.Misc, new ArrayList<Integer>(Arrays.asList(54)));
      this.registerSetting(onHover = new ComboSetting("On hover", new String[]{"darken", "brighten", "nothing"}, 0));
   }

   public void onEnable() {
      if (Utils.Player.isPlayerInGame() && mc.currentScreen != Raven.client.getClickGui()) {
         mc.displayGuiScreen(Raven.client.getClickGui());
            Raven.client.getClickGui().initMain();
      }

      this.disable();
   }

   public Color modifyColor(Color colour){
      switch (onHover.getMode()){
         case "nothing":
            return colour;
         case "darken":
            return colour.darker();
         case "brighten":
            return colour.brighter();
      }
      return colour;
   }
}
