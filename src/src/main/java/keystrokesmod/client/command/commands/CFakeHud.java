package keystrokesmod.client.command.commands;

import keystrokesmod.client.clickgui.raven.Terminal;
import keystrokesmod.client.command.Command;
import keystrokesmod.client.module.modules.client.FakeHud;

public class CFakeHud extends Command {

    public CFakeHud() {
        super("fakehud", "fakehud add <Name>, fakehud remove <Name>", 3, 100, new String[] { "add/remove" },
                new String[] { "fh" });
    }

    @Override
    public void onCall(String[] args) {
        switch (args[0]) {
        case "add":
            for (int i = 1; i < args.length; i++) {
                FakeHud.addModule(args[i]);
                Terminal.print("added " + args[i] + "!");
            }
            break;
        case "remove":
            for (int i = 1; i < args.length; i++) {
                FakeHud.removeModule(args[i]);
                Terminal.print("removed " + args[i] + "!");
            }
            break;
        default:
            Terminal.print("incorrect arguments");
        }

    }
}