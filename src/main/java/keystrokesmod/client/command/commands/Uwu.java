package keystrokesmod.client.command.commands;

import keystrokesmod.client.command.Command;
import keystrokesmod.client.main.Raven;

import static keystrokesmod.client.clickgui.raven.Terminal.print;

public class Uwu extends Command {
    private static boolean u;
    public Uwu() {
        super("uwu", "hevex/blowsy added this lol", 0, 0,  new String[] {},  new String[] {"hevex", "blowsy", "weeb", "torture", "noplsno"});
        u = false;
    }

    @Override
    public void onCall(String[] args){
        if (u) {
            return;
        }

        Raven.getExecutor().execute(() -> {
            u = true;

            for(int i = 0; i < 4; ++i) {
                if (i == 0) {
                    print("&e" + "nya");
                } else if (i == 1) {
                    print("&a" + "ichi ni san");
                } else if (i == 2) {
                    print("&e" + "nya");
                } else {
                    print("&a" + "arigatou!");
                }

                try {
                    Thread.sleep(500L);
                } catch (InterruptedException var2) {
                }
            }

            u = false;
        });
    }
}
