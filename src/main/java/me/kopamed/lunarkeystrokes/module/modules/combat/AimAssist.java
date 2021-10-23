//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.ModuleManager;
import me.kopamed.lunarkeystrokes.module.setting.settings.Mode;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.module.modules.world.AntiBot;
import me.kopamed.lunarkeystrokes.utils.Utils;
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
   public static Slider verticalSpeed; // todo
   public static Tick ignoreTeam;
   public static Mode priority; // todo
   public static Tick lock; // todo done


   public static Slider speed, compliment;
   public static Slider fov;
   public static Slider distance;
   public static Tick clickAim;
   public static Tick weaponOnly;
   public static Tick aimInvis;
   public static Tick breakBlocks;
   public static Tick blatantMode;
   public static Tick ignoreFriends;
   public static ArrayList<Entity> friends = new ArrayList<>();
   private Entity lockedEntity = null;

   public AimAssist() {
      super("AimAssist", Module.category.combat, 0);
      this.registerSetting(speed = new Slider("Speed 1", 45.0D, 5.0D, 100.0D, 1.0D));// todo
      this.registerSetting(compliment = new Slider("Speed 2", 15.0D, 2D, 97.0D, 1.0D));// todo
      this.registerSetting(fov = new Slider("FOV", 90.0D, 15.0D, 360.0D, 1.0D));
      this.registerSetting(distance = new Slider("Distance", 3.3D, 1.0D, 10.0D, 0.1D));
      this.registerSetting(clickAim = new Tick("Click aim", true));
      this.registerSetting(breakBlocks = new Tick("Break blocks", true));
      this.registerSetting(ignoreFriends = new Tick("Ignore Friends", true));
      this.registerSetting(ignoreTeam = new Tick("Ignore teammates", true)); // todo
      this.registerSetting(weaponOnly = new Tick("Weapon only", false));
      this.registerSetting(aimInvis = new Tick("Aim invis", false));
      this.registerSetting(blatantMode = new Tick("Blatant mode", false));
      this.registerSetting(lock = new Tick("Lock", true));
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
            if ((clickAim.isToggled() && Utils.Client.autoClickerClicking()) || (Mouse.isButtonDown(0) && !ModuleManager.autoClicker.isEnabled()) || !clickAim.isToggled()) {
               Entity en = this.getEnemy();
               if (en != null) {
                  if (Ravenbplus.debugger) {
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
         if(Ravenbplus.debugger){
            Utils.Player.sendMessageToSelf("unformatted / " + bruhentity.getDisplayName().getUnformattedText().replace("§", "%"));

            Utils.Player.sendMessageToSelf("susbstring entity / " + bruhentity.getDisplayName().getUnformattedText().substring(0, 2));
            Utils.Player.sendMessageToSelf("substring player / " + mc.thePlayer.getDisplayName().getUnformattedText().substring(0, 2));
         }
         if(mc.thePlayer.isOnSameTeam((EntityLivingBase) entity) || mc.thePlayer.getDisplayName().getUnformattedText().startsWith(bruhentity.getDisplayName().getUnformattedText().substring(0, 2))) return true;
      } catch (Exception fhwhfhwe) {
         if(Ravenbplus.debugger) {
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
