package keystrokesmod.client.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

@Mixin(priority = 1005,value = EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

    public MixinEntityLivingBase(World worldIn) {
        super(worldIn);
    }

    /*
    @Shadow
    public float rotationYawHead;

    @Shadow
    public float prevRotationYawHead;

    @Override
    @Overwrite
    public Vec3 getLook(float partialTicks)
    {
        LookEvent e = new LookEvent(rotationPitch, prevRotationPitch, rotationYawHead, prevRotationYawHead);
        Raven.eventBus.post(e);

        if (partialTicks == 1.0F)
            return this.getVectorForRotation(e.getPitch(), e.getYaw());
        float f = e.getPrevPitch() + ((e.getPitch() - e.getPrevPitch()) * partialTicks);
        float f1 = e.getPrevYaw() + ((e.getYaw() - e.getPrevYaw()) * partialTicks);
        return this.getVectorForRotation(f, f1);
    }*/

}