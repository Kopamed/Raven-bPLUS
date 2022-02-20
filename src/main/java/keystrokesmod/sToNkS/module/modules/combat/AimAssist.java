package keystrokesmod.sToNkS.module.modules.combat;

import keystrokesmod.sToNkS.main.Ravenbplus;
import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleManager;
import keystrokesmod.sToNkS.module.ModuleSettingSlider;
import keystrokesmod.sToNkS.module.ModuleSettingTick;
import keystrokesmod.sToNkS.module.modules.world.AntiBot;
import keystrokesmod.sToNkS.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class AimAssist extends Module {
   public static ModuleSettingSlider speed, compliment;
   public static ModuleSettingSlider fov;
   public static ModuleSettingSlider distance;
   public static ModuleSettingTick clickAim;
   public static ModuleSettingTick weaponOnly;
   public static ModuleSettingTick aimInvis;
   public static ModuleSettingTick breakBlocks;
   public static ModuleSettingTick blatantMode;
   public static ModuleSettingTick ignoreFriends;
   public static ArrayList<Entity> friends = new ArrayList<>();

   public AimAssist() {
      super("AimAssist", Module.category.combat, 0);
      this.registerSetting(speed = new ModuleSettingSlider("Speed 1", 45.0D, 5.0D, 100.0D, 1.0D));
      this.registerSetting(compliment = new ModuleSettingSlider("Speed 2", 15.0D, 2D, 97.0D, 1.0D));
      this.registerSetting(fov = new ModuleSettingSlider("FOV", 90.0D, 15.0D, 360.0D, 1.0D));
      this.registerSetting(distance = new ModuleSettingSlider("Distance", 4.5D, 1.0D, 10.0D, 0.5D));
      this.registerSetting(clickAim = new ModuleSettingTick("Click aim", true));
      this.registerSetting(breakBlocks = new ModuleSettingTick("Break blocks", true));
      this.registerSetting(ignoreFriends = new ModuleSettingTick("Ignore Friends", true));
      this.registerSetting(weaponOnly = new ModuleSettingTick("Weapon only", false));
      this.registerSetting(aimInvis = new ModuleSettingTick("Aim invis", false));
      this.registerSetting(blatantMode = new ModuleSettingTick("Blatant mode", false));
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

            Module autoClicker = ModuleManager.getModuleByClazz(AutoClicker.class);
            //what if player clicking but mouse not down ????
            if ((clickAim.isToggled() && Utils.Client.autoClickerClicking()) || (Mouse.isButtonDown(0) && autoClicker != null && !autoClicker.isEnabled()) || !clickAim.isToggled()) {
               Entity en = this.getEnemy();
               if (en != null) {
                  if (Ravenbplus.debugger) {
                     Utils.Player.sendMessageToSelf(this.getName() + " &e" + en.getName());
                  }

                  if (blatantMode.isToggled()) {
                     Utils.Player.aim(en, 0.0F, false);
                  } else {
                     double n = Utils.Player.fovFromEntity(en);
                     if (n > 1.0D || n < -1.0D) {
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
      Iterator var2 = mc.theWorld.playerEntities.iterator();

      EntityPlayer en;
      do {
         do {
            do {
               do {
                  do {
                     do {
                        do {
                           if (!var2.hasNext()) {
                              return null;
                           }

                           en = (EntityPlayer) var2.next();
                        } while (ignoreFriends.isToggled() && isAFriend(en));
                     } while(en == mc.thePlayer);
                  } while(en.deathTime != 0);
               } while(!aimInvis.isToggled() && en.isInvisible());
            } while((double)mc.thePlayer.getDistanceToEntity(en) > distance.getInput());
         } while(AntiBot.bot(en));
      } while(!blatantMode.isToggled() && !Utils.Player.fov(en, (float)fov));

      return en;
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
      for (NetworkPlayerInfo networkPlayerInfo : new ArrayList<>(mc.getNetHandler().getPlayerInfoMap())) {
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
