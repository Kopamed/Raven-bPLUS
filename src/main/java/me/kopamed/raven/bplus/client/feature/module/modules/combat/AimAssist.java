package me.kopamed.raven.bplus.client.feature.module.modules.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.helper.manager.ModuleManager;
import me.kopamed.raven.bplus.client.feature.setting.settings.ComboSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.feature.module.modules.world.AntiBot;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;

public class AimAssist extends Module {
   //recode
   public static NumberSetting verticalSpeed; // todo
   public static BooleanSetting ignoreTeam;
   public static ComboSetting priority; // todo
   public static BooleanSetting lock; // todo done


   public static NumberSetting speed, compliment;
   public static NumberSetting fov;
   public static NumberSetting distance;
   public static BooleanSetting clickAim;
   public static BooleanSetting weaponOnly;
   public static BooleanSetting aimInvis;
   public static BooleanSetting breakBlocks;
   public static BooleanSetting blatantMode;
   public static BooleanSetting ignoreFriends;
   public static ArrayList<Entity> friends = new ArrayList<>();
   private Entity lockedEntity = null;

   public AimAssist() {
      super("AimAssist", ModuleCategory.Combat, 0);
      this.registerSetting(speed = new NumberSetting("Speed 1", 45.0D, 5.0D, 100.0D, 1.0D));// todo
      this.registerSetting(compliment = new NumberSetting("Speed 2", 15.0D, 2D, 97.0D, 1.0D));// todo
      this.registerSetting(fov = new NumberSetting("FOV", 90.0D, 15.0D, 360.0D, 1.0D));
      this.registerSetting(distance = new NumberSetting("Distance", 3.3D, 1.0D, 10.0D, 0.1D));
      this.registerSetting(clickAim = new BooleanSetting("Click aim", true));
      this.registerSetting(breakBlocks = new BooleanSetting("Break blocks", true));
      this.registerSetting(ignoreFriends = new BooleanSetting("Ignore Friends", true));
      this.registerSetting(ignoreTeam = new BooleanSetting("Ignore teammates", true)); // todo
      this.registerSetting(weaponOnly = new BooleanSetting("Weapon only", false));
      this.registerSetting(aimInvis = new BooleanSetting("Aim invis", false));
      this.registerSetting(blatantMode = new BooleanSetting("Blatant mode", false));
      this.registerSetting(lock = new BooleanSetting("Lock", true));
   }

   public void update() {

      if(!Utils.Client.currentScreenMinecraft()){
         return;
      }
      if(!Utils.Player.isPlayerInGame()) return;

         if (breakBlocks.isToggled() && mc.objectMouseOver != null) {
            BlockPos p = mc.objectMouseOver.getBlockPos();
            if (p != null) {
               Block bl = mc.theWorld.getBlockState(p).getBlock();
               if (bl != Blocks.air && !(bl instanceof BlockLiquid) && bl instanceof  Block) {
                  return;
               }
            }
         }


         if (!weaponOnly.isToggled() || Utils.Player.isPlayerHoldingWeapon()) {

            //what if player clicking but mouse not down ????
            if ((clickAim.isToggled() && Utils.Client.autoClickerClicking()) || (Mouse.isButtonDown(0) && !ModuleManager.leftClicker.isToggled()) || !clickAim.isToggled()) {
               Entity en = this.getEnemy();
               if (en != null) {
                  if (Raven.client.getDebugManager().isDebugging()) {
                     Utils.Player.sendMessageToSelf(this.getName() + " &e" + en.getName());
                  }

                  if (blatantMode.isToggled()) {
                     Utils.Player.aim(en, 0.0F, false);
                  } else {
                     double n = Utils.Player.fovFromEntity(en);
                     if (n > 1.5D || n < -1.5D) {
                        double complimentSpeed = n*(ThreadLocalRandom.current().nextDouble(compliment.getInput() - 1.47328, compliment.getInput() + 2.48293)/100);
                        double val2 = complimentSpeed + ThreadLocalRandom.current().nextDouble(speed.getInput() - 4.723847, speed.getInput());
                        float val = (float)(-(complimentSpeed + n / (101.0D - (float)ThreadLocalRandom.current().nextDouble(speed.getInput() - 4.723847, speed.getInput()))));
                        mc.thePlayer.rotationYaw += val;
                     }
                  }
               }

            }
         }
      }


   public static boolean isAFriend(Entity entity) {
      if(entity == mc.thePlayer) return true;

      for (Entity wut : friends){
         if (wut.equals(entity))
            return true;
      }
      try {
         EntityPlayer bruhentity = (EntityPlayer) entity;
         if(Raven.client.getDebugManager().isDebugging()){
            Utils.Player.sendMessageToSelf("unformatted / " + bruhentity.getDisplayName().getUnformattedText().replace("§", "%"));

            Utils.Player.sendMessageToSelf("susbstring entity / " + bruhentity.getDisplayName().getUnformattedText().substring(0, 2));
            Utils.Player.sendMessageToSelf("substring player / " + mc.thePlayer.getDisplayName().getUnformattedText().substring(0, 2));
         }
         if(mc.thePlayer.isOnSameTeam((EntityLivingBase) entity) || mc.thePlayer.getDisplayName().getUnformattedText().startsWith(bruhentity.getDisplayName().getUnformattedText().substring(0, 2))) return true;
      } catch (Exception fhwhfhwe) {
         if(Raven.client.getDebugManager().isDebugging()) {
            Utils.Player.sendMessageToSelf(fhwhfhwe.getMessage());
         }
      }



      return false;
   }

   public Entity getEnemy() {
      int fov = (int) AimAssist.fov.getInput();
      Iterator playerEntitiesIter = mc.theWorld.playerEntities.iterator();
      EntityPlayer entityPlayer;

      if(lockedEntity != null && lock.isToggled()){
         if(Utils.Player.isEntityInFOV(lockedEntity, (float)fov) && (double)mc.thePlayer.getDistanceToEntity(lockedEntity) < distance.getInput()){
            return lockedEntity;
         }
      }


      do {// what the fuck
         do {
            do {
               do {
                  do {
                     do {
                        do {
                           if (!playerEntitiesIter.hasNext()) {
                              return null;
                           }

                           entityPlayer = (EntityPlayer) playerEntitiesIter.next();
                        } while (ignoreFriends.isToggled() && isAFriend(entityPlayer));
                     } while(entityPlayer == mc.thePlayer);
                  } while(entityPlayer.deathTime != 0);
               } while(!aimInvis.isToggled() && entityPlayer.isInvisible());
            } while((double)mc.thePlayer.getDistanceToEntity(entityPlayer) > distance.getInput());
         } while(AntiBot.bot(entityPlayer));
      } while(!blatantMode.isToggled() && !Utils.Player.isEntityInFOV(entityPlayer, (float)fov));

      this.lockedEntity = entityPlayer;


      return entityPlayer;
   }



   public static void addFriend(Entity entityPlayer) {
      friends.add(entityPlayer);
   }

   public static boolean addFriend(String name) {
      boolean found = false;
      for (Entity entity:mc.theWorld.getLoadedEntityList()) {
         if (entity.getName().equalsIgnoreCase(name) || entity.getCustomNameTag().equalsIgnoreCase(name)) {
            if(!isAFriend(entity)) {
               addFriend(entity);
               found = true;
            }
         }
      }

      return found;
   }

   public static boolean removeFriend(String name) {
      boolean removed = false;
      boolean found = false;
      for (NetworkPlayerInfo networkPlayerInfo : new ArrayList<NetworkPlayerInfo>(mc.getNetHandler().getPlayerInfoMap())) {
         Entity entity = mc.theWorld.getPlayerEntityByName(networkPlayerInfo.getDisplayName().getUnformattedText());
         if (entity.getName().equalsIgnoreCase(name) || entity.getCustomNameTag().equalsIgnoreCase(name)) {
            removed = removeFriend(entity);
            found = true;
         }
      }

      return found && removed;
   }

   public static boolean removeFriend(Entity entityPlayer) {
      try{
         friends.remove(entityPlayer);
      } catch (Exception eeeeee){
         eeeeee.printStackTrace();
         return false;
      }
      return true;
   }

   public static ArrayList<Entity> getFriends() {
      return friends;
   }
}
