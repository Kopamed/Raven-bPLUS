//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.combat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class BurstClicker extends Module {
   public static Description artificialDragClicking;
   public static Slider clicks;
   public static Slider delay;
   public static Tick delayRandomizer;
   public static Tick placeWhenBlock;
   private boolean l_c = false;
   private boolean l_r = false;
   private Method rightClickMouse = null;

   public BurstClicker() {
      super("BurstClicker", Module.category.combat, 0);
      this.registerSetting(artificialDragClicking = new Description("Artificial dragclicking."));
      this.registerSetting(clicks = new Slider("Clicks", 0.0D, 0.0D, 50.0D, 1.0D));
      this.registerSetting(delay = new Slider("Delay (ms)", 5.0D, 1.0D, 40.0D, 1.0D));
      this.registerSetting(delayRandomizer = new Tick("Delay randomizer", true));
      this.registerSetting(placeWhenBlock = new Tick("Place when block", false));
      try {
         try {
            this.rightClickMouse = mc.getClass().getDeclaredMethod("func_147121_ag");
         } catch (NoSuchMethodException var4) {
            try {
               this.rightClickMouse = mc.getClass().getDeclaredMethod("rightClickMouse");
            } catch (NoSuchMethodException var3) {
            }
         }
      } catch(NoClassDefFoundError varbruh){
         varbruh.printStackTrace();
      }

      if (this.rightClickMouse != null) {
         this.rightClickMouse.setAccessible(true);
      }

   }

   public void onEnable() {
      if (clicks.getInput() != 0.0D && mc.currentScreen == null && mc.inGameHasFocus) {
         Ravenbplus.getExecutor().execute(() -> {
            try {
               int cl = (int) clicks.getInput();
               int del = (int) delay.getInput();

               for(int i = 0; i < cl * 2 && this.isEnabled() && Utils.Player.isPlayerInGame() && mc.currentScreen == null && mc.inGameHasFocus; ++i) {
                  if (i % 2 == 0) {
                     this.l_c = true;
                     if (del != 0) {
                        int realDel = del;
                        if (delayRandomizer.isToggled()) {
                           realDel = del + Utils.Java.rand().nextInt(25) * (Utils.Java.rand().nextBoolean() ? -1 : 1);
                           if (realDel <= 0) {
                              realDel = del / 3 - realDel;
                           }
                        }

                        Thread.sleep(realDel);
                     }
                  } else {
                     this.l_r = true;
                  }
               }

               this.disable();
            } catch (InterruptedException var5) {
            }

         });
      } else {
         this.disable();
      }
   }

   public void onDisable() {
      this.l_c = false;
      this.l_r = false;
   }

   @SubscribeEvent
   public void r(RenderTickEvent ev) {
      if (Utils.Player.isPlayerInGame()) {
         if (this.l_c) {
            this.c(true);
            this.l_c = false;
         } else if (this.l_r) {
            this.c(false);
            this.l_r = false;
         }
      }

   }

   private void c(boolean st) {
      boolean r = placeWhenBlock.isToggled() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock;
      if (r) {
         try {
            this.rightClickMouse.invoke(mc);
         } catch (IllegalAccessException | InvocationTargetException var4) {
         }
      } else {
         int key = mc.gameSettings.keyBindAttack.getKeyCode();
         KeyBinding.setKeyBindState(key, st);
         if (st) {
            KeyBinding.onTick(key);
         }
      }

      Utils.Client.setMouseButtonState(r ? 1 : 0, st);
   }
}
