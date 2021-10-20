//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.combat;

import java.lang.reflect.Field;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class DelayRemover extends Module {
   public static Description a;
   private Field l = null;

   public DelayRemover() {
      super("Delay Remover", Module.category.combat, 0);
      this.registerSetting(a = new Description("Gives you 1.7 hitreg."));
   }

   public void onEnable() {
      try {
         this.l = Minecraft.class.getDeclaredField("field_71429_W");
      } catch (Exception var4) {
         try {
            this.l = Minecraft.class.getDeclaredField("leftClickCounter");
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
      if (Utils.Player.isPlayerInGame() && this.l != null) {
         if (!mc.inGameHasFocus || mc.thePlayer.capabilities.isCreativeMode) {
            return;
         }

         try {
            this.l.set(mc, 0);
         } catch (IllegalAccessException | IndexOutOfBoundsException var3) {
         }
      }

   }
}
