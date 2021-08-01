package keystrokesmod.command.commands;

import keystrokesmod.CommandLine;
import keystrokesmod.command.Command;

public class Clear extends Command {
    public Clear() {
        super("clear", "Clears the terminal", 0,0, new String[] {}, new String[] {"l", "clr"});
    }

    @Override
    public void onCall(String[] args) {
        CommandLine.commandLineHistory.clear();
    }
}
