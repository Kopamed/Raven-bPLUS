package keystrokesmod.client.module.modules.combat;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.MouseManager;
import keystrokesmod.client.utils.Utils;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class ClickAssist extends Module {
    public static DescriptionSetting desc;
    public static SliderSetting chance;
    public static TickSetting L;
    public static TickSetting R;
    public static TickSetting blocksOnly;
    public static TickSetting weaponOnly;
    public static TickSetting onlyWhileTargeting;
    public static TickSetting above5;
    private Robot bot;
    private boolean engagedLeft;
    private boolean engagedRight;

    public ClickAssist() {
        super("ClickAssist", ModuleCategory.combat);

        this.registerSetting(desc = new DescriptionSetting("Boost your CPS."));
        this.registerSetting(chance = new SliderSetting("Chance", 80.0D, 0.0D, 100.0D, 1.0D));
        this.registerSetting(L = new TickSetting("Left click", true));
        this.registerSetting(weaponOnly = new TickSetting("Weapon only", true));
        this.registerSetting(onlyWhileTargeting = new TickSetting("Only while targeting", false));
        this.registerSetting(R = new TickSetting("Right click", false));
        this.registerSetting(blocksOnly = new TickSetting("Blocks only", true));
        this.registerSetting(above5 = new TickSetting("Above 5 cps", false));
    }

    public void onEnable() {
        try {
            this.bot = new Robot();
        } catch (AWTException var2) {
            this.disable();
        }

    }

    public void onDisable() {
        this.engagedLeft = false;
        this.engagedRight = false;
        this.bot = null;
    }

    @Subscribe
    public void onMouseUpdate(ForgeEvent fe) {
        if (fe.getEvent() instanceof MouseEvent) {
            MouseEvent ev = (MouseEvent) fe.getEvent();

            if (ev.button >= 0 && ev.buttonstate && chance.getInput() != 0.0D && Utils.Player.isPlayerInGame()) {
                if (mc.currentScreen == null && !mc.thePlayer.isEating() && !mc.thePlayer.isBlocking()) {
                    double ch;
                    if (ev.button == 0 && L.isToggled()) {
                        if (this.engagedLeft) {
                            this.engagedLeft = false;
                        } else {
                            if (weaponOnly.isToggled() && !Utils.Player.isPlayerHoldingWeapon()) {
                                return;
                            }

                            if (onlyWhileTargeting.isToggled()
                                    && (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null)) {
                                return;
                            }

                            if (chance.getInput() != 100.0D) {
                                ch = Math.random();
                                if (ch >= chance.getInput() / 100.0D) {
                                    this.fix(0);
                                    return;
                                }
                            }

                            this.bot.mouseRelease(16);
                            this.bot.mousePress(16);
                            this.engagedLeft = true;
                        }
                    } else if (ev.button == 1 && R.isToggled()) {
                        if (this.engagedRight) {
                            this.engagedRight = false;
                        } else {
                            if (blocksOnly.isToggled()) {
                                ItemStack item = mc.thePlayer.getHeldItem();
                                if (item == null || !(item.getItem() instanceof ItemBlock)) {
                                    this.fix(1);
                                    return;
                                }
                            }

                            if (above5.isToggled() && MouseManager.getRightClickCounter() <= 5) {
                                this.fix(1);
                                return;
                            }

                            if (chance.getInput() != 100.0D) {
                                ch = Math.random();
                                if (ch >= chance.getInput() / 100.0D) {
                                    this.fix(1);
                                    return;
                                }
                            }

                            this.bot.mouseRelease(4);
                            this.bot.mousePress(4);
                            this.engagedRight = true;
                        }
                    }

                }
                this.fix(0);
                this.fix(1);
            }
        }
    }

    private void fix(int t) {
        if (t == 0) {
            if (this.engagedLeft && !Mouse.isButtonDown(0)) {
                this.bot.mouseRelease(16);
            }
        } else if (t == 1 && this.engagedRight && !Mouse.isButtonDown(1)) {
            this.bot.mouseRelease(4);
        }

    }
}