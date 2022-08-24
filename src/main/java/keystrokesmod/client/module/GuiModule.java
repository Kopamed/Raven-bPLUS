package keystrokesmod.client.module;

import keystrokesmod.client.main.Raven;

public class GuiModule extends Module{
	
	private ModuleCategory moduleCategory;

	public GuiModule(ModuleCategory moduleCategory) {
		super(moduleCategory.name(), ModuleCategory.category);
		this.moduleCategory = moduleCategory;
		hasBind = false;
		showInHud = false;
		enabled = true;
	}
	
	@Override
	public void postApplyConfig() {
		Raven.clickGui.getCategoryComponent(moduleCategory).visable = enabled;
	}
	
	@Override
	public void onEnable() {
		Raven.clickGui.getCategoryComponent(moduleCategory).visable = true;
	}
	
	@Override
	public void onDisable() {
		Raven.clickGui.getCategoryComponent(moduleCategory).visable = false;
	}
	
	public ModuleCategory getGuiCategory() {
		return moduleCategory;
	}
	

}
