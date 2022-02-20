package keystrokesmod.sToNkS.command.commands;

import keystrokesmod.sToNkS.clickgui.raven.CommandLine;
import keystrokesmod.sToNkS.command.Command;
import keystrokesmod.sToNkS.module.modules.other.NameHider;
import keystrokesmod.sToNkS.utils.Utils;

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
