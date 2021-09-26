package keystrokesmod.command.commands;

import keystrokesmod.clickgui.raven.CommandLine;
import keystrokesmod.command.Command;
import keystrokesmod.main.Ravenbplus;
import keystrokesmod.utils.Utils;

public class SetKey extends Command {
    public SetKey() {
        super("setkey", "Sets hypixel's API key<br>To get a new key, run<br>`/api new`", 2, 2, new String[] {"paste/hypixel", "key"}, new String[] {"apikey"});
    }

    @Override
    public void onCall(String[] args) {
        if(args == null || args.length == 2) {
            this.incorrectArgs();
            return;
        }


        CommandLine.print("§3Setting...", 1);
        String n;
        n = args[2];
        if(args[1].equalsIgnoreCase("paste")){
            Ravenbplus.getExecutor().execute(() -> {
                Utils.URLS.pasteApiKey = n;
            });
        } else if(args[1].equalsIgnoreCase("hypixel")){
            Ravenbplus.getExecutor().execute(() -> {
                if (Utils.URLS.isHypixelKeyValid(n)) {
                    Utils.URLS.hypixelApiKey = n;
                    CommandLine.print("&a" + "success!", 0);
                    Ravenbplus.clientConfig.saveConfig();
                } else {
                    CommandLine.print("&c" + "Invalid key.", 0);
                }

            });
        }
    }
}
