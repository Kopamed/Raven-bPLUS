package me.kopamed.lunarkeystrokes.command.commands;

import me.kopamed.lunarkeystrokes.utils.ChatHelper;
import me.kopamed.lunarkeystrokes.command.Command;

public class Ping extends Command {
    public Ping() {
        super("ping", "Gets your ping", 0, 0, new String[] {}, new String[] {"p", "connection", "lag"});
    }

    @Override
    public void onCall(String[] args) {
        ChatHelper.checkPing();
    }
}
