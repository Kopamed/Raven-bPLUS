//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.other;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.utils.ay;
import keystrokesmod.module.modules.minigames.DuelsStats;

public class NameHider extends Module {
   public static ModuleDesc a;
   public static String n = "raven";

   public NameHider() {
      super("Name Hider", Module.category.other, 0);
      this.registerSetting(a = new ModuleDesc(ay.uf("command") + ": cname [name]"));
   }

   public static String getUnformattedTextForChat(String s) {
      if (mc.thePlayer != null) {
         s = DuelsStats.nk.isEmpty() ? s.replace(mc.thePlayer.getName(), n) : s.replace(DuelsStats.nk, n);
      }

      return s;
   }
}
