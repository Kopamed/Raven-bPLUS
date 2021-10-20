package me.kopamed.lunarkeystrokes.command.commands;

import me.kopamed.lunarkeystrokes.command.Command;

import static me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine.print;

public class Shoutout extends Command {
    public Shoutout() {
        super("shoutout", "Everyone who helped make b+", 0, 0,  new String[] {},  new String[] {"love", "thanks"});
    }

    @Override
    public void onCall(String[] args){
        print("&eEveryone who made B+ possible:", 1);
        print("- kopamed (client dev)", 0);
        print("- hevex (weeaboo)", 0);
        print("- blowsy (raven owner)", 0);
        print("- jmraichdev (client dev)", 0);
        print("- nighttab (website dev)", 0);
        print("- mood (java help)", 0);
        print("- jc (b3 b2 betta tester)", 0);
    }
}
