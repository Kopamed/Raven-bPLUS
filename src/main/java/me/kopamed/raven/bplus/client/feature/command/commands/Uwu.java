package me.kopamed.raven.bplus.client.feature.command.commands;

import me.kopamed.raven.bplus.client.feature.command.Command;
import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine;

public class Uwu extends Command {
    private static boolean u;
    public Uwu() {
        super("uwu", "hevex added this lol", 0, 0,  new String[] {},  new String[] {"hevex", "weeb", "torture", "noplsno"});
        u = false;
    }

    @Override
    public void onCall(String[] args){
        if (u) {
            return;
        }

        Raven.client.getExecutor().execute(() -> {
            u = true;

            for(int i = 0; i < 4; ++i) {
                if (i == 0) {
                    CommandLine.print("&e" + "nya", 1);
                } else if (i == 1) {
                    CommandLine.print("&a" + "ichi ni san", 0);
                } else if (i == 2) {
                    CommandLine.print("&e" + "nya", 0);
                } else {
                    CommandLine.print("&a" + "arigatou!", 0);
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
