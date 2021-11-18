package me.kopamed.raven.bplus;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.keystroke.KeyStroke;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main
{
    public static final String MODID = "lunarkeystrokes";
    public static final String VERSION = "b1";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
     // new KeyStroke(); //todo
        new Raven();
    }
}
