package me.kopamed.raven.bplus.helper.tweaker;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.modules.client.ClientNameSpoof;
import me.kopamed.raven.bplus.helper.manager.ModuleManager;
import me.kopamed.raven.bplus.client.feature.module.modules.combat.AutoClicker;
import me.kopamed.raven.bplus.client.feature.module.modules.combat.Reach;
import me.kopamed.raven.bplus.client.feature.module.modules.movement.KeepSprint;
import me.kopamed.raven.bplus.client.feature.module.modules.movement.NoSlow;
import me.kopamed.raven.bplus.client.feature.module.modules.other.NameHider;
import me.kopamed.raven.bplus.client.feature.module.modules.other.StringEncrypt;
import me.kopamed.raven.bplus.client.feature.module.modules.player.SafeWalk;
import me.kopamed.raven.bplus.client.feature.module.modules.render.AntiShuffle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import org.lwjgl.input.Mouse;

import java.util.List;

public class ASMEventHandler {
   private static final Minecraft mc = Minecraft.getMinecraft();

   /**
    * called when Minecraft format text
    */
   @SuppressWarnings("unused")
   public static String getUnformattedTextForChat(String s) {
      // check if Modules have ben initialized
      if (ModuleManager.isInitialized) {
         if (ModuleManager.nameHider.isToggled()) {
            s = NameHider.getUnformattedTextForChat(s);
         }

         if (ModuleManager.antiShuffle.isToggled()) {
            s = AntiShuffle.getUnformattedTextForChat(s);
         }

         if (ModuleManager.stringEncrypt.isToggled()) {
            s = StringEncrypt.getUnformattedTextForChat(s);
         }
      }
      return s;
   }


   /**
    * called when an entity moves
    */
   @SuppressWarnings("unused")
   public static boolean onEntityMove(Entity entity) {
      if (entity == mc.thePlayer && mc.thePlayer.onGround) {
         if (ModuleManager.safeWalk.isToggled() && !SafeWalk.doShift.isToggled()) {
            if (SafeWalk.blocksOnly.isToggled()) {
               ItemStack i = mc.thePlayer.getHeldItem();
               if (i == null || !(i.getItem() instanceof ItemBlock)) {
                  return mc.thePlayer.isSneaking();
               }
            }

            return true;
         } else {
            return mc.thePlayer.isSneaking();
         }
      } else {
         return false;
      }
   }

   /*
    UNUSED @see : ClientNameSpoof.newName
   @Deprecated
   public String getModName()
   {
      return "lunarclient:db2533c";
   }*/
   @SuppressWarnings("unused")
   public static String getModName() {
      ClientNameSpoof cns = (ClientNameSpoof) Raven.client.getModuleManager().getModuleByName("ClientNameSpoofer");
      if(cns.isToggled()){
         return ClientNameSpoof.newName;
      }
      List<String> modNames = Lists.newArrayListWithExpectedSize(3);
      modNames.add("fml");
      modNames.add("forge");

      if (Loader.instance().getFMLBrandingProperties().containsKey("snooperbranding"))
      {
         modNames.add(Loader.instance().getFMLBrandingProperties().get("snooperbranding"));
      }
      return Joiner.on(',').join(modNames);
   }

   /**
    * called when a player is using an item (aka right-click)
    */
   @SuppressWarnings("unused")
   public static void onLivingUpdate() {
      if (ModuleManager.noSlow.isToggled()) {
         NoSlow.sl();
      } else {
         mc.thePlayer.movementInput.moveStrafe *= 0.2F;
         mc.thePlayer.movementInput.moveForward *= 0.2F;
      }
   }

   /**
    * called when a player is moving (sprinting) and hits another one
    */
   @SuppressWarnings("unused")
   public static void onAttackTargetEntityWithCurrentItem(Entity en) {
      if (ModuleManager.keepSprint.isToggled()) {
         KeepSprint.sl(en);
      } else {
         mc.thePlayer.motionX *= 0.6D;
         mc.thePlayer.motionZ *= 0.6D;
      }
   }

   /**
    * called every ticks
    */
   @SuppressWarnings("unused")
   public static void onTick() {
      if (!ModuleManager.leftClicker.isToggled() || !Mouse.isButtonDown(0) || !Reach.call()) {
         mc.entityRenderer.getMouseOver(1.0F);
      }
   }
}
