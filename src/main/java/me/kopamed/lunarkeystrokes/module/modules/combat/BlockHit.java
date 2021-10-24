package me.kopamed.lunarkeystrokes.module.modules.combat;

import me.kopamed.lunarkeystrokes.module.*;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.RangeSlider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.utils.CoolDown;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.modules.world.AntiBot;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.concurrent.ThreadLocalRandom;

public class BlockHit extends Module {
    public static Slider eventType, chance;
    public static Description eventTypeDesc;
    public static Tick onlyPlayers, onRightMBHold;
    public static RangeSlider waitMs, hitPer, postDelay, range;
    public static boolean executingAction, hitCoolDown, alreadyHit, safeGuard;
    public static int hitTimeout, hitsWaited;
    private CoolDown actionTimer = new CoolDown(0), postDelayTimer = new CoolDown(0);
    private boolean waitingForPostDelay;

    public BlockHit() {
        super("BlockHit", category.combat, 0);
        this.registerSetting(onlyPlayers = new Tick("Only combo players", true));
        this.registerSetting(onRightMBHold = new Tick("When holding down rmb", true));
        this.registerSetting(waitMs = new RangeSlider("Action Time (MS)", 110, 150, 1, 500, 1));
        this.registerSetting(hitPer = new RangeSlider("Once every ... hits", 1, 1, 1, 10, 1));
        this.registerSetting(postDelay = new RangeSlider("Post Delay (MS)", 10, 40, 0, 500, 1));
        this.registerSetting(chance =  new Slider("Chance %", 100, 0, 100, 1));
        this.registerSetting(range = new RangeSlider("Range: ", 0, 2.8, 0, 6, 0.05));
        this.registerSetting(eventType = new Slider("Value: ", 2, 1, 2, 1));
        this.registerSetting(eventTypeDesc = new Description("Mode: POST"));
    }

    public void guiUpdate() {
        eventTypeDesc.setDesc(Utils.md + Utils.Modes.SprintResetTimings.values()[(int) eventType.getInput() - 1]);
    }


    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if(!Utils.Player.isPlayerInGame())
            return;

        if(onRightMBHold.isToggled() && !Utils.Player.tryingToCombo()){
            if(!safeGuard || Utils.Player.isPlayerHoldingWeapon() && Mouse.isButtonDown(0)) {
                safeGuard = true;
                finishCombo();
            }
            return;
        }
        if(waitingForPostDelay){
            if(postDelayTimer.hasTimeElapsed()){
                executingAction = true;
                startCombo();
                //////////System.out.println("Combo started");
                waitingForPostDelay = false;
                if(safeGuard) safeGuard = false;
                actionTimer.start();
            }
            return;
        }

        if(executingAction) {
            if(actionTimer.hasTimeElapsed()){
                executingAction = false;
                finishCombo();
                return;
            }else {
                return;
            }
        }

        if(onRightMBHold.isToggled() && Utils.Player.tryingToCombo()) {
            if(mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null) {
                if(!safeGuard  || Utils.Player.isPlayerHoldingWeapon() && Mouse.isButtonDown(0)) {
                    safeGuard = true;
                    finishCombo();
                }
                return;
            } else {
                Entity target = mc.objectMouseOver.entityHit;
                if(target.isDead) {
                    if(!safeGuard  || Utils.Player.isPlayerHoldingWeapon() && Mouse.isButtonDown(0)) {
                        safeGuard = true;
                        finishCombo();
                    }
                    return;
                }
            }
        }

        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof Entity && Mouse.isButtonDown(0)) {
            Entity target = mc.objectMouseOver.entityHit;
            //////////System.out.println(target.hurtResistantTime);
            if(target.isDead) {
                if(onRightMBHold.isToggled() && Mouse.isButtonDown(1) && Mouse.isButtonDown(0)) {
                    if(!safeGuard  || Utils.Player.isPlayerHoldingWeapon() && Mouse.isButtonDown(0)) {
                        safeGuard = true;
                        finishCombo();
                    }
                }
                return;
            }

            if (mc.thePlayer.getDistanceToEntity(target) <= range.getInputMax() && mc.thePlayer.getDistanceToEntity(target) >= range.getInputMin()) {
                if ((target.hurtResistantTime >= 10 && Utils.Modes.SprintResetTimings.values()[(int) eventType.getInput() - 1] == Utils.Modes.SprintResetTimings.POST) || (target.hurtResistantTime <= 10 && Utils.Modes.SprintResetTimings.values()[(int) eventType.getInput() - 1] == Utils.Modes.SprintResetTimings.PRE)) {

                    if (onlyPlayers.isToggled()){
                        if (!(target instanceof EntityPlayer)){
                            return;
                        }
                    }

                    if(AntiBot.bot(target)){
                        return;
                    }


                    if (hitCoolDown && !alreadyHit) {
                        //////////System.out.println("coolDownCheck");
                        hitsWaited++;
                        if(hitsWaited >= hitTimeout){
                            //////////System.out.println("hiit cool down reached");
                            hitCoolDown = false;
                            hitsWaited = 0;
                        } else {
                            //////////System.out.println("still waiting for cooldown");
                            alreadyHit = true;
                            return;
                        }
                    }

                    //////////System.out.println("Continued");
                    if(!(chance.getInput() == 100 ? true : Math.random() <= chance.getInput()/100))
                        return;

                    if(!alreadyHit){
                        //////////System.out.println("Startring combo code");
                        guiUpdate();
                        if(hitPer.getInputMin() == hitPer.getInputMax()) {
                            hitTimeout =  (int) hitPer.getInputMin();
                        } else {

                            hitTimeout = ThreadLocalRandom.current().nextInt((int) hitPer.getInputMin(), (int) hitPer.getInputMax());
                        }
                        hitCoolDown = true;
                        hitsWaited = 0;

                        actionTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble(waitMs.getInputMin(),  waitMs.getInputMax()+0.01));
                        if(postDelay.getInputMax() != 0){
                            postDelayTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble(postDelay.getInputMin(),  postDelay.getInputMax()+0.01));
                            postDelayTimer.start();
                            waitingForPostDelay = true;
                        } else {
                            executingAction = true;
                            startCombo();
                            actionTimer.start();
                            //////////System.out.println("Combo started");
                            alreadyHit = true;
                            if(safeGuard) safeGuard = false;
                        }
                        alreadyHit = true;
                    }
                } else {
                    if(alreadyHit){
                        alreadyHit = false;
                    }

                    //////////System.out.println("REEEEEEE");
                    if(safeGuard) safeGuard = false;
                }
            }
        }
    }

    private static void finishCombo() {
        int key = mc.gameSettings.keyBindUseItem.getKeyCode();
        KeyBinding.setKeyBindState(key, false);
        Utils.Client.setMouseButtonState(1, false);
    }

    private static void startCombo() {
        if(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            int key = mc.gameSettings.keyBindUseItem.getKeyCode();
            KeyBinding.setKeyBindState(key, true);
            KeyBinding.onTick(key);
            Utils.Client.setMouseButtonState(1, true);
        }
    }
}
