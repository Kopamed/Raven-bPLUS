package me.kopamed.raven.bplus.client.feature.module.modules.player;

import java.awt.Color;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Freecam extends Module {
   public static NumberSetting speed;
   public static BooleanSetting disbleOnDamage;
   public static BooleanSetting showPlayer;
   private final double toRad = 0.017453292519943295D;
   public static EntityOtherPlayerMP en = null;
   private int[] lcc = new int[]{Integer.MAX_VALUE, 0};
   private final float[] startingAngles = new float[]{0.0F, 0.0F};

   public Freecam() {
      super("Freecam", ModuleCategory.Player, 0);
      this.registerSetting(speed = new NumberSetting("Speed", 0.2D, 0.05D, 1D, 0.05D));
      this.registerSetting(disbleOnDamage = new BooleanSetting("Disable on damage", true));
      this.registerSetting(showPlayer = new BooleanSetting("ShowPlayer", true));
   }

   @Override
   public void onEnable() {
      if(!Utils.Player.isPlayerInGame()) {
         return;
      }
      if (!mc.thePlayer.onGround) {
         //this.disable();
      } else {
         en = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
         en.copyLocationAndAnglesFrom(mc.thePlayer);
         this.startingAngles[0] = en.rotationYawHead = mc.thePlayer.rotationYawHead;
         this.startingAngles[1] = mc.thePlayer.rotationPitch;
         en.setVelocity(0.0D, 0.0D, 0.0D);
         en.setInvisible(!showPlayer.isToggled());
         mc.theWorld.addEntityToWorld(-8008, en);
         mc.setRenderViewEntity(en);
      }
   }

   public void onDisable() {
      if (en != null) {
         mc.setRenderViewEntity(mc.thePlayer);
         mc.thePlayer.rotationYaw = mc.thePlayer.rotationYawHead = this.startingAngles[0];
         mc.thePlayer.rotationPitch = this.startingAngles[1];
         mc.theWorld.removeEntity(en);
         en = null;
      }

      this.lcc = new int[]{Integer.MAX_VALUE, 0};
      int rg = 1;
      int x = mc.thePlayer.chunkCoordX;
      int z = mc.thePlayer.chunkCoordZ;

      for(int x2 = -1; x2 <= 1; ++x2) {
         for(int z2 = -1; z2 <= 1; ++z2) {
            int a = x + x2;
            int b = z + z2;
            mc.theWorld.markBlockRangeForRenderUpdate(a * 16, 0, b * 16, a * 16 + 15, 256, b * 16 + 15);
         }
      }

   }

   @SubscribeEvent
   public void update(TickEvent.RenderTickEvent w) {
      if(!Utils.Player.isPlayerInGame() || en == null)
         return;
       if (disbleOnDamage.isToggled() && mc.thePlayer.hurtTime != 0) {
         this.disable();
      } else {
         mc.thePlayer.setSprinting(false);
         mc.thePlayer.moveForward = 0.0F;
         mc.thePlayer.moveStrafing = 0.0F;
         en.rotationYaw = en.rotationYawHead = mc.thePlayer.rotationYaw;
         en.rotationPitch = mc.thePlayer.rotationPitch;
         double s = 0.215D * speed.getInput();
         EntityOtherPlayerMP var10000;
         double rad;
         double dx;
         double dz;
         if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            rad = (double)en.rotationYawHead * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX += dx;
            var10000 = en;
            var10000.posZ += dz;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
            rad = (double)en.rotationYawHead * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX -= dx;
            var10000 = en;
            var10000.posZ -= dz;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
            rad = (double)(en.rotationYawHead - 90.0F) * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX += dx;
            var10000 = en;
            var10000.posZ += dz;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
            rad = (double)(en.rotationYawHead + 90.0F) * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = en;
            var10000.posX += dx;
            var10000 = en;
            var10000.posZ += dz;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
            var10000 = en;
            var10000.posY += 0.93D * s;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
            var10000 = en;
            var10000.posY -= 0.93D * s;
         }

         mc.thePlayer.setSneaking(false);
         if (this.lcc[0] != Integer.MAX_VALUE && (this.lcc[0] != en.chunkCoordX || this.lcc[1] != en.chunkCoordZ)) {
            int x = en.chunkCoordX;
            int z = en.chunkCoordZ;
            mc.theWorld.markBlockRangeForRenderUpdate(x * 16, 0, z * 16, x * 16 + 15, 256, z * 16 + 15);
         }

         this.lcc[0] = en.chunkCoordX;
         this.lcc[1] = en.chunkCoordZ;
      }
   }

   @SubscribeEvent
   public void re(RenderWorldLastEvent e) {
      if (Utils.Player.isPlayerInGame()) {
         mc.thePlayer.renderArmPitch = mc.thePlayer.prevRenderArmPitch = 700.0F;
         Utils.HUD.drawBoxAroundEntity(mc.thePlayer, 1, 0.0D, 0.0D, Color.green.getRGB(), true);
         Utils.HUD.drawBoxAroundEntity(mc.thePlayer, 2, 0.0D, 0.0D, Color.green.getRGB(), true);
      }

   }

   @SubscribeEvent
   public void m(MouseEvent e) {
      if (Utils.Player.isPlayerInGame() && e.button != -1) {
         //e.setCanceled(true);
         if(mc.objectMouseOver.entityHit ==  mc.thePlayer){
            e.setCanceled(true);
         }
      }

   }
}
