//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.raven.bplus.client.feature.module.modules.other;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.Description;
import me.kopamed.raven.bplus.helper.utils.Utils;
import me.kopamed.raven.bplus.client.feature.module.modules.minigames.DuelsStats;

public class NameHider extends Module {
   public static Description a;
   public static String n = "ravenb+";

   public NameHider() {
      super("Name Hider", ModuleCategory.Other, 0);
      this.registerSetting(a = new Description(Utils.Java.uf("command") + ": cname [name]"));
   }

   public static String getUnformattedTextForChat(String s) {
      if (mc.thePlayer != null) {
         s = DuelsStats.nk.isEmpty() ? s.replace(mc.thePlayer.getName(), n) : s.replace(DuelsStats.nk, n);
      }

      return s;
   }
}
