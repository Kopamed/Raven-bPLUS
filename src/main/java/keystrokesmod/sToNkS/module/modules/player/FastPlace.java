package keystrokesmod.sToNkS.module.modules.player;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleSettingSlider;
import keystrokesmod.sToNkS.module.ModuleSettingTick;
import keystrokesmod.sToNkS.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class FastPlace extends Module {
   public static ModuleSettingSlider delaySlider;
   public static ModuleSettingTick blockOnly;

   public final static Field rightClickDelayTimerField;

   static {
      rightClickDelayTimerField = ReflectionHelper.findField(Minecraft.class, "field_71467_ac", "rightClickDelayTimer");

      if (rightClickDelayTimerField != null) {
         rightClickDelayTimerField.setAccessible(true);
      }
   }

   public FastPlace() {
      super("FastPlace", Module.category.player, 0);
      this.registerSetting(delaySlider = new ModuleSettingSlider("Delay", 0.0D, 0.0D, 4.0D, 1.0D));
      this.registerSetting(blockOnly = new ModuleSettingTick("Blocks only", true));
   }

   @Override
   public boolean canBeEnabled() {
      return rightClickDelayTimerField != null;
   }

   @FMLEvent
   public void onPlayerTick(PlayerTickEvent event) {
      if (event.phase == Phase.END) {
         if (Utils.Player.isPlayerInGame() && mc.inGameHasFocus && rightClickDelayTimerField != null) {
            if (blockOnly.isToggled()) {
               ItemStack item = mc.thePlayer.getHeldItem();
               if (item == null || !(item.getItem() instanceof ItemBlock)) {
                  return;
               }
            }

            try {
               int c = (int) delaySlider.getInput();
               if (c == 0) {
                  rightClickDelayTimerField.set(mc, 0);
               } else {
                  if (c == 4) {
                     return;
                  }

                  int d = rightClickDelayTimerField.getInt(mc);
                  if (d == 4) {
                     rightClickDelayTimerField.set(mc, c);
                  }
               }
            } catch (IllegalAccessException | IndexOutOfBoundsException ignored) {}
         }
      }
   }
}
