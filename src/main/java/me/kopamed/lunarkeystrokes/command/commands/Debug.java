package me.kopamed.lunarkeystrokes.command.commands;

import me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine;
import me.kopamed.lunarkeystrokes.command.Command;
import me.kopamed.lunarkeystrokes.main.Ravenbplus;

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
