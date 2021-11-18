package me.kopamed.raven.bplus.client.feature.command.commands;

import me.kopamed.raven.bplus.helper.utils.ChatHelper;
import me.kopamed.raven.bplus.client.feature.command.Command;

public class Ping extends Command {
    public Ping() {
        super("ping", "Gets your ping", 0, 0, new String[] {}, new String[] {"p", "connection", "lag"});
    }

    @Override
    public void onCall(String[] args) {
        ChatHelper.checkPing();
    }
}
