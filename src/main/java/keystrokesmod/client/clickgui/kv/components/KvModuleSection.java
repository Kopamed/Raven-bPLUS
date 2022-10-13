package keystrokesmod.client.clickgui.kv.components;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.kv.KvCompactGui;
import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.clickgui.kv.KvSection;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.Module.ModuleCategory;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class KvModuleSection extends KvSection {

    private final List<KvCategoryComponent> topCategories;
    private List<KvCategoryComponent> currentCategories = new ArrayList<KvCategoryComponent>();
    private List<KvComponent> currentComponents = new ArrayList<KvComponent>(); // settings/modules
    private List<KvComponent> allCurrentComponents = new ArrayList<KvComponent>(); // settings/modules + categories
    public static final int padding = 5;
    public static int categoryScroll, moduleScroll;
    public KvModuleComponent openModule;
    public KvCategoryComponent currentCategory;
    public static KvModuleSection moduleSec;
    private int mouseX, mouseY, leftSectionWidth
    			,categoryX, categoryWidth, categoryY, categoryHeight, moduleX, moduleY, moduleWidth, moduleHeight;

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
    	this.mouseX = mouseX;
    	this.mouseY = mouseY;
        super.drawScreen(mouseX, mouseY, partialTicks);
        // vertical boarder
        Gui.drawRect(containerX + leftSectionWidth, containerY + (containerHeight / 6),
                containerX + (containerWidth / 4) + 1, containerY + containerHeight, Utils.Client.rainbowDraw(1, 0));

        // drawing categories
        int sf = new ScaledResolution(Raven.mc).getScaleFactor();

        GL11.glScissor(
                categoryX * sf, (categoryY - (containerHeight / 6))* sf, categoryWidth * sf, categoryHeight * sf);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        for (KvCategoryComponent categoryComponent : topCategories)
            categoryComponent.draw(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        // drawing modules/settings
        if(openModule != null) {
            openModule.drawOpen(mouseX, mouseY);
            return;
        }
        GL11.glScissor(moduleX * sf, (moduleY - (containerHeight / 6))* sf, moduleWidth * sf, moduleHeight * sf);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        for (KvComponent module : currentComponents)
            module.draw(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
    	//wtf did i do here i cant even remember
        if (super.mouseClicked(mouseX, mouseY, mouseButton))
            return true;
        for (KvComponent component : currentCategories)
			if (component.mouseDown(mouseX, mouseY, mouseButton))
					return true;
        if(openModule != null)
			try {
        	openModule.clicked(mouseButton, mouseX, mouseY);
        	return false;
        	} catch(Exception e) {
        		e.printStackTrace(); // lol
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
        int offset = 0;
        categoryX = containerX;
        categoryY = containerY + (containerHeight / 6);
        categoryWidth = containerWidth / 4;
        categoryHeight =  (containerHeight/6) * 5;
        for (KvCategoryComponent categoryComponent : topCategories) {
            categoryComponent.setCoords(containerX + padding, categoryY + padding + offset);
            categoryComponent.setDimensions(categoryWidth, (containerHeight / 12));

            currentCategories.add(categoryComponent);
            offset += categoryComponent.getHeight();

            if (categoryComponent.isOpen())
                for (KvCategoryComponent childCategoryComponent : categoryComponent.getChildCategorys()) {
                    childCategoryComponent.setCoords(containerX + (2 * padding), categoryY + padding + offset);
                    childCategoryComponent.setDimensions(categoryWidth, containerHeight / 12);

                    currentCategories.add(childCategoryComponent);
                    offset += childCategoryComponent.getHeight();
                }
        }
    }

    public void refreshModules() {
        currentComponents.clear();
        int xOffSet = 0;
        int yOffSet = 0;
        int iterations = 0;
        moduleX = containerX + leftSectionWidth + padding;
        moduleY = containerY + (containerHeight/6);
        moduleWidth = containerWidth - leftSectionWidth;
        moduleHeight = (containerY + containerHeight) - moduleY;
        for (KvModuleComponent module : currentCategory.getModules()) {
            iterations++;

            module.setCoords(moduleX + xOffSet, moduleY + padding + yOffSet);
            module.setDimensions((moduleWidth - (6 * padding)) / 3, (containerWidth - leftSectionWidth - (4 * padding)) / 3);

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
    	moduleScroll = 0;
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

    @Override
    public void mouseReleased(int x, int y, int button) {
    	for (KvComponent component : allCurrentComponents)
			component.mouseReleased(x, y, button);
    }

    @Override
    public void keyTyped(char t, int k) {
    	if(openModule != null)
			openModule.keyTyped(t, k);
    }

    @Override
    public void scroll(float i) {
    	//if((mouseX > containerX) && (mouseX < (containerX + containerWidth)) && (mouseY > containerY) && (mouseY < (containerY + containerHeight)))
		//	moduleScroll += i;
    	if(
    			((mouseX > categoryX) && (mouseX < (categoryX + categoryWidth)) && (mouseY > categoryY) && (mouseY < (categoryY + categoryHeight)))

    			&& ((currentCategories.get(currentCategories.size() - 1).getY() > categoryY) || (i > 0))) {
    			categoryScroll += i;

			if(categoryScroll > 0) categoryScroll = 0;

    	} else if(((mouseX > moduleX) && (mouseX < (moduleX + moduleWidth)) && (mouseY > moduleY) && (mouseY < (moduleY + moduleHeight)))
    			&& ((currentComponents.get(currentComponents.size() - 1 ).getY() > moduleY) || (i > 0))) {
    		moduleScroll += i;
    		if(moduleScroll > 0) moduleScroll = 0;
    		if((openModule != null) && (openModule.maxScroll() > moduleScroll)) moduleScroll = openModule.maxScroll();
    	}
    }

}
