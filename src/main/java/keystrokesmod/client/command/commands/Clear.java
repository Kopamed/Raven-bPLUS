package keystrokesmod.client.command.commands;

import keystrokesmod.client.clickgui.raven.CommandLine;
import keystrokesmod.client.command.Command;

public class Clear extends Command {
    public Clear() {
        super("clear", "Clears the terminal", 0,0, new String[] {}, new String[] {"l", "clr"});
    }

    @Override
    public void onCall(String[] args) {
        CommandLine.commandLineHistory.clear();
    }
}
