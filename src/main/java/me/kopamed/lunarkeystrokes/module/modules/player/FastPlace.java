//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.player;

import java.lang.reflect.Field;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class FastPlace extends Module {
   public static Slider a;
   public static Tick b;
   public static Field r = null;

   public FastPlace() {
      super("FastPlace", Module.category.player, 0);
      this.registerSetting(a = new Slider("Delay", 0.0D, 0.0D, 4.0D, 1.0D));
      this.registerSetting(b = new Tick("Blocks only", true));

      try {
         r = mc.getClass().getDeclaredField("field_71467_ac");
      } catch (Exception var4) {
         try {
            r = mc.getClass().getDeclaredField("rightClickDelayTimer");
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
         if (Utils.Player.isPlayerInGame() && mc.inGameHasFocus && r != null) {
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
