//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.combat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {
   public static ModuleDesc bestWithDelayRemover, modeDesc;
   public static ModuleSetting2 leftMinCPS, rightMinCPS;
   public static ModuleSetting2 leftMaxCPS, rightMaxCPS;
   public static ModuleSetting2 jitter;
   public static ModuleSetting weaponOnly;
   public static ModuleSetting breakBlocks;
   public static ModuleSetting leftClick;
   public static ModuleSetting rightClick;
   public static ModuleSetting inventoryFill;
   public static ModuleSetting allowEat, allowBow;
   public static ModuleSetting2 mode;
   private Random rand = null;
   private Method playerMouseInput;
   private long i;
   private long j;
   private long k;
   private long l;
   private double m;
   private boolean n;
   private boolean leftHeld;
   private double speedLeft, speedRight;
   private double leftHoldLength, rightHoldLength;
   private long lastClick;
   private long hold;

   public AutoClicker() {
      super("AutoClicker", Module.category.combat, 0);
      this.registerSetting(bestWithDelayRemover = new ModuleDesc("Best with delay remover."));
      this.registerSetting(leftClick = new ModuleSetting("Left click", true));
      this.registerSetting(leftMinCPS = new ModuleSetting2("Left Min CPS", 9.0D, 1.0D, 60.0D, 0.5D));
      this.registerSetting(leftMaxCPS = new ModuleSetting2("Left Max CPS", 13.0D, 1.0D, 60.0D, 0.5D));
      this.registerSetting(rightClick = new ModuleSetting("Right click", false));
      this.registerSetting(rightMinCPS = new ModuleSetting2("Right Min CPS", 12.0D, 1.0D, 60.0D, 0.5D));
      this.registerSetting(rightMaxCPS = new ModuleSetting2("Right Max CPS", 16.0D, 1.0D, 60.0D, 0.5D));
      this.registerSetting(inventoryFill = new ModuleSetting("Inventory fill", false));
      this.registerSetting(weaponOnly = new ModuleSetting("Weapon only", false));
      this.registerSetting(breakBlocks = new ModuleSetting("Break blocks", false));
      this.registerSetting(allowEat = new ModuleSetting("Allow eat", true));
      this.registerSetting(allowBow = new ModuleSetting("Allow bow", true));
      this.registerSetting(jitter = new ModuleSetting2("Jitter", 0.0D, 0.0D, 3.0D, 0.1D));
      this.registerSetting(mode = new ModuleSetting2("Value", 2.0D, 1.0D, 2.0D, 1.0D));
      this.registerSetting(modeDesc = new ModuleDesc("Mode: LEGIT"));

      try {
         this.playerMouseInput = GuiScreen.class.getDeclaredMethod("func_73864_a", Integer.TYPE, Integer.TYPE, Integer.TYPE);
      } catch (Exception var4) {
         try {
            this.playerMouseInput = GuiScreen.class.getDeclaredMethod("mouseClicked", Integer.TYPE, Integer.TYPE, Integer.TYPE);
         } catch (Exception var3) {
         }
      }

      if (this.playerMouseInput != null) {
         this.playerMouseInput.setAccessible(true);
      }

   }

   public void onEnable() {
      if (this.playerMouseInput == null) {
         this.disable();
      }

      this.rand = new Random();
   }

   public void onDisable() {
      this.i = 0L;
      this.j = 0L;
      this.leftHeld = false;
   }

   public void guiUpdate() {
      ay.b(leftMinCPS, leftMaxCPS);
      modeDesc.setDesc(ay.md + ay.ClickMode.values()[(int)(mode.getInput() - 1.0D)].name());
   }

   @SubscribeEvent
   public void onRenderTick(RenderTickEvent ev) {
      if (ev.phase != Phase.END && ay.isPlayerInGame() && !mc.thePlayer.isEating() && ay.ClickMode.values()[(int)(mode.getInput() - 1.0D)] == ay.ClickMode.RAVEN) {
         if (mc.currentScreen == null && mc.inGameHasFocus) {
            if (weaponOnly.isToggled() && !ay.wpn()) {
               return;
            }

            Mouse.poll();
            if (leftClick.isToggled() && Mouse.isButtonDown(0)) {
               this.leftClickExecute(mc.gameSettings.keyBindAttack.getKeyCode());
            } else if (rightClick.isToggled() && Mouse.isButtonDown(1)) {
               this.rightClickExecute(mc.gameSettings.keyBindUseItem.getKeyCode());
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
                  this.genTimings();
                  this.inInvClick(mc.currentScreen);
               }
            } else {
               this.genTimings();
            }
         }

      }
   }

   public void leftClickExecute(int key) {
      if (breakBlocks.isToggled() && mc.objectMouseOver != null) {
         BlockPos p = mc.objectMouseOver.getBlockPos();
         if (p != null) {
            Block bl = mc.theWorld.getBlockState(p).getBlock();
            if (bl != Blocks.air && !(bl instanceof BlockLiquid)) {
               if (!this.leftHeld) {
                  KeyBinding.setKeyBindState(key, true);
                  KeyBinding.onTick(key);
                  this.leftHeld = true;
               }

               return;
            }

            if (this.leftHeld) {
               KeyBinding.setKeyBindState(key, false);
               this.leftHeld = false;
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
            ay.sc(0, true);
            this.genTimings();
         } else if (System.currentTimeMillis() > this.i) {
            KeyBinding.setKeyBindState(key, false);
            ay.sc(0, false);
         }
      } else {
         this.genTimings();
      }

   }

   public void rightClickExecute(int key) {
      ItemStack item = mc.thePlayer.getHeldItem();
      if (item != null) {
         if (this.allowEat.isToggled()) {
            if ((item.getItem() instanceof ItemFood)) {
               return;
            }
         }
         if (this.allowBow.isToggled()) {
            if (item.getItem() instanceof ItemBow) {
               return;
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
            ay.sc(1, true);
            this.genTimings();
         } else if (System.currentTimeMillis() > this.i) {
            KeyBinding.setKeyBindState(key, false);
            ay.sc(1, false);
         }
      } else {
         this.testgenTimings();
      }

   }

   public void genTimings() {
      double clickSpeed = ay.ranModuleVal(leftMinCPS, leftMaxCPS, this.rand) + 0.4D * this.rand.nextDouble();
      long delay = (long)((int)Math.round(1000.0D / clickSpeed));
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
         delay = (long)((double)delay * this.m);
      }

      if (System.currentTimeMillis() > this.l) {
         if (this.rand.nextInt(100) >= 80) {
            delay += 50L + (long)this.rand.nextInt(100);
         }

         this.l = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
      }

      this.j = System.currentTimeMillis() + delay;
      this.i = System.currentTimeMillis() + delay / 2L - (long)this.rand.nextInt(10);
   }

   public void testgenTimings() {
      double clickSpeed = ay.ranModuleVal(rightMinCPS, rightMaxCPS, this.rand) + 0.4D * this.rand.nextDouble();
      System.out.println("Click speeed " + clickSpeed);
      long delay = (long)((int)Math.round(1000.0D / clickSpeed));
      System.out.println("Delay " + delay);
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
         delay = (long)((double)delay * this.m);
      }

      if (System.currentTimeMillis() > this.l) {
         if (this.rand.nextInt(100) >= 80) {
            delay += 50L + (long)this.rand.nextInt(100);
         }

         this.l = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
      }

      this.j = System.currentTimeMillis() + delay;
      this.i = System.currentTimeMillis() + delay / 2L - (long)this.rand.nextInt(10);
   }

   private void inInvClick(GuiScreen guiScreen) {
      int mouseInGUIPosX = Mouse.getX() * guiScreen.width / mc.displayWidth;
      int mouseInGUIPosY = guiScreen.height - Mouse.getY() * guiScreen.height / mc.displayHeight - 1;

      try {
         this.playerMouseInput.invoke(guiScreen, mouseInGUIPosX, mouseInGUIPosY, 0);
      } catch (IllegalAccessException | InvocationTargetException var5) {
      }

   }

   @SubscribeEvent
   public void onTick(TickEvent.PlayerTickEvent e) {
      if (!ay.isPlayerInGame()) {
         return;
      }

      if (ay.ClickMode.values()[(int)(mode.getInput() - 1.0D)] != ay.ClickMode.LEGIT)
         return;

      speedLeft = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftMinCPS.getInput() - 0.2, leftMaxCPS.getInput());
      leftHoldLength = speedLeft / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftMinCPS.getInput(), leftMaxCPS.getInput());
      speedRight = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble( rightMinCPS.getInput() - 0.2, rightMaxCPS.getInput());
      rightHoldLength = speedRight / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(rightMinCPS.getInput(), rightMaxCPS.getInput());
      //If none of the buttons are allowed to click, what is the point in generating clicktimes anyway?
      //if (!leftActive && !rightActive) {
        // return;
      //}


      // Uhh left click only, mate
      if (Mouse.isButtonDown(0) && leftClick.isToggled()) {
         if(breakBlocks.isToggled()) {
            BlockPos lookingBlock = mc.objectMouseOver.getBlockPos();
            if (lookingBlock != null) {
               Block stateBlock = mc.theWorld.getBlockState(lookingBlock).getBlock();
               if (stateBlock != Blocks.air && !(stateBlock instanceof BlockLiquid)) {
                  int key = mc.gameSettings.keyBindAttack.getKeyCode();
                  KeyBinding.setKeyBindState(key, true);
                  KeyBinding.onTick(key);
                  return;
               }
            }
         }

         double speedLeft = 1.0 / ThreadLocalRandom.current().nextDouble(leftMinCPS.getInput() - 0.2, leftMaxCPS.getInput());
         if (System.currentTimeMillis() - lastClick > speedLeft * 1000) {
            lastClick = System.currentTimeMillis();
            if (hold < lastClick){
               hold = lastClick;
            }
            int key = mc.gameSettings.keyBindAttack.getKeyCode();
            KeyBinding.setKeyBindState(key, true);
            KeyBinding.onTick(key);
         } else if (System.currentTimeMillis() - hold > leftHoldLength * 1000) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
         }
      }
      //we cheat in a block game ft. right click
      if (Mouse.isButtonDown(1) && rightClick.isToggled()) {
         ItemStack item = mc.thePlayer.getHeldItem();
         if (item != null) {
            if (allowEat.isToggled()) {
               if ((item.getItem() instanceof ItemFood)) {
                  return;
               }
            }
            if (allowBow.isToggled()) {
               if (item.getItem() instanceof ItemBow) {
                  return;
               }
            }
         }



         if (System.currentTimeMillis() - lastClick > speedRight * 1000) {
            lastClick = System.currentTimeMillis();
            if (hold < lastClick){
               hold = lastClick;
            }
            int key = mc.gameSettings.keyBindUseItem.getKeyCode();
            KeyBinding.setKeyBindState(key, true);
            KeyBinding.onTick(key);
         } else if (System.currentTimeMillis() - hold > rightHoldLength * 1000) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
         }
      }
   }
}
