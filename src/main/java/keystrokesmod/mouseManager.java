//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod;

import java.util.ArrayList;
import java.util.List;

import keystrokesmod.main.Ravenbplus;
import keystrokesmod.module.modules.world.AntiBot;
import keystrokesmod.utils.ay;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class mouseManager {
   private static final List<Long> leftClicks = new ArrayList();
   private static final List<Long> rightClicks = new ArrayList();
   public static long leftClickTimer = 0L;
   public static long rightClickTimer = 0L;

   @SubscribeEvent
   public void onMouseUpdate(MouseEvent mouse) {
      if (mouse.buttonstate) {
         if (mouse.button == 0) {
            addLeftClick();
            if (Ravenbplus.debugger && Ravenbplus.mc.objectMouseOver != null) {
               Entity en = Ravenbplus.mc.objectMouseOver.entityHit;
               if (en == null) {
                  return;
               }

               ay.sendMessageToSelf("&7&m-------------------------");
               ay.sendMessageToSelf("n: " + en.getName());
               ay.sendMessageToSelf("rn: " + en.getName().replace("§", "%"));
               ay.sendMessageToSelf("d: " + en.getDisplayName().getUnformattedText());
               ay.sendMessageToSelf("rd: " + en.getDisplayName().getUnformattedText().replace("§", "%"));
               ay.sendMessageToSelf("b?: " + AntiBot.bot(en));
            }
         } else if (mouse.button == 1) {
            addRightClick();
         }

      }
   }

   public static void addLeftClick() {
      leftClicks.add(leftClickTimer = System.currentTimeMillis());
   }

   public static void addRightClick() {
      rightClicks.add(rightClickTimer = System.currentTimeMillis());
   }


   //prev f
   public static int getLeftClickCounter() {
      try{
         leftClicks.removeIf(o -> o < System.currentTimeMillis() - 1000L);
      } catch (Exception welpTheKeystrokes){
         welpTheKeystrokes.printStackTrace();
      }
      return leftClicks.size();
   }


   // prev i
   public static int getRightClickCounter() {
      rightClicks.removeIf(o -> o < System.currentTimeMillis() - 1000L);
      return rightClicks.size();
   }
}
