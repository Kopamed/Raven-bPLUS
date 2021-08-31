package keystrokesmod.module.modules.render;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.utils.ay;

public class AntiShuffle extends Module {
   public static ModuleDesc a;
   private static final String c = "§k";

   public AntiShuffle() {
      super("AntiShuffle", Module.category.render, 0);
      this.registerSetting(a = new ModuleDesc(ay.uf("remove") + " &k"));
   }

   public static String getUnformattedTextForChat(String s) {
      return s.replace(c, "");
   }
}
