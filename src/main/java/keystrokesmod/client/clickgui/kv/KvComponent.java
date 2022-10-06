package keystrokesmod.client.clickgui.kv;

public class KvComponent {
    
    protected int x, y, width, height;
    protected boolean isHovering;

    public void draw(int mouseX, int mouseY) {
        isHovering = isMouseOver(mouseX, mouseY);
    }

    public boolean mouseDown(int x, int y, int button) {
        return false;
    }

    public void mouseReleased(int x, int y, int button) {
        
    }

    
    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public boolean isMouseOver(int x, int y) {
        return                
                x > this.x
                && x < this.x + width
                && y > this.y
                && y < this.y + height;
    }

    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    
}
