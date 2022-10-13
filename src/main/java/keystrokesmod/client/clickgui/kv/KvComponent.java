package keystrokesmod.client.clickgui.kv;

public abstract class KvComponent {

    protected int x, y, width, height;

    public void draw(int mouseX, int mouseY) {

    }

	public void clicked(int button, int x, int y) {

	}

    public boolean mouseDown(int x, int y, int button) {
		if (isMouseOver(x, y)) {
			clicked(button, x, y);
			return true;
		}
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

    public void keyTyped(char t, int k) {

    }

	public void scroll(float i) {
		y += i;
	}

}
