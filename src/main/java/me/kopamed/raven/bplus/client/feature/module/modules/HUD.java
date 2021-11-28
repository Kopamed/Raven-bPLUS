package me.kopamed.raven.bplus.client.feature.module.modules;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.helper.manager.ModuleManager;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class HUD extends Module {
   private me.kopamed.raven.bplus.client.visual.hud.HUD hud;

   public HUD() {
      super("HUD", "Displays which modules are active", ModuleCategory.Render);
      this.hud = new me.kopamed.raven.bplus.client.visual.hud.HUD();
   }

   @SubscribeEvent
   public void a(RenderTickEvent ev) {
      hud.draw(ev);
   }
}
