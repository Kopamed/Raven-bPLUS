package me.kopamed.raven.bplus;

import me.kopamed.raven.bplus.client.Raven;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.awt.*;
import java.io.IOException;

@Mod(modid = Main.MODID, version = Main.VERSION, clientSideOnly = true)
public class Main
{
    public static final String MODID = "particles";
    public static final String VERSION = "1";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws IOException, FontFormatException {
        new Raven();
    }
}
