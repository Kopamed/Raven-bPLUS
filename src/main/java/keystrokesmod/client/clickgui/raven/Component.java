package keystrokesmod.client.clickgui.raven;

public interface Component {
   void draw();

   void update(int mousePosX, int mousePosY);

   void mouseDown(int x, int y, int b);

   void mouseReleased(int x, int y, int m);

   void keyTyped(char t, int k);

   void setComponentStartAt(int n);

   int getHeight();
}
