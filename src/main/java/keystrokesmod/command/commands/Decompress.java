package keystrokesmod.command.commands;

import keystrokesmod.ay;
import keystrokesmod.command.Command;
import keystrokesmod.config.Config;

import static keystrokesmod.CommandLine.print;

public class Decompress extends Command {
    public Decompress(){
        super("decompress", "Decompresses a string", 1, 1, new String[] {"String ya wanna decompress"}, new String[] {"dcmp", "desqueez"});
    }
    @Override
    public void onCall(String[] args) {
        if(args == null) this.incorrectArgs();
        if(args.length == 2){
            ay.copyToClipboard(Config.decrypt(args[1]));
            print("Successfully copied to clipboard!", 1);
        }
        else {
            this.incorrectArgs();
        }
    }
}
