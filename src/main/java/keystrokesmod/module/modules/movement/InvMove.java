//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.movement;

import keystrokesmod.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class InvMove extends Module {
   public InvMove() {
      super(new char[]{'I', 'n', 'v', 'M', 'o', 'v', 'e'}, Module.category.movement, 0);
   }

   public void update() {
      if (mc.currentScreen != null) {
         if (mc.currentScreen instanceof GuiChat) {
            return;
         }

         KeyBinding var10000 = mc.gameSettings.keyBindForward;
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
         var10000 = mc.gameSettings.keyBindBack;
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
         var10000 = mc.gameSettings.keyBindRight;
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
         var10000 = mc.gameSettings.keyBindLeft;
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
         var10000 = mc.gameSettings.keyBindJump;
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
