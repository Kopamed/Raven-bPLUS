package me.kopamed.raven.bplus.client.feature.module.modules.client;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.Description;
import me.kopamed.raven.bplus.helper.utils.Utils;

public class ClientNameSpoof extends Module {
    public static Description desc;
    public static String newName = "";

    public ClientNameSpoof(){
        super("ClientNameSpoofer", ModuleCategory.Client, 0);
        this.registerSetting(desc = new Description(Utils.Java.uf("command") + ": f3name [name]"));
    }
}
