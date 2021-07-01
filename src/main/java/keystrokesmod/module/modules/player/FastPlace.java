//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.player;

import java.lang.reflect.Field;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSetting2;
import keystrokesmod.ay;
import keystrokesmod.module.ModuleSetting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class FastPlace extends Module {
   public static ModuleSetting2 a;
   public static ModuleSetting b;
   public static Field r = null;

   public FastPlace() {
      super(new char[]{'F', 'a', 's', 't', 'P', 'l', 'a', 'c', 'e'}, Module.category.player, 0);
      this.registerSetting(a = new ModuleSetting2(new char[]{'D', 'e', 'l', 'a', 'y'}, 0.0D, 0.0D, 4.0D, 1.0D));
      this.registerSetting(b = new ModuleSetting(new char[]{'B', 'l', 'o', 'c', 'k', 's', ' ', 'o', 'n', 'l', 'y'}, true));

      try {
         r = mc.getClass().getDeclaredField(new String(new char[]{'f', 'i', 'e', 'l', 'd', '_', '7', '1', '4', '6', '7', '_', 'a', 'c'}));
      } catch (Exception var4) {
         try {
            r = mc.getClass().getDeclaredField(new String(new char[]{'r', 'i', 'g', 'h', 't', 'C', 'l', 'i', 'c', 'k', 'D', 'e', 'l', 'a', 'y', 'T', 'i', 'm', 'e', 'r'}));
         } catch (Exception var3) {
         }
      }

      if (r != null) {
         r.setAccessible(true);
      }

   }

   public void onEnable() {
      if (r == null) {
         this.disable();
      }

   }

   @SubscribeEvent
   public void a(PlayerTickEvent e) {
      if (e.phase == Phase.END) {
         if (ay.isPlayerInGame() && mc.inGameHasFocus && r != null) {
            if (b.isToggled()) {
               ItemStack item = mc.thePlayer.getHeldItem();
               if (item == null || !(item.getItem() instanceof ItemBlock)) {
                  return;
               }
            }

            try {
               int c = (int)a.getInput();
               if (c == 0) {
                  r.set(mc, 0);
               } else {
                  if (c == 4) {
                     return;
                  }

                  int d = r.getInt(mc);
                  if (d == 4) {
                     r.set(mc, c);
                  }
               }
            } catch (IllegalAccessException var4) {
            } catch (IndexOutOfBoundsException var5) {
            }
         }

      }
   }
}
