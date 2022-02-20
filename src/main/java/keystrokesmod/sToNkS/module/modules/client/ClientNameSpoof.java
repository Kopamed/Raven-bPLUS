package keystrokesmod.sToNkS.module.modules.client;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleDesc;
import keystrokesmod.sToNkS.utils.Utils;

public class ClientNameSpoof extends Module {
    public static ModuleDesc desc;
    public static String newName = "";

    public ClientNameSpoof(){
        super("ClientNameSpoofer", category.client, 0);
        this.registerSetting(desc = new ModuleDesc(Utils.Java.uf("command") + ": f3name [name]"));
    }
}
