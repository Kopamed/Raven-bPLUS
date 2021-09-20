package keystrokesmod.module.modules.combat;

import keystrokesmod.module.*;
import keystrokesmod.utils.Utils;
import keystrokesmod.module.modules.world.AntiBot;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.concurrent.ThreadLocalRandom;

public class STap extends Module {
    public static ModuleSettingSlider range, eventType;
    public static ModuleDesc eventTypeDesc;
    public static ModuleSettingTick onlyPlayers;
    public static ModuleSettingDoubleSlider actionTicks, onceEvery;
    public static double comboLasts;
    public static boolean comboing, hitCoolDown, alreadyHit;
    public static int hitTimeout, hitsWaited;

    public STap(){
        super("STap", category.combat, 0);
        this.registerSetting(onlyPlayers = new ModuleSettingTick("Only combo players", true));
        this.registerSetting(actionTicks = new ModuleSettingDoubleSlider("Action Time (MS)",  25, 55, 1, 500, 1));
        this.registerSetting(onceEvery =  new ModuleSettingDoubleSlider("Once every ... hits", 25, 55, 1, 500, 1));
        this.registerSetting(range = new ModuleSettingSlider("Range: ", 3, 1, 6, 0.05));
        this.registerSetting(eventType = new ModuleSettingSlider("Value: ", 2, 1, 2, 1));
        this.registerSetting(eventTypeDesc = new ModuleDesc("Mode: POST"));
    }

    public void guiUpdate(){
        eventTypeDesc.setDesc(Utils.md + Utils.Modes.SprintResetTimings.values()[(int) eventType.getInput() - 1]);
    }


    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if(!Utils.Player.isPlayerInGame())
            return;

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

                        comboLasts = ThreadLocalRandom.current().nextDouble(actionTicks.getInputMin(),  actionTicks.getInputMax()+0.01) + System.currentTimeMillis();
                        comboing = true;
                        startCombo();
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
        if(!Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
        }
    }

    private static void startCombo() {
        if(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
            KeyBinding.onTick(mc.gameSettings.keyBindBack.getKeyCode());
        }
    }
}
