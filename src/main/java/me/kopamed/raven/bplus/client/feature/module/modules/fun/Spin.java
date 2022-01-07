package me.kopamed.raven.bplus.client.feature.module.modules.fun;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Spin extends Module {
    public static NumberSetting a;
    public static NumberSetting b;
    private float yaw;

    public Spin() {
        super("Spin", ModuleCategory.Misc, 0);
        this.registerSetting(a = new NumberSetting("Rotation", 360.0D, 30.0D, 360.0D, 1.0D));
        this.registerSetting(b = new NumberSetting("Speed", 25.0D, 1.0D, 60.0D, 1.0D));
    }

    public void onEnable() {
        this.yaw = mc.thePlayer.rotationYaw;
    }

    public void onDisable() {
        this.yaw = 0.0F;
    }

    @SubscribeEvent
    public void update(TickEvent.ClientTickEvent e) {
        double left = (double)this.yaw + a.getInput() - (double)mc.thePlayer.rotationYaw;
        EntityPlayerSP var10000;
        var10000 = mc.thePlayer;
        if (left < b.getInput()) {
            var10000.rotationYaw = (float)((double)var10000.rotationYaw + left);
            this.disable();
        } else {
            var10000.rotationYaw = (float)((double)var10000.rotationYaw + b.getInput());
            if ((double)mc.thePlayer.rotationYaw >= (double)this.yaw + a.getInput()) {
                this.disable();
            }
        }

    }
}