//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.keystroke;

import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class keystrokeCommand extends CommandBase {
   public String getCommandName() {
      return "lunarkeystrokes";
   }

   public void processCommand(ICommandSender sender, String[] args) {
      Ravenbplus.toggleKeyStrokeConfigGui();
   }

   public String getCommandUsage(ICommandSender sender) {
      return "lunarkeystrokes";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public boolean canCommandSenderUseCommand(ICommandSender sender) {
      return true;
   }
}
