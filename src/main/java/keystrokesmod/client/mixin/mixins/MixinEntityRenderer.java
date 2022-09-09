package keystrokesmod.client.mixin.mixins;

import com.google.common.base.Predicates;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.combat.HitBox;
import keystrokesmod.client.module.modules.combat.Reach;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(priority = 995, value = EntityRenderer.class)
public class MixinEntityRenderer {

    @Shadow
    private Minecraft mc;
    @Shadow
    private Entity pointedEntity;

    /**
     * @author mc code
     */
    @Overwrite
    public void getMouseOver(float p_getMouseOver_1_) {
        Entity entity = this.mc.getRenderViewEntity();
        if (entity != null && this.mc.theWorld != null) {
            this.mc.mcProfiler.startSection("pick");
            this.mc.pointedEntity = null;
            double reach = this.mc.playerController.getBlockReachDistance();

            Module reachMod = Raven.moduleManager.getModuleByClazz(Reach.class);

            if (reachMod.isEnabled()) {
                reach = Reach.getReach();
            }

            this.mc.objectMouseOver = entity.rayTrace(reach, p_getMouseOver_1_);
            double distanceToVec = reach;

            Vec3 vec3 = entity.getPositionEyes(p_getMouseOver_1_);
            boolean flag = false;

            if (!reachMod.isEnabled()) {
                if (this.mc.playerController.extendedReach()) {
                    reach = 6.0D;
                    distanceToVec = 6.0D;
                } else if (reach > 3.0D) {
                    flag = true;
                }
            }

            if (this.mc.objectMouseOver != null) {
                distanceToVec = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
            }

            Vec3 vec31 = entity.getLook(p_getMouseOver_1_);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * reach, vec31.yCoord * reach, vec31.zCoord * reach);
            this.pointedEntity = null;
            Vec3 vec33 = null;
            float f = 1.0F;
            List<Entity> list = this.mc.theWorld.getEntitiesInAABBexcluding(entity,
                    entity.getEntityBoundingBox()
                            .addCoord(vec31.xCoord * reach, vec31.yCoord * reach, vec31.zCoord * reach).expand(f, f, f),
                    Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            double d2 = distanceToVec;

            for (Entity entity1 : list) {
                float f1 = entity1.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
                if (axisalignedbb.isVecInside(vec3)) {
                    if (d2 >= 0.0D) {
                        this.pointedEntity = entity1;
                        vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                        d2 = 0.0D;
                    }
                } else if (movingobjectposition != null) {
                    double d3 = vec3.distanceTo(movingobjectposition.hitVec);
                    if (d3 < d2 || d2 == 0.0D) {
                        if (entity1 == entity.ridingEntity && !entity.canRiderInteract()) {
                            if (d2 == 0.0D) {
                                this.pointedEntity = entity1;
                                vec33 = movingobjectposition.hitVec;
                            }
                        } else {
                            this.pointedEntity = entity1;
                            vec33 = movingobjectposition.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }

            if (this.pointedEntity != null && flag && vec3.distanceTo(vec33) > (reachMod.isEnabled()? Reach.getReach() : 3.0D)) {
                this.pointedEntity = null;
                assert vec33 != null;
                this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33,
                        null, new BlockPos(vec33));
            }

            if (this.pointedEntity != null && (d2 < distanceToVec || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }

            this.mc.mcProfiler.endSection();
        }

    }
}