package keystrokesmod.client.module.modules.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Mouse;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.player.RightClicker;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AimAssist extends Module {
    public static SliderSetting speedYaw, complimentYaw, speedPitch, complimentPitch;
    public static SliderSetting fov;
    public static SliderSetting distance;
    public static SliderSetting pitchOffSet;
    public static TickSetting clickAim;
    public static TickSetting stopWhenOver;
    public static TickSetting aimPitch;
    public static TickSetting weaponOnly;
    public static TickSetting aimInvis;
    public static TickSetting breakBlocks;
    public static TickSetting blatantMode;
    public static TickSetting ignoreFriends;
    public static TickSetting ignoreNaked;
    public static ArrayList<Entity> friends = new ArrayList<>();

    public AimAssist() {
        super("AimAssist", ModuleCategory.combat);

        this.registerSetting(speedYaw = new SliderSetting("Speed 1 (yaw)", 45.0D, 5.0D, 100.0D, 1.0D));
        this.registerSetting(complimentYaw = new SliderSetting("Speed 2 (yaw)", 15.0D, 2D, 97.0D, 1.0D));
        this.registerSetting(speedPitch = new SliderSetting("Speed 1 (pitch)", 45.0D, 5.0D, 100.0D, 1.0D));
        this.registerSetting(complimentPitch = new SliderSetting("Speed 2 (pitch)", 15.0D, 2D, 97.0D, 1.0D));
        this.registerSetting(pitchOffSet = new SliderSetting("pitchOffSet (blocks)", 4D, -2, 2, 0.050D));
        this.registerSetting(fov = new SliderSetting("FOV", 90.0D, 15.0D, 360.0D, 1.0D));
        this.registerSetting(distance = new SliderSetting("Distance", 4.5D, 1.0D, 10.0D, 0.1D));
        this.registerSetting(clickAim = new TickSetting("Click aim", true));
        this.registerSetting(breakBlocks = new TickSetting("Break blocks", true));
        this.registerSetting(ignoreFriends = new TickSetting("Ignore Friends", true));
        this.registerSetting(weaponOnly = new TickSetting("Weapon only", false));
        this.registerSetting(aimInvis = new TickSetting("Aim invis", false));
        this.registerSetting(blatantMode = new TickSetting("Blatant mode", false));
        this.registerSetting(aimPitch = new TickSetting("Aim pitch", false));
        this.registerSetting(ignoreNaked = new TickSetting("Ignore naked", false));
    }

    @Subscribe
    public void onRender(ForgeEvent fe) {
        try {
            if (fe.getEvent() instanceof TickEvent.ClientTickEvent) {
                if (!Utils.Client.currentScreenMinecraft() || !Utils.Player.isPlayerInGame())
                    return;

                if (breakBlocks.isToggled() && (mc.objectMouseOver != null)) {
                    BlockPos p = mc.objectMouseOver.getBlockPos();
                    if (p != null) {
                        Block bl = mc.theWorld.getBlockState(p).getBlock();
                        if ((bl != Blocks.air) && !(bl instanceof BlockLiquid) && (bl != null))
                            return;
                    }
                }

                if (!weaponOnly.isToggled() || Utils.Player.isPlayerHoldingWeapon()) {

                    Module autoClicker = Raven.moduleManager.getModuleByClazz(RightClicker.class); // right clicker???????????
                    // what if player clicking but mouse not down ????
                    if ((clickAim.isToggled() && Utils.Client.autoClickerClicking())
                            || (Mouse.isButtonDown(0) && (autoClicker != null) && !autoClicker.isEnabled())
                            || !clickAim.isToggled()) {
                        Entity en = this.getEnemy();
                        if (en != null)
                            if (blatantMode.isToggled())
                                Utils.Player.aim(en, (float) pitchOffSet.getInput());
                            else {
                                double n = Utils.Player.fovFromEntity(en);
                                if ((n > 1.0D) || (n < -1.0D)) {
                                    double complimentSpeed = n
                                            * (ThreadLocalRandom.current().nextDouble(complimentYaw.getInput() - 1.47328,
                                                    complimentYaw.getInput() + 2.48293) / 100);
                                    float val = (float) (-(complimentSpeed + (n / (101.0D - (float) ThreadLocalRandom.current()
                                            .nextDouble(speedYaw.getInput() - 4.723847, speedYaw.getInput())))));
                                    mc.thePlayer.rotationYaw += val;
                                }
                                if (aimPitch.isToggled()) {
                                    double complimentSpeed = Utils.Player.PitchFromEntity(en,
                                            (float) pitchOffSet.getInput())
                                            * (ThreadLocalRandom.current().nextDouble(complimentPitch.getInput() - 1.47328,
                                                    complimentPitch.getInput() + 2.48293) / 100);

                                    float val = (float) (-(complimentSpeed
                                            + (n / (101.0D - (float) ThreadLocalRandom.current()
                                                    .nextDouble(speedPitch.getInput() - 4.723847,
                                                            speedPitch.getInput())))));

                                    mc.thePlayer.rotationPitch += val;

                                }
                            }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAFriend(Entity entity) {
        if (entity == mc.thePlayer)
            return true;

        for (Entity wut : friends)
            if (wut.equals(entity))
                return true;
        try {
            EntityPlayer bruhentity = (EntityPlayer) entity;
            if (Raven.debugger) {
                Utils.Player.sendMessageToSelf(
                        "unformatted / " + bruhentity.getDisplayName().getUnformattedText().replace("§", "%"));

                Utils.Player.sendMessageToSelf(
                        "susbstring entity / " + bruhentity.getDisplayName().getUnformattedText().substring(0, 2));
                Utils.Player.sendMessageToSelf(
                        "substring player / " + mc.thePlayer.getDisplayName().getUnformattedText().substring(0, 2));
            }
            if (mc.thePlayer.isOnSameTeam((EntityLivingBase) entity) || mc.thePlayer.getDisplayName()
                    .getUnformattedText().startsWith(bruhentity.getDisplayName().getUnformattedText().substring(0, 2)))
                return true;
        } catch (Exception fhwhfhwe) {
            if (Raven.debugger)
                Utils.Player.sendMessageToSelf(fhwhfhwe.getMessage());
        }

        return false;
    }

    public Entity getEnemy() {
        int fov = (int) AimAssist.fov.getInput();
        List<EntityPlayer> var2 = mc.theWorld.playerEntities;
        for (EntityPlayer en : var2)
            if(
                    (ignoreFriends.isToggled() || !isAFriend(en))
                    && (en != mc.thePlayer)
                    && (aimInvis.isToggled() || !en.isInvisible())
                    && (mc.thePlayer.getDistanceToEntity(en) < distance.getInput())
                    && (!AntiBot.bot(en))
                    && (Utils.Player.fov(en, fov))
                    && (!ignoreNaked.isToggled() || ((en.getCurrentArmor(3) == null) && (en.getCurrentArmor(2) == null) && (en.getCurrentArmor(1) == null) && (en.getCurrentArmor(0) == null)))
                    )
                return en;
        return null;
    }

    public static void addFriend(Entity entityPlayer) {
        friends.add(entityPlayer);
    }

    public static boolean addFriend(String name) {
        boolean found = false;
        for (Entity entity : mc.theWorld.getLoadedEntityList())
            if (entity.getName().equalsIgnoreCase(name) || entity.getCustomNameTag().equalsIgnoreCase(name))
                if (!isAFriend(entity)) {
                    addFriend(entity);
                    found = true;
                }

        return found;
    }

    public static boolean removeFriend(String name) {
        boolean removed = false;
        boolean found = false;
        for (NetworkPlayerInfo networkPlayerInfo : new ArrayList<>(mc.getNetHandler().getPlayerInfoMap())) {
            Entity entity = mc.theWorld.getPlayerEntityByName(networkPlayerInfo.getDisplayName().getUnformattedText());
            if (entity.getName().equalsIgnoreCase(name) || entity.getCustomNameTag().equalsIgnoreCase(name)) {
                removed = removeFriend(entity);
                found = true;
            }
        }

        return found && removed;
    }

    public static boolean removeFriend(Entity entityPlayer) {
        try {
            friends.remove(entityPlayer);
        } catch (Exception eeeeee) {
            eeeeee.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Entity> getFriends() {
        return friends;
    }
}