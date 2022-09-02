package keystrokesmod.client.module.modules.player;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {
   public static DescriptionSetting warning;
   public static ComboSetting mode;

   int ticks = 0;
   double dist = 0;
   boolean spoofing = false;

   public NoFall() {
      super("NoFall", ModuleCategory.player);

      this.registerSetting(warning = new DescriptionSetting("HypixelSpoof silent flags."));
      this.registerSetting(mode = new ComboSetting("Mode", Mode.Spoof));
   }

   public void update() {
      switch ((Mode) mode.getMode()) {
         case Spoof:
            if ((double) mc.thePlayer.fallDistance > 2.5D) {
               mc.thePlayer.onGround = true;
            }
            break;

         case HypixelSpoof:
            if(mc.thePlayer.onGround) {
               ticks = 0;
               dist = 0;
               spoofing = false;
            } else {
               if(mc.thePlayer.fallDistance > 2) {
                  if(spoofing) {
                     ticks++;
                     mc.thePlayer.onGround = true;

                     if(ticks >= 2) {
                        spoofing = false;
                        ticks = 0;
                        dist = mc.thePlayer.fallDistance;
                     }
                  } else {
                     if(mc.thePlayer.fallDistance - dist > 2) {
                        spoofing = true;
                     }
                  }
               }
            }
            break;
      }
   }

   public enum Mode {
      Spoof, HypixelSpoof
   }
}
