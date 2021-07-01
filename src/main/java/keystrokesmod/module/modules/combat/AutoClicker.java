//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.combat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import keystrokesmod.*;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSetting;
import keystrokesmod.module.ModuleSetting2;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {
   public static ModuleDesc bestWithDelayRemover;
   public static ModuleSetting2 minCPS;
   public static ModuleSetting2 maxCPS;
   public static ModuleSetting2 jitter;
   public static ModuleSetting weaponOnly;
   public static ModuleSetting breakBlocks;
   public static ModuleSetting leftClick;
   public static ModuleSetting rightClick;
   public static ModuleSetting inventoryFill;
   private Random rand = null;
   private Method gs;
   private long i;
   private long j;
   private long k;
   private long l;
   private double m;
   private boolean n;
   private boolean hol;

   public AutoClicker() {
      super("AutoClicker", Module.category.combat, 0);
      this.registerSetting(bestWithDelayRemover = new ModuleDesc("Best with delay remover."));
      this.registerSetting(minCPS = new ModuleSetting2("Min CPS", 9.0D, 1.0D, 20.0D, 0.5D));
      this.registerSetting(maxCPS = new ModuleSetting2("Max CPS", 12.0D, 1.0D, 20.0D, 0.5D));
      this.registerSetting(jitter = new ModuleSetting2("Jitter", 0.0D, 0.0D, 3.0D, 0.1D));
      this.registerSetting(leftClick = new ModuleSetting("Left click", true));
      this.registerSetting(rightClick = new ModuleSetting("Right click", false));
      this.registerSetting(inventoryFill = new ModuleSetting("Inventory fill", false));
      this.registerSetting(weaponOnly = new ModuleSetting("Weapon only", false));
      this.registerSetting(breakBlocks = new ModuleSetting("Break blocks", false));

      try {
         this.gs = GuiScreen.class.getDeclaredMethod("func_73864_a", Integer.TYPE, Integer.TYPE, Integer.TYPE);
      } catch (Exception var4) {
         try {
            this.gs = GuiScreen.class.getDeclaredMethod("mouseClicked", Integer.TYPE, Integer.TYPE, Integer.TYPE);
         } catch (Exception var3) {
         }
      }

      if (this.gs != null) {
         this.gs.setAccessible(true);
      }

   }

   public void onEnable() {
      if (this.gs == null) {
         this.disable();
      }

      this.rand = new Random();
   }

   public void onDisable() {
      this.i = 0L;
      this.j = 0L;
      this.hol = false;
   }

   public void guiUpdate() {
      ay.b(minCPS, maxCPS);
   }

   @SubscribeEvent
   public void k(RenderTickEvent ev) {
      if (ev.phase != Phase.END && ay.isPlayerInGame() && !mc.thePlayer.isEating()) {
         if (mc.currentScreen == null && mc.inGameHasFocus) {
            if (weaponOnly.isToggled() && !ay.wpn()) {
               return;
            }

            Mouse.poll();
            if (leftClick.isToggled() && Mouse.isButtonDown(0)) {
               this.dc(mc.gameSettings.keyBindAttack.getKeyCode(), 0);
            } else if (rightClick.isToggled() && Mouse.isButtonDown(1)) {
               this.dc(mc.gameSettings.keyBindUseItem.getKeyCode(), 1);
            } else {
               this.i = 0L;
               this.j = 0L;
            }
         } else if (inventoryFill.isToggled() && mc.currentScreen instanceof GuiInventory) {
            if (!Mouse.isButtonDown(0) || !Keyboard.isKeyDown(54) && !Keyboard.isKeyDown(42)) {
               this.i = 0L;
               this.j = 0L;
            } else if (this.i != 0L && this.j != 0L) {
               if (System.currentTimeMillis() > this.j) {
                  this.gd();
                  this.ci(mc.currentScreen);
               }
            } else {
               this.gd();
            }
         }

      }
   }

   public void dc(int key, int mouse) {
      if (breakBlocks.isToggled() && mouse == 0 && mc.objectMouseOver != null) {
         BlockPos p = mc.objectMouseOver.getBlockPos();
         if (p != null) {
            Block bl = mc.theWorld.getBlockState(p).getBlock();
            if (bl != Blocks.air && !(bl instanceof BlockLiquid)) {
               if (!this.hol) {
                  KeyBinding.setKeyBindState(key, true);
                  KeyBinding.onTick(key);
                  this.hol = true;
               }

               return;
            }

            if (this.hol) {
               KeyBinding.setKeyBindState(key, false);
               this.hol = false;
            }
         }
      }

      if (jitter.getInput() > 0.0D) {
         double a = jitter.getInput() * 0.45D;
         EntityPlayerSP var10000;
         if (this.rand.nextBoolean()) {
            var10000 = mc.thePlayer;
            var10000.rotationYaw = (float)((double)var10000.rotationYaw + (double)this.rand.nextFloat() * a);
         } else {
            var10000 = mc.thePlayer;
            var10000.rotationYaw = (float)((double)var10000.rotationYaw - (double)this.rand.nextFloat() * a);
         }

         if (this.rand.nextBoolean()) {
            var10000 = mc.thePlayer;
            var10000.rotationPitch = (float)((double)var10000.rotationPitch + (double)this.rand.nextFloat() * a * 0.45D);
         } else {
            var10000 = mc.thePlayer;
            var10000.rotationPitch = (float)((double)var10000.rotationPitch - (double)this.rand.nextFloat() * a * 0.45D);
         }
      }

      if (this.j > 0L && this.i > 0L) {
         if (System.currentTimeMillis() > this.j) {
            KeyBinding.setKeyBindState(key, true);
            KeyBinding.onTick(key);
            ay.sc(mouse, true);
            this.gd();
         } else if (System.currentTimeMillis() > this.i) {
            KeyBinding.setKeyBindState(key, false);
            ay.sc(mouse, false);
         }
      } else {
         this.gd();
      }

   }

   public void gd() {
      double c = ay.mmVal(minCPS, maxCPS, this.rand) + 0.4D * this.rand.nextDouble();
      long d = (long)((int)Math.round(1000.0D / c));
      if (System.currentTimeMillis() > this.k) {
         if (!this.n && this.rand.nextInt(100) >= 85) {
            this.n = true;
            this.m = 1.1D + this.rand.nextDouble() * 0.15D;
         } else {
            this.n = false;
         }

         this.k = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
      }

      if (this.n) {
         d = (long)((double)d * this.m);
      }

      if (System.currentTimeMillis() > this.l) {
         if (this.rand.nextInt(100) >= 80) {
            d += 50L + (long)this.rand.nextInt(100);
         }

         this.l = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
      }

      this.j = System.currentTimeMillis() + d;
      this.i = System.currentTimeMillis() + d / 2L - (long)this.rand.nextInt(10);
   }

   private void ci(GuiScreen s) {
      int x = Mouse.getX() * s.width / mc.displayWidth;
      int y = s.height - Mouse.getY() * s.height / mc.displayHeight - 1;

      try {
         this.gs.invoke(s, x, y, 0);
      } catch (IllegalAccessException | InvocationTargetException var5) {
      }

   }
}
