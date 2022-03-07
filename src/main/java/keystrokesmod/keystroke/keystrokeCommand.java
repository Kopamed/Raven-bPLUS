package keystrokesmod.keystroke;

import keystrokesmod.sToNkS.main.Ravenbplus;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class keystrokeCommand extends CommandBase {
   public String getCommandName() {
      return "keystrokesmod";
   }

   public void processCommand(ICommandSender sender, String[] args) {
      Ravenbplus.toggleKeyStrokeConfigGui();
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
