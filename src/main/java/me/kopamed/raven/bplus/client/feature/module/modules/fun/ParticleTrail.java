package me.kopamed.raven.bplus.client.feature.module.modules.fun;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;

public class ParticleTrail extends Module {
    public static NumberSetting a;
    public static DescriptionSetting b;

    public ParticleTrail() {
        super("Particle Trail", ModuleCategory.Misc, 0);
        this.registerSetting(a = new NumberSetting("Value:", 1, 1, EnumParticleTypes.values().length, 1));
        this.registerSetting(b = new DescriptionSetting("Mode: FLAME"));
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
