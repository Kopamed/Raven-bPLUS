package keystrokesmod.command.commands;

import keystrokesmod.CommandLine;
import keystrokesmod.command.Command;
import keystrokesmod.main.Ravenb3;

public class Debug extends Command {
    public Debug() {
        super("debug", "Toggles B+ debbugger", 0, 0,  new String[] {},  new String[] {"dbg", "log"});
    }

    @Override
    public void onCall(String[] args) {
        Ravenb3.debugger = !Ravenb3.debugger;
        CommandLine.print("Debug " + (Ravenb3.debugger ? "enabled" : "disabled") + ".", 1);
    }
}
