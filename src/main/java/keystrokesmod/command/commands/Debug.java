package keystrokesmod.command.commands;

import keystrokesmod.clickgui.raven.CommandLine;
import keystrokesmod.command.Command;
import keystrokesmod.main.Ravenbplus;

public class Debug extends Command {
    public Debug() {
        super("debug", "Toggles B+ debbugger", 0, 0,  new String[] {},  new String[] {"dbg", "log"});
    }

    @Override
    public void onCall(String[] args) {
        Ravenbplus.debugger = !Ravenbplus.debugger;
        CommandLine.print("Debug " + (Ravenbplus.debugger ? "enabled" : "disabled") + ".", 1);
    }
}
