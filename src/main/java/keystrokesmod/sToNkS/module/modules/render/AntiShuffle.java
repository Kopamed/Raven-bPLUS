package keystrokesmod.sToNkS.module.modules.render;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleDesc;
import keystrokesmod.sToNkS.utils.Utils;

public class AntiShuffle extends Module {
   public static ModuleDesc a;
   private static final String c = "§k";

   public AntiShuffle() {
      super("AntiShuffle", Module.category.render, 0);
      this.registerSetting(a = new ModuleDesc(Utils.Java.uf("remove") + " &k"));
   }

   public static String getUnformattedTextForChat(String s) {
      return s.replace(c, "");
   }
}
