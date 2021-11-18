package me.kopamed.raven.bplus.client.feature.module.modules.render;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.Description;
import me.kopamed.raven.bplus.helper.utils.Utils;

public class AntiShuffle extends Module {
   public static Description a;
   private static final String c = "§k";

   public AntiShuffle() {
      super("AntiShuffle", ModuleCategory.render, 0);
      this.registerSetting(a = new Description(Utils.Java.uf("remove") + " &k"));
   }

   public static String getUnformattedTextForChat(String s) {
      return s.replace(c, "");
   }
}
