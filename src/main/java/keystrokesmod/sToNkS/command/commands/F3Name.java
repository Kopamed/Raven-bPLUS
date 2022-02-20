package keystrokesmod.sToNkS.command.commands;

import keystrokesmod.sToNkS.clickgui.raven.CommandLine;
import keystrokesmod.sToNkS.command.Command;
import keystrokesmod.sToNkS.module.modules.client.ClientNameSpoof;

public class F3Name extends Command {
    public F3Name() {
        super("f3name", "Changes the client's name in f3", 1, 1,  new String[] {"New client name"},  new String[] {"f3n"});
    }

    public void onCall(String[] args){
        if(args == null){
            this.incorrectArgs();
            return;
        }
        StringBuilder wut = new StringBuilder(args[1]);
        if(args.length > 2){
            for(int i = 2; i < args.length; i++){
                wut.append(" ").append(args[i]);
            }
        }
        ClientNameSpoof.newName = wut.toString();
        CommandLine.print("§aSet client name to " + wut, 1);
    }
}
