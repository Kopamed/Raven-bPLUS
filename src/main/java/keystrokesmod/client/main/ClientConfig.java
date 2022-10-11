package keystrokesmod.client.main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import keystrokesmod.client.clickgui.raven.components.CategoryComponent;
import keystrokesmod.client.module.GuiModule;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.Module.ModuleCategory;
import keystrokesmod.client.module.modules.HUD;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.keystroke.KeyStroke;
import net.minecraft.client.Minecraft;

public class ClientConfig {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static boolean applyingConfig;
    // when you are coding the config manager and life be like
    // public static String
    // ip_token_discord_webhook_logger_spyware_malware_minecraft_block_hacker_sigma_miner_100_percent_haram_no_cap_m8_Kopamed(kv_is_still_sexier_tho)_is_sexy
    // = "https://imgur.com/a/hYd1023";
    // dude wtf bro i was not expecting that i opened that up on my school bus
    private final File cfgDir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "keystrokes");
    private final File cfgFile;
    private final String fileName = "clientconfig.kv";
    private JsonObject config;

    public ClientConfig() {
        if (!cfgDir.exists())
            cfgDir.mkdir();
        cfgFile = new File(cfgDir, fileName);
        if (!cfgFile.exists())
            try {
                cfgFile.createNewFile();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        else {
            final JsonParser jsonParser = new JsonParser();
            try (FileReader reader = new FileReader(cfgFile)) {
                final Object obj = jsonParser.parse(reader);
                config = (JsonObject) obj;
            } catch (JsonSyntaxException | ClassCastException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void applyConfig() {
        applyingConfig = true;
        try {
            Utils.URLS.hypixelApiKey = config.get("apikey").getAsString();
            Utils.URLS.pasteApiKey = config.get("pastekey").getAsString();
            loadClickGuiCoords(config.get("clickgui").getAsJsonObject().get("catPos").getAsJsonObject());
            Raven.configManager.loadConfigByName(config.get("currentconfig").getAsString());
            loadHudCoords(config.get("hud").getAsJsonObject());
            loadTerminalCoords(config.get("clickgui").getAsJsonObject());
            loadModules(config.get("modules").getAsJsonObject());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        applyingConfig = false;
    }

    public void applyKeyStrokeSettingsFromConfigFile() {
        try {
            final JsonObject data = config.get("keystrokes").getAsJsonObject();
            KeyStroke.x = data.get("x").getAsInt();
            KeyStroke.y = data.get("y").getAsInt();
            KeyStroke.enabled = data.get("enabled").getAsBoolean();
            KeyStroke.showMouseButtons = data.get("mbEnabled").getAsBoolean();
            KeyStroke.currentColorNumber = data.get("color").getAsInt();
            KeyStroke.outline = data.get("outline").getAsBoolean();
        } catch (final Throwable var4) {
            var4.printStackTrace();
        }
    }

    private JsonObject getClickGuiAsJson() {
        final JsonObject data = new JsonObject();
        data.add("catPos", getClickGuiPosAsJson());
        data.addProperty("terminalX", Raven.clickGui.terminal.getX());
        data.addProperty("terminalY", Raven.clickGui.terminal.getY());
        data.addProperty("width", Raven.clickGui.terminal.getWidth());
        data.addProperty("height", Raven.clickGui.terminal.getHeight());
        data.addProperty("hidden", Raven.clickGui.terminal.hidden); // lmao what cant u just check if the module is
                                                                    // tuned on
        data.addProperty("opened", Raven.clickGui.terminal.opened);
        return data;
    }

    public JsonObject getClickGuiPosAsJson() {
        final JsonObject data = new JsonObject();
        for (final CategoryComponent cat : Raven.clickGui.getCategoryList()) {
            final JsonObject catData = new JsonObject();
            catData.addProperty("X", cat.getX());
            catData.addProperty("Y", cat.getY());
            catData.addProperty("visable", cat.visable);
            catData.addProperty("opened", cat.categoryOpened);
            data.add(cat.categoryName.name(), catData);
        }
        return data;
    }

    public JsonObject getConfigAsJson() {
        final JsonObject data = new JsonObject();

        data.addProperty("apikey", Utils.URLS.hypixelApiKey);
        data.addProperty("pastekey", Utils.URLS.pasteApiKey);
        data.addProperty("currentconfig", Raven.configManager.getConfig().getName());
        data.add("keystrokes", getKeystrokeAsJson());
        data.add("hud", getHudAsJson());
        data.add("clickgui", getClickGuiAsJson());
        data.add("modules", getModulesAsJson());

        return data;
    }

    private JsonObject getHudAsJson() {
        final JsonObject data = new JsonObject();
        data.addProperty("hudX", HUD.getHudX());
        data.addProperty("hudY", HUD.getHudY());
        return data;
    }

    private JsonObject getKeystrokeAsJson() {
        final JsonObject data = new JsonObject();
        data.addProperty("x", KeyStroke.x);
        data.addProperty("y", KeyStroke.y);
        data.addProperty("enabled", KeyStroke.enabled);
        data.addProperty("mbEnabled", KeyStroke.showMouseButtons);
        data.addProperty("color", KeyStroke.currentColorNumber);
        data.addProperty("outline", KeyStroke.outline);
        return data;
    }

    private JsonObject getModulesAsJson() {
        final JsonObject data = new JsonObject();
        for (final Module m : Raven.moduleManager.getClientConfigModules())
            if (!(m instanceof GuiModule))
                data.add(m.getName(), m.getConfigAsJson());
        return data;
    }

    private void loadClickGuiCoords(JsonObject data) {
        for (final CategoryComponent cat : Raven.clickGui.getCategoryList()) {
            final JsonObject catData = data.get(cat.categoryName.name()).getAsJsonObject();
            cat.setCoords(catData.get("X").getAsInt(), catData.get("Y").getAsInt());
            cat.setOpened(catData.get("opened").getAsBoolean());
            if (cat.categoryName != ModuleCategory.category) {
                final boolean visable = (cat.categoryName == ModuleCategory.category)
                        || catData.get("visable").getAsBoolean();
                cat.visable = visable;
                Raven.moduleManager.guiModuleManager.getModuleByModuleCategory(cat.categoryName).setToggled(visable);
            }
        }
    }

    private void loadHudCoords(JsonObject data) {
        HUD.setHudX(data.get("hudX").getAsInt());
        HUD.setHudY(data.get("hudY").getAsInt());
    }

    private void loadModules(JsonObject data) {
        final List<Module> knownModules = new ArrayList<>(Raven.moduleManager.getClientConfigModules());
        for (final Module module : knownModules)
            if (data.has(module.getName()))
                module.applyConfigFromJson(data.get(module.getName()).getAsJsonObject());
            else
                module.resetToDefaults();
    }

    private void loadTerminalCoords(JsonObject data) {
        Raven.clickGui.terminal.setLocation(data.get("terminalX").getAsInt(), data.get("terminalY").getAsInt());
        Raven.clickGui.terminal.setSize(data.get("width").getAsInt(), data.get("height").getAsInt());
        Raven.clickGui.terminal.opened = data.get("opened").getAsBoolean();
        Raven.clickGui.terminal.hidden = data.get("hidden").getAsBoolean();
    }

    public void saveConfig() {
        if (applyingConfig)
            return;
        this.config = getConfigAsJson();

        try (PrintWriter out = new PrintWriter(new FileWriter(cfgFile))) {
            out.write(config.toString());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void updateKeyStrokesSettings() {
        config.add("keystrokes", getKeystrokeAsJson());
        saveConfig();
    }
}
