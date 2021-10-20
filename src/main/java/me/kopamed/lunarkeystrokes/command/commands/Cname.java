package me.kopamed.lunarkeystrokes.command.commands;

import me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.command.Command;
import me.kopamed.lunarkeystrokes.module.modules.other.NameHider;

public class Cname extends Command {
    public Cname() {
        super("cname", "Hides your name client-side", 1, 1, new String[] {"New name"}, new String[] {"cn", "changename"});
    }

    @Override
    public void onCall(String[] args) {
        if (args == null) {
            this.incorrectArgs();
            return;
        }

        NameHider.n = args[1];
        CommandLine.print("&a" + Utils.Java.uf("name") + "Nick has been set to:".substring(4), 1);
        CommandLine.print("\"" + NameHider.n + "\"", 0);
    }
}
