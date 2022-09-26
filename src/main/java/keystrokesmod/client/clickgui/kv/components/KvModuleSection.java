package keystrokesmod.client.clickgui.kv.components;

import java.util.ArrayList;
import java.util.List;

import keystrokesmod.client.clickgui.kv.KvCompactGui;
import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.clickgui.kv.KvSection;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.Module.ModuleCategory;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.gui.Gui;

public class KvModuleSection extends KvSection {

    private final List<KvCategoryComponent> topCategories;
    private List<KvCategoryComponent> currentCategories = new ArrayList<KvCategoryComponent>();
    private List<KvComponent> currentComponents = new ArrayList<KvComponent>(); // settings/modules
    private List<KvComponent> allCurrentComponents = new ArrayList<KvComponent>(); // settings/modules + categories
    public static final int padding = 5;
    public KvModuleComponent openModule;
    public KvCategoryComponent currentCategory;
    public int leftSectionWidth;
    public static KvModuleSection moduleSec;

    public KvModuleSection() {
        super("Modules");
        topCategories = new ArrayList<>();
        Module.ModuleCategory[] values = Module.ModuleCategory.values();
        for (ModuleCategory moduleCategory : values)
            if (moduleCategory.getParentCategory() == ModuleCategory.category)
                topCategories.add(new KvCategoryComponent(moduleCategory));
        currentCategory = topCategories.get(2);
        moduleSec = this;
    }

    @Override
    public void refresh() {
        super.initGui(containerX, containerY, containerWidth, containerHeight);
        refreshCategories();
        refreshModules();
        refreshList();
        leftSectionWidth = KvCompactGui.containerWidth/4;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        // vertical boarder
        Gui.drawRect(containerX + leftSectionWidth, containerY + (containerHeight / 6),
                containerX + (containerWidth / 4) + 1, containerY + containerHeight, Utils.Client.rainbowDraw(1, 0));

        // drawing categories
        // note i need to do this
        // note i hate scissor
        // GL11.glPushMatrix();
        // GL11.glEnable(GL11.GL_SCISSOR_TEST);
        // GL11.glScissor(mouseY, mouseY, mouseY, mouseY);
        for (KvCategoryComponent categoryComponent : topCategories)
            categoryComponent.draw(mouseX, mouseY);

        // drawing modules/settings
        if(openModule != null) { //eclipse wouldnt let me scroll to the end of the line :sob:
            openModule.drawOpen();
            return;
        }
        for (KvComponent module : currentComponents)
            module.draw(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (super.mouseClicked(mouseX, mouseY, mouseButton))
            return true;
        if(openModule != null)
			try {
        	openModule.clicked(mouseButton, mouseX, mouseY);
        	return false;
        	} catch(Exception e) {
        		e.printStackTrace();
        	}
        for (KvComponent component : allCurrentComponents)
            if (component.mouseDown(mouseX, mouseY, mouseButton))
                return true;
        return false;
    }

    public void setCurrentCategory(KvCategoryComponent cc) {
        currentCategory = cc;
        refreshModules();
        refreshList();
    }

    public void refreshCategories() {
        currentCategories.clear();
        int categoryHeight = 0;
        for (KvCategoryComponent categoryComponent : topCategories) {
            categoryComponent.setCoords(containerX + padding,containerY + (containerHeight / 6) + padding + categoryHeight);
            categoryComponent.setDimensions(containerWidth / 4, containerHeight / 12);

            currentCategories.add(categoryComponent);
            categoryHeight += categoryComponent.getHeight();

            if (categoryComponent.isOpen())
                for (KvCategoryComponent childCategoryComponent : categoryComponent.getChildCategorys()) {
                    childCategoryComponent.setCoords(containerX + (2 * padding),containerY + (containerHeight / 6) + padding + categoryHeight);
                    childCategoryComponent.setDimensions(containerWidth / 4, containerHeight / 12);

                    currentCategories.add(childCategoryComponent);
                    categoryHeight += childCategoryComponent.getHeight();
                }
        }
    }

    public void refreshModules() {
        currentComponents.clear();
        int xOffSet = 0;
        int yOffSet = 0;
        int iterations = 0;
        for (KvModuleComponent module : currentCategory.getModules()) {
            iterations++;

            module.setCoords(containerX + leftSectionWidth + padding + xOffSet,containerY + (containerHeight / 6) + padding + yOffSet);
            module.setDimensions((containerWidth - leftSectionWidth - (6 * padding)) / 3, (containerWidth - leftSectionWidth - (4 * padding)) / 3);

            xOffSet += (containerWidth - (containerWidth / 4)) / 3;
            currentComponents.add(module);

            if (iterations == 3) {
                iterations = 0;
                xOffSet = 0;
                yOffSet += (containerWidth - (containerWidth / 4)) / 3;
            }
        }
    }

    public void setOpenmodule(KvModuleComponent component) {
    	openModule = component;
    	if(openModule != null)
        component.setBoxCoords(
        			leftSectionWidth + KvCompactGui.containerX,
                    KvCompactGui.horizontalBoarderY,
                    KvCompactGui.containerWidth - leftSectionWidth,
                    (KvCompactGui.containerY + KvCompactGui.containerHeight) - (KvCompactGui.horizontalBoarderY));
    }

    public void refreshList() {
        allCurrentComponents.clear();
        allCurrentComponents.addAll(currentCategories);
        allCurrentComponents.addAll(currentComponents);
    }

}
