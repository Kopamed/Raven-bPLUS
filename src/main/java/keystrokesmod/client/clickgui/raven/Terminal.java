package keystrokesmod.client.clickgui.raven;

import java.awt.Color;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.utils.CoolDown;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Terminal {
    private int x;
    private int y;
    private int width;
    private int height;
    private final int barHeight;
    private final int border;
    private final int minWidth;
    private final int minHeight;
    private final int resizeButtonSize;
    public boolean opened;
    public boolean hidden;
    private boolean resizing;
    private boolean focused;
    private final CoolDown keyDown = new CoolDown(500);

    private int backCharsCursor;

    public final int[] acceptableKeycodes = { 41, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 26, 27, 39, 40, 0, 51, 52,
            53, 41, 145, 144, 147, 146, 57, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 30, 31, 32, 33, 34, 35, 36, 37, 38,
            44, 45, 46, 47, 48, 49, 50 };

    private final Minecraft mc;
    private final FontRenderer fr;

    private String inputText = "";
    private static final ArrayList<String> out = new ArrayList<>();
    private final String prefix = "$ ";

    private boolean dragging;
    private double windowStartDragX;
    private double windowStartDragY;
    private double mouseStartDragX;
    private double mouseStartDragY;

    public Terminal() {
        this.x = 0;
        this.y = 0;
        this.width = 300;
        this.minWidth = 184;
        this.height = 214;
        this.minHeight = 67;
        this.barHeight = 13;
        this.mc = Minecraft.getMinecraft();
        this.fr = mc.fontRendererObj;
        this.border = 2;
        this.resizeButtonSize = 10;
    }

    public static void clearTerminal() {
        out.clear();
    }

    public void show() {
        this.hidden = false;
    }

    public void hide() {
        this.hidden = true;
    }

    public void draw() {
        if (hidden)
            return;

        // this.barHeight = mc.displayHeight / 90;
        double desiredTextSize = barHeight * 0.65;
        double scaleFactor = desiredTextSize / fr.FONT_HEIGHT;
        double coordFactor = 1 / scaleFactor;
        double margin = (int) ((barHeight - desiredTextSize) * 0.8);
        float textY = (float) ((y + margin) * coordFactor);
        float textX = (float) ((x + margin) * coordFactor);
        float buttonX = (float) (((x + width) - margin - fr.getStringWidth(opened ? "-" : "+")) * coordFactor);

        int cursorX = 0;
        int cursorY = 0;

        float outStartX = (float) ((x + margin + border));
        float outFinishX = (float) (((x + width) - (margin + border)));
        float outStartY = (float) ((y + barHeight + margin));
        float outFinishY = (float) (((y + height) - margin - border));

        float maxTextWidth = outFinishX - outStartX;
        int maxLines = Math.floorDiv((int) (outFinishY - outStartY), (int) (desiredTextSize + margin));
        int linesPrinted = 0;

        cursorX = (int) outStartX;

        outStartX *= coordFactor;
        outFinishX *= coordFactor;
        outStartY *= coordFactor;
        outFinishY *= coordFactor;

        Gui.drawRect(x, y, x + width, y + barHeight, 0xff2D3742);

        if (opened) {
            Gui.drawRect(x, y + barHeight, x + width, y + height, new Color(51, 51, 51, 210).getRGB());

            Gui.drawRect(x, y + barHeight, x + border, y + height, 0xff2D3742);

            Gui.drawRect(x + width, y + barHeight, (x + width) - border, y + height, 0xff2D3742);

            Gui.drawRect(x, (y + height) - border, x + width, y + height, 0xff2D3742);

            Gui.drawRect((x + width) - resizeButtonSize, (y + height) - resizeButtonSize, x + width, y + height,
                    new Color(79, 104, 158).getRGB());
        }

        GL11.glPushMatrix();
        GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
        fr.drawString("Terminal", textX, textY, 0xffffff, false);

        fr.drawString(opened ? "-" : "+", buttonX, textY, 0xffffff, false);

        if (opened) {
            ArrayList<String> currentOut = new ArrayList<>(out);
            currentOut.add(prefix + inputText);
            String currentLine;
            ArrayList<String> finalOut = new ArrayList<>();

            int end = currentOut.size() >= maxLines ? currentOut.size() - maxLines : 0;
            for (int j = currentOut.size() - 1; j >= end; j--) {
                currentLine = currentOut.get(j);
                String[] splitUpLine = splitUpLine(currentLine, maxTextWidth, scaleFactor);
                for (int i = splitUpLine.length - 1; i >= 0; i--) {
                    if (linesPrinted >= maxLines)
                        break;
                    finalOut.add(splitUpLine[i]);
                    linesPrinted++;
                }
            }

            String[] inputTextLineSplit = splitUpLine(
                    prefix + inputText.substring(0, inputText.length() - backCharsCursor), maxTextWidth, scaleFactor);
            String finalInputLine = inputTextLineSplit[inputTextLineSplit.length - 1];
            cursorX += (int) (fr.getStringWidth(finalInputLine) * scaleFactor);

            for (int j = finalOut.size() - 1; j >= 0; j--) {
                currentLine = finalOut.get(j);
                int topMargin = (int) (((finalOut.size() - 1 - j) * (desiredTextSize + margin) * coordFactor)
                        + outStartY);

                fr.drawString(currentLine, (int) outStartX, topMargin, new Color(32, 194, 14).getRGB());
                if (currentLine.startsWith(finalInputLine))
                    cursorY = (int) (topMargin / coordFactor);
            }

        }
        GL11.glPopMatrix();
        if (opened)
            Gui.drawRect(cursorX, cursorY, cursorX + 1, (int) (cursorY + desiredTextSize), 0xffffffff);
    }

    private String[] splitUpLine(String currentLine, float maxTextWidth, double scaleSize) {
        if ((fr.getStringWidth(currentLine) * scaleSize) <= maxTextWidth)
            return new String[] { currentLine };
        for (int i = currentLine.length(); i >= 0; i--) {
            String newLine = currentLine.substring(0, i);
            if ((fr.getStringWidth(newLine) * scaleSize) <= maxTextWidth)
                return mergeArray(new String[] { newLine },
                        splitUpLine(currentLine.substring(i), maxTextWidth, scaleSize));
        }
        return new String[] { "" };
    }

    public static void print(String message) {
        out.add(message);
    }

    public static String[] mergeArray(String[] arr1, String[] arr2) {
        return ArrayUtils.addAll(arr1, arr2);
    }

    public void update(int x, int y) {
        if (hidden)
            return;
        if (dragging) {
            this.x = (int) (windowStartDragX + (x - mouseStartDragX));
            this.y = (int) (windowStartDragY + (y - mouseStartDragY));
        } else if (resizing) {
            int newWidth = Math.max(x, this.x + minWidth) - this.x;
            int newHeight = Math.max(y, this.y + minHeight) - this.y;
            this.width = newWidth;
            this.height = newHeight;
        }
    }

    public void mouseDown(int x, int y, int b) {
        focused = false;
        if (hidden)
            return;
        if (overToggleButton(x, y) && (b == 0))
            this.opened = !opened;
        else if (overBar(x, y)) {
            if (b == 0) {
                dragging = true;
                mouseStartDragX = x;
                mouseStartDragY = y;
                windowStartDragX = this.x;
                windowStartDragY = this.y;
            } else if (b == 1)
                this.opened = !opened;
        } else if (overResize(x, y) && (b == 0))
            this.resizing = true;
        else if (overWindow(x, y) && (b == 0))
            this.focused = true;
    }

    public void mouseReleased(int x, int y, int m) {
        if (hidden)
            return;
        if (dragging)
            dragging = false;
        else if (resizing)
            resizing = false;
    }

    public void keyTyped(char t, int k) {
        if (!focused)
            return;

        switch (k) {
        case 28:
            out.add(prefix + inputText);
            proccessInput();
            inputText = "";
            backCharsCursor = 0;
            break;
        case 14:
            if (inputText.substring(0, inputText.length() - backCharsCursor).length() > 0)
                if (backCharsCursor == 0)
                    inputText = inputText.substring(0, inputText.length() - 1);
                else {
                    String deletable = inputText.substring(0, inputText.length() - backCharsCursor);
                    String appendable = inputText.substring(inputText.length() - backCharsCursor);
                    if (deletable.length() > 0)
                        deletable = deletable.substring(0, deletable.length() - 1);
                    inputText = deletable + appendable;
                }
            break;
        case 15:
            addCharToInput("    ");
            break;
        case 203:
            if (backCharsCursor < inputText.length())
                backCharsCursor++;
            break;
        case 205:
            if (backCharsCursor > 0)
                backCharsCursor--;
            break;
        default: {
            if (!containsElement(acceptableKeycodes, k))
                return;
            String e = String.valueOf(t);
            if (!e.isEmpty())
                addCharToInput(e);
            break;
        }
        }
        // up arrow 200
        // down 208
        // left 203
        // right 205
        // enter 28
        // backspace 14
        // tab 15
    }

    private boolean containsElement(int[] acceptableKeycodes, int k) {
        for (int i : acceptableKeycodes)
            if (i == k)
                return true;
        return false;
    }

    private void addCharToInput(String e) {
        if (backCharsCursor == 0)
            inputText += e;
        else {
            String deletable = inputText.substring(0, inputText.length() - backCharsCursor);
            String appendable = inputText.substring(inputText.length() - backCharsCursor);
            inputText = deletable + e + appendable;
        }
    }

    private void proccessInput() {
        if (!inputText.isEmpty())
            try {
                String command = inputText.split(" ")[0];
                boolean hasArgs = inputText.contains(" ");
                String[] args = hasArgs ? inputText.substring(command.length() + 1).split(" ") : new String[0];

                Raven.commandManager.executeCommand(command, args);
            } catch (IndexOutOfBoundsException fuck) {
            }
    }

    public void setComponentStartAt(int n) {

    }

    public int getHeight() {
        return height;
    }

    public boolean overPosition(int x, int y) {
        if (hidden)
            return false;
        return opened ? overWindow(x, y) : overBar(x, y);
    }

    public boolean overBar(int x, int y) {
        return (x >= this.x) && (x <= (this.x + width)) && (y >= this.y) && (y <= (this.y + barHeight));
    }

    public boolean overWindow(int x, int y) {
        if (!opened)
            return false;
        return (x >= this.x) && (x <= (this.x + width)) && (y >= this.y) && (y <= (this.y + height));
    }

    public boolean overResize(int x, int y) {
        return (x >= ((this.x + width) - resizeButtonSize)) && (x <= (this.x + width)) && (y >= ((this.y + height) - resizeButtonSize))
                && (y <= (this.y + height));
    }

    public boolean overToggleButton(int x, int y) {
        return (x >= ((this.x + width) - barHeight)) && (x <= (this.x + width)) && (y >= this.y) && (y <= (this.y + barHeight));
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean hidden() {
        return hidden;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }
}
