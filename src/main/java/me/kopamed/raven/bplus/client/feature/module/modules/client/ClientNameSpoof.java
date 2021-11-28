package me.kopamed.raven.bplus.client.feature.module.modules.client;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;

public class ClientNameSpoof extends Module {
    public static DescriptionSetting desc;
    public static String newName = "lunarclient:db2533c"; //default val

    public ClientNameSpoof(){
        super("ClientNameSpoofer", ModuleCategory.Misc, 0);
        this.registerSetting(desc = new DescriptionSetting(Utils.Java.uf("command") + ": f3name [name]"));
    }
}
