package keystrokesmod.sToNkS.module.modules.other;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleDesc;
import keystrokesmod.sToNkS.module.modules.minigames.DuelsStats;
import keystrokesmod.sToNkS.utils.Utils;

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
