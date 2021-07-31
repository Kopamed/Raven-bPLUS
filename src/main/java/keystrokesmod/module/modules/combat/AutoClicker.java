//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.combat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import keystrokesmod.*;
import keystrokesmod.main.NotAName;
import keystrokesmod.main.Ravenb3;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.ModuleSettingSlider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {
   public static ModuleDesc bestWithDelayRemover, modeDesc;
   public static ModuleSettingSlider leftMinCPS, rightMinCPS;
   public static ModuleSettingSlider leftMaxCPS, rightMaxCPS;
   public static ModuleSettingSlider jitter;
   public static ModuleSettingTick weaponOnly;
   public static ModuleSettingTick breakBlocks;
   public static ModuleSettingTick onlyBlocks;
   public static ModuleSettingTick preferFastPlace;
   public static ModuleSettingTick noBlockSword;
   public static ModuleSettingTick leftClick;
   public static ModuleSettingTick rightClick;
   public static ModuleSettingTick inventoryFill;
   public static ModuleSettingTick allowEat, allowBow;
   public static ModuleSettingSlider mode;
   private Random rand = null;
   private Method playerMouseInput;
   private long lefti, righti;
   private long leftj, rightj;
   private long leftk, rightk;
   private long leftl, rightl;
   private double leftm, rightm;
   private boolean leftn, rightn;
   private boolean leftHeld, rightHeld;
   private double speedLeft, speedRight;
   private double leftHoldLength, rightHoldLength;
   private long lastClick;
   private long leftHold, rightHold;

   public AutoClicker() {
      super("AutoClicker", Module.category.combat, 0);
      this.registerSetting(bestWithDelayRemover = new ModuleDesc("Best with delay remover."));
      this.registerSetting(leftClick = new ModuleSettingTick("Left click", true));
      this.registerSetting(leftMinCPS = new ModuleSettingSlider("Left Min CPS", 9.0D, 1.0D, 60.0D, 0.5D));
      this.registerSetting(leftMaxCPS = new ModuleSettingSlider("Left Max CPS", 13.0D, 1.0D, 60.0D, 0.5D));
      this.registerSetting(rightClick = new ModuleSettingTick("Right click", false));
      this.registerSetting(rightMinCPS = new ModuleSettingSlider("Right Min CPS", 12.0D, 1.0D, 60.0D, 0.5D));
      this.registerSetting(rightMaxCPS = new ModuleSettingSlider("Right Max CPS", 16.0D, 1.0D, 60.0D, 0.5D));
      this.registerSetting(inventoryFill = new ModuleSettingTick("Inventory fill", false));
      this.registerSetting(weaponOnly = new ModuleSettingTick("Weapon only", false));
      this.registerSetting(noBlockSword = new ModuleSettingTick("Don't rightclick sword", true));
      this.registerSetting(onlyBlocks = new ModuleSettingTick("Only rightclick with blocks", false));
      this.registerSetting(preferFastPlace = new ModuleSettingTick("Prefer fast place", false));
      this.registerSetting(breakBlocks = new ModuleSettingTick("Break blocks", false));
      this.registerSetting(allowEat = new ModuleSettingTick("Allow eat", true));
      this.registerSetting(allowBow = new ModuleSettingTick("Allow bow", true));
      this.registerSetting(jitter = new ModuleSettingSlider("Jitter", 0.0D, 0.0D, 3.0D, 0.1D));
      this.registerSetting(mode = new ModuleSettingSlider("Value", 2.0D, 1.0D, 2.0D, 1.0D));
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
      this.lefti = 0L;
      this.leftj = 0L;
      this.leftHeld = false;
   }

   public void guiUpdate() {
      ay.correctSliders(leftMinCPS, leftMaxCPS);
      ay.correctSliders(rightMinCPS, rightMaxCPS);
      modeDesc.setDesc(ay.md + ay.ClickMode.values()[(int)(mode.getInput() - 1.0D)].name());
   }

   @SubscribeEvent
   public void onRenderTick(RenderTickEvent ev) {
      if (ev.phase != Phase.END && ay.isPlayerInGame() && ay.ClickMode.values()[(int)(mode.getInput() - 1.0D)] == ay.ClickMode.RAVEN) {
         if (mc.currentScreen == null && mc.inGameHasFocus) {
            if (weaponOnly.isToggled() && !ay.wpn()) {
               return;
            }

            Mouse.poll();
            if (leftClick.isToggled() && Mouse.isButtonDown(0)) {
               this.leftClickExecute(mc.gameSettings.keyBindAttack.getKeyCode());
            } else if (rightClick.isToggled() && Mouse.isButtonDown(1)) {
               if (!this.rightClickAllowed())
                  return;
               this.rightClickExecute(mc.gameSettings.keyBindUseItem.getKeyCode());
            } else {
               this.lefti = 0L;
               this.leftj = 0L;
               this.righti = 0L;
               this.rightj = 0L;
            }
         } else if (inventoryFill.isToggled() && mc.currentScreen instanceof GuiInventory) {
            if (!Mouse.isButtonDown(0) || !Keyboard.isKeyDown(54) && !Keyboard.isKeyDown(42)) {
               this.lefti = 0L;
               this.leftj = 0L;
            } else if (this.lefti != 0L && this.leftj != 0L) {
               if (System.currentTimeMillis() > this.leftj) {
                  this.genLeftTimings();
                  this.inInvClick(mc.currentScreen);
               }
            } else {
               this.genLeftTimings();
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
         EntityPlayerSP entityPlayer;
         if (this.rand.nextBoolean()) {
            entityPlayer = mc.thePlayer;
            entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw + (double)this.rand.nextFloat() * a);
         } else {
            entityPlayer = mc.thePlayer;
            entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw - (double)this.rand.nextFloat() * a);
         }

         if (this.rand.nextBoolean()) {
            entityPlayer = mc.thePlayer;
            entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch + (double)this.rand.nextFloat() * a * 0.45D);
         } else {
            entityPlayer = mc.thePlayer;
            entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch - (double)this.rand.nextFloat() * a * 0.45D);
         }
      }

      if (this.leftj > 0L && this.lefti > 0L) {
         if (System.currentTimeMillis() > this.leftj) {
            KeyBinding.setKeyBindState(key, true);
            KeyBinding.onTick(key);
            ay.sc(0, true);
            this.genLeftTimings();
         } else if (System.currentTimeMillis() > this.lefti) {
            KeyBinding.setKeyBindState(key, false);
            ay.sc(0, false);
         }
      } else {
         this.genLeftTimings();
      }

   }

   public void rightClickExecute(int key) {
      if (jitter.getInput() > 0.0D) {
         double jitterMultiplier = jitter.getInput() * 0.45D;
         EntityPlayerSP entityPlayer;
         if (this.rand.nextBoolean()) {
            entityPlayer = mc.thePlayer;
            entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw + (double)this.rand.nextFloat() * jitterMultiplier);
         } else {
            entityPlayer = mc.thePlayer;
            entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw - (double)this.rand.nextFloat() * jitterMultiplier);
         }

         if (this.rand.nextBoolean()) {
            entityPlayer = mc.thePlayer;
            entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch + (double)this.rand.nextFloat() * jitterMultiplier * 0.45D);
         } else {
            entityPlayer = mc.thePlayer;
            entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch - (double)this.rand.nextFloat() * jitterMultiplier * 0.45D);
         }
      }

      if (this.rightj > 0L && this.righti > 0L) {
         if (System.currentTimeMillis() > this.rightj) {
            KeyBinding.setKeyBindState(key, true);
            KeyBinding.onTick(key);
            ay.sc(1, true);
            this.genRightTimings();
         } else if (System.currentTimeMillis() > this.righti) {
            KeyBinding.setKeyBindState(key, false);
            ay.sc(1, false);
         }
      } else {
         this.genRightTimings();
      }

   }

   public void genLeftTimings() {
      double clickSpeed = ay.ranModuleVal(leftMinCPS, leftMaxCPS, this.rand) + 0.4D * this.rand.nextDouble();
      long delay = (long)((int)Math.round(1000.0D / clickSpeed));
      if (System.currentTimeMillis() > this.leftk) {
         if (!this.leftn && this.rand.nextInt(100) >= 85) {
            this.leftn = true;
            this.leftm = 1.1D + this.rand.nextDouble() * 0.15D;
         } else {
            this.leftn = false;
         }

         this.leftk = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
      }

      if (this.leftn) {
         delay = (long)((double)delay * this.leftm);
      }

      if (System.currentTimeMillis() > this.leftl) {
         if (this.rand.nextInt(100) >= 80) {
            delay += 50L + (long)this.rand.nextInt(100);
         }

         this.leftl = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
      }

      this.leftj = System.currentTimeMillis() + delay;
      this.lefti = System.currentTimeMillis() + delay / 2L - (long)this.rand.nextInt(10);
   }

   public void genRightTimings() {
      double clickSpeed = ay.ranModuleVal(rightMinCPS, rightMaxCPS, this.rand) + 0.4D * this.rand.nextDouble();
      long delay = (long)((int)Math.round(1000.0D / clickSpeed));
      if (System.currentTimeMillis() > this.rightk) {
         if (!this.rightn && this.rand.nextInt(100) >= 85) {
            this.rightn = true;
            this.rightm = 1.1D + this.rand.nextDouble() * 0.15D;
         } else {
            this.rightn = false;
         }

         this.rightk = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
      }

      if (this.rightn) {
         delay = (long)((double)delay * this.rightm);
      }

      if (System.currentTimeMillis() > this.rightl) {
         if (this.rand.nextInt(100) >= 80) {
            delay += 50L + (long)this.rand.nextInt(100);
         }

         this.rightl = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
      }

      this.rightj = System.currentTimeMillis() + delay;
      this.righti = System.currentTimeMillis() + delay / 2L - (long)this.rand.nextInt(10);
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
      guiUpdate();
      if (ay.ClickMode.values()[(int)(mode.getInput() - 1.0D)] != ay.ClickMode.LEGIT)
         return;

      speedLeft = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftMinCPS.getInput() - 0.2D, leftMaxCPS.getInput());
      leftHoldLength = speedLeft / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftMinCPS.getInput() - 0.02D, leftMaxCPS.getInput());
      speedRight = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble( rightMinCPS.getInput() - 0.2D, rightMaxCPS.getInput());
      rightHoldLength = speedRight / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(rightMinCPS.getInput() - 0.02D, rightMaxCPS.getInput());
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
            if (leftHold < lastClick){
               leftHold = lastClick;
            }
            int key = mc.gameSettings.keyBindAttack.getKeyCode();
            KeyBinding.setKeyBindState(key, true);
            KeyBinding.onTick(key);
         } else if (System.currentTimeMillis() - leftHold > leftHoldLength * 1000) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
         }
      }
      //we cheat in a block game ft. right click
      if (Mouse.isButtonDown(1) && rightClick.isToggled()) {
         if (!this.rightClickAllowed())
            return;



         if (System.currentTimeMillis() - lastClick > speedRight * 1000) {
            lastClick = System.currentTimeMillis();
            if (leftHold < lastClick){
               leftHold = lastClick;
            }
            int key = mc.gameSettings.keyBindUseItem.getKeyCode();
            KeyBinding.setKeyBindState(key, true);
            KeyBinding.onTick(key);
         } else if (System.currentTimeMillis() - leftHold > rightHoldLength * 1000) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
         }
      }
   }

   public boolean rightClickAllowed() {
      ItemStack item = mc.thePlayer.getHeldItem();
      if (item != null) {
         if (this.allowEat.isToggled()) {
            if ((item.getItem() instanceof ItemFood)) {
               return false;
            }
         }
         if (this.allowBow.isToggled()) {
            if (item.getItem() instanceof ItemBow) {
               return false;
            }
         }
         if (this.onlyBlocks.isToggled()) {
            if (!(item.getItem() instanceof ItemBlock))
               return false;
         }
         if (this.noBlockSword.isToggled()) {
            if (item.getItem() instanceof ItemSword)
               return false;
         }
      }

      if(this.preferFastPlace.isToggled()) {
         Module fastplace = NotAName.moduleManager.getModuleByName("FastPlace");
         if (fastplace.isEnabled())
            return false;
      }
      return true;
   }
}
