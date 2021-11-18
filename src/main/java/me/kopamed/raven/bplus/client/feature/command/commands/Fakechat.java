package me.kopamed.raven.bplus.client.feature.command.commands;

import me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine;
import me.kopamed.raven.bplus.helper.utils.Utils;
import me.kopamed.raven.bplus.client.feature.command.Command;
import me.kopamed.raven.bplus.client.feature.module.modules.other.FakeChat;

public class Fakechat extends Command {
    public Fakechat() {
        super(FakeChat.command, "Sends a message in chat", 1, 600,  new String[] {"text"},  new String[] {"fkct"});
    }

    @Override
    public void onCall(String[] args) {
        if (args == null) {
            this.incorrectArgs();
            return;
        }

        String n;
        String c = Utils.Java.joinStringList(args, " ");
        n = c.replaceFirst(FakeChat.command, "").substring(1);
        if (n.isEmpty() || n.equals("\\n")) {
            CommandLine.print(FakeChat.c4, 1);
            return;
        }

        FakeChat.msg = n;
        CommandLine.print("&aMessage set!", 1);
    }
}
