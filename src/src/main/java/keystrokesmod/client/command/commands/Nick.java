package keystrokesmod.client.command.commands;

import keystrokesmod.client.clickgui.raven.Terminal;
import keystrokesmod.client.command.Command;
import keystrokesmod.client.module.modules.other.NameHider;

public class Nick extends Command {
    public Nick() {
        super("nick", "Like nickhider mod", 1, 1, new String[] { "the new name" }, new String[] { "nk", "nickhider" });
    }

    @Override
    public void onCall(String[] args) {
        if (args.length == 0) {
            this.incorrectArgs();
            return;
        }

        NameHider.playerNick = args[0];
        Terminal.print("&aNick has been set to: " + NameHider.playerNick);
    }
}
