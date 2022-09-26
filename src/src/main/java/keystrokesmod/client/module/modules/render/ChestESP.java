package keystrokesmod.client.module.modules.render;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.Iterator;

public class ChestESP extends Module {
    public static SliderSetting a;
    public static SliderSetting b;
    public static SliderSetting c;
    public static TickSetting d;

    public ChestESP() {
        super("ChestESP", ModuleCategory.render);
        a = new SliderSetting("Red", 0.0D, 0.0D, 255.0D, 1.0D);
        b = new SliderSetting("Green", 0.0D, 0.0D, 255.0D, 1.0D);
        c = new SliderSetting("Blue", 255.0D, 0.0D, 255.0D, 1.0D);
        d = new TickSetting("Rainbow", false);
        this.registerSetting(a);
        this.registerSetting(b);
        this.registerSetting(c);
        this.registerSetting(d);
    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof RenderWorldLastEvent) {
            if (Utils.Player.isPlayerInGame()) {
                int rgb = d.isToggled() ? Utils.Client.rainbowDraw(2L, 0L)
                        : (new Color((int) a.getInput(), (int) b.getInput(), (int) c.getInput())).getRGB();
                Iterator var3 = mc.theWorld.loadedTileEntityList.iterator();

                while (true) {
                    TileEntity te;
                    do {
                        if (!var3.hasNext()) {
                            return;
                        }

                        te = (TileEntity) var3.next();
                    } while (!(te instanceof TileEntityChest) && !(te instanceof TileEntityEnderChest));

                    Utils.HUD.re(te.getPos(), rgb, true);
                }
            }
        }
    }
}
