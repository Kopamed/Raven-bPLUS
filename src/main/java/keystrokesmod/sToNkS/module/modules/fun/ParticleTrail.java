package keystrokesmod.sToNkS.module.modules.fun;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleDesc;
import keystrokesmod.sToNkS.module.ModuleSettingSlider;
import keystrokesmod.sToNkS.utils.Utils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;

public class ParticleTrail extends Module {
    public static ModuleSettingSlider a;
    public static ModuleDesc b;

    public ParticleTrail() {
        super("Particle Trail", Module.category.fun, 0);
        this.registerSetting(a = new ModuleSettingSlider("Value:", 1, 1, EnumParticleTypes.values().length, 1));
        this.registerSetting(b = new ModuleDesc("Mode: FLAME"));
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
