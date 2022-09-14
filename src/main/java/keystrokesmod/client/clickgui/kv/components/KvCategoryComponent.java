package keystrokesmod.client.clickgui.kv.components;

import java.util.ArrayList;
import java.util.List;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.Module.ModuleCategory;
import keystrokesmod.client.utils.font.FontUtil;

public class KvCategoryComponent extends KvComponent {
    
    private final ModuleCategory category;
    private final List<ModuleCategory> childCategories;
    private final List<KvModuleComponent> modules;

    public KvCategoryComponent(ModuleCategory category) {
        this.category = category;
        this.childCategories = category.getChildCategories();
        modules = new ArrayList<KvModuleComponent>();
        for(Module module : Raven.moduleManager.getModulesInCategory(category)) {
            modules.add(new KvModuleComponent(module));
        }
    }
    
    
    @Override
    public void draw(int mouseX, int mouseY) {
        FontUtil.normal.drawStringWithShadow(category.getName(), x, y, Raven.kvCompactGui.currentCategory == this ? 0xFFFFFF00 : isMouseOver(mouseX, mouseY) ? 0xA000FF00 : 0xFF00FF00);
    }

    @Override
    public boolean mouseDown(int x, int y, int button) {
        if(button == 0 && isMouseOver(x, y)) {
            Raven.kvCompactGui.setCurrentCategory(this);
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        
    }

    @Override
    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
    

    @Override
    public boolean isMouseOver(int x, int y) {
        return                
                x > this.x
                && x < this.x + width
                && y > this.y
                && y < this.y + height;
    }

    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getX() {
        return x;
    }
    
    public ModuleCategory getCategory() {
        return category;
    }
    
    public List<ModuleCategory> getChildCategory() {
        return childCategories;
    }
    
    public List<KvModuleComponent> getModules() {
        return modules;
    }
}
