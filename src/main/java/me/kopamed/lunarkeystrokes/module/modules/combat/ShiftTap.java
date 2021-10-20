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

public class ShiftTap extends Module {
    public static Slider range, eventType, chance;
    public static Description eventTypeDesc;
    public static Tick onlyPlayers;
    public static RangeSlider actionTicks, onceEvery, postDelay;
    public static boolean comboing, hitCoolDown, alreadyHit, waitingForPostDelay;
    public static int hitTimeout, hitsWaited;
    public static CoolDown actionTimer = new CoolDown(0), postDelayTimer = new CoolDown(0);

    public ShiftTap(){
        super("ShiftTap", category.combat, 0);
        this.registerSetting(onlyPlayers = new Tick("Only combo players", true));
        this.registerSetting(actionTicks = new RangeSlider("Action Time (MS)",  25, 55, 1, 500, 1));
        this.registerSetting(onceEvery =  new RangeSlider("Once every ... hits", 1, 1, 1, 10, 1));
        this.registerSetting(postDelay =  new RangeSlider("Post delay (MS)", 25, 55, 1, 500, 1));
        this.registerSetting(chance =  new Slider("Chance %", 100, 0, 100, 1));
        this.registerSetting(range = new Slider("Range: ", 3, 1, 6, 0.05));
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

        if(waitingForPostDelay){
            if(postDelayTimer.hasTimeElapsed()){
                waitingForPostDelay = false;
                comboing = true;
                startCombo();
                actionTimer.start();
            }
            return;
        }

        if(comboing) {
            if(actionTimer.hasTimeElapsed()){
                comboing = false;
                finishCombo();
                return;
            }else {
                return;
            }
        }



        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof Entity && Mouse.isButtonDown(0)) {
            Entity target = mc.objectMouseOver.entityHit;
            //////////System.out.println(target.hurtResistantTime);
            if(target.isDead) {
                return;
            }

            if (mc.thePlayer.getDistanceToEntity(target) <= range.getInput()) {
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
                        if(onceEvery.getInputMin() == onceEvery.getInputMax()) {
                            hitTimeout =  (int)onceEvery.getInputMin();
                        } else {

                            hitTimeout = ThreadLocalRandom.current().nextInt((int)onceEvery.getInputMin(), (int)onceEvery.getInputMax());
                        }
                        hitCoolDown = true;
                        hitsWaited = 0;

                        actionTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble(actionTicks.getInputMin(),  actionTicks.getInputMax()+0.01));

                        if(postDelay.getInputMax() != 0){
                            postDelayTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble(postDelay.getInputMin(),  postDelay.getInputMax()+0.01));
                            postDelayTimer.start();
                            waitingForPostDelay = true;
                        } else {
                            comboing = true;
                            startCombo();
                            actionTimer.start();
                            //////////System.out.println("Combo started");
                            alreadyHit = true;
                        }

                        //////////System.out.println("Combo started");
                        alreadyHit = true;
                    }
                } else {
                    if(alreadyHit){
                        //////////System.out.println("UnHit");
                    }
                    alreadyHit = false;
                    //////////System.out.println("REEEEEEE");
                }
            }
        }
    }

    private static void finishCombo() {
        if(!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()))
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
    }

    private static void startCombo() {
        if(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
            KeyBinding.onTick(mc.gameSettings.keyBindSneak.getKeyCode());
        }
    }
}
