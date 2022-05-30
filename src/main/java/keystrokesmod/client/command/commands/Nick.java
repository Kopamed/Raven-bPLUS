package keystrokesmod.client.command.commands;

import keystrokesmod.client.clickgui.raven.CommandLine;
import keystrokesmod.client.command.Command;
import keystrokesmod.client.module.modules.minigames.DuelsStats;

public class Nick extends Command {
    public Nick() {
        super("nick", "Like nickhider mod", 1, 1,  new String[] {"the new name"},  new String[] {"nk", "nickhider"});
    }

    @Override
    public void onCall(String[] args){
        if (args == null) {
            this.incorrectArgs();
            return;
        }

        DuelsStats.nk = args[1];
        CommandLine.print("&aNick has been set to:", 1);
        CommandLine.print("\"" + DuelsStats.nk + "\"", 0);
    }
}
