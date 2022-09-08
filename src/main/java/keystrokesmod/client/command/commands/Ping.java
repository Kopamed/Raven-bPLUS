package keystrokesmod.client.command.commands;

import keystrokesmod.client.command.Command;
import keystrokesmod.client.utils.PingChecker;

public class Ping extends Command {
    public Ping() {
        super("ping", "Gets your ping", 0, 0, new String[] {}, new String[] { "p", "connection", "lag" });
    }

    @Override
    public void onCall(String[] args) {
        PingChecker.checkPing();
    }
}
