//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.movement;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.utils.Utils;

public class Fly extends Module {
   private final Fly.VanFly vanFly = new VanFly();
   private final Fly.GliFly gliFly = new Fly.GliFly();
   public static Description dc;
   public static Slider a;
   public static Slider b;
   private static final String c1 = "Vanilla";
   private static final String c2 = "Glide";

   public Fly() {
      super("Fly", Module.category.movement, 0);
      this.registerSetting(a = new Slider("Value", 1.0D, 1.0D, 2.0D, 1.0D));
      this.registerSetting(dc = new Description(Utils.md + c1));
      this.registerSetting(b = new Slider("Speed", 2.0D, 1.0D, 5.0D, 0.1D));
   }

   public void onEnable() {
      switch((int)a.getInput()) {
      case 1:
         this.vanFly.onEnable();
         break;
      case 2:
         this.gliFly.onEnable();
      }

   }

   public void onDisable() {
      switch((int)a.getInput()) {
      case 1:
         this.vanFly.onDisable();
         break;
      case 2:
         this.gliFly.onDisable();
      }

   }

   public void update() {
      switch((int)a.getInput()) {
      case 1:
         this.vanFly.update();
         break;
      case 2:
         this.gliFly.update();
      }

   }

   public void guiUpdate() {
      switch((int)a.getInput()) {
      case 1:
         dc.setDesc(Utils.md + c1);
         break;
      case 2:
         dc.setDesc(Utils.md + c2);
      }

   }

   class GliFly {
      boolean opf = false;

      public void onEnable() {
      }

      public void onDisable() {
         this.opf = false;
      }

      public void update() {
         if (Module.mc.thePlayer.movementInput.moveForward > 0.0F) {
            if (!this.opf) {
               this.opf = true;
               if (Module.mc.thePlayer.onGround) {
                  Module.mc.thePlayer.jump();
               }
            } else {
               if (Module.mc.thePlayer.onGround || Module.mc.thePlayer.isCollidedHorizontally) {
                  Fly.this.disable();
                  return;
               }

               double s = 1.94D * Fly.b.getInput();
               double r = Math.toRadians(Module.mc.thePlayer.rotationYaw + 90.0F);
               Module.mc.thePlayer.motionX = s * Math.cos(r);
               Module.mc.thePlayer.motionZ = s * Math.sin(r);
            }
         }

      }
   }

   static class VanFly {
      private final float dfs = 0.05F;

      public void onEnable() {
      }

      public void onDisable() {
         if (Module.mc.thePlayer.capabilities.isFlying) {
            Module.mc.thePlayer.capabilities.isFlying = false;
         }

         Module.mc.thePlayer.capabilities.setFlySpeed(0.05F);
      }

      public void update() {
         Module.mc.thePlayer.motionY = 0.0D;
         Module.mc.thePlayer.capabilities.setFlySpeed((float)(0.05000000074505806D * Fly.b.getInput()));
         Module.mc.thePlayer.capabilities.isFlying = true;
      }
   }
}
