//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.combat;

import java.lang.reflect.Field;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.ay;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class DelayRemover extends Module {
   public static ModuleDesc a;
   private Field l = null;

   public DelayRemover() {
      super(new char[]{'D', 'e', 'l', 'a', 'y', ' ', 'R', 'e', 'm', 'o', 'v', 'e', 'r'}, Module.category.combat, 0);
      this.registerSetting(a = new ModuleDesc(new String(new char[]{'G', 'i', 'v', 'e', 's', ' ', 'y', 'o', 'u', ' ', '1', '.', '7', ' ', 'h', 'i', 't', 'r', 'e', 'g', '.'})));
   }

   public void onEnable() {
      try {
         this.l = Minecraft.class.getDeclaredField(new String(new char[]{'f', 'i', 'e', 'l', 'd', '_', '7', '1', '4', '2', '9', '_', 'W'}));
      } catch (Exception var4) {
         try {
            this.l = Minecraft.class.getDeclaredField(new String(new char[]{'l', 'e', 'f', 't', 'C', 'l', 'i', 'c', 'k', 'C', 'o', 'u', 'n', 't', 'e', 'r'}));
         } catch (Exception var3) {
         }
      }

      if (this.l != null) {
         this.l.setAccessible(true);
      } else {
         this.disable();
      }

   }

   @SubscribeEvent
   public void a(PlayerTickEvent b) {
      if (ay.isPlayerInGame() && this.l != null) {
         if (!mc.inGameHasFocus || mc.thePlayer.capabilities.isCreativeMode) {
            return;
         }

         try {
            this.l.set(mc, 0);
         } catch (IllegalAccessException var3) {
         } catch (IndexOutOfBoundsException var4) {
         }
      }

   }
}
