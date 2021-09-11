package keystrokesmod.module.modules.client;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.utils.Utils;

public class ClientNameSpoof extends Module {
    public static ModuleDesc desc;
    public static String newName = "";

    public ClientNameSpoof(){
        super("ClientNameSpoofer", category.client, 0);
        this.registerSetting(desc = new ModuleDesc(Utils.Java.uf("command") + ": f3name [name]"));
    }
}
