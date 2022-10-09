package keystrokesmod.client.clickgui.kv.components;

import java.util.ArrayList;
import java.util.List;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.Module.ModuleCategory;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.gui.Gui;

public class KvCategoryComponent extends KvComponent {

    private final ModuleCategory category;
    private final List<KvCategoryComponent> childCategories;
    private final List<KvModuleComponent> modules;
	private boolean open;
	private int actualY;

    public KvCategoryComponent(ModuleCategory category) {
        this.category = category;

        childCategories = new ArrayList<KvCategoryComponent>();
        for(ModuleCategory moduleCategory : category.getChildCategories())
			childCategories.add(new KvCategoryComponent(moduleCategory));

        modules = new ArrayList<KvModuleComponent>();
        for(Module module : Raven.moduleManager.getModulesInCategory(category))
			modules.add(new KvModuleComponent(module));
    }


    @Override
    public void draw(int mouseX, int mouseY) {
    	y = (int) (actualY + KvModuleSection.categoryScroll);
		int color = KvModuleSection.moduleSec.currentCategory == this ? 0xFFFFFF00: isMouseOver(mouseX, mouseY) ? 0xA000FF00 : 0xFF00FF00;
		FontUtil.normal.drawStringWithShadow(category.getName(), x, y, color);

		if (!childCategories.isEmpty()) {
			Gui.drawRect(x - 3, y - 2, x - 2, y + FontUtil.normal.getHeight(), color);
			if (open)
				for (KvCategoryComponent category : childCategories)
					category.draw(mouseX, mouseY);
		}
    }

    @Override
	public void clicked(int button, int x, int y) {
    	switch(button) {
    		case 0:
    			KvModuleSection.moduleSec.setOpenmodule(null);
    			KvModuleSection.moduleSec.setCurrentCategory(this);
    			KvModuleSection.moduleSec.setCurrentCategory(this);
    			break;
    		case 1:
				if (!childCategories.isEmpty()) {
					open = !open;
					Raven.kvCompactGui.initGui();
				}
				break;
    	}
    }

    @Override
	public void setCoords(int x, int y) {
        this.x = x;
        this.actualY = y;
    }

	public boolean isOpen() {
		return open;
	}

    public ModuleCategory getCategory() {
        return category;
    }

	public List<KvCategoryComponent> getChildCategorys() {
        return childCategories;
    }

    public List<KvModuleComponent> getModules() {
        return modules;
    }
}
