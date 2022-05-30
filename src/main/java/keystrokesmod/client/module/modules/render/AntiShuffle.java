package keystrokesmod.client.module.modules.render;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.DescriptionSetting;
import keystrokesmod.client.utils.Utils;

public class AntiShuffle extends Module {
   public static DescriptionSetting a;
   private static final String c = "§k";

   public AntiShuffle() {
      super("AntiShuffle", Module.category.render, 0);
      this.registerSetting(a = new DescriptionSetting(Utils.Java.uf("remove") + " &k"));
   }

   public static String getUnformattedTextForChat(String s) {
      return s.replace(c, "");
   }
}
