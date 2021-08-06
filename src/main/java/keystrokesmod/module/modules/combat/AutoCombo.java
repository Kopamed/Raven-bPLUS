package keystrokesmod.module.modules.combat;

import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import javax.management.Descriptor;
import java.util.concurrent.ThreadLocalRandom;

public class AutoCombo extends Module {
    public static ModuleSettingSlider comboMode;
    public static ModuleDesc comboModeDesc;
    public static ModuleSettingSlider minActionTicks, maxActionTicks;
    public static int tickLasts, currentTick;
    public static boolean comboing;

    public AutoCombo() {
        super("Auto Combo", category.combat, 0);
        this.registerSetting(minActionTicks = new ModuleSettingSlider("Min ticks: ", 1, 1, 20, 1));
        this.registerSetting(maxActionTicks = new ModuleSettingSlider("Man ticks: ", 5, 1, 20, 1));
        this.registerSetting(comboMode = new ModuleSettingSlider("Value: ", 1, 1, 3, 1));
        this.registerSetting(comboModeDesc = new ModuleDesc("Mode: BlockHit"));
    }


    public void guiUpdate() {
        comboModeDesc.setDesc(ay.md + ComboMode.values()[(int) (comboMode.getInput() - 1)]);
        ay.correctSliders(minActionTicks, maxActionTicks);
    }


    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if(comboing) {
            if(currentTick >= tickLasts){
                comboing = false;
                finishCombo();
            }else {
                currentTick++;
            }
        }



        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof Entity && Mouse.isButtonDown(0)) {
            Entity target = mc.objectMouseOver.entityHit;
            if (mc.thePlayer.getDistanceToEntity(target) <= 3) {
                if (!target.canAttackWithItem()) {
                    currentTick = 0;
                    if(minActionTicks.getInput() == maxActionTicks.getInput()){
                        tickLasts = ThreadLocalRandom.current().nextInt((int) minActionTicks.getInput()-1, (int) maxActionTicks.getInput());
                    } else {
                        tickLasts = ThreadLocalRandom.current().nextInt((int) minActionTicks.getInput(), (int) maxActionTicks.getInput());
                    }
                    comboing = true;
                    startCombo();
                }
            }
        }
    }

    private static void finishCombo() {
        if(ComboMode.values()[(int) (comboMode.getInput() - 1)] == ComboMode.BLOCKHIT) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        }
        else if(ComboMode.values()[(int) (comboMode.getInput() - 1)] == ComboMode.WTAP) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
        }
        else if(ComboMode.values()[(int) (comboMode.getInput() - 1)] == ComboMode.STAP) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
        }
    }

    private static void startCombo() {
        if(ComboMode.values()[(int) (comboMode.getInput() - 1)] == ComboMode.BLOCKHIT) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
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