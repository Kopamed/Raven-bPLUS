package keystrokesmod.client.module.modules.combat;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.mixin.mixins.IMinecraft;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.setting.impl.*;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;

import java.util.stream.Stream;

import static keystrokesmod.client.utils.Utils.Player.sendMessageToSelf;

/**
 * @author sigmaclientwastaken
 * warning, shitcode, do not touch
 */
public class LegitAura extends Module {

    public static DescriptionSetting dRotSpeed1, dRotSpeed2, dHitChance, dCombo;
    public static DoubleSliderSetting clickJitter, aps, reach, comboRange;
    public static SliderSetting hitChance, rotationSpeed, switchHits, targetRange;
    public static ComboSetting<TargetingMode> targetingMode;
    public static TickSetting combo;
    public static TickSetting tPlayers, tOthers, tInvisible, tDead, tSmartSwitch;

    public LegitAura() {
        super("LegitAura", ModuleCategory.combat);

        this.registerSetting(aps = new DoubleSliderSetting("CPS", 9.5, 11, 1, 20, 0.1));
        this.registerSetting(reach = new DoubleSliderSetting("Reach (Blocks)", 3, 3.2, 3, 6, 0.05));
        this.registerSetting(clickJitter = new DoubleSliderSetting("Attack Jitter", 0.1, 0.2, 0, 2, 0.05));
        this.registerSetting(new DescriptionSetting(EnumChatFormatting.GRAY + "        "));
        this.registerSetting(dHitChance = new DescriptionSetting(EnumChatFormatting.GRAY + "% chance that an attack will hit."));
        this.registerSetting(hitChance = new SliderSetting("Hit Chance %", 95, 10, 100, 1));
        this.registerSetting(new DescriptionSetting(EnumChatFormatting.GRAY + "      "));
        this.registerSetting(dRotSpeed1 = new DescriptionSetting(EnumChatFormatting.GRAY + "Rotation Speed Math:"));
        this.registerSetting(dRotSpeed2 = new DescriptionSetting(EnumChatFormatting.YELLOW + "rotation speed * sensitivity"));
        this.registerSetting(rotationSpeed = new SliderSetting("Rotation Speed", 80, 10, 200, 5));
        this.registerSetting(dCombo = new DescriptionSetting(EnumChatFormatting.GRAY + "Stops enemy velocity with projectile."));
        this.registerSetting(new DescriptionSetting(EnumChatFormatting.GRAY + "     "));
        this.registerSetting(combo = new TickSetting("Try to Combo", true));
        this.registerSetting(comboRange = new DoubleSliderSetting("Combo Range", 3.1, 3.3, 3, 6, 0.05));
        this.registerSetting(new DescriptionSetting(EnumChatFormatting.GRAY + "   "));
        this.registerSetting(dCombo = new DescriptionSetting(EnumChatFormatting.GRAY + "Targets:"));
        this.registerSetting(tPlayers = new TickSetting("Players", true));
        this.registerSetting(tOthers = new TickSetting("Other", false));
        this.registerSetting(tInvisible = new TickSetting("Invisible", false));
        this.registerSetting(tDead = new TickSetting("Dead", false));
        this.registerSetting(new DescriptionSetting(EnumChatFormatting.GRAY + "    "));
        this.registerSetting(targetRange = new SliderSetting("Targeting Range", 3.4, 3, 6, 0.05));
        this.registerSetting(targetingMode = new ComboSetting<>("Targeting Mode", TargetingMode.SINGLE));
        this.registerSetting(switchHits = new SliderSetting("Switch Hits", 2, 1, 5, 1));
        this.registerSetting(tSmartSwitch = new TickSetting("Smart Target Switching", false));

    }

    boolean triedToCombo;
    EntityLivingBase target;
    int hits = 0;
    long lastAttack;

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if(event.isPre()) {
            sendMessageToSelf("pre");
            if(target != null) {

                // make sure target is still valid
                if(!isCurrentTargetValid(target)) {
                    target = null;
                    return;
                }

                // cps check
                if((System.currentTimeMillis() - lastAttack) >= Utils.Client.ranModuleVal(aps, Utils.Java.rand())) {

                    // ray trace
                    MovingObjectPosition raytrace = Utils.Player.rayTrace(Utils.Client.ranModuleVal(reach, Utils.Java.rand()), ((IMinecraft) mc).getTimer().renderPartialTicks);
                    assert raytrace != null;
                    boolean canHit = raytrace.entityHit != null;
                    boolean isRealTarget = canHit && raytrace.entityHit == target;

                    // actually attacking
                    if (canHit && (isRealTarget ||
                            (raytrace.entityHit instanceof EntityLivingBase && isCurrentTargetValid((EntityLivingBase) raytrace.entityHit)))) {

                        // swing and attack
                        mc.thePlayer.swingItem();

                        // hit chance
                        if(Math.random() <= (hitChance.getInput()/100)) {
                            // attack
                            sendMessageToSelf("attack");
                            mc.thePlayer.attackTargetEntityWithCurrentItem(raytrace.entityHit);
                        }

                        // don't switch targets unless you're hitting the right person
                        if (isRealTarget) {
                            hits++;

                            // switch targets after enough hits
                            if (hits >= switchHits.getInput()) {
                                target = getTarget();
                                hits = 0;
                            }
                        } else if (tSmartSwitch.isToggled()) {
                            // switch to the person you just hit
                            target = (EntityLivingBase) raytrace.entityHit;
                            hits = 0;
                        }
                    } else {
                        // just swing
                        mc.thePlayer.swingItem();
                    }

                    // set last attack
                    lastAttack = System.currentTimeMillis();

                }

            } else {
                // get a target
                target = getTarget();
                hits = 0;
            }
        }
    }

    private boolean isCurrentTargetValid(EntityLivingBase e) {
        if(e == mc.thePlayer) {
            return false;
        }

        if(!tPlayers.isToggled() && e instanceof EntityPlayer) {
            return false;
        }

        if(!tOthers.isToggled() && !(e instanceof EntityPlayer)) {
            return false;
        }

        if(!tDead.isToggled() && (e.isDead || e.getHealth() <= 0)) {
            return false;
        }

        if(!tInvisible.isToggled() && e.isInvisibleToPlayer(mc.thePlayer)) {
            return false;
        }

        if(AntiBot.bot(e)) {
            return false;
        }

        return e.getDistanceToEntity(mc.thePlayer) <= targetRange.getInput();
    }

    private boolean isTargetValid(EntityLivingBase e) {
        if(e == mc.thePlayer) {
            return false;
        }

        if(!tPlayers.isToggled() && e instanceof EntityPlayer) {
            return false;
        }

        if(!tOthers.isToggled() && !(e instanceof EntityPlayer)) {
            return false;
        }

        if(!tDead.isToggled() && (e.isDead || e.getHealth() <= 0)) {
            return false;
        }

        if(!tInvisible.isToggled() && e.isInvisibleToPlayer(mc.thePlayer)) {
            return false;
        }

        if(e == target) {
            return false;
        }

        if(AntiBot.bot(e)) {
            return false;
        }

        return e.getDistanceToEntity(mc.thePlayer) <= targetRange.getInput();
    }

    private EntityLivingBase getTarget() {
        Stream<Entity> stream = mc.theWorld.loadedEntityList.stream().filter(e -> e instanceof EntityLivingBase).filter(e -> e != mc.thePlayer);

        if(!tPlayers.isToggled()) {
            stream = stream.filter(e -> !(e instanceof EntityPlayer));
        }

        if(!tOthers.isToggled()) {
            stream = stream.filter(e -> (e instanceof EntityPlayer));
        }

        if(!tDead.isToggled()) {
            stream = stream.filter(e -> e instanceof EntityLivingBase && !(e.isDead || ((EntityLivingBase) e).getHealth() <= 0));
        }

        if(!tInvisible.isToggled()) {
            stream = stream.filter(e -> e instanceof EntityLivingBase && !e.isInvisibleToPlayer(mc.thePlayer));
        }

        stream = stream.filter(e -> e != target).filter(e -> !AntiBot.bot(e)).filter(e -> e.getDistanceToEntity(mc.thePlayer) <= targetRange.getInput());

        return (EntityLivingBase) stream.findFirst().orElse(null);
    }

    public enum TargetingMode {
        SINGLE, SWITCH
    }

}
