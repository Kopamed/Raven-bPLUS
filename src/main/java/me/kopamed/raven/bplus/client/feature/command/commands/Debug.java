package me.kopamed.raven.bplus.client.feature.command.commands;

import me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine;
import me.kopamed.raven.bplus.client.feature.command.Command;
import me.kopamed.raven.bplus.client.Raven;

public class Debug extends Command {
    public Debug() {
        super("blatant", "Toggles B+ debbugger", 0, 0,  new String[] {},  new String[] {"dbg", "log"});
    }

    @Override
    public void onCall(String[] args) {
        Raven.client.getDebugManager().setDebugging(!Raven.client.getDebugManager().isDebugging());
        CommandLine.print("Debug " + (Raven.client.getDebugManager().isDebugging() ? "enabled" : "disabled") + ".", 1);
    }
}
