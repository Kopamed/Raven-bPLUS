package keystrokesmod.client.module.modules.player;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class FastPlace extends Module {
   public static SliderSetting delaySlider;
   public static TickSetting blockOnly;

   public final static Field rightClickDelayTimerField;

   static {
      rightClickDelayTimerField = ReflectionHelper.findField(Minecraft.class, "field_71467_ac", "rightClickDelayTimer");

      if (rightClickDelayTimerField != null) {
         rightClickDelayTimerField.setAccessible(true);
      }
   }

   public FastPlace() {
      super("FastPlace", ModuleCategory.player);
      this.registerSetting(delaySlider = new SliderSetting("Delay", 0.0D, 0.0D, 4.0D, 1.0D));
      this.registerSetting(blockOnly = new TickSetting("Blocks only", true));
   }

   @Override
   public boolean canBeEnabled() {
      return rightClickDelayTimerField != null;
   }

   @SubscribeEvent
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
