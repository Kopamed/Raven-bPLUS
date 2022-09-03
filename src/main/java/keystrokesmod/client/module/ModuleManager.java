package keystrokesmod.client.module;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.modules.HUD;
import keystrokesmod.client.module.modules.client.GuiModule;
import keystrokesmod.client.module.modules.client.*;
import keystrokesmod.client.module.modules.combat.*;
import keystrokesmod.client.module.modules.config.ConfigSettings;
import keystrokesmod.client.module.modules.hotkey.*;
import keystrokesmod.client.module.modules.minigames.*;
import keystrokesmod.client.module.modules.minigames.Sumo.SumoBot;
import keystrokesmod.client.module.modules.minigames.Sumo.SumoClicker;
import keystrokesmod.client.module.modules.movement.*;
import keystrokesmod.client.module.modules.other.FakeChat;
import keystrokesmod.client.module.modules.other.MiddleClick;
import keystrokesmod.client.module.modules.other.NameHider;
import keystrokesmod.client.module.modules.other.WaterBucket;
import keystrokesmod.client.module.modules.player.*;
import keystrokesmod.client.module.modules.render.*;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.modules.world.ChatLogger;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public static boolean initialized;
    public final GuiModuleManager guiModuleManager;

    public ModuleManager() {
        this.guiModuleManager = new GuiModuleManager();

        if (initialized)
            return;

        addModule(new ChestStealer());
        addModule(new AutoArmour());
        addModule(new LeftClicker());
        addModule(new RightClicker());
        addModule(new AimAssist());
        addModule(new ClickAssist());
        addModule(new DelayRemover());
        addModule(new HitBox());
        addModule(new Reach());
        addModule(new Velocity());
        addModule(new Boost());
        addModule(new Fly());
        addModule(new InvMove());
        addModule(new KeepSprint());
        addModule(new NoSlow());
        addModule(new Sprint());
        addModule(new StopMotion());
        addModule(new Timer());
        addModule(new VClip());
        addModule(new AutoJump());
        addModule(new AutoPlace());
        addModule(new BedAura());
        addModule(new FallSpeed());
        addModule(new FastPlace());
        addModule(new Freecam());
        addModule(new NoFall());
        addModule(new SafeWalk());
        addModule(new AntiBot());
        addModule(new AntiShuffle());
        addModule(new Chams());
        addModule(new ChestESP());
        addModule(new Nametags());
        addModule(new PlayerESP());
        addModule(new Tracers());
        addModule(new HUD());
        addModule(new BridgeInfo());
        addModule(new DuelsStats());
        addModule(new MurderMystery());
        addModule(new SumoFences());
        addModule(new SlyPort());
        addModule(new FakeChat());
        addModule(new NameHider());
        addModule(new WaterBucket());
        //addModule(new AutoConfig());
        addModule(new Terminal());
        addModule(new GuiModule());
        addModule(new SelfDestruct());
        addModule(new ChatLogger());
        addModule(new BridgeAssist());
        addModule(new Fullbright());
        addModule(new UpdateCheck());
        addModule(new AutoHeader());
        addModule(new AutoTool());
        addModule(new Blocks());
        addModule(new Ladders());
        addModule(new Weapon());
        addModule(new Pearl());
        addModule(new Armour());
        addModule(new Healing());
        addModule(new Trajectories());
        addModule(new WTap());
        addModule(new BlockHit());
        addModule(new STap());
        addModule(new AutoWeapon());
        addModule(new BedwarsOverlay());

        addModule(new ShiftTap());
        addModule(new FPSSpoofer());

        addModule(new AutoBlock());
        addModule(new MiddleClick());
        addModule(new Projectiles());
        addModule(new FakeHud());
        addModule(new ConfigSettings());
        addModule(new SumoBot());
        addModule(new SumoClicker());
        addModule(new Parkour());
        // why ?
        // idk dude. you tell me why. I am pretty sure this was blowsy's work.

        initialized = true;
    }

    public void addModule(Module m) {
        modules.add(m);
    }

    public void removeModuleByName(String s) {
        Module m = getModuleByName(s);
        modules.remove(m);
        m.component.category.r3nd3r();
    }

    // prefer using getModuleByClazz();
    // ok might add in 1.0.18
    public Module getModuleByName(String name) {
        if (!initialized) return null;

        for (Module module : modules) {
            if (module.getName().replaceAll(" ", "").equalsIgnoreCase(name) || module.getName().equalsIgnoreCase(name))
                return module;
        }
        return null;
    }

    public Module getModuleByClazz(Class<? extends Module> c) {
        if (!initialized) return null;

        for (Module module : modules) {
            if (module.getClass().equals(c))
                return module;
        }
        return null;
    }

    public List<Module> getModules() {
        ArrayList<Module> allModules = new ArrayList<>(modules);
        try {
            allModules.addAll(Raven.configManager.configModuleManager.getConfigModules());
        } catch (NullPointerException ignored) {
        }
        try {
            allModules.addAll(guiModuleManager.getModules());
        } catch (NullPointerException ignored) {
        }
        return allModules;
    }

    public List<Module> getConfigModules() {
        List<Module> modulesOfC = new ArrayList<>();

        for (Module mod : getModules()) {
            if (!mod.isClientConfig()) {
                modulesOfC.add(mod);
            }
        }

        return modulesOfC;
    }

    public List<Module> getClientConfigModules() {
        List<Module> modulesOfCC = new ArrayList<>();

        for (Module mod : getModules()) {
            if (mod.isClientConfig()) {
                modulesOfCC.add(mod);
            }
        }

        return modulesOfCC;
    }

    public List<Module> getModulesInCategory(Module.ModuleCategory categ) {
        ArrayList<Module> modulesOfCat = new ArrayList<>();

        for (Module mod : getModules()) {
            if (mod.moduleCategory().equals(categ)) {
                modulesOfCat.add(mod);
            }
        }

        return modulesOfCat;
    }

    public void sort() {
        modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
    }

    public int numberOfModules() {
        return modules.size();
    }

    public void sortLongShort() {
        modules.sort(Comparator.comparingInt(o2 -> Utils.mc.fontRendererObj.getStringWidth(o2.getName())));
    }

    public void sortShortLong() {
        modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
    }

    public int getLongestActiveModule(FontRenderer fr) {
        int length = 0;
        for (Module mod : modules) {
            if (mod.isEnabled()) {
                if (fr.getStringWidth(mod.getName()) > length) {
                    length = fr.getStringWidth(mod.getName());
                }
            }
        }
        return length;
    }

    public int getBoxHeight(FontRenderer fr, int margin) {
        int length = 0;
        for (Module mod : modules) {
            if (mod.isEnabled()) {
                length += fr.FONT_HEIGHT + margin;
            }
        }
        return length;
    }

}
