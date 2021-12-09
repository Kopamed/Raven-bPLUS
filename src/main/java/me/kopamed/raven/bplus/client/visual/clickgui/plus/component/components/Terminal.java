package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.PlusGui;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class Terminal extends Component {

    //TODO: Use siccors to curt text into the box
    // add programs
    // add drag panel
    // add close
    // add custom clr and shit to the config interfacae

    private boolean hasFocus = false;
    private final double heightRatio = 0.32;
    private final double widthRatio = 0.32;

    @Override
    public void update(int x, int y) {
        if(!this.isVisible())
            return;
        super.update(x, y);
    }

    @Override
    public void paint(FontRenderer fr) {
        if(!this.isVisible())
            return;
        Gui.drawRect((int)this.getX(), (int)this.getY(), (int)(this.getX() + this.getWidth()), (int)(this.getY() + this.getHeight()), this.getColor().getRGB());
        for(Component component: this.getComponents()){
            component.paint(fr);
        }
    }

    @Override
    public void mouseDown(int x, int y, int mb) {
        if(!this.isVisible())
            return;
        super.mouseDown(x, y, mb);
    }

    @Override
    public void mouseReleased(int x, int y, int mb) {
        if(!this.isVisible())
            return;
        super.mouseReleased(x, y, mb);
    }

    @Override
    public void onResize() {
        ScaledResolution sr = new ScaledResolution(Raven.client.getMc());
        this.setSize(widthRatio * sr.getScaledWidth(), heightRatio * sr.getScaledHeight());
        super.onResize();
    }
}
