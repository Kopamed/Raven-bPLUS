//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod;

import java.util.ArrayList;
import java.util.List;

import keystrokesmod.main.Ravenb3;
import keystrokesmod.module.modules.world.AntiBot;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class cl {
   private static List<Long> a = new ArrayList();
   private static List<Long> b = new ArrayList();
   public static long LL = 0L;
   public static long LR = 0L;

   @SubscribeEvent
   public void onMouseUpdate(MouseEvent d) {
      if (d.buttonstate) {
         if (d.button == 0) {
            aL();
            if (Ravenb3.debugger && Ravenb3.mc.objectMouseOver != null) {
               Entity en = Ravenb3.mc.objectMouseOver.entityHit;
               if (en == null) {
                  return;
               }

               ay.sm("&7&m-------------------------");
               ay.sm("n: " + en.getName());
               ay.sm("rn: " + en.getName().replace("§", "%"));
               ay.sm("d: " + en.getDisplayName().getUnformattedText());
               ay.sm("rd: " + en.getDisplayName().getUnformattedText().replace("§", "%"));
               ay.sm("b?: " + AntiBot.bot(en));
            }
         } else if (d.button == 1) {
            aR();
         }

      }
   }

   public static void aL() {
      a.add(LL = System.currentTimeMillis());
   }

   public static void aR() {
      b.add(LR = System.currentTimeMillis());
   }

   public static int f() {
      a.removeIf(o -> (Long) o < System.currentTimeMillis() - 1000L);
      return a.size();
   }

   public static int i() {
      b.removeIf(o -> (Long) o < System.currentTimeMillis() - 1000L);
      return b.size();
   }
}
