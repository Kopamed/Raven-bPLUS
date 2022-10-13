package keystrokesmod.client.module;

import keystrokesmod.client.clickgui.raven.components.CategoryComponent;
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
        CategoryComponent cc = Raven.clickGui.getCategoryComponent(moduleCategory);
        cc.initGui();
        cc.visable = true;
    }

    @Override
    public void onDisable() {
        Raven.clickGui.getCategoryComponent(moduleCategory).visable = false;
    }

    public ModuleCategory getGuiCategory() {
        return moduleCategory;
    }

}
