package me.kopamed.lunarkeystrokes.module.modules.render;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.utils.Utils;

public class AntiShuffle extends Module {
   public static Description a;
   private static final String c = "§k";

   public AntiShuffle() {
      super("AntiShuffle", Module.category.render, 0);
      this.registerSetting(a = new Description(Utils.Java.uf("remove") + " &k"));
   }

   public static String getUnformattedTextForChat(String s) {
      return s.replace(c, "");
   }
}
