package me.kopamed.lunarkeystrokes.module.modules.client;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.utils.Utils;

public class ClientNameSpoof extends Module {
    public static Description desc;
    public static String newName = "";

    public ClientNameSpoof(){
        super("ClientNameSpoofer", category.client, 0);
        this.registerSetting(desc = new Description(Utils.Java.uf("command") + ": f3name [name]"));
    }
}
