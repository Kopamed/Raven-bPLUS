package me.kopamed.lunarkeystrokes.command.commands;

import me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine;
import me.kopamed.lunarkeystrokes.command.Command;
import me.kopamed.lunarkeystrokes.module.modules.minigames.DuelsStats;

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
