package me.kopamed.lunarkeystrokes.module.modules.fun;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import net.minecraft.client.entity.EntityPlayerSP;

public class Spin extends Module {
    public static Slider a;
    public static Slider b;
    private float yaw;

    public Spin() {
        super("Spin", Module.category.fun, 0);
        this.registerSetting(a = new Slider("Rotation", 360.0D, 30.0D, 360.0D, 1.0D));
        this.registerSetting(b = new Slider("Speed", 25.0D, 1.0D, 60.0D, 1.0D));
    }

    public void onEnable() {
        this.yaw = mc.thePlayer.rotationYaw;
    }

    public void onDisable() {
        this.yaw = 0.0F;
    }

    public void update() {
        double left = (double)this.yaw + a.getInput() - (double)mc.thePlayer.rotationYaw;
        EntityPlayerSP var10000;
        if (left < b.getInput()) {
            var10000 = mc.thePlayer;
            var10000.rotationYaw = (float)((double)var10000.rotationYaw + left);
            this.disable();
        } else {
            var10000 = mc.thePlayer;
            var10000.rotationYaw = (float)((double)var10000.rotationYaw + b.getInput());
            if ((double)mc.thePlayer.rotationYaw >= (double)this.yaw + a.getInput()) {
                this.disable();
            }
        }

    }
}