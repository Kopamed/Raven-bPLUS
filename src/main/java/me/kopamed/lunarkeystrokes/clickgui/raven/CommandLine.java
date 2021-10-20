//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.clickgui.raven;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.kopamed.lunarkeystrokes.utils.ChatHelper;
import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

public class CommandLine {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static boolean f = true;
   private static final int maxLines = 80;
   private static final boolean u = false;
   private static final List<Integer> cs = Arrays.asList((new Color(170, 107, 148, 50)).getRGB(), (new Color(122, 158, 134, 50)).getRGB(), (new Color(16, 16, 16, 50)).getRGB(), (new Color(64, 114, 148, 50)).getRGB());
   private static int ccs = 0;
   private static int lccs = -1;
   public static List<String> commandLineHistory = new ArrayList();

   public static void rCMD(String c) {
      if (!c.isEmpty()) {
         String cm = c.toLowerCase();
         boolean hasArgs = c.contains(" ");
         String[] args = hasArgs ? c.split(" ") : null;

         Ravenbplus.commandManager.executeCommand(cm.split(" ")[0], args);
      }
   }

   public static void print(String message, int breakLineMode) {
      if (breakLineMode == 1 || breakLineMode == 2) {
         commandLineHistory.add("");
      }

      commandLineHistory.add(message);
      if (breakLineMode == 2 || breakLineMode == 3) {
         commandLineHistory.add("");
      }

      while (commandLineHistory.size() > maxLines) {
         commandLineHistory.remove(0);
      }
   }

   public static void rc(FontRenderer fr, int h, int w, int s) {
      int x = w - 195;
      int y = h - 130;
      int sY = h - 345;
      int sH = 230;
      GL11.glEnable(3089);
      int mw = w * s;
      GL11.glScissor(0, mc.displayHeight - (sY + sH) * s, mw - (mw < 2 ? 0 : 2), mc.displayHeight);
      Utils.HUD.db(1000, 1000, ccs);
      rss(fr, commandLineHistory, x, y);
      GL11.glDisable(3089);
   }

   private static void rss(FontRenderer fr, List<String> rs, int x, int y) {
      if (f) {
         f = false;
         print("Welcome,", 0);
         print("Use \"help\" for help.", 0);
      }

      if (!rs.isEmpty()) {
         for(int i = rs.size() - 1; i >= 0; --i) {
            String s = rs.get(i);
            int c = -1;
            if (s.contains("&a")) {
               s = s.replace("&a", "");
               c = Color.green.getRGB();
            } else if (s.contains("&c")) {
               s = s.replace("&c", "");
               c = Color.red.getRGB();
            } else if (s.contains("&e")) {
               s = s.replace("&e", "");
               c = Color.yellow.getRGB();
            }

            fr.drawString(s, x, y, c);
            y -= fr.FONT_HEIGHT + 5;
         }

      }
   }

   public static void setccs() {
      int val = Utils.Java.rand().nextInt(cs.size());
      if (val == lccs) {
         val += val == 3 ? -3 : 1;
      }

      lccs = val;
      ccs = cs.get(val);
   }

   public static void od() {
      ChatHelper.reset();
   }
}
