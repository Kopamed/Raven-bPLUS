package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class InvMove extends Module {
   public InvMove() {
      super("InvMove", ModuleCategory.movement);
   }

   public void update() {
      if (mc.currentScreen != null) {
         if (mc.currentScreen instanceof GuiChat) {
            return;
         }

         KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
         EntityPlayerSP var1;
         if (Keyboard.isKeyDown(208) && mc.thePlayer.rotationPitch < 90.0F) {
            var1 = mc.thePlayer;
            var1.rotationPitch += 6.0F;
         }

         if (Keyboard.isKeyDown(200) && mc.thePlayer.rotationPitch > -90.0F) {
            var1 = mc.thePlayer;
            var1.rotationPitch -= 6.0F;
         }

         if (Keyboard.isKeyDown(205)) {
            var1 = mc.thePlayer;
            var1.rotationYaw += 6.0F;
         }

         if (Keyboard.isKeyDown(203)) {
            var1 = mc.thePlayer;
            var1.rotationYaw -= 6.0F;
         }
      }

   }
}
