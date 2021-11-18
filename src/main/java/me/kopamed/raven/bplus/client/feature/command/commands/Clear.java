package me.kopamed.raven.bplus.client.feature.command.commands;

import me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine;
import me.kopamed.raven.bplus.client.feature.command.Command;

public class Clear extends Command {
    public Clear() {
        super("clear", "Clears the terminal", 0,0, new String[] {}, new String[] {"l", "clr"});
    }

    @Override
    public void onCall(String[] args) {
        CommandLine.commandLineHistory.clear();
    }
}
