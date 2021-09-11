package keystrokesmod.module.modules.combat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import keystrokesmod.main.NotAName;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.modules.debug.Click;
import keystrokesmod.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {
   public static ModuleDesc bestWithDelayRemover, modeDesc, timingsDesc;
   public static ModuleSettingSlider leftMinCPS, rightMinCPS, breakBlocksMin, breakBlocksMax;
   public static ModuleSettingSlider leftMaxCPS, rightMaxCPS;
   public static ModuleSettingSlider jitterLeft;
   public static ModuleSettingSlider jitterRight;
   public static ModuleSettingTick weaponOnly;
   public static ModuleSettingTick breakBlocks;
   public static ModuleSettingTick onlyBlocks;
   public static ModuleSettingTick preferFastPlace;
   public static ModuleSettingTick noBlockSword;
   public static ModuleSettingTick leftClick;
   public static ModuleSettingTick rightClick;
   public static ModuleSettingTick inventoryFill;
   public static ModuleSettingTick allowEat, allowBow;
   public static ModuleSettingSlider rightClickDelay;
   public static ModuleSettingSlider clickEvent, clickTimings;

   private Random rand = null;
   private Method playerMouseInput;
   private long leftDownTime, righti;
   private long leftUpTime, rightj;
   private long leftk, rightk;
   private long leftl, rightl;
   private double leftm, rightm;
   private boolean leftn, rightn;
   private boolean breakHeld;
   private boolean watingForBreakTimeout;
   private double breakBlockFinishWaitTime;
   private long lastClick;
   private long leftHold, rightHold;
   private boolean rightClickWaiting;
   private double rightClickWaitStartTime;
   private boolean allowedClick;
   public static boolean autoClickerEnabled, breakTimeDone;
   public static int clickFinder;
   public static int clickCount;
   private boolean leftDown;
   private boolean rightDown;

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
      this.registerSetting(breakBlocksMin = new ModuleSettingSlider("Breack blocks min delay", 20.0D, 0.0D, 1000.0D, 5D));
      this.registerSetting(breakBlocksMax = new ModuleSettingSlider("Breack blocks max delay", 50.0D, 0.0D, 1000.0D, 5D));
      this.registerSetting(allowEat = new ModuleSettingTick("Allow eat", true));
      this.registerSetting(allowBow = new ModuleSettingTick("Allow bow", true));
      this.registerSetting(jitterLeft = new ModuleSettingSlider("Jitter left", 0.0D, 0.0D, 3.0D, 0.1D));
      this.registerSetting(jitterRight = new ModuleSettingSlider("Jitter right", 0.0D, 0.0D, 3.0D, 0.1D));
      this.registerSetting(rightClickDelay = new ModuleSettingSlider("Rightclick delay (ms)", 85D, 0D, 500D, 1.0D));
      this.registerSetting(clickTimings = new ModuleSettingSlider("ClickStyle", 1.0D, 1.0D, 2.0D, 1.0D));
      this.registerSetting(timingsDesc = new ModuleDesc("Mode: RAVEN"));
      this.registerSetting(clickEvent = new ModuleSettingSlider("Event", 2.0D, 1.0D, 2.0D, 1.0D));
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

      this.rightClickWaiting = false;
      autoClickerEnabled = false;
      clickFinder = 2;
      clickCount = 1;
   }

   public void onEnable() {
      if (this.playerMouseInput == null) {
         this.disable();
      }

      this.rightClickWaiting = false;
      this.allowedClick = false;
      //////System.out.println("Reset allowedClick");
      this.rand = new Random();
      autoClickerEnabled = true;
   }

   public void onDisable() {
      this.leftDownTime = 0L;
      this.leftUpTime = 0L;
      this.rightClickWaiting = false;
      autoClickerEnabled = false;
   }

   public void guiUpdate() {
      Utils.Client.correctSliders(leftMinCPS, leftMaxCPS);
      Utils.Client.correctSliders(rightMinCPS, rightMaxCPS);
      Utils.Client.correctSliders(breakBlocksMin, breakBlocksMax);
      modeDesc.setDesc(Utils.md + Utils.Modes.ClickEvents.values()[(int)(clickEvent.getInput() - 1.0D)].name());
      timingsDesc.setDesc(Utils.md + Utils.Modes.ClickTimings.values()[(int)(clickTimings.getInput() - 1.0D)].name());
   }

   @SubscribeEvent
   public void onRenderTick(RenderTickEvent ev) {
      if(!Utils.Client.currentScreenMinecraft() &&
              !(Minecraft.getMinecraft().currentScreen instanceof GuiInventory) // to make it work in survival inventory
          && !(Minecraft.getMinecraft().currentScreen instanceof GuiChest) // to make it work in chests
      )
         return;

      if(Utils.Modes.ClickEvents.values()[(int)clickEvent.getInput() - 1] != Utils.Modes.ClickEvents.RENDER)
         return;

      if(Utils.Modes.ClickTimings.values()[(int)clickTimings.getInput() - 1] == Utils.Modes.ClickTimings.RAVEN){
         ravenClick();
      }
      else if (Utils.Modes.ClickTimings.values()[(int)clickTimings.getInput() - 1] == Utils.Modes.ClickTimings.SKID){
         //////System.out.println("skidlcick");
         skidClick(ev, null);
      }
   }

   @SubscribeEvent
   public void onTick(TickEvent.PlayerTickEvent ev) {
      if(!Utils.Client.currentScreenMinecraft() && !(Minecraft.getMinecraft().currentScreen instanceof GuiInventory)
              && !(Minecraft.getMinecraft().currentScreen instanceof GuiChest) // to make it work in chests
      )
         return;

      if(Utils.Modes.ClickEvents.values()[(int)clickEvent.getInput() - 1] != Utils.Modes.ClickEvents.TICK)
         return;

      if(Utils.Modes.ClickTimings.values()[(int)clickTimings.getInput() - 1] == Utils.Modes.ClickTimings.RAVEN){
         //////System.out.println("ravern");
         ravenClick();
      }
      else if (Utils.Modes.ClickTimings.values()[(int)clickTimings.getInput() - 1] == Utils.Modes.ClickTimings.SKID){
         //////System.out.println("skidlcick");
         skidClick(null, ev);
      }
   }

   private void skidClick(RenderTickEvent er, TickEvent.PlayerTickEvent e) {
      if (!Utils.Player.isPlayerInGame())
         return;

      guiUpdate();

      double speedLeft1 = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftMinCPS.getInput() - 0.2D, leftMaxCPS.getInput());
      double leftHoldLength = speedLeft1 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftMinCPS.getInput() - 0.02D, leftMaxCPS.getInput());
      double speedRight = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(rightMinCPS.getInput() - 0.2D, rightMaxCPS.getInput());
      double rightHoldLength = speedRight / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(rightMinCPS.getInput() - 0.02D, rightMaxCPS.getInput());
      //If none of the buttons are allowed to click, what is the point in generating clicktimes anyway?
      //if (!leftActive && !rightActive) {
      // return;
      //}

      if (mc.currentScreen == null && mc.inGameHasFocus) {
         // Uhh left click only, mate
         if (Mouse.isButtonDown(0) && leftClick.isToggled()) {
            if(breakBlock()) return;
            if (weaponOnly.isToggled() && !Utils.Player.isPlayerHoldingWeapon()) {
               return;
            }
            if (jitterLeft.getInput() > 0.0D) {
               double a = jitterLeft.getInput() * 0.45D;
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

            double speedLeft = 1.0 / ThreadLocalRandom.current().nextDouble(leftMinCPS.getInput() - 0.2, leftMaxCPS.getInput());
            if (System.currentTimeMillis() - lastClick > speedLeft * 1000) {
               lastClick = System.currentTimeMillis();
               if (leftHold < lastClick){
                  leftHold = lastClick;
               }
               int key = mc.gameSettings.keyBindAttack.getKeyCode();
               KeyBinding.setKeyBindState(key, true);
               Click.minecraftPressed(true);
               KeyBinding.onTick(key);
               Utils.Client.setMouseButtonState(0, true);
            } else if (System.currentTimeMillis() - leftHold > leftHoldLength * 1000) {
               KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
               Click.minecraftPressed(false);
               Utils.Client.setMouseButtonState(0, false);
            }
         }
         //we cheat in a block game ft. right click
         if(!Mouse.isButtonDown(1) && !rightDown){
               KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
               Utils.Client.setMouseButtonState(1, false);
         }

         if (Mouse.isButtonDown(1) && rightClick.isToggled() || rightDown) {
            if (!this.rightClickAllowed())
               return;


            if (jitterRight.getInput() > 0.0D) {
               double jitterMultiplier = jitterRight.getInput() * 0.45D;
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

            if (System.currentTimeMillis() - lastClick > speedRight * 1000) {
               lastClick = System.currentTimeMillis();
               if (rightHold < lastClick){
                  rightHold = lastClick;
               }
               int key = mc.gameSettings.keyBindUseItem.getKeyCode();
               KeyBinding.setKeyBindState(key, true);
               Utils.Client.setMouseButtonState(1, true);
               KeyBinding.onTick(key);
               rightDown = false;
            } else if (System.currentTimeMillis() - rightHold > rightHoldLength * 1000) {
               rightDown = true;
               KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
               Utils.Client.setMouseButtonState(1, false);

            }
         } else if (!Mouse.isButtonDown(1)){
            this.rightClickWaiting = false;
            this.allowedClick = false;
            //////System.out.println("Reset allowedClick");
         }
      }else if (inventoryFill.isToggled() && (mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChest)) {
         if (!Mouse.isButtonDown(0) || !Keyboard.isKeyDown(54) && !Keyboard.isKeyDown(42)) {
            this.leftDownTime = 0L;
            this.leftUpTime = 0L;
         } else if (this.leftDownTime != 0L && this.leftUpTime != 0L) {
            if (System.currentTimeMillis() > this.leftUpTime) {
               this.genLeftTimings();
               this.inInvClick(mc.currentScreen);
            }
         } else {
            this.genLeftTimings();
         }
      } else{
         if(!Mouse.isButtonDown(0)) {

         }
      }

   }

   private void ravenClick() {
      if (mc.currentScreen == null && mc.inGameHasFocus) {

         //Mouse.poll();
         if(!Mouse.isButtonDown(0) && !leftDown) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
            Utils.Client.setMouseButtonState(0, false);
         }
         if (leftClick.isToggled() && Mouse.isButtonDown(0) || leftDown) {
            if (weaponOnly.isToggled() && !Utils.Player.isPlayerHoldingWeapon()) {
               return;
            }
            this.leftClickExecute(mc.gameSettings.keyBindAttack.getKeyCode());
         }
         else if (rightClick.isToggled() && Mouse.isButtonDown(1)) {
            this.rightClickExecute(mc.gameSettings.keyBindUseItem.getKeyCode());
         } else if (!Mouse.isButtonDown(1)){
            this.rightClickWaiting = false;
            this.allowedClick = false;
            //////System.out.println("Reset allowedClick");
            this.righti = 0L;
            this.rightj = 0L;
            this.leftDownTime = 0L;
            this.leftUpTime = 0L;
         }
      } else if (inventoryFill.isToggled() && (mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChest)) {
         if (!Mouse.isButtonDown(0) || !Keyboard.isKeyDown(54) && !Keyboard.isKeyDown(42)) {
            this.leftDownTime = 0L;
            this.leftUpTime = 0L;
         } else if (this.leftDownTime != 0L && this.leftUpTime != 0L) {
            if (System.currentTimeMillis() > this.leftUpTime) {
               this.genLeftTimings();
               this.inInvClick(mc.currentScreen);
            }
         } else {
            this.genLeftTimings();
         }
      }
   }


   public boolean rightClickAllowed() {
      ItemStack item = mc.thePlayer.getHeldItem();
      if (item != null) {
         if (allowEat.isToggled()) {
            if ((item.getItem() instanceof ItemFood)) {
               return false;
            }
         }
         if (allowBow.isToggled()) {
            if (item.getItem() instanceof ItemBow) {
               return false;
            }
         }
         if (onlyBlocks.isToggled()) {
            if (!(item.getItem() instanceof ItemBlock))
               return false;
         }
         if (noBlockSword.isToggled()) {
            if (item.getItem() instanceof ItemSword)
               return false;
         }
      }

      if(preferFastPlace.isToggled()) {
         Module fastplace = NotAName.moduleManager.getModuleByName("FastPlace");
         if (fastplace.isEnabled())
            return false;
      }

      if(rightClickDelay.getInput() != 0){
         if(!rightClickWaiting && !allowedClick) {
            this.rightClickWaitStartTime = System.currentTimeMillis();
            this.rightClickWaiting = true;
            //////System.out.println("Started waiting");
            return  false;
         } else if(this.rightClickWaiting && !allowedClick) {
            double passedTime = System.currentTimeMillis() - this.rightClickWaitStartTime;
            //////System.out.println("Waiting but not allowed");
            if (passedTime >= rightClickDelay.getInput()) {
               this.allowedClick = true;
               this.rightClickWaiting = false;
               //////System.out.println("allowed");
               return true;
            } else {
               //////System.out.println("Waiting");
               return false;
            }
         }
            //////System.out.println("Something else " + this.rightClickWaiting + " " + allowedClick);
      }


      return true;
   }

   public void leftClickExecute(int key) {

      if(breakBlock()) return;

      if (jitterLeft.getInput() > 0.0D) {
         double a = jitterLeft.getInput() * 0.45D;
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

      if (this.leftUpTime > 0L && this.leftDownTime > 0L) {
         if (System.currentTimeMillis() > this.leftUpTime && leftDown) {
            KeyBinding.setKeyBindState(key, true);
            KeyBinding.onTick(key);
            this.genLeftTimings();
            Utils.Client.setMouseButtonState(0, true);
            leftDown = false;
         } else if (System.currentTimeMillis() > this.leftDownTime) {
            KeyBinding.setKeyBindState(key, false);
            leftDown = true;
            Utils.Client.setMouseButtonState(0, false);
         }
      } else {
         //////System.out.println("gen");
         this.genLeftTimings();
      }

   }

   public void rightClickExecute(int key) {
      if (!this.rightClickAllowed())
         return;

      if (jitterRight.getInput() > 0.0D) {
         double jitterMultiplier = jitterRight.getInput() * 0.45D;
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
            Utils.Client.setMouseButtonState(1, false);
            Utils.Client.setMouseButtonState(1, true);
            this.genRightTimings();
         } else if (System.currentTimeMillis() > this.righti) {
            KeyBinding.setKeyBindState(key, false);
            //ay.setMouseButtonState(1, false);
         }
      } else {
         this.genRightTimings();
      }

   }

   public void genLeftTimings() {
      double clickSpeed = Utils.Client.ranModuleVal(leftMinCPS, leftMaxCPS, this.rand) + 0.4D * this.rand.nextDouble();
      long delay = (int)Math.round(1000.0D / clickSpeed);
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

      this.leftUpTime = System.currentTimeMillis() + delay;
      this.leftDownTime = System.currentTimeMillis() + delay / 2L - (long)this.rand.nextInt(10);
   }

   public void genRightTimings() {
      double clickSpeed = Utils.Client.ranModuleVal(rightMinCPS, rightMaxCPS, this.rand) + 0.4D * this.rand.nextDouble();
      long delay = (int)Math.round(1000.0D / clickSpeed);
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

   public boolean breakBlock() {
      if (breakBlocks.isToggled() && mc.objectMouseOver != null) {
         BlockPos p = mc.objectMouseOver.getBlockPos();
         if (p != null) {
            Block bl = mc.theWorld.getBlockState(p).getBlock();
            if (bl != Blocks.air && !(bl instanceof BlockLiquid)) {
               if(breakBlocksMax.getInput() == 0){
                  if(!breakHeld) {
                     int e = mc.gameSettings.keyBindAttack.getKeyCode();
                     KeyBinding.setKeyBindState(e, true);
                     KeyBinding.onTick(e);
                     breakHeld = true;
                  }
                  return true;
               }
               if(!breakTimeDone && !watingForBreakTimeout) {
                  watingForBreakTimeout = true;
                  guiUpdate();
                  breakBlockFinishWaitTime = ThreadLocalRandom.current().nextDouble(breakBlocksMin.getInput(), breakBlocksMax.getInput()+1) + System.currentTimeMillis();
                  return false;
               } else if(!breakTimeDone && watingForBreakTimeout) {
                  if (System.currentTimeMillis() > breakBlockFinishWaitTime) {
                     breakTimeDone = true;
                     watingForBreakTimeout = false;
                  }
               }

               if(breakTimeDone && !watingForBreakTimeout) {
                  if(!breakHeld) {
                     int e = mc.gameSettings.keyBindAttack.getKeyCode();
                     KeyBinding.setKeyBindState(e, true);
                     KeyBinding.onTick(e);
                     breakHeld = true;
                  }
                  return true;
               }
            }
            if(breakHeld) {
               breakHeld = false;
               breakTimeDone = false;
               watingForBreakTimeout = false;
            }
         }
      }
      return false;
   }
}