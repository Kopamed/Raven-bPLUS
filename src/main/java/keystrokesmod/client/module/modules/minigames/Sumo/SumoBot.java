package keystrokesmod.client.module.modules.minigames.Sumo;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.combat.AimAssist;
import keystrokesmod.client.module.modules.combat.STap;
import keystrokesmod.client.module.modules.render.AntiShuffle;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class SumoBot extends Module {

    private State state = State.HUB;
    private STap sTap;
    private SumoClicker leftClicker;
    private AimAssist aimAssist;
    private final CoolDown timer = new CoolDown(0);
    private final CoolDown rcTimer = new CoolDown(0);
    private final CoolDown slotTimer = new CoolDown(0);
    private final SliderSetting autoClickerTrigger;

    public SumoBot() {
        super("Sumo Bot", ModuleCategory.sumo);
        this.registerSetting(autoClickerTrigger = new SliderSetting("Distance for Autoclicker", 4.5, 1, 5, 0.1));
    }

    public void onEnable() {
        state = State.HUB;
        sTap = (STap) Raven.moduleManager.getModuleByName("STap");
        leftClicker = (SumoClicker) Raven.moduleManager.getModuleByName("Sumo Clicker");
        aimAssist = (AimAssist) Raven.moduleManager.getModuleByName("AimAssist");
        mc.gameSettings.pauseOnLostFocus = false;
    }


    private void matchStart() {
        state = State.INGAME;
        sTap.setToggled(true);
        aimAssist.setToggled(true);
        mc.thePlayer.inventory.currentItem = 4;
        slotTimer.setCooldown(2300);
        slotTimer.start();
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
    }


    private void matchEnd() {
        timer.setCooldown(2300);
        timer.start();
        state = State.GAMEEND;
        sTap.setToggled(false);
        leftClicker.setToggled(false);
        aimAssist.setToggled(false);
        mc.thePlayer.inventory.currentItem = 4;
        KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
    }

    @Subscribe
    public void onRender2D(Render2DEvent e) {
        if (!Utils.Player.isPlayerInGame())
            return;
        //Utils.Player.sendMessageToSelf(state.toString());
        if (slotTimer.firstFinish()) {
            mc.thePlayer.inventory.currentItem = 4;
        }
        if (rcTimer.firstFinish() && state == State.GAMEEND) {
            KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
            rcTimer.setCooldown(1900);
            rcTimer.start();
        }
        if (state == State.QUEUE) {
            for (String s : Utils.Client.getPlayersFromScoreboard()) {
                if (s.contains("Opponent")) {
                    matchStart();
                }
            }
        } else if (state == State.GAMEEND) {
            if (timer.hasFinished()) {
                state = State.QUEUE;
                rcTimer.setCooldown(1900);
                rcTimer.start();
                KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
            }
        } else if (state == State.HUB) {
            if (timer.hasFinished()) {
                if (timer.firstFinish()) {
                    mc.thePlayer.sendChatMessage("/play duels_sumo_duel");
                    state = State.QUEUE;
                }
                timer.setCooldown(1500);
                timer.start();
            }
        }

        if (state == State.INGAME) {
            if (leftClicker.isEnabled()) {
                if (Utils.Player.getClosestPlayer(autoClickerTrigger.getInput()) == null) {
                    leftClicker.disable();
                }
            } else if (!leftClicker.isEnabled()) {
                if (Utils.Player.getClosestPlayer(autoClickerTrigger.getInput()) != null) {
                    leftClicker.enable();
                }
            }
        }
    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof ClientChatReceivedEvent) {
            if (AntiShuffle.getUnformattedTextForChat(((ClientChatReceivedEvent) fe.getEvent()).message.getFormattedText()).contains("WINNER") ||
                    AntiShuffle.getUnformattedTextForChat(((ClientChatReceivedEvent) fe.getEvent()).message.getFormattedText()).contains("DRAW")) {
                matchEnd();
            }
        }
    }

    public void reQueue() {
        mc.thePlayer.sendChatMessage("/hub");
        state = State.HUB;
    }

    public enum State {
        INGAME,
        HUB,
        QUEUE,
        GAMEEND
    }


}