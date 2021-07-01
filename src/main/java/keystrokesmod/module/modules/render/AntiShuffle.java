package keystrokesmod.module.modules.render;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.ay;

public class AntiShuffle extends Module {
   public static ModuleDesc a;
   private static String c = "§k";

   public AntiShuffle() {
      super(new char[]{'A', 'n', 't', 'i', 'S', 'h', 'u', 'f', 'f', 'l', 'e'}, Module.category.render, 0);
      this.registerSetting(a = new ModuleDesc(ay.uf("remove") + " &k"));
   }

   public static String getUnformattedTextForChat(String s) {
      return s.replace(c, "");
   }
}
