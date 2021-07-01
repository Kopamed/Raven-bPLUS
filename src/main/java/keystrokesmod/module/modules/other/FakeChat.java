//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.other;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.ay;
import net.minecraft.util.ChatComponentText;

public class FakeChat extends Module {
   public static ModuleDesc a;
   public static String msg = "&eThis is a fake chat message.";
   public static final String command = "fakechat";
   public static final String c4 = "&cInvalid message.";

   public FakeChat() {
      super("Fake Chat", Module.category.other, 0);
      this.registerSetting(a = new ModuleDesc(ay.uf("command") + ": " + command + " [msg]"));
   }

   public void onEnable() {
      if (msg.contains("\\n")) {
         String[] split = msg.split("\\\\n");

         for (String s : split) {
            this.sm(s);
         }
      } else {
         this.sm(msg);
      }

      this.disable();
   }

   private void sm(String txt) {
      mc.thePlayer.addChatMessage(new ChatComponentText(ay.r(txt)));
   }
}
