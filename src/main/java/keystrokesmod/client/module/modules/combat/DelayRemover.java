package keystrokesmod.client.module.modules.combat;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.GameLoopEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class DelayRemover extends Module {
    public static DescriptionSetting desc;

    private final Field leftClickCounterField;

    public DelayRemover() {
        super("Delay Remover", ModuleCategory.combat);
        withEnabled(true);

        this.registerSetting(desc = new DescriptionSetting("Gives you 1.7 hitreg."));
        this.leftClickCounterField = ReflectionHelper.findField(Minecraft.class, "field_71429_W", "leftClickCounter");
        if (this.leftClickCounterField != null)
            this.leftClickCounterField.setAccessible(true);
    }

    @Override
    public boolean canBeEnabled() {
        return this.leftClickCounterField != null;
    }

    @Subscribe
    public void onGameLoop(GameLoopEvent event) {
        if (Utils.Player.isPlayerInGame() && this.leftClickCounterField != null) {
            if (!mc.inGameHasFocus || mc.thePlayer.capabilities.isCreativeMode) {
                return;
            }

            try {
                this.leftClickCounterField.set(mc, 0);
            } catch (IllegalAccessException | IndexOutOfBoundsException ex) {
                ex.printStackTrace();
                this.disable();
            }
        }
    }
}
