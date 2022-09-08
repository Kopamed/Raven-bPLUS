package keystrokesmod.client.module.modules.player;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.TickEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import org.lwjgl.input.Mouse;

public class AutoPlace extends Module {
    public static DescriptionSetting ds;
    public static TickSetting a, b, top;
    public static SliderSetting c;
    private double lfd;
    private final int d = 25;
    private long l;
    private int f;
    private MovingObjectPosition lm;
    private BlockPos lp;

    public AutoPlace() {
        super("AutoPlace", ModuleCategory.player);
        this.registerSetting(ds = new DescriptionSetting("FD: FPS/80"));
        this.registerSetting(c = new SliderSetting("Frame delay", 8.0D, 0.0D, 30.0D, 1.0D));
        this.registerSetting(a = new TickSetting("Hold right", true));
        this.registerSetting(top = new TickSetting("Place on top & bottom", false));
    }

    public void guiUpdate() {
        if (this.lfd != c.getInput()) {
            this.rv();
        }

        this.lfd = c.getInput();
    }

    public void onDisable() {
        if (a.isToggled()) {
            this.rd(4);
        }

        this.rv();
    }

    @Subscribe
    public void onTick(TickEvent e) {
        Module fastPlace = Raven.moduleManager.getModuleByClazz(FastPlace.class);
        if (a.isToggled() && Mouse.isButtonDown(1) && !mc.thePlayer.capabilities.isFlying && fastPlace != null
                && !fastPlace.isEnabled()) {
            ItemStack i = mc.thePlayer.getHeldItem();
            if (i == null || !(i.getItem() instanceof ItemBlock)) {
                return;
            }

            this.rd(mc.thePlayer.motionY > 0.0D ? 1 : 1000);
        }

    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof DrawBlockHighlightEvent) {
            if (Utils.Player.isPlayerInGame()) {
                if (mc.currentScreen == null && !mc.thePlayer.capabilities.isFlying) {
                    ItemStack i = mc.thePlayer.getHeldItem();
                    if (i != null && i.getItem() instanceof ItemBlock) {
                        MovingObjectPosition m = mc.objectMouseOver;
                        if (m != null && m.typeOfHit == MovingObjectType.BLOCK
                                && ((m.sideHit != EnumFacing.UP && m.sideHit != EnumFacing.DOWN) || top.isToggled())) {
                            if (this.lm != null && (double) this.f < c.getInput()) {
                                ++this.f;
                            } else {
                                this.lm = m;
                                BlockPos pos = m.getBlockPos();
                                if (this.lp == null || pos.getX() != this.lp.getX() || pos.getY() != this.lp.getY()
                                        || pos.getZ() != this.lp.getZ()) {
                                    Block b = mc.theWorld.getBlockState(pos).getBlock();
                                    if (b != null && b != Blocks.air && !(b instanceof BlockLiquid)) {
                                        if (!a.isToggled() || Mouse.isButtonDown(1)) {
                                            long n = System.currentTimeMillis();
                                            if (n - this.l >= 25L) {
                                                this.l = n;
                                                if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, i,
                                                        pos, m.sideHit, m.hitVec)) {
                                                    Utils.Client.setMouseButtonState(1, true);
                                                    mc.thePlayer.swingItem();
                                                    mc.getItemRenderer().resetEquippedProgress();
                                                    Utils.Client.setMouseButtonState(1, false);
                                                    this.lp = pos;
                                                    this.f = 0;
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void rd(int i) {
        try {
            if (FastPlace.rightClickDelayTimerField != null) {
                FastPlace.rightClickDelayTimerField.set(mc, i);
            }
        } catch (IllegalAccessException | IndexOutOfBoundsException ignored) {
        }
    }

    private void rv() {
        this.lp = null;
        this.lm = null;
        this.f = 0;
    }
}
