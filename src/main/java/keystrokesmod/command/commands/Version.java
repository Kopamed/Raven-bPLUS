package keystrokesmod.command.commands;

import keystrokesmod.command.Command;

public class Version extends Command {
    public Version() {
        super("version", "tells you what build of B+ you are using", 0, 0, new String[] {}, new String[] {"v", "ver", "whicvh", "build", "b"});
    }

    @Override
    public void onCall(String[] args) {

    }
}
