package keystrokesmod.client.module.modules.combat;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.*;
import keystrokesmod.client.module.modules.player.FastPlace;
import keystrokesmod.client.module.setting.impl.*;
import keystrokesmod.client.tweaker.interfaces.IThrowableItem;
import keystrokesmod.client.utils.Utils;
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
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AutoClicker extends Module {
   public static DescriptionSetting bestWithDelayRemover, modeDesc, timingsDesc;
   public static SliderSetting jitterLeft;
   public static SliderSetting jitterRight;
   public static TickSetting weaponOnly;
   public static TickSetting breakBlocks;
   public static TickSetting onlyBlocks;
   public static TickSetting preferFastPlace;
   public static TickSetting noBlockSword;
   public static TickSetting leftClick;
   public static TickSetting rightClick;
   public static TickSetting inventoryFill;
   public static TickSetting allowEat, allowBow;
   public static SliderSetting rightClickDelay;
   public static DoubleSliderSetting leftCPS, rightCPS, breakBlocksDelay;
   public static ComboSetting clickStyle, clickTimings;

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
      super("AutoClicker", ModuleCategory.combat);

      this.registerSetting(bestWithDelayRemover = new DescriptionSetting("Best with delay remover."));

      this.registerSetting(leftClick = new TickSetting("Left click", true));
      this.registerSetting(leftCPS = new DoubleSliderSetting("Left CPS", 9, 13, 1, 60, 0.5));
      this.registerSetting(jitterLeft = new SliderSetting("Jitter left", 0.0D, 0.0D, 3.0D, 0.1D));
      this.registerSetting(inventoryFill = new TickSetting("Inventory fill", false));
      this.registerSetting(weaponOnly = new TickSetting("Weapon only", false));
      this.registerSetting(breakBlocks = new TickSetting("Break blocks", false));
      this.registerSetting(breakBlocksDelay = new DoubleSliderSetting("Breack blocks delay (MS)", 20, 50, 0,1000, 1));

      this.registerSetting(rightClick = new TickSetting("Right click", false));
      this.registerSetting(rightCPS = new DoubleSliderSetting("RightCPS", 12, 16, 1,60, 0.5));
      this.registerSetting(jitterRight = new SliderSetting("Jitter right", 0.0D, 0.0D, 3.0D, 0.1D));
      this.registerSetting(rightClickDelay = new SliderSetting("Rightclick delay (ms)", 85D, 0D, 500D, 1.0D));
      this.registerSetting(noBlockSword = new TickSetting("Don't rightclick sword", true));
      this.registerSetting(onlyBlocks = new TickSetting("Only rightclick with blocks", false));
      this.registerSetting(preferFastPlace = new TickSetting("Prefer fast place", false));
      this.registerSetting(allowEat = new TickSetting("Allow eat", true));
      this.registerSetting(allowBow = new TickSetting("Allow bow", true));

      this.registerSetting(clickTimings = new ComboSetting("Click event", ClickEvent.Render));
      this.registerSetting(clickStyle = new ComboSetting("Click Style", ClickStyle.Raven));

      try {
         this.playerMouseInput = ReflectionHelper.findMethod(
                 GuiScreen.class,
                 null,
                 new String[]{
                         "func_73864_a",
                         "mouseClicked"
                 },
                 Integer.TYPE,
                 Integer.TYPE,
                 Integer.TYPE
         );
      } catch (Exception ex) {
         ex.printStackTrace();
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
      this.rand = new Random();
      autoClickerEnabled = true;
   }

   public void onDisable() {
      this.leftDownTime = 0L;
      this.leftUpTime = 0L;
      this.rightClickWaiting = false;
      autoClickerEnabled = false;
   }

   @SubscribeEvent
   public void onRenderTick(RenderTickEvent ev) {
      if(!Utils.Client.currentScreenMinecraft() &&
              !(Minecraft.getMinecraft().currentScreen instanceof GuiInventory) // to make it work in survival inventory
          && !(Minecraft.getMinecraft().currentScreen instanceof GuiChest) // to make it work in chests
      )
         return;

      if(clickTimings.getMode() != ClickEvent.Render)
         return;

      if(clickStyle.getMode() == ClickStyle.Raven){
         ravenClick();
      }
      else if (clickStyle.getMode() == ClickStyle.SKid){
         skidClick(ev, null);
      }
   }

   @SubscribeEvent
   public void onTick(TickEvent.PlayerTickEvent ev) {
      if(!Utils.Client.currentScreenMinecraft() && !(Minecraft.getMinecraft().currentScreen instanceof GuiInventory)
              && !(Minecraft.getMinecraft().currentScreen instanceof GuiChest) // to make it work in chests
      )
         return;

      if(clickTimings.getMode() != ClickEvent.Tick)
         return;

      if(clickStyle.getMode() == ClickStyle.Raven){
         ravenClick();
      }
      else if (clickStyle.getMode() == ClickStyle.SKid){
         skidClick(null, ev);
      }
   }

   private void skidClick(RenderTickEvent er, TickEvent.PlayerTickEvent e) {
      if (!Utils.Player.isPlayerInGame())
         return;

      guiUpdate();

      double speedLeft1 = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftCPS.getInputMin() - 0.2D, leftCPS.getInputMax());
      double leftHoldLength = speedLeft1 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftCPS.getInputMin() - 0.02D, leftCPS.getInputMax());
      double speedRight = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(rightCPS.getInputMin() - 0.2D, rightCPS.getInputMax());
      double rightHoldLength = speedRight / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(rightCPS.getInputMin() - 0.02D, rightCPS.getInputMax());
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

            double speedLeft = 1.0 / ThreadLocalRandom.current().nextDouble(leftCPS.getInputMin() - 0.2, leftCPS.getInputMax());
            if (System.currentTimeMillis() - lastClick > speedLeft * 1000) {
               lastClick = System.currentTimeMillis();
               if (leftHold < lastClick){
                  leftHold = lastClick;
               }
               int key = mc.gameSettings.keyBindAttack.getKeyCode();
               KeyBinding.setKeyBindState(key, true);
               KeyBinding.onTick(key);
               Utils.Client.setMouseButtonState(0, true);
            } else if (System.currentTimeMillis() - leftHold > leftHoldLength * 1000) {
               KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
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
            if (!(item.getItem() instanceof ItemBlock)) {
               if (item.getItem() instanceof IThrowableItem) {
                  if (!((IThrowableItem)item.getItem()).isThrowable(item)) return false;
               } else {
                  return false;
               }
            }
         }

         if (noBlockSword.isToggled()) {
            if (item.getItem() instanceof ItemSword)
               return false;
         }
      }

      if(preferFastPlace.isToggled()) {
         Module fastplace = Raven.moduleManager.getModuleByClazz(FastPlace.class);
         if (fastplace != null && fastplace.isEnabled())
            return false;
      }

      if(rightClickDelay.getInput() != 0){
         if(!rightClickWaiting && !allowedClick) {
            this.rightClickWaitStartTime = System.currentTimeMillis();
            this.rightClickWaiting = true;
            return  false;
         } else if(this.rightClickWaiting && !allowedClick) {
            double passedTime = System.currentTimeMillis() - this.rightClickWaitStartTime;
            if (passedTime >= rightClickDelay.getInput()) {
               this.allowedClick = true;
               this.rightClickWaiting = false;
               return true;
            } else {
               return false;
            }
         }
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
            //Utils.Client.setMouseButtonState(1, false);
         }
      } else {
         this.genRightTimings();
      }

   }

   public void genLeftTimings() {
      double clickSpeed = Utils.Client.ranModuleVal(leftCPS, this.rand) + 0.4D * this.rand.nextDouble();
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
      double clickSpeed = Utils.Client.ranModuleVal(rightCPS, this.rand) + 0.4D * this.rand.nextDouble();
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
               if(breakBlocksDelay.getInputMax() == 0){
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
                  breakBlockFinishWaitTime = ThreadLocalRandom.current().nextDouble(breakBlocksDelay.getInputMin(), breakBlocksDelay.getInputMax()+1) + System.currentTimeMillis();
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

   public enum ClickStyle {
      Raven,
      SKid
   }

   public enum ClickEvent {
      Tick,
      Render
   }
}