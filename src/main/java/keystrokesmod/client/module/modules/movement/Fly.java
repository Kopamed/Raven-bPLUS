package keystrokesmod.client.module.modules.movement;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.TickEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;

public class Fly extends Module {
    private final Fly.VanFly vanFly = new VanFly();
    private final Fly.GliFly gliFly = new Fly.GliFly();
    public static DescriptionSetting dc;
    public static SliderSetting a;
    public static SliderSetting b;
    private static final String c1 = "Vanilla";
    private static final String c2 = "Glide";

    public Fly() {
        super("Fly", ModuleCategory.movement);
        this.registerSetting(a = new SliderSetting("Value", 1.0D, 1.0D, 2.0D, 1.0D));
        this.registerSetting(dc = new DescriptionSetting(Utils.md + c1));
        this.registerSetting(b = new SliderSetting("Speed", 2.0D, 1.0D, 5.0D, 0.1D));
    }

    public void onEnable() {
        switch ((int) a.getInput()) {
        case 1:
            this.vanFly.onEnable();
            break;
        case 2:
            this.gliFly.onEnable();
        }

    }

    public void onDisable() {
        switch ((int) a.getInput()) {
        case 1:
            this.vanFly.onDisable();
            break;
        case 2:
            this.gliFly.onDisable();
        }

    }

    @Subscribe
    public void onTick(TickEvent e) {
        switch ((int) a.getInput()) {
        case 1:
            this.vanFly.update();
            break;
        case 2:
            this.gliFly.update();
        }

    }

    public void guiUpdate() {
        switch ((int) a.getInput()) {
        case 1:
            dc.setDesc(Utils.md + c1);
            break;
        case 2:
            dc.setDesc(Utils.md + c2);
        }

    }

    class GliFly {
        boolean opf;

        public void onEnable() {
        }

        public void onDisable() {
            this.opf = false;
        }

        public void update() {
            if (Module.mc.thePlayer.movementInput.moveForward > 0.0F) {
                if (!this.opf) {
                    this.opf = true;
                    if (Module.mc.thePlayer.onGround) {
                        Module.mc.thePlayer.jump();
                    }
                } else {
                    if (Module.mc.thePlayer.onGround || Module.mc.thePlayer.isCollidedHorizontally) {
                        Fly.this.disable();
                        return;
                    }

                    double s = 1.94D * b.getInput();
                    double r = Math.toRadians(Module.mc.thePlayer.rotationYaw + 90.0F);
                    Module.mc.thePlayer.motionX = s * Math.cos(r);
                    Module.mc.thePlayer.motionZ = s * Math.sin(r);
                }
            }

        }
    }

    static class VanFly {
        private final float dfs = 0.05F;

        public void onEnable() {
        }

        public void onDisable() {
            if (Minecraft.getMinecraft().thePlayer == null)
                return;

            if (Minecraft.getMinecraft().thePlayer.capabilities.isFlying) {
                Minecraft.getMinecraft().thePlayer.capabilities.isFlying = false;
            }

            Minecraft.getMinecraft().thePlayer.capabilities.setFlySpeed(0.05F);
        }

        public void update() {
            Module.mc.thePlayer.motionY = 0.0D;
            Module.mc.thePlayer.capabilities.setFlySpeed((float) (0.05000000074505806D * b.getInput()));
            Module.mc.thePlayer.capabilities.isFlying = true;
        }
    }
}
