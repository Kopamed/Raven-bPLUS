package me.kopamed.raven.bplus.helper.manager;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.BindMode;
import me.kopamed.raven.bplus.client.feature.module.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Iterator;

public class KeybindManager {
    public KeybindManager(){
    }

    @SubscribeEvent
    public void help(TickEvent.ClientTickEvent e) {
        ModuleManager moduleManager = Raven.client.getModuleManager();
        int moduleNumber = moduleManager.getModules().size();

        for(int i = 0; i < moduleNumber; i++){
            Module module = moduleManager.getModules().get(i);

            if(module.canToggle() && module.getName().equals("Gui"))
                module.keybind();


            //if(module.isEnabled())
                //System.out.println(module.getName());
        }
        for (int i = 0; i < Keyboard.getKeyCount(); i++){
            if(Keyboard.isKeyDown(i))
                System.out.println(i + " " + Keyboard.getKeyName(i));
        }

        System.out.println("=============================");
    }
}
