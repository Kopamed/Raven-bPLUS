package keystrokesmod.client.module;

import keystrokesmod.client.main.Raven;

public class GuiModule extends Module {

    private final ModuleCategory moduleCategory;

    public GuiModule(ModuleCategory moduleCategory, ModuleCategory parentCategory) {
        super(moduleCategory.getName(), parentCategory);
        this.moduleCategory = moduleCategory;
        hasBind = false;
        showInHud = false;
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
