package me.kopamed.lunarkeystrokes.command;

import me.kopamed.lunarkeystrokes.clickgui.raven.CommandLine;

public class Command {
    private final String name;
    private final String help;
    private final int minArgs;
    private final int maxArgs;
    private final String[] alias;
    private String[] args;

    public Command(String name, String help, int minArgs, int maxArgs, String[] args, String[] alias) {
        this.name = name;
        this.help = help;
        this.minArgs = minArgs;
        this.maxArgs = maxArgs;
        this.args = args;
        this.alias = alias;
    }

    public Command(String name, String help, int minArgs, int maxArgs, String[] args) {
        this(name, help, minArgs, maxArgs,args, new String[] {});
    }

    public String getName() {
        return name;
    }

    public String getHelp() {
        return help;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public int getMaxArgs() {
        return maxArgs;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void onCall(String[] args) {

    }

    public void incorrectArgs() {
        CommandLine.print("&cIncorrect arguments! Run", 1);
        CommandLine.print("&c`help " + this.getName() + "` for usage info", 0);
    }

    public String[] getAliases() {
        return this.alias;
    }
}
