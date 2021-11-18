//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.raven.bplus.keystroke.command;

import me.kopamed.raven.bplus.client.Raven;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class keystrokeCommand extends CommandBase {
   public String getCommandName() {
      return "lunarkeystrokes";
   }

   public void processCommand(ICommandSender sender, String[] args) {
      //Raven.toggleKeyStrokeConfigGui();
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
