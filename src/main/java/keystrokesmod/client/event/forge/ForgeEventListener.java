package keystrokesmod.client.event.forge;

import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.main.Raven;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.Post;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.client.event.RenderLivingEvent.Specials;

public class ForgeEventListener {

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
    public void onRenderTick(TickEvent.RenderTickEvent e) {
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
    public void onRenderPlayerEventPost(Post e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onRenderPlayerEventPre(Pre e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

    @SubscribeEvent
    public void onRenderPlayerEventPre(Specials.Pre e) {
        Raven.eventBus.post(new ForgeEvent(e));
    }

}
