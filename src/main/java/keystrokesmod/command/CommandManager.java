package keystrokesmod.command;

import keystrokesmod.clickgui.raven.CommandLine;
import keystrokesmod.command.commands.*;
import keystrokesmod.main.Ravenbplus;
import keystrokesmod.module.modules.HUD;
import keystrokesmod.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CommandManager {
    public List<Command> commandList;
    public List<Command> sortedCommandList;

    public CommandManager() {
        this.commandList = new ArrayList<Command>();
        this.sortedCommandList = new ArrayList<Command>();
        this.addCommand(new Update());
        this.addCommand(new Help());
        this.addCommand(new SetKey());
        this.addCommand(new Discord());
        this.addCommand(new Config());
        this.addCommand(new Clear());
        this.addCommand(new Cname());
        this.addCommand(new Debug());
        this.addCommand(new Duels());
        this.addCommand(new Fakechat());
        this.addCommand(new Nick());
        this.addCommand(new Ping());
        this.addCommand(new Shoutout());
        this.addCommand(new Uwu());
        this.addCommand(new Friends());
        this.addCommand(new Version());
        this.addCommand(new F3Name());

    }

    public void addCommand(Command c) {
        this.commandList.add(c);
    }

    public List<Command> getCommandList() {
        return this.commandList;
    }

    public Command getCommandByName(String name) {
        for (Command command : this.commandList) {
            if (command.getName().equalsIgnoreCase(name))
                return command;
            for (String alias : command.getAliases()) {
                if (alias.equalsIgnoreCase(name))
                    return command;
            }
        }
        return null;
    }

    public void noSuchCommand(String name) {
        CommandLine.print("&cCommand '" + name + "' not found!", 1);
        CommandLine.print("&cReport this on the discord", 0);
        CommandLine.print("&cif this is an error!", 0);
    }

    public void executeCommand(String commandName, String[] args) {
        Command command = Ravenbplus.commandManager.getCommandByName(commandName);

        if (command == null) {
            this.noSuchCommand(commandName);
            return;
        }

        command.onCall(args);
    }

    public void sort() {
        if (HUD.alphabeticalSort.isToggled()) {
            Collections.sort(this.sortedCommandList, Comparator.comparing(Command::getName));
        } else {
            Collections.sort(this.sortedCommandList, (o1, o2) -> {
                return Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName());
            });
        }

    }
}
