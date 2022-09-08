package keystrokesmod.client.module.modules.world;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.module.Module;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatLogger extends Module {
    private final File dir;
    private File chatLog;
    public String fileName;
    public String extension;

    public ChatLogger() {
        super("Chat Logger", ModuleCategory.world);

        extension = "txt";
        dir = new File(mc.mcDataDir, "keystrokes" + File.separator + "logs");
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void onEnable() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        fileName = dtf.format(now) + "." + extension;
        this.chatLog = new File(dir, fileName);
        if (!chatLog.exists()) {
            try {
                chatLog.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onEnable();
    }

    @Subscribe
    public void onMessageRecieved(ForgeEvent fe) {
        if (fe.getEvent() instanceof ClientChatReceivedEvent) {
            try (FileWriter fw = new FileWriter(this.chatLog.getPath(), true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.println(((ClientChatReceivedEvent) fe.getEvent()).message.getUnformattedText());
            } catch (IOException e) {
                // shit
            }
        }
    }
}
