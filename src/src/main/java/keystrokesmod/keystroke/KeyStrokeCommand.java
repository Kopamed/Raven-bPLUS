//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.keystroke;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class KeyStrokeCommand extends CommandBase {
    public String getCommandName() {
        return "keystrokesmod";
    }

    public void processCommand(ICommandSender sender, String[] args) {
        KeyStrokeMod.toggleKeyStrokeConfigGui();
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
