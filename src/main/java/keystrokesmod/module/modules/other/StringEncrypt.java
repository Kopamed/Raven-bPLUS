//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.other;

import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSetting;
import keystrokesmod.module.ModuleSetting2;
import keystrokesmod.module.modules.ClickGui;

public class StringEncrypt extends Module {
   private static final String m1 = "&k";
   private static final String m2 = "3 char";
   private static final String m3 = "Char shift";
   private static final String m4 = "Blank";
   private static int m3s = 1;
   private boolean m3t = false;
   public static ModuleSetting a;
   public static ModuleSetting b;
   public static ModuleSetting2 c;
   public static ModuleDesc d;

   public StringEncrypt() {
      super("String Encrypt", Module.category.other, 0);
      this.registerSetting(a = new ModuleSetting("Ignore debug", false));
      this.registerSetting(b = new ModuleSetting("Ignore all GUI", false));
      this.registerSetting(c = new ModuleSetting2("Value", 1.0D, 1.0D, 4.0D, 1.0D));
      this.registerSetting(d = new ModuleDesc(ay.md + m1));
   }

   public void onEnable() {
      if (c.getInput() == 3.0D) {
         m3s = ay.rand().nextInt(10) - 5;
         if (m3s == 0) {
            m3s = 1;
         }
      }

   }

   public void guiUpdate() {
      switch((int)c.getInput()) {
      case 1:
         this.m3t = false;
         d.setDesc(ay.md + m1);
         break;
      case 2:
         this.m3t = false;
         d.setDesc(ay.md + m2);
         break;
      case 3:
         if (!this.m3t) {
            m3s = ay.rand().nextInt(10) - 5;
            if (m3s == 0) {
               m3s = 1;
            }
         }

         this.m3t = true;
         d.setDesc(ay.md + m3);
         break;
      case 4:
         this.m3t = false;
         d.setDesc(ay.md + m4);
      }

   }

   public static String getUnformattedTextForChat(String s) {
      if (mc.currentScreen instanceof ClickGui) {
         return s;
      } else if (a.isToggled() && mc.gameSettings.showDebugInfo) {
         return s;
      } else if (b.isToggled() && mc.currentScreen != null) {
         return s;
      } else {
         String s2;
         if (StringEncrypt.c.getInput() == 1.0D) {
            s2 = "";
            String s3 = "";
            boolean w = false;

            for(int i = 0; i < s.length(); ++i) {
               String c = Character.toString(s.charAt(i));
               if (c.equals("§")) {
                  w = true;
                  s3 = s3 + c;
               } else if (w) {
                  w = false;
                  s3 = s3 + c;
               } else {
                  s2 = s2 + s3 + "§" + "k" + c;
                  s3 = "";
               }
            }

            return s2;
         } else if (StringEncrypt.c.getInput() == 2.0D) {
            return s.length() > 3 ? s.substring(0, 3) : s;
         } else if (StringEncrypt.c.getInput() != 3.0D) {
            return "";
         } else {
            s2 = "";

            for(int i = 0; i < s.length(); ++i) {
               char c = (char)(s.charAt(i) + m3s);
               s2 = s2 + c;
            }

            return s2;
         }
      }
   }
}
