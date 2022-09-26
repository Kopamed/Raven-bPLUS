package keystrokesmod.client.command.commands;

import keystrokesmod.client.command.Command;

import static keystrokesmod.client.clickgui.raven.Terminal.print;

public class Shoutout extends Command {
    public Shoutout() {
        super("shoutout", "Everyone who helped make b+", 0, 0, new String[] {}, new String[] { "love", "thanks" });
    }

    @Override
    public void onCall(String[] args) {
        print("Everyone who made b++ possible:");
        print("- kv! aka KingVoid (current dev)");
        print("- kopamed (raven b+ dev)");
        print("- hevex/blowsy (weeaboo, b3 dev) (disapproves to b+ as he earned less money because less ppl clicked on his adfly link)");
        print("- blowsy (hevex's alt)");
        print("- jmraichdev (client dev)");
        print("- nighttab (website dev)");
        print("- mood (java help)");
        print("- jc (b3 b2 betta tester, very good moaner (moans very loudly in discord vcs, giving everyone emotional motivation))");
    }
}
