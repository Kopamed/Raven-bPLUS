//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.movement;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSetting2;
import keystrokesmod.ay;

public class Fly extends Module {
   private final Fly.VanFly vanFly = new Fly.VanFly();
   private final Fly.GliFly gliFly = new Fly.GliFly();
   public static ModuleDesc dc;
   public static ModuleSetting2 a;
   public static ModuleSetting2 b;
   private static final String c1 = new String(new char[]{'V', 'a', 'n', 'i', 'l', 'l', 'a'});
   private static final String c2 = new String(new char[]{'G', 'l', 'i', 'd', 'e'});

   public Fly() {
      super(new char[]{'F', 'l', 'y'}, Module.category.movement, 0);
      this.registerSetting(a = new ModuleSetting2(new char[]{'V', 'a', 'l', 'u', 'e'}, 1.0D, 1.0D, 2.0D, 1.0D));
      this.registerSetting(dc = new ModuleDesc(ay.md + c1));
      this.registerSetting(b = new ModuleSetting2(new char[]{'S', 'p', 'e', 'e', 'd'}, 2.0D, 1.0D, 5.0D, 0.1D));
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
         dc.setDesc(ay.md + c1);
         break;
      case 2:
         dc.setDesc(ay.md + c2);
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
               double r = Math.toRadians((double)(Module.mc.thePlayer.rotationYaw + 90.0F));
               Module.mc.thePlayer.motionX = s * Math.cos(r);
               Module.mc.thePlayer.motionZ = s * Math.sin(r);
            }
         }

      }
   }

   class VanFly {
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
