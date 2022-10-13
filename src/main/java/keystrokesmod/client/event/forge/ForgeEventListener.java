package keystrokesmod.client.event.forge;

import keystrokesmod.client.clickgui.raven.ClickGui;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderLivingEvent.Specials;
import net.minecraftforge.client.event.RenderPlayerEvent.Post;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Why? Other than the main few events that modules will be using, there's a ton
 * of other events they use. These are all Forge events, so there is no point in
 * creating tons of mixins to hook all of them. So instead, they are put into a
 * ForgeEvent and handled by Raven's event system. Also, the guiUpdate() in
 * modules is hooked here because it's the best place to do it.
 */
public class ForgeEventListener {

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.END) {
            Raven.eventBus.post(new keystrokesmod.client.event.impl.TickEvent());
            if (Utils.Player.isPlayerInGame())
                for (Module module : Raven.moduleManager.getModules())
                    if (Minecraft.getMinecraft().currentScreen instanceof ClickGui)
                        module.guiUpdate();
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent e) {
        if (e.phase == TickEvent.Phase.END) {
            if (Utils.Player.isPlayerInGame())
                for (Module module : Raven.moduleManager.getModules())
                    if (Minecraft.getMinecraft().currentScreen == null)
                        module.keybind();

            Raven.eventBus.post(new Render2DEvent());
        }

        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onHit(AttackEntityEvent e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onMouseUpdate(MouseEvent e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onRenderPlayerPost(Post e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onRenderPlayerPre(Pre e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onRenderLivingSpecialsPre(Specials.Pre e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onRenderPlayerEventPre(RenderLivingEvent e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

}
