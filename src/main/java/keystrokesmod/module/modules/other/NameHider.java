package keystrokesmod.module.modules.other;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.utils.Utils;
import keystrokesmod.module.modules.minigames.DuelsStats;

public class NameHider extends Module {
   public static ModuleDesc a;
   public static String n = "ravenb+";

   public NameHider() {
      super("Name Hider", Module.category.other, 0);
      this.registerSetting(a = new ModuleDesc(Utils.Java.uf("command") + ": cname [name]"));
   }

   public static String getUnformattedTextForChat(String s) {
      if (mc.thePlayer != null) {
         s = DuelsStats.nk.isEmpty() ? s.replace(mc.thePlayer.getName(), n) : s.replace(DuelsStats.nk, n);
      }

      return s;
   }
}
