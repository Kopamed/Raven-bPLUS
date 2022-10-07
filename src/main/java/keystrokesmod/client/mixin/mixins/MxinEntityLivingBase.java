package keystrokesmod.client.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import keystrokesmod.client.event.impl.LookEvent;
import keystrokesmod.client.main.Raven;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Mixin(EntityLivingBase.class)
public abstract class MxinEntityLivingBase extends Entity {

	@Shadow
	public float rotationYawHead;

	@Shadow
	public float prevRotationYawHead;

	public MxinEntityLivingBase(World worldIn) {
		super(worldIn);
	}

    /**
     * @author mc code
     * @reason lookevent
     */
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
    }

}
