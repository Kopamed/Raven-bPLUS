package keystrokesmod.module.modules.client;

import keystrokesmod.CommandLine;
import keystrokesmod.ay;
import keystrokesmod.main.Ravenb3;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.version;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateCheck extends Module {
    public static ModuleDesc howToUse;
    public static ModuleSettingTick copyToClipboard;
    public static ModuleSettingTick openLink;
    public UpdateCheck() {
        super("Update", category.client, 0);
        this.registerSetting(howToUse = new ModuleDesc(ay.uf("command") + ": update"));
        this.registerSetting(copyToClipboard = new ModuleSettingTick("Copy to clipboard", true));
        this.registerSetting(openLink = new ModuleSettingTick("Open dl in browser", true));
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (version.outdated()) {
            Ravenb3.outdated = true;
            ay.sendMessageToSelf("The current version or Raven B+ is outdated. Visit https://github.com/Kopamed/Raven-bPLUS to download the latest version.");
            ay.sendMessageToSelf("https://github.com/Kopamed/Raven-bPLUS");
        }
        if (version.isBeta()) {
            Ravenb3.beta = true;
            ay.sendMessageToSelf("Man is on beta and asking for stable. You mad bruv?");
            ay.sendMessageToSelf("https://github.com/Kopamed/Raven-bPLUS");
        }
        else {
            ay.sendMessageToSelf("You are on the latest public version!");
        }
        if (copyToClipboard.isToggled()) {
            if (ay.copyToClipboard(Ravenb3.sourceLocation))
                ay.sendMessageToSelf("Successfully copied download link to clipboard!");
        }
        if(openLink.isToggled()) {
            URL url = null;
            try {
                url = new URL(Ravenb3.sourceLocation);
                ay.openWebpage(url);
            } catch (MalformedURLException bruh) {
                bruh.printStackTrace();
                ay.sendMessageToSelf("&cFailed to open page! Please report this bug in Raven b+'s discord");
            }
        }
        this.disable();
    }
}
