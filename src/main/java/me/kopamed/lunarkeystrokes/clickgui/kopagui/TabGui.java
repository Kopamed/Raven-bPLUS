package me.kopamed.lunarkeystrokes.clickgui.kopagui;

import me.kopamed.lunarkeystrokes.utils.Timer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.util.concurrent.ScheduledFuture;

public class TabGui extends GuiScreen {
    private ScheduledFuture sf;
    private Timer slideProgress;

    private ScaledResolution sr;

    //public static ArrayList<CategoryTab> categories;
    int guiX = 0, guiY = 0, width, height, topBarHeigt = 16, margin = 4, closeButtonSize = 6;
    public static final int ravenColour = 0xffb317ef;
    boolean leftDown = false, rightDown = false;

    int mouseStartX, mouseStartY, guiOnStartX, guiOnStartY;

    int buttonHeights = 16;

    //List<CategoryComponent> categories = new ArrayList<>();
   /* public TabGui(){
        //categories = new ArrayList<CategoryTab>();
        int maxWidth = 0;
        int startAt = margin *3 + 1 + topBarHeigt;
        for(Module.category cat : Module.category.values()){
            //if(fontRendererObj.getStringWidth(cat + "") > maxWidth)
               // maxWidth = fontRendererObj.getStringWidth(cat + "");

            CategoryComponent categoryComponent = new CategoryComponent(cat, this.fontRendererObj);
            categoryComponent.setY(startAt);
            this.categories.add(categoryComponent);
            startAt += buttonHeights + margin;

        }

        if(maxWidth != 0){
            for(CategoryComponent cat : this.categories){
                cat.setWidth(maxWidth + margin *2);
            }
        }
    }

    public void initMain() {
        //initGui();
        (this.slideProgress = new Timer(250F)).start();
    }

    public void initGui() {
        super.initGui();
        this.sr = new ScaledResolution(this.mc);
        width = (int)(sr.getScaledWidth()*0.5);
        height = (int)(sr.getScaledHeight()*0.5);
        //guiX = (int)(sr.getScaledWidth()*0.5 - width/2);
        //guiY = (int)(sr.getScaledHeight()*0.5 - height/2);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // main rect
        compute(mouseX, mouseY);
        int slidePos = this.slideProgress.getValueInt((guiY + height)*-1, guiY, 1);
        int prevY = guiY;
        guiY = slidePos;
        drawRect(guiX, guiY, guiX + width, guiY + height, 0xff262626);

        int miniLengt = fontRendererObj.getStringWidth(Ravenbplus.version) / 2;
        int nameLengt = fontRendererObj.getStringWidth(Ravenbplus.clientName);
        int centerdY = (topBarHeigt - fontRendererObj.FONT_HEIGHT)/2;
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        drawString(fontRendererObj, Ravenbplus.version, (guiX +  margin + topBarHeigt + nameLengt)*2, (guiY + margin + centerdY + fontRendererObj.FONT_HEIGHT/2)*2, 0xff464a50);
        GL11.glPopMatrix();

        drawString(fontRendererObj, Ravenbplus.clientName, (guiX +  margin + topBarHeigt), (guiY + margin + centerdY), ravenColour);
        //


        // drawing a colourd
        drawHorizontalLine(guiX + margin, guiX + width - margin, guiY + margin*2 + topBarHeigt, ravenColour);
        drawRect(guiX + width - margin - closeButtonSize - (topBarHeigt - closeButtonSize)/2, guiY + margin + (topBarHeigt - closeButtonSize)/2, guiX + width - margin - (topBarHeigt - closeButtonSize)/2, guiY + margin +closeButtonSize+ (topBarHeigt - closeButtonSize)/2, ravenColour);



        for(CategoryComponent cat : this.categories){
            cat.render();
        }


        Minecraft.getMinecraft().getTextureManager().bindTexture(mResourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(guiX + margin, guiY + margin, 0, 0, topBarHeigt, topBarHeigt, topBarHeigt, topBarHeigt);
        guiY = prevY;
    }

    private void compute(int x, int y) {
        if(leftDown) {
            int devianceX = mouseStartX - x;
            int devianceY = mouseStartY - y;

            guiX = guiOnStartX - devianceX;
            guiY = guiOnStartY - devianceY;

            if(guiX < 0)
                guiX = 0;
            if(guiY < 0)
                guiY = 0;

            if(guiX + width > sr.getScaledWidth())
                guiX = sr.getScaledWidth() - width;
            if(guiY + height > sr.getScaledHeight())
                guiY = sr.getScaledHeight() - height;

            update();
        }
        update();

    }

    private void update() {
        int startAt = margin *3 + 1 + topBarHeigt + guiY;

        for(CategoryComponent cat : this.categories){
            cat.setX(guiX + margin);

            cat.setY(startAt);
            startAt += buttonHeights + margin;
        }
    }

    private boolean insideDragable(int x, int y){
        if(x >= guiX && x <= guiX + width && y >= guiY && y <= guiY + margin*2 + topBarHeigt)
            return true;
        return false;
    }

    public void mouseClicked(int x, int y, int mouseButton) throws IOException {
        if(mouseButton == 0){
            if(insideKillButton(x, y)){
                    mc.displayGuiScreen(null);
            }else if(insideDragable(x, y)) {
                leftDown = true;
                mouseStartX = x;
                mouseStartY = y;
                guiOnStartX = guiX;
                guiOnStartY = guiY;
            }
        } else if(mouseButton == 1){
            rightDown = true;
        }
    }

    private boolean insideKillButton(int x, int y) {
        if(x >= guiX + width - margin - closeButtonSize - (topBarHeigt - closeButtonSize)/2 && x <= guiX + width - margin - (topBarHeigt - closeButtonSize)/2 && y >= guiY + margin + (topBarHeigt - closeButtonSize)/2 && y <= guiY + margin +closeButtonSize+ (topBarHeigt - closeButtonSize)/2)
            return true;
        return false;
    }

    public void mouseReleased(int x, int y, int mouseButton) {
        if(mouseButton == 0){
            leftDown =  false;
        } else if(mouseButton == 1){
            rightDown = false;
        }
    }

    public void keyTyped(char t, int k) {
        System.out.println("Pressed " + t + " with int " + k);
        if(k == Keyboard.KEY_ESCAPE){
            mc.displayGuiScreen(null);
        }
    }

    public void actionPerformed(GuiButton b) {

    }

    public void onGuiClosed() {
        super.onGuiClosed();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }*/
}

// https://discordtokenloggeripgrabber.com haha get fucked. i am gonna recode the whole tabgui because i just got brain damage when i looked at my code. like i am talking "lainux is a windows application" levels of brain damage
//send help reeeeeeeeeeeeeeeeeeeee
