package keystrokesmod.client.command.commands;

import keystrokesmod.client.command.Command;
import keystrokesmod.client.module.modules.combat.AimAssist;
import net.minecraft.entity.Entity;

import static keystrokesmod.client.clickgui.raven.Terminal.print;

public class Friends extends Command {
    public Friends() {
        super("friends", "Allows you to manage and view your friends list", 1, 2,
                new String[] { "add / remove / list", "Player's name" }, new String[] { "f", "amigos", "lonely4ever" });
    }

    @Override
    public void onCall(String[] args) {
        if (args.length == 0) {
            listFriends();
        } else if (args[0].equalsIgnoreCase("list")) {
            listFriends();
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                boolean added = AimAssist.addFriend(args[1]);
                if (added) {
                    print("Successfully added " + args[1] + " to your friends list!");
                } else {
                    print("An error occurred!");
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                boolean removed = AimAssist.removeFriend(args[1]);
                if (removed) {
                    print("Successfully removed " + args[1] + " from your friends list!");
                } else {
                    print("An error occurred!");
                }
            }
        } else {
            this.incorrectArgs();
        }
    }

    public void listFriends() {
        if (AimAssist.getFriends().isEmpty()) {
            print("You have no friends. :(");
        } else {
            print("Your friends are:");
            for (Entity entity : AimAssist.getFriends()) {
                print(entity.getName());
            }
        }
    }
}
