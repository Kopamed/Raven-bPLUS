package keystrokesmod.client.clickgui.raven;

public abstract class Component {

    protected int x, y, x2, y2, width, height;
    public boolean visable = true;

    public void draw(int mouseX, int mouseY) {

    }

    public void clicked(int x, int y, int button) {

    }

    public void mouseReleased(int x, int y, int button) {

    }

    public void guiClosed() {

    }

    public boolean mouseDown(int x, int y, int button) {
        if (isMouseOver(x, y)) {
            clicked(x, y, button);
            return true;
        }
        return false;
    }

    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
        x2 = x + width;
        y2 = y + height;
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        x2 = x + width;
        y2 = y + height;
    }

    public boolean isMouseOver(int x, int y) {
        return
                (x > this.x)
                && (x < (this.x + width))
                && (y > this.y)
                && (y < (this.y + height));
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

    public int getY2() {
        return y2;
    }

    public int getX2() {
        return x2;
    }

    public void keyTyped(char t, int k) {

    }

    public void scroll(float i) {
        y += i;
        y2 += y;
    }

}
