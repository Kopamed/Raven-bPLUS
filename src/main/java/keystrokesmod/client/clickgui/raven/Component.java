package keystrokesmod.client.clickgui.raven;

public interface Component {
   public void draw();

   public void update(int mousePosX, int mousePosY);

   public void mouseDown(int x, int y, int b);

   public void mouseReleased(int x, int y, int m);

   public void keyTyped(char t, int k);

   public void setComponentStartAt(int n);

   public int getHeight();
}
