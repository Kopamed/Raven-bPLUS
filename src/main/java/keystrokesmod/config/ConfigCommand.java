package keystrokesmod.config;

import keystrokesmod.ay;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ConfigCommand extends CommandBase {

    private final String COMMAND = "compress";

    public String getCommandName() { return COMMAND; }

    public String getCommandUsage(ICommandSender sender) { return "/" + COMMAND; }

    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "compress":
                    ay.sendMessageToSelf("Compress what bruh");
                    return;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("compress")) {
                Config.encrypt(args[1]);
                return;
            }
        }
        ay.sendMessageToSelf("&eInvalid command usage.");
    }

    public int getRequiredPermissionLevel() { return 0; }

    public boolean canCommandSenderUseCommand(ICommandSender sender) { return true; }

}