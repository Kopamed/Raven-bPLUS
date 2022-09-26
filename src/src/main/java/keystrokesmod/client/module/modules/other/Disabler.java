package keystrokesmod.client.module.modules.other;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.util.EnumChatFormatting;

import java.util.LinkedList;

public class Disabler extends Module {
    public static DescriptionSetting warning, mmcSafeWarning1, mmcSafeWarning2;
    public static ComboSetting mode;
    public static DoubleSliderSetting mmcSafeDelay;
    public static

    LinkedList<Packet<?>> mmcPackets = new LinkedList<>();
    boolean mmc;

    public Disabler() {
        super("Disabler", ModuleCategory.other);

        this.registerSetting(warning = new DescriptionSetting("WILL BAN DONT USE"));
        this.registerSetting(mode = new ComboSetting("Mode", Mode.MMCSafe));
        this.registerSetting(
                mmcSafeWarning1 = new DescriptionSetting(EnumChatFormatting.GRAY + "Difference between min and max"));
        this.registerSetting(
                mmcSafeWarning2 = new DescriptionSetting(EnumChatFormatting.GRAY + "should be less than 5."));
        this.registerSetting(mmcSafeDelay = new DoubleSliderSetting("MMCSafe Delay", 77, 80, 10, 200, 1));

        this.registerSetting(new DescriptionSetting(
                EnumChatFormatting.BOLD.toString() + EnumChatFormatting.AQUA + "GHOST CLIENT WITH DISABLER OP"));

    }

    @Override
    public void onEnable() {
        mmcPackets.clear();
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        switch ((Mode) mode.getMode()) {
        case MMCSafe:
            if (e.isOutgoing() && !mmc) {
                if (e.getPacket() instanceof C00PacketKeepAlive) {
                    mmcPackets.add(e.getPacket());
                    e.cancel();
                }

                if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
                    mmcPackets.add(e.getPacket());
                    e.cancel();
                }

                int packetsCap = Utils.Java.randomInt(mmcSafeDelay.getInputMin(), mmcSafeDelay.getMax());

                while (mmcPackets.size() >= packetsCap) {
                    mmc = true;
                    mc.thePlayer.sendQueue.addToSendQueue(mmcPackets.poll());
                }
                mmc = false;
            }
            break;
        }
    }

    public enum Mode {
        MMCSafe
    }
}
