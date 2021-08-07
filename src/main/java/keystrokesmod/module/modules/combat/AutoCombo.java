package keystrokesmod.module.modules.combat;

import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.modules.world.AntiBot;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.concurrent.ThreadLocalRandom;

public class AutoCombo extends Module {
    public static ModuleSettingSlider comboMode, range;
    public static ModuleSettingTick onlyPlayers;
    public static ModuleDesc comboModeDesc;
    public static ModuleSettingSlider minActionTicks, maxActionTicks, minOnceEvery, maxOnceEvery;
    public static double comboLasts, currentTime;
    public static boolean comboing, hitCoolDown, alreadyHit;
    public static int hitTimeout, hitsWaited;

    public AutoCombo() {
        super("Auto Combo", category.combat, 0);
        this.registerSetting(onlyPlayers = new ModuleSettingTick("Only combo players", true));
        this.registerSetting(minActionTicks = new ModuleSettingSlider("Min ms: ", 75, 1, 500, 5));
        this.registerSetting(maxActionTicks = new ModuleSettingSlider("Man ms: ", 120, 1, 500, 5));
        this.registerSetting(minOnceEvery = new ModuleSettingSlider("Once every min hits: ", 1, 1, 10, 1));
        this.registerSetting(maxOnceEvery = new ModuleSettingSlider("Once every max hits: ", 1, 1, 10, 1));
        this.registerSetting(range = new ModuleSettingSlider("Range: ", 2.85, 1, 6, 0.05));
        this.registerSetting(comboMode = new ModuleSettingSlider("Value: ", 1, 1, 3, 1));
        this.registerSetting(comboModeDesc = new ModuleDesc("Mode: BlockHit"));
    }


    public void guiUpdate() {
        comboModeDesc.setDesc(ay.md + ComboMode.values()[(int) (comboMode.getInput() - 1)]);
        ay.correctSliders(minActionTicks, maxActionTicks);
        ay.correctSliders(minOnceEvery, maxOnceEvery);
    }


    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if(comboing) {
            if(System.currentTimeMillis() >= comboLasts){
                comboing = false;
                finishCombo();
                return;
            }else {
                return;
            }
        }



        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof Entity && Mouse.isButtonDown(0)) {
            Entity target = mc.objectMouseOver.entityHit;
            //System.out.println(target.hurtResistantTime);
            if(target.isDead) {
                return;
            }

            if (mc.thePlayer.getDistanceToEntity(target) <= range.getInput()) {
                if (target.hurtResistantTime <= 10) {

                    if (onlyPlayers.isToggled()){
                        if (!(target instanceof EntityPlayer)){
                            return;
                        }
                    }

                    if(AntiBot.bot(target)){
                        return;
                    }


                    if (hitCoolDown && !alreadyHit) {
                        //System.out.println("coolDownCheck");
                        hitsWaited++;
                        if(hitsWaited >= hitTimeout){
                            //System.out.println("hiit cool down reached");
                            hitCoolDown = false;
                            hitsWaited = 0;
                        } else {
                            //System.out.println("still waiting for cooldown");
                            alreadyHit = true;
                            return;
                        }
                    }

                    //System.out.println("Continued");

                    if(!alreadyHit){
                        //System.out.println("Startring combo code");
                        guiUpdate();
                        if(minOnceEvery.getInput() == maxOnceEvery.getInput()) {
                            hitTimeout =  (int)minOnceEvery.getInput();
                        } else {

                            hitTimeout = ThreadLocalRandom.current().nextInt((int)minOnceEvery.getInput(), (int)maxOnceEvery.getInput());
                        }
                        hitCoolDown = true;
                        hitsWaited = 0;

                        comboLasts = ThreadLocalRandom.current().nextDouble(minActionTicks.getInput(),  maxActionTicks.getInput()+0.01) + System.currentTimeMillis();
                        comboing = true;
                        startCombo();
                        //System.out.println("Combo started");
                        alreadyHit = true;
                    }
                } else {
                    if(alreadyHit){
                        //System.out.println("UnHit");
                    }
                    alreadyHit = false;
                    //System.out.println("REEEEEEE");
                }
            }
        }
    }

    private static void finishCombo() {
        if(ComboMode.values()[(int) (comboMode.getInput() - 1)] == ComboMode.BLOCKHIT) {
            int key = mc.gameSettings.keyBindUseItem.getKeyCode();
            KeyBinding.setKeyBindState(key, false);
            ay.setMouseButtonState(1, false);
        }
        else if(ComboMode.values()[(int) (comboMode.getInput() - 1)] == ComboMode.WTAP) {
            if(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())){
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
            }
        }
        else if(ComboMode.values()[(int) (comboMode.getInput() - 1)] == ComboMode.STAP) {
            if(!Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())){
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
            }
        }
    }

    private static void startCombo() {
        if(ComboMode.values()[(int) (comboMode.getInput() - 1)] == ComboMode.BLOCKHIT) {
            int key = mc.gameSettings.keyBindUseItem.getKeyCode();
            KeyBinding.setKeyBindState(key, true);
            KeyBinding.onTick(key);
            ay.setMouseButtonState(1, true);
        }
        else if(ComboMode.values()[(int) (comboMode.getInput() - 1)] == ComboMode.WTAP) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            KeyBinding.onTick(mc.gameSettings.keyBindForward.getKeyCode());
        }
        else if(ComboMode.values()[(int) (comboMode.getInput() - 1)] == ComboMode.STAP) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
            KeyBinding.onTick(mc.gameSettings.keyBindBack.getKeyCode());
        }
    }


    public static enum ComboMode {
        BLOCKHIT,
        WTAP,
        STAP;
    }
}