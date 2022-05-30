package keystrokesmod.keystroke;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class KeystrokeCommand extends CommandBase {
   public String getCommandName() {
      return "keystrokesmod";
   }

   public void processCommand(ICommandSender sender, String[] args) {
      Minecraft.getMinecraft().displayGuiScreen(new KeyStrokeConfigGui());
   }

   public String getCommandUsage(ICommandSender sender) {
      return "/keystrokesmod";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public boolean canCommandSenderUseCommand(ICommandSender sender) {
      return true;
   }
}
