package keystrokesmod.module.modules.client;

import keystrokesmod.ay;
import keystrokesmod.main.Ravenb3;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.version;

public class UpdateCheck extends Module {
    public static ModuleDesc howToUse;
    public UpdateCheck() {
        super("Update", category.client, 0);
        this.registerSetting(howToUse = new ModuleDesc(ay.uf("command") + ": update"));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (version.outdated()) {
            Ravenb3.outdated = true;
            ay.sendMessageToSelf("The current version or Raven B+ is outdated. Visit https://github.com/Kopamed/Raven-bPLUS to download the latest version.");
        }
        this.disable();
    }
}
