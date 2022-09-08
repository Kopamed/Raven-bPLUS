package keystrokesmod.client.command.commands;

import keystrokesmod.client.clickgui.raven.Terminal;
import keystrokesmod.client.command.Command;
import keystrokesmod.client.module.modules.other.FakeChat;
import keystrokesmod.client.utils.Utils;

public class Fakechat extends Command {
    public Fakechat() {
        super(FakeChat.command, "Sends a message in chat", 1, 600, new String[] { "text" }, new String[] { "fkct" });
    }

    @Override
    public void onCall(String[] args) {
        if (args.length == 0) {
            this.incorrectArgs();
            return;
        }

        String n;
        String c = Utils.Java.joinStringList(args, " ");
        n = c.replaceFirst(FakeChat.command, "").substring(1);
        if (n.isEmpty() || n.equals("\\n")) {
            Terminal.print(FakeChat.c4);
            return;
        }

        FakeChat.msg = n;
        Terminal.print("Message set!");
    }
}
