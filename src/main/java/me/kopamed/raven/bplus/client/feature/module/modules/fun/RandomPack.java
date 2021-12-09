package me.kopamed.raven.bplus.client.feature.module.modules.fun;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;


public class RandomPack extends Module {
    public static DescriptionSetting packAmount;
    private final File dir;

    public RandomPack(){
        super("RandomPack", ModuleCategory.Misc);

        dir = new File(mc.mcDataDir + File.separator + "resourcepacks");
        this.registerSetting(packAmount = new DescriptionSetting("You have " + getPackAmount() + " packs"));
    }

    public void guiUpdate(){
        packAmount.setDesc("You have " + getPackAmount() + " packs");
    }

    @SubscribeEvent
    public void fuckme(TickEvent.RenderTickEvent er){
        if(this.isToggled()){
            mc.getResourcePackRepository().setResourcePackInstance(dir.listFiles()[(int)(Math.random() * getPackAmount())]);
            this.disable();
        }
    }

    private int getPackAmount(){
        return dir.list().length;
    }
}
