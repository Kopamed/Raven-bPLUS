package keystrokesmod.sToNkS.command.commands;

import keystrokesmod.sToNkS.command.Command;
import keystrokesmod.sToNkS.utils.ChatHelper;

public class Ping extends Command {
    public Ping() {
        super("ping", "Gets your ping", 0, 0, new String[] {}, new String[] {"p", "connection", "lag"});
    }

    @Override
    public void onCall(String[] args) {
        ChatHelper.checkPing();
    }
}
