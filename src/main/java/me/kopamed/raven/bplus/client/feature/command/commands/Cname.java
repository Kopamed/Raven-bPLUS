package me.kopamed.raven.bplus.client.feature.command.commands;

import me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine;
import me.kopamed.raven.bplus.helper.utils.Utils;
import me.kopamed.raven.bplus.client.feature.command.Command;
import me.kopamed.raven.bplus.client.feature.module.modules.other.NameHider;

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
