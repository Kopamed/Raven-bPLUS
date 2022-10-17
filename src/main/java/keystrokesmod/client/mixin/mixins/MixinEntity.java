package keystrokesmod.client.mixin.mixins;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import keystrokesmod.client.event.impl.MoveInputEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.player.SafeWalk;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

@Mixin(priority = 995, value = Entity.class)
public abstract class MixinEntity {

    @Shadow
    public boolean noClip;

    @Shadow
    public abstract void setEntityBoundingBox(AxisAlignedBB p_setEntityBoundingBox_1_);

    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow
    protected abstract void resetPositionToBB();

    @Shadow
    public World worldObj;
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    protected boolean isInWeb;
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    public boolean onGround;

    @Shadow
    public abstract boolean isSneaking();

    @Shadow
    public float stepHeight;
    @Shadow
    public boolean isCollidedHorizontally;
    @Shadow
    public boolean isCollidedVertically;
    @Shadow
    public boolean isCollided;

    @Shadow
    protected abstract void updateFallState(double p_updateFallState_1_, boolean p_updateFallState_3_,
            Block p_updateFallState_4_, BlockPos p_updateFallState_5_);

    @Shadow
    protected abstract boolean canTriggerWalking();

    @Shadow
    public Entity ridingEntity;
    @Shadow
    public float distanceWalkedModified;
    @Shadow
    public float distanceWalkedOnStepModified;
    @Shadow
    private int nextStepDistance;

    @Shadow
    public abstract boolean isInWater();

    @Shadow
    public abstract void playSound(String p_playSound_1_, float p_playSound_2_, float p_playSound_3_);

    @Shadow
    protected abstract String getSwimSound();

    @Shadow
    protected Random rand;

    @Shadow
    protected abstract void playStepSound(BlockPos p_playStepSound_1_, Block p_playStepSound_2_);

    @Shadow
    protected abstract void doBlockCollisions();

    @Shadow
    public abstract void addEntityCrashInfo(CrashReportCategory p_addEntityCrashInfo_1_);

    @Shadow
    public abstract boolean isWet();

    @Shadow
    protected abstract void dealFireDamage(int p_dealFireDamage_1_);

    @Shadow
    private int fire;

    @Shadow
    public abstract void setFire(int p_setFire_1_);

    @Shadow
    public int fireResistance;

    @Shadow
    public float rotationYaw;

    /**
     * @author mc code
     * @reason too complicated to inject without mod compatibility issues
     */
    @Overwrite
    public void moveEntity(double p_moveEntity_1_, double p_moveEntity_3_, double p_moveEntity_5_) {
        if (this.noClip) {
            this.setEntityBoundingBox(
                    this.getEntityBoundingBox().offset(p_moveEntity_1_, p_moveEntity_3_, p_moveEntity_5_));
            this.resetPositionToBB();
        } else {
            this.worldObj.theProfiler.startSection("move");
            double d0 = this.posX;
            double d1 = this.posY;
            double d2 = this.posZ;
            if (this.isInWeb) {
                this.isInWeb = false;
                p_moveEntity_1_ *= 0.25D;
                p_moveEntity_3_ *= 0.05000000074505806D;
                p_moveEntity_5_ *= 0.25D;
                this.motionX = 0.0D;
                this.motionY = 0.0D;
                this.motionZ = 0.0D;
            }

            double d3 = p_moveEntity_1_;
            double d4 = p_moveEntity_3_;
            double d5 = p_moveEntity_5_;
            boolean flag; // = this.onGround && this.isSneaking() && ((Entity) ((Object) this)) instanceof
                          // EntityPlayer;

            Minecraft mc = Minecraft.getMinecraft();

            if (((Object) this == mc.thePlayer) && mc.thePlayer.onGround) {
                Module safeWalk = Raven.moduleManager.getModuleByClazz(SafeWalk.class);

                if ((safeWalk != null) && safeWalk.isEnabled() && !SafeWalk.doShift.isToggled()) {
                    flag = true;

                    if (SafeWalk.blocksOnly.isToggled()) {
                        ItemStack i = mc.thePlayer.getHeldItem();
                        if ((i == null) || !(i.getItem() instanceof ItemBlock)) {
                            flag = mc.thePlayer.isSneaking(); // this used to cause issues, sorry!
                                                              // - sigmaclientwastaken
                        }
                    }

                    // this took 30 seconds
                    if (SafeWalk.lookDown.isToggled()) {
                        if ((mc.thePlayer.rotationPitch < SafeWalk.pitchRange.getInputMin())
                                || (mc.thePlayer.rotationPitch > SafeWalk.pitchRange.getInputMax())) {
                            flag = mc.thePlayer.isSneaking();
                        }
                    }

                    if(SafeWalk.shawtyMoment.isToggled()) {
                        if((mc.thePlayer.movementInput.moveForward > 0) && (mc.thePlayer.movementInput.moveStrafe == 0)) {
                            flag = mc.thePlayer.isSneaking();
                        }
                    }

                } else {
                    flag = mc.thePlayer.isSneaking();
                }
            } else {
                flag = false;
            }

            if (flag) {
                double d6;
                for (d6 = 0.05D; (p_moveEntity_1_ != 0.0D) && this.worldObj
                        .getCollidingBoundingBoxes(((Entity) ((Object) this)),
                                this.getEntityBoundingBox().offset(p_moveEntity_1_, -1.0D, 0.0D))
                        .isEmpty(); d3 = p_moveEntity_1_) {
                    if ((p_moveEntity_1_ < d6) && (p_moveEntity_1_ >= -d6)) {
                        p_moveEntity_1_ = 0.0D;
                    } else if (p_moveEntity_1_ > 0.0D) {
                        p_moveEntity_1_ -= d6;
                    } else {
                        p_moveEntity_1_ += d6;
                    }
                }

                for (; (p_moveEntity_5_ != 0.0D) && this.worldObj
                        .getCollidingBoundingBoxes(((Entity) ((Object) this)),
                                this.getEntityBoundingBox().offset(0.0D, -1.0D, p_moveEntity_5_))
                        .isEmpty(); d5 = p_moveEntity_5_) {
                    if ((p_moveEntity_5_ < d6) && (p_moveEntity_5_ >= -d6)) {
                        p_moveEntity_5_ = 0.0D;
                    } else if (p_moveEntity_5_ > 0.0D) {
                        p_moveEntity_5_ -= d6;
                    } else {
                        p_moveEntity_5_ += d6;
                    }
                }

                for (; (p_moveEntity_1_ != 0.0D) && (p_moveEntity_5_ != 0.0D)
                        && this.worldObj
                                .getCollidingBoundingBoxes(((Entity) ((Object) this)),
                                        this.getEntityBoundingBox().offset(p_moveEntity_1_, -1.0D, p_moveEntity_5_))
                                .isEmpty(); d5 = p_moveEntity_5_) {
                    if ((p_moveEntity_1_ < d6) && (p_moveEntity_1_ >= -d6)) {
                        p_moveEntity_1_ = 0.0D;
                    } else if (p_moveEntity_1_ > 0.0D) {
                        p_moveEntity_1_ -= d6;
                    } else {
                        p_moveEntity_1_ += d6;
                    }

                    d3 = p_moveEntity_1_;
                    if ((p_moveEntity_5_ < d6) && (p_moveEntity_5_ >= -d6)) {
                        p_moveEntity_5_ = 0.0D;
                    } else if (p_moveEntity_5_ > 0.0D) {
                        p_moveEntity_5_ -= d6;
                    } else {
                        p_moveEntity_5_ += d6;
                    }
                }
            }

            List<AxisAlignedBB> list1 = this.worldObj.getCollidingBoundingBoxes(((Entity) ((Object) this)),
                    this.getEntityBoundingBox().addCoord(p_moveEntity_1_, p_moveEntity_3_, p_moveEntity_5_));
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();

            AxisAlignedBB axisalignedbb1;
            for (Iterator var22 = list1.iterator(); var22.hasNext(); p_moveEntity_3_ = axisalignedbb1
                    .calculateYOffset(this.getEntityBoundingBox(), p_moveEntity_3_)) {
                axisalignedbb1 = (AxisAlignedBB) var22.next();
            }

            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, p_moveEntity_3_, 0.0D));
            boolean flag1 = this.onGround || ((d4 != p_moveEntity_3_) && (d4 < 0.0D));

            AxisAlignedBB axisalignedbb13;
            Iterator var55;
            for (var55 = list1.iterator(); var55.hasNext(); p_moveEntity_1_ = axisalignedbb13
                    .calculateXOffset(this.getEntityBoundingBox(), p_moveEntity_1_)) {
                axisalignedbb13 = (AxisAlignedBB) var55.next();
            }

            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(p_moveEntity_1_, 0.0D, 0.0D));

            for (var55 = list1.iterator(); var55.hasNext(); p_moveEntity_5_ = axisalignedbb13
                    .calculateZOffset(this.getEntityBoundingBox(), p_moveEntity_5_)) {
                axisalignedbb13 = (AxisAlignedBB) var55.next();
            }

            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, 0.0D, p_moveEntity_5_));
            if ((this.stepHeight > 0.0F) && flag1 && ((d3 != p_moveEntity_1_) || (d5 != p_moveEntity_5_))) {
                double d11 = p_moveEntity_1_;
                double d7 = p_moveEntity_3_;
                double d8 = p_moveEntity_5_;
                AxisAlignedBB axisalignedbb3 = this.getEntityBoundingBox();
                this.setEntityBoundingBox(axisalignedbb);
                p_moveEntity_3_ = this.stepHeight;
                List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(((Entity) ((Object) this)),
                        this.getEntityBoundingBox().addCoord(d3, p_moveEntity_3_, d5));
                AxisAlignedBB axisalignedbb4 = this.getEntityBoundingBox();
                AxisAlignedBB axisalignedbb5 = axisalignedbb4.addCoord(d3, 0.0D, d5);
                double d9 = p_moveEntity_3_;

                AxisAlignedBB axisalignedbb6;
                for (Iterator var35 = list.iterator(); var35
                        .hasNext(); d9 = axisalignedbb6.calculateYOffset(axisalignedbb5, d9)) {
                    axisalignedbb6 = (AxisAlignedBB) var35.next();
                }

                axisalignedbb4 = axisalignedbb4.offset(0.0D, d9, 0.0D);
                double d15 = d3;

                AxisAlignedBB axisalignedbb7;
                for (Iterator var37 = list.iterator(); var37
                        .hasNext(); d15 = axisalignedbb7.calculateXOffset(axisalignedbb4, d15)) {
                    axisalignedbb7 = (AxisAlignedBB) var37.next();
                }

                axisalignedbb4 = axisalignedbb4.offset(d15, 0.0D, 0.0D);
                double d16 = d5;

                AxisAlignedBB axisalignedbb8;
                for (Iterator var39 = list.iterator(); var39
                        .hasNext(); d16 = axisalignedbb8.calculateZOffset(axisalignedbb4, d16)) {
                    axisalignedbb8 = (AxisAlignedBB) var39.next();
                }

                axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d16);
                AxisAlignedBB axisalignedbb14 = this.getEntityBoundingBox();
                double d17 = p_moveEntity_3_;

                AxisAlignedBB axisalignedbb9;
                for (Iterator var42 = list.iterator(); var42
                        .hasNext(); d17 = axisalignedbb9.calculateYOffset(axisalignedbb14, d17)) {
                    axisalignedbb9 = (AxisAlignedBB) var42.next();
                }

                axisalignedbb14 = axisalignedbb14.offset(0.0D, d17, 0.0D);
                double d18 = d3;

                AxisAlignedBB axisalignedbb10;
                for (Iterator var44 = list.iterator(); var44
                        .hasNext(); d18 = axisalignedbb10.calculateXOffset(axisalignedbb14, d18)) {
                    axisalignedbb10 = (AxisAlignedBB) var44.next();
                }

                axisalignedbb14 = axisalignedbb14.offset(d18, 0.0D, 0.0D);
                double d19 = d5;

                AxisAlignedBB axisalignedbb11;
                for (Iterator var46 = list.iterator(); var46
                        .hasNext(); d19 = axisalignedbb11.calculateZOffset(axisalignedbb14, d19)) {
                    axisalignedbb11 = (AxisAlignedBB) var46.next();
                }

                axisalignedbb14 = axisalignedbb14.offset(0.0D, 0.0D, d19);
                double d20 = (d15 * d15) + (d16 * d16);
                double d10 = (d18 * d18) + (d19 * d19);
                if (d20 > d10) {
                    p_moveEntity_1_ = d15;
                    p_moveEntity_5_ = d16;
                    p_moveEntity_3_ = -d9;
                    this.setEntityBoundingBox(axisalignedbb4);
                } else {
                    p_moveEntity_1_ = d18;
                    p_moveEntity_5_ = d19;
                    p_moveEntity_3_ = -d17;
                    this.setEntityBoundingBox(axisalignedbb14);
                }

                AxisAlignedBB axisalignedbb12;
                for (Iterator var50 = list.iterator(); var50.hasNext(); p_moveEntity_3_ = axisalignedbb12
                        .calculateYOffset(this.getEntityBoundingBox(), p_moveEntity_3_)) {
                    axisalignedbb12 = (AxisAlignedBB) var50.next();
                }

                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, p_moveEntity_3_, 0.0D));
                if (((d11 * d11) + (d8 * d8)) >= ((p_moveEntity_1_ * p_moveEntity_1_) + (p_moveEntity_5_ * p_moveEntity_5_))) {
                    p_moveEntity_1_ = d11;
                    p_moveEntity_3_ = d7;
                    p_moveEntity_5_ = d8;
                    this.setEntityBoundingBox(axisalignedbb3);
                }
            }

            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection("rest");
            this.resetPositionToBB();
            this.isCollidedHorizontally = (d3 != p_moveEntity_1_) || (d5 != p_moveEntity_5_);
            this.isCollidedVertically = d4 != p_moveEntity_3_;
            this.onGround = this.isCollidedVertically && (d4 < 0.0D);
            this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
            int k = MathHelper.floor_double(this.posZ);
            BlockPos blockpos = new BlockPos(i, j, k);
            Block block1 = this.worldObj.getBlockState(blockpos).getBlock();
            if (block1.getMaterial() == Material.air) {
                Block block = this.worldObj.getBlockState(blockpos.down()).getBlock();
                if ((block instanceof BlockFence) || (block instanceof BlockWall) || (block instanceof BlockFenceGate)) {
                    block1 = block;
                    blockpos = blockpos.down();
                }
            }

            this.updateFallState(p_moveEntity_3_, this.onGround, block1, blockpos);
            if (d3 != p_moveEntity_1_) {
                this.motionX = 0.0D;
            }

            if (d5 != p_moveEntity_5_) {
                this.motionZ = 0.0D;
            }

            if (d4 != p_moveEntity_3_) {
                block1.onLanded(this.worldObj, ((Entity) ((Object) this)));
            }

            if (this.canTriggerWalking() && !flag && (this.ridingEntity == null)) {
                double d12 = this.posX - d0;
                double d13 = this.posY - d1;
                double d14 = this.posZ - d2;
                if (block1 != Blocks.ladder) {
                    d13 = 0.0D;
                }

                if ((block1 != null) && this.onGround) {
                    block1.onEntityCollidedWithBlock(this.worldObj, blockpos, ((Entity) ((Object) this)));
                }

                this.distanceWalkedModified = (float) ((double) this.distanceWalkedModified
                        + ((double) MathHelper.sqrt_double((d12 * d12) + (d14 * d14)) * 0.6D));
                this.distanceWalkedOnStepModified = (float) ((double) this.distanceWalkedOnStepModified
                        + ((double) MathHelper.sqrt_double((d12 * d12) + (d13 * d13) + (d14 * d14)) * 0.6D));
                if ((this.distanceWalkedOnStepModified > (float) this.nextStepDistance)
                        && (block1.getMaterial() != Material.air)) {
                    this.nextStepDistance = (int) this.distanceWalkedOnStepModified + 1;
                    if (this.isInWater()) {
                        float f = MathHelper.sqrt_double((this.motionX * this.motionX * 0.20000000298023224D)
                                + (this.motionY * this.motionY) + (this.motionZ * this.motionZ * 0.20000000298023224D))
                                * 0.35F;
                        if (f > 1.0F) {
                            f = 1.0F;
                        }

                        this.playSound(this.getSwimSound(), f,
                                1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F));
                    }

                    this.playStepSound(blockpos, block1);
                }
            }

            try {
                this.doBlockCollisions();
            } catch (Throwable var52) {
                CrashReport crashreport = CrashReport.makeCrashReport(var52, "Checking entity block collision");
                CrashReportCategory crashreportcategory = crashreport
                        .makeCategory("Entity being checked for collision");
                this.addEntityCrashInfo(crashreportcategory);
                throw new ReportedException(crashreport);
            }

            boolean flag2 = this.isWet();
            if (this.worldObj.isFlammableWithin(this.getEntityBoundingBox().contract(0.001D, 0.001D, 0.001D))) {
                this.dealFireDamage(1);
                if (!flag2) {
                    ++this.fire;
                    if (this.fire == 0) {
                        this.setFire(8);
                    }
                }
            } else if (this.fire <= 0) {
                this.fire = -this.fireResistance;
            }

            if (flag2 && (this.fire > 0)) {
                this.playSound("random.fizz", 0.7F, 1.6F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F));
                this.fire = -this.fireResistance;
            }

            this.worldObj.theProfiler.endSection();
        }

    }

    /**
     * @author mc code
     * @reason friction
     */
    @Overwrite
    public void moveFlying(float strafe, float forward, float fric) {
        MoveInputEvent e = new MoveInputEvent(strafe, forward, fric, this.rotationYaw);
        if((Object) this == Minecraft.getMinecraft().thePlayer)
            Raven.eventBus.post(e);

        strafe = e.getStrafe();
        forward = e.getForward();
        fric = e.getFriction();
        float yaw = e.getYaw();

        float f = (strafe * strafe) + (forward * forward);

        if (f >= 1.0E-4F) {
            f = MathHelper.sqrt_float(f);
            if (f < 1.0F) {
                f = 1.0F;
            }

            f = fric / f;
            strafe *= f;
            forward *= f;
            float f1 = MathHelper.sin((yaw * 3.1415927F) / 180.0F);
            float f2 = MathHelper.cos((yaw * 3.1415927F) / 180.0F);
            this.motionX += (strafe * f2) - (forward * f1);
            this.motionZ += (forward * f2) + (strafe * f1);
        }

    }

}
