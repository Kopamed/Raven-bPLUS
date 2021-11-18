package me.kopamed.raven.bplus.client.feature.command.commands;

import me.kopamed.raven.bplus.client.feature.command.Command;
import me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine;

public class Shoutout extends Command {
    public Shoutout() {
        super("shoutout", "Everyone who helped make b+", 0, 0,  new String[] {},  new String[] {"love", "thanks"});
    }

    @Override
    public void onCall(String[] args){
        CommandLine.print("&eEveryone who made B+ possible:", 1);
        CommandLine.print("- kopamed (client dev)", 0);
        CommandLine.print("- hevex (weeaboo)", 0);
        CommandLine.print("- blowsy (raven owner)", 0);
        CommandLine.print("- jmraichdev (client dev)", 0);
        CommandLine.print("- nighttab (website dev)", 0);
        CommandLine.print("- mood (java help)", 0);
        CommandLine.print("- jc (b3 b2 betta tester)", 0);
    }
}
