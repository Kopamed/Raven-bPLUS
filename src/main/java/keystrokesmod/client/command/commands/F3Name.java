package keystrokesmod.client.command.commands;


import keystrokesmod.client.clickgui.raven.Terminal;
import keystrokesmod.client.command.Command;
import keystrokesmod.client.module.modules.client.ClientNameSpoof;

public class F3Name extends Command {
    public F3Name() {
        super("f3name", "Changes the client's name in f3", 1, 1,  new String[] {"New client name"},  new String[] {"f3n"});
    }

    public void onCall(String[] args){
        if(args.length == 0){
            this.incorrectArgs();
            return;
        }
        StringBuilder wut = new StringBuilder(args[0]);
        if(args.length > 1){
            for(int i = 2; i < args.length; i++){
                wut.append(" ").append(args[i]);
            }
        }
        ClientNameSpoof.newName = wut.toString();
        Terminal.print("Set client name to " + wut);
    }
}
