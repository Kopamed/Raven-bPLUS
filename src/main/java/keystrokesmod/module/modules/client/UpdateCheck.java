package keystrokesmod.module.modules.client;

import keystrokesmod.ay;
import keystrokesmod.main.Ravenb3;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.version;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class UpdateCheck extends Module {
    public static ModuleDesc howToUse;
    public static ModuleSettingTick copyToClipboard;
    public UpdateCheck() {
        super("Update", category.client, 0);
        this.registerSetting(howToUse = new ModuleDesc(ay.uf("command") + ": update"));
        this.registerSetting(copyToClipboard = new ModuleSettingTick("Copy to clipboard", true));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (version.outdated()) {
            Ravenb3.outdated = true;
            ay.sendMessageToSelf("The current version or Raven B+ is outdated. Visit https://github.com/Kopamed/Raven-bPLUS to download the latest version.");
            ay.sendMessageToSelf("https://github.com/Kopamed/Raven-bPLUS");
            if (this.copyToClipboard.isToggled()) {
                StringSelection selection = new StringSelection(Ravenb3.sourceLocation);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
                ay.sendMessageToSelf("Successfully copied download link to clipboard!");
            }
        }
        if (version.isBeta()) {
            Ravenb3.beta = true;
            ay.sendMessageToSelf("Man is on beta and asking for stable. You mad bruv?");
            ay.sendMessageToSelf("https://github.com/Kopamed/Raven-bPLUS");
            if (this.copyToClipboard.isToggled()) {
                StringSelection selection = new StringSelection(Ravenb3.sourceLocation);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
                ay.sendMessageToSelf("Successfully copied download link to clipboard!");
            }
        }
        this.disable();
    }
}
