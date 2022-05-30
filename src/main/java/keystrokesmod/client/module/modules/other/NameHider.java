package keystrokesmod.client.module.modules.other;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.DescriptionSetting;
import keystrokesmod.client.module.modules.minigames.DuelsStats;
import keystrokesmod.client.utils.Utils;

public class NameHider extends Module {
   public static DescriptionSetting a;
   public static String n = "ravenb+";

   public NameHider() {
      super("Name Hider", Module.category.other, 0);
      this.registerSetting(a = new DescriptionSetting(Utils.Java.uf("command") + ": cname [name]"));
   }

   public static String getUnformattedTextForChat(String s) {
      if (mc.thePlayer != null) {
         s = DuelsStats.nk.isEmpty() ? s.replace(mc.thePlayer.getName(), n) : s.replace(DuelsStats.nk, n);
      }

      return s;
   }
}
