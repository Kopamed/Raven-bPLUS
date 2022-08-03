package keystrokesmod.client.module.modules.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.HUD;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.utils.Utils;

public class FakeHud extends Module{
	
	private Gson gson = new Gson();
	public DescriptionSetting ds;

	public static List<Module> list = new ArrayList<Module>();	

	public FakeHud() {
		super("Fake Hud", ModuleCategory.client);
		enableAll();
		this.registerSetting(ds = new DescriptionSetting("Command: fakehud add/remove <name>"));
	}

	
	@Override
	public JsonObject getConfigAsJson(){
		JsonObject settings = new JsonObject();

		for(Setting setting : this.settings){
			if(setting != null) {
				JsonObject settingData = setting.getConfigAsJson();
				settings.add(setting.settingName, settingData);
			}
		}

		JsonObject data = new JsonObject();
		data.addProperty("enabled", enabled);
		data.addProperty("keycode", keycode);
		data.add("settings", settings);
		String str = "";
		for(Module m : list) {
			str = str + m.getName() + ",";
		}
		data.addProperty("list", str);

		return data;
	}
	
	@Override
	public void applyConfigFromJson(JsonObject data){
		try {
			this.keycode = data.get("keycode").getAsInt();
			setToggled(data.get("enabled").getAsBoolean());
			JsonObject settingsData = data.get("settings").getAsJsonObject();
			for (Setting setting : getSettings()) {
				if (settingsData.has(setting.getName())) {
					setting.applyConfigFromJson(
							settingsData.get(setting.getName()).getAsJsonObject()
							);
				}
			}
		} catch (NullPointerException ignored){

		} 
		try {
			String str = data.get("list").getAsString();
			String strl[] = str.split(",");
			List<Module> configList = new ArrayList<Module>();
			for(String s : strl) {
				addModule(s);
			}
		} catch (NullPointerException e) {
			list = new ArrayList<Module>() {{
				add(new Module("AntiFbi", ModuleCategory.client));
				add(new Module("AutoBackdoor", ModuleCategory.client));
				add(new Module("DupeMacro", ModuleCategory.client));
				add(new Module("GroomAura", ModuleCategory.client));
				add(new Module("Nostaff", ModuleCategory.client));
				add(new Module("SimonDisabler", ModuleCategory.client));
				add(new Module("Forceop", ModuleCategory.client));
				add(new Module("Twerk", ModuleCategory.client));
			}};	
		}
	}
	
	public void onEnable() {
		s();
	}

	public static void s() {
		if (HUD.positionMode == Utils.HUD.PositionMode.UPLEFT || HUD.positionMode == Utils.HUD.PositionMode.UPRIGHT) {
			sortShortLong();
		}
		else if(HUD.positionMode == Utils.HUD.PositionMode.DOWNLEFT || HUD.positionMode == Utils.HUD.PositionMode.DOWNRIGHT) {
			sortLongShort();
		}
	}

	public static List<Module> getModules() {
		int i = 0;
		List<Module> rlist = new ArrayList<Module>();
		for(Module module : Raven.moduleManager.getModules()) {
			if(module.isEnabled() && list.size() > i) {
				rlist.add(list.get(i));
				i++;
			}
		}
		return rlist;
	}

	public static void addModule(String str) {
		for(Module module : list) {
			if(module.getName().equals(str)) {
				return;
			}
		}
		Module m = new Module(str, ModuleCategory.client);
		m.enable();
		list.add(m);
		s();
	}

	public static boolean removeModule(String str) {
		for(Module module : list) {
			if(module.getName().equals(str)) {
				list.remove(module);
				s();
				return true;
			}
		}
		return false;
	}

	public void enableAll() {
		for(Module module : list) {
			module.enable();
		}
	}

	public static void sort() {
		list.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
	}


	public static void sortLongShort() {
		list.sort(Comparator.comparingInt(o2 -> Utils.mc.fontRendererObj.getStringWidth(o2.getName())));
	}

	public static void sortShortLong() {
		list.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
	}
}
