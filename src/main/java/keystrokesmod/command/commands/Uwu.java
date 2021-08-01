package keystrokesmod.command.commands;

import keystrokesmod.command.Command;
import keystrokesmod.main.Ravenb3;

import static keystrokesmod.CommandLine.print;

public class Uwu extends Command {
    private static boolean u;
    public Uwu() {
        super("uwu", "hevex added this lol", 0, 0,  new String[] {},  new String[] {"hevex", "weeb", "torture", "noplsno"});
        this.u = false;
    }

    @Override
    public void onCall(String[] args){
        if (this.u) {
            return;
        }

        Ravenb3.getExecutor().execute(() -> {
            this.u = true;

            for(int i = 0; i < 4; ++i) {
                if (i == 0) {
                    print("&e" + "nya", 1);
                } else if (i == 1) {
                    print("&a" + "ichi ni san", 0);
                } else if (i == 2) {
                    print("&e" + "nya", 0);
                } else {
                    print("&a" + "arigatou!", 0);
                }

                try {
                    Thread.sleep(500L);
                } catch (InterruptedException var2) {
                }
            }

            this.u = false;
        });
    }
}
