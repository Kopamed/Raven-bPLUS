package keystrokesmod.sToNkS.command.commands;

import keystrokesmod.sToNkS.clickgui.raven.CommandLine;
import keystrokesmod.sToNkS.command.Command;
import keystrokesmod.sToNkS.main.Ravenbplus;

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
