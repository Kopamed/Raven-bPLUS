package me.kopamed.lunarkeystrokes.module.modules.fun;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;

public class ParticleTrail extends Module {
    public static Slider a;
    public static Description b;

    public ParticleTrail() {
        super("Particle Trail", Module.category.fun, 0);
        this.registerSetting(a = new Slider("Value:", 1, 1, EnumParticleTypes.values().length, 1));
        this.registerSetting(b = new Description("Mode: FLAME"));
    }

    public void guiUpdate() {
        b.setDesc(Utils.md + EnumParticleTypes.values()[(int)a.getInput() - 1]);
    }

    public void update() {
        Vec3 vec = mc.thePlayer.getLookVec();
        double x = mc.thePlayer.posX - vec.xCoord * 2.0D;
        double y = mc.thePlayer.posY + ((double)mc.thePlayer.getEyeHeight() - 0.2D);
        double z = mc.thePlayer.posZ - vec.zCoord * 2.0D;
        try{
            mc.thePlayer.worldObj.spawnParticle(EnumParticleTypes.values()[(int)a.getInput() - 1], x, y, z, 0.0D, 0.0D, 0.0D, 0);
        } catch (ReportedException e){}

    }
}
