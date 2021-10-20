package me.kopamed.lunarkeystrokes.command.commands;

import me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine;
import me.kopamed.lunarkeystrokes.command.Command;

public class Clear extends Command {
    public Clear() {
        super("clear", "Clears the terminal", 0,0, new String[] {}, new String[] {"l", "clr"});
    }

    @Override
    public void onCall(String[] args) {
        CommandLine.commandLineHistory.clear();
    }
}
