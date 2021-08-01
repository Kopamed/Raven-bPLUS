package keystrokesmod.command.commands;

import keystrokesmod.command.Command;

import static keystrokesmod.CommandLine.print;

public class Shoutout extends Command {
    public Shoutout() {
        super("shoutout", "Everyone who helped make b+", 0, 0,  new String[] {},  new String[] {"love", "thanks"});
    }

    @Override
    public void onCall(String[] args){
        print("&eEveryone who made B+ possible:", 1);
        print("- hevex", 0);
        print("- blowsy", 0);
        print("- jmraichdev", 0);
    }
}
