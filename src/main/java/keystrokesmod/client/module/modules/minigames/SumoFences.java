package keystrokesmod.client.module.modules.minigames;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;

import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

public class SumoFences extends Module {
    public static DescriptionSetting a;
    public static DescriptionSetting d;
    public static SliderSetting b;
    public static SliderSetting c;
    private java.util.Timer t;
    private final List<String> m = Arrays.asList("Sumo", "Space Mine", "White Crystal");
    private IBlockState f;
    private static final List<BlockPos> f_p = Arrays.asList(new BlockPos(9, 65, -2), new BlockPos(9, 65, -1),
            new BlockPos(9, 65, 0), new BlockPos(9, 65, 1), new BlockPos(9, 65, 2), new BlockPos(9, 65, 3),
            new BlockPos(8, 65, 3), new BlockPos(8, 65, 4), new BlockPos(8, 65, 5), new BlockPos(7, 65, 5),
            new BlockPos(7, 65, 6), new BlockPos(7, 65, 7), new BlockPos(6, 65, 7), new BlockPos(5, 65, 7),
            new BlockPos(5, 65, 8), new BlockPos(4, 65, 8), new BlockPos(3, 65, 8), new BlockPos(3, 65, 9),
            new BlockPos(2, 65, 9), new BlockPos(1, 65, 9), new BlockPos(0, 65, 9), new BlockPos(-1, 65, 9),
            new BlockPos(-2, 65, 9), new BlockPos(-3, 65, 9), new BlockPos(-3, 65, 8), new BlockPos(-4, 65, 8),
            new BlockPos(-5, 65, 8), new BlockPos(-5, 65, 7), new BlockPos(-6, 65, 7), new BlockPos(-7, 65, 7),
            new BlockPos(-7, 65, 6), new BlockPos(-7, 65, 5), new BlockPos(-8, 65, 5), new BlockPos(-8, 65, 4),
            new BlockPos(-8, 65, 3), new BlockPos(-9, 65, 3), new BlockPos(-9, 65, 2), new BlockPos(-9, 65, 1),
            new BlockPos(-9, 65, 0), new BlockPos(-9, 65, -1), new BlockPos(-9, 65, -2), new BlockPos(-9, 65, -3),
            new BlockPos(-8, 65, -3), new BlockPos(-8, 65, -4), new BlockPos(-8, 65, -5), new BlockPos(-7, 65, -5),
            new BlockPos(-7, 65, -6), new BlockPos(-7, 65, -7), new BlockPos(-6, 65, -7), new BlockPos(-5, 65, -7),
            new BlockPos(-5, 65, -8), new BlockPos(-4, 65, -8), new BlockPos(-3, 65, -8), new BlockPos(-3, 65, -9),
            new BlockPos(-2, 65, -9), new BlockPos(-1, 65, -9), new BlockPos(0, 65, -9), new BlockPos(1, 65, -9),
            new BlockPos(2, 65, -9), new BlockPos(3, 65, -9), new BlockPos(3, 65, -8), new BlockPos(4, 65, -8),
            new BlockPos(5, 65, -8), new BlockPos(5, 65, -7), new BlockPos(6, 65, -7), new BlockPos(7, 65, -7),
            new BlockPos(7, 65, -6), new BlockPos(7, 65, -5), new BlockPos(8, 65, -5), new BlockPos(8, 65, -4),
            new BlockPos(8, 65, -3), new BlockPos(9, 65, -3));
    private final String c1;
    private final String c2;
    private final String c3;
    private final String c4;
    private final String c5;
    private int ymod;

    public SumoFences() {
        super("Sumo Fences", ModuleCategory.minigames);
        this.f = Blocks.oak_fence.getDefaultState();
        this.c1 = "Mode: Sumo Duel";
        this.c2 = "Oak fence";
        this.c3 = "Leaves";
        this.c4 = "Glass";
        this.c5 = "Barrier";
        this.registerSetting(a = new DescriptionSetting("Fences for Hypixel sumo."));
        this.registerSetting(b = new SliderSetting("Fence height", 4.0D, 1.0D, 16.0D, 1.0D));
        this.registerSetting(c = new SliderSetting("Block type", 1.0D, 1.0D, 4.0D, 1.0D));
        this.registerSetting(d = new DescriptionSetting(Utils.md + this.c2));
    }

    public void onEnable() {
        (this.t = new java.util.Timer()).scheduleAtFixedRate(this.t(), 0L, 500L);
    }

    public void onDisable() {
        if (this.t != null) {
            this.t.cancel();
            this.t.purge();
            this.t = null;
        }

        for (BlockPos p : f_p) {
            for (int i = 0; (double) i < b.getInput(); ++i) {
                BlockPos p2 = new BlockPos(p.getX(), p.getY() + i, p.getZ());
                if (mc.theWorld.getBlockState(p2).getBlock() == this.f) {
                    mc.theWorld.setBlockState(p2, Blocks.air.getDefaultState());
                }
            }
        }

    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof MouseEvent) {
            MouseEvent e = ((MouseEvent) fe.getEvent());

            if (e.buttonstate && (e.button == 0 || e.button == 1) && Utils.Player.isPlayerInGame() && this.is()) {
                MovingObjectPosition mop = mc.objectMouseOver;
                if (mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
                    int x = mop.getBlockPos().getX();
                    int z = mop.getBlockPos().getZ();

                    for (BlockPos pos : f_p) {
                        if (pos.getX() == x && pos.getZ() == z) {
                            e.setCanceled(true);
                            if (e.button == 0) {
                                Utils.Player.swing();
                            }

                            Mouse.poll();
                            break;
                        }
                    }
                }
            }
        }
    }

    public TimerTask t() {
        return new TimerTask() {
            public void run() {
                if (SumoFences.this.is()) {

                    for (BlockPos p : f_p) {
                        for (int i = 0; (double) i < b.getInput(); ++i) {
                            BlockPos p2 = new BlockPos(p.getX(), p.getY() + i + ymod, p.getZ());
                            if (Module.mc.theWorld.getBlockState(p2).getBlock() == Blocks.air) {
                                Module.mc.theWorld.setBlockState(p2, SumoFences.this.f);
                            }
                        }
                    }

                }
            }
        };
    }

    private boolean is() {
        if (Utils.Client.isHyp()) {

            for (String l : Utils.Client.getPlayersFromScoreboard()) {
                String s = Utils.Java.str(l);
                if (s.startsWith("Map:")) {
                    if (this.m.contains(s.substring(5))) {
                        ymod = s.contains("Fort Royale") ? 7 : 0;
                        return true;
                    }
                } else if (s.equals(this.c1)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void guiUpdate() {
        switch ((int) c.getInput()) {
        case 1:
            this.f = Blocks.oak_fence.getDefaultState();
            d.setDesc(Utils.md + this.c2);
            break;
        case 2:
            this.f = Blocks.leaves.getDefaultState();
            d.setDesc(Utils.md + this.c3);
            break;
        case 3:
            this.f = Blocks.glass.getDefaultState();
            d.setDesc(Utils.md + this.c4);
            break;
        case 4:
            this.f = Blocks.barrier.getDefaultState();
            d.setDesc(Utils.md + this.c5);
        }

    }
}
