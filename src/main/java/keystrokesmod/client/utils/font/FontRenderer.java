package keystrokesmod.client.utils.font;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class FontRenderer extends CFont {
    CFont.CharData[] boldChars = new CFont.CharData[256], italicChars = new CFont.CharData[256],
            boldItalicChars = new CFont.CharData[256];
    int[] colorCode = new int[32];
    String colorcodeIdentifiers = "0123456789abcdefklmnor";
    DynamicTexture texBold, texItalic, texItalicBold;

    public FontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }

    public int drawStringWithShadow(String text, double x2, float y2, int color) {

        float shadowWidth = this.drawString(text, x2 + 0.9f, (double) y2 + 0.5f, color, true, 8.3f);

        return (int) Math.max(shadowWidth, this.drawString(text, x2, y2, color, false, 8.3f));
    }

    public int drawString(String text, double x2, float y2, int color) {
        return (int) this.drawString(text, x2, y2, color, false, 8.3f);
    }

    public int drawPassword(String text, double x2, float y2, int color) {
        return (int) this.drawString(text.replaceAll(".", "."), x2, y2, color, false, 8f);
    }

    public int drawNoBSString(String text, double x2, float y2, int color) {
        return (int) this.drawNoBSString(text, x2, y2, color, false);
    }

    public int drawSmoothString(String text, double x2, float y2, int color) {
        return (int) this.drawSmoothString(text, x2, y2, color, false);
    }

    public int drawCenteredSmoothString(String text, double x2, float y2, int color) {
        return (int) this.drawSmoothString(text, x2 - (float) (this.getStringWidth(text) / 2), y2, color, false);
    }

    public double getPasswordWidth(String text) {
        return this.getStringWidth(text.replaceAll(".", "."), 8);
    }

    public float drawCenteredString(String text, float x2, float y2, int color) {
        return this.drawString(text, x2 - (float) (this.getStringWidth(text) / 2), y2, color);
    }

    public float drawNoBSCenteredString(String text, float x2, float y2, int color) {
        return this.drawNoBSString(text, x2 - (float) (this.getStringWidth(text) / 2), y2, color);
    }

    public float drawCenteredStringWithShadow(String text, float x2, float y2, int color) {
        return this.drawStringWithShadow(text, x2 - (float) (this.getStringWidth(text) / 2), y2, color);
    }

    public float drawString(String text, double x, double y, int color, boolean shadow, float kerning) {
        x -= 1.0;

        if (text == null)
			return 0;

        if (color == 0x20FFFFFF)
			color = 0xFFFFFF;

        if ((color & 0xFC000000) == 0)
			color |= 0xFF000000;

        if (shadow)
			color = ((color & 0xFCFCFC) >> 2) | (color & 0xFF000000);

        CFont.CharData[] currentData = this.charData;
        float alpha = (float) ((color >> 24) & 255) / 255f;
        boolean randomCase = false, bold = false, italic = false, strikethrough = false, underline = false,
                render = true;
        x *= 2;
        y = (y - 3) * 2;
        GL11.glPushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor3d(1d, 1d, 1d);
        GlStateManager.resetColor();
        GlStateManager.color((float) ((color >> 16) & 255) / 255f, (float) ((color >> 8) & 255) / 255f,
                (float) (color & 255) / 255f, alpha);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(this.tex.getGlTextureId());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.tex.getGlTextureId());

        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);

            if (character == '\u00a7') {
                int colorIndex = 21;

                try {
                    colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(index + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    randomCase = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;

                    if (colorIndex < 0)
						colorIndex = 15;

                    if (shadow)
						colorIndex += 16;

                    int colorcode = this.colorCode[colorIndex];
                    GlStateManager.color((float) ((colorcode >> 16) & 255) / 255f, (float) ((colorcode >> 8) & 255) / 255f,
                            (float) (colorcode & 255) / 255f, alpha);
                } else
					switch (colorIndex) {
					case 16:
						randomCase = true;
						break;
					case 17:
						bold = true;
						if (italic) {
						    GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
						    currentData = this.boldItalicChars;
						} else {
						    GlStateManager.bindTexture(this.texBold.getGlTextureId());
						    currentData = this.boldChars;
						}
						break;
					case 18:
						strikethrough = true;
						break;
					case 19:
						underline = true;
						break;
					case 20:
						italic = true;
						if (bold) {
						    GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
						    currentData = this.boldItalicChars;
						} else {
						    GlStateManager.bindTexture(this.texItalic.getGlTextureId());
						    currentData = this.italicChars;
						}
						break;
					default:
						bold = false;
						italic = false;
						randomCase = false;
						underline = false;
						strikethrough = false;
						GlStateManager.color((float) ((color >> 16) & 255) / 255f, (float) ((color >> 8) & 255) / 255f,
						        (float) (color & 255) / 255f, alpha);
						GlStateManager.bindTexture(this.tex.getGlTextureId());
						currentData = this.charData;
						break;
					}

                ++index;
            } else if (character < currentData.length) {
                GL11.glBegin(GL11.GL_TRIANGLES);
                this.drawChar(currentData, character, (float) x, (float) y);
                GL11.glEnd();

                if (strikethrough)
					this.drawLine(x, y + (double) (currentData[character].height / 2),
                            (x + (double) currentData[character].width) - 8,
                            y + (double) (currentData[character].height / 2), 1);

                if (underline)
					this.drawLine(x, (y + (double) currentData[character].height) - 2,
                            (x + (double) currentData[character].width) - 8,
                            (y + (double) currentData[character].height) - 2, 1);

                x += (currentData[character].width - kerning) + this.charOffset;
            }
        }

        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GlStateManager.resetColor();
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        return (float) x / 2f;
    }

    public float drawSmoothString(String text, double x, double y, int color, boolean shadow) {
        x -= 1;

        if (text == null)
			return 0;

        CFont.CharData[] currentData = this.charData;
        float alpha = (float) ((color >> 24) & 255) / 255f;
        boolean randomCase = false, bold = false, italic = false, strikethrough = false, underline = false,
                render = true;
        x *= 2;
        y = (y - 3) * 2;
        GL11.glPushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.resetColor();
        GlStateManager.color((float) ((color >> 16) & 255) / 255f, (float) ((color >> 8) & 255) / 255f,
                (float) (color & 255) / 255f, alpha);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(this.tex.getGlTextureId());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.tex.getGlTextureId());
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);

            if (character == '\u00a7') {
                int colorIndex = 21;

                try {
                    colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(index + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    randomCase = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;

                    if (colorIndex < 0)
						colorIndex = 15;

                    if (shadow)
						colorIndex += 16;

                    int colorcode = this.colorCode[colorIndex];
                    GlStateManager.color((float) ((colorcode >> 16) & 255) / 255f, (float) ((colorcode >> 8) & 255) / 255f,
                            (float) (colorcode & 255) / 255f, alpha);
                } else
					switch (colorIndex) {
					case 16:
						randomCase = true;
						break;
					case 17:
						bold = true;
						if (italic) {
						    GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
						    currentData = this.boldItalicChars;
						} else {
						    GlStateManager.bindTexture(this.texBold.getGlTextureId());
						    currentData = this.boldChars;
						}
						break;
					case 18:
						strikethrough = true;
						break;
					case 19:
						underline = true;
						break;
					case 20:
						italic = true;
						if (bold) {
						    GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
						    currentData = this.boldItalicChars;
						} else {
						    GlStateManager.bindTexture(this.texItalic.getGlTextureId());
						    currentData = this.italicChars;
						}
						break;
					default:
						bold = false;
						italic = false;
						randomCase = false;
						underline = false;
						strikethrough = false;
						GlStateManager.color((float) ((color >> 16) & 255) / 255f, (float) ((color >> 8) & 255) / 255f,
						        (float) (color & 255) / 255f, alpha);
						GlStateManager.bindTexture(this.tex.getGlTextureId());
						currentData = this.charData;
						break;
					}

                ++index;
            } else if (character < currentData.length) {
                GL11.glBegin(GL11.GL_TRIANGLES);
                this.drawChar(currentData, character, (float) x, (float) y);
                GL11.glEnd();

                if (strikethrough)
					this.drawLine(x, y + (double) (currentData[character].height / 2),
                            (x + (double) currentData[character].width) - 8,
                            y + (double) (currentData[character].height / 2), 1);

                if (underline)
					this.drawLine(x, (y + (double) currentData[character].height) - 2,
                            (x + (double) currentData[character].width) - 8,
                            (y + (double) currentData[character].height) - 2, 1);

                x += (currentData[character].width - 8.3f) + this.charOffset;
            }
        }

        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        //GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        return (float) x / 2f;
    }

    public float drawNoBSString(String text, double x, double y, int color, boolean shadow) {
        x -= 1;

        if (text == null)
			return 0;

        CFont.CharData[] currentData = this.charData;
        float alpha = (float) ((color >> 24) & 0xFF) / 255f;
        boolean randomCase = false, bold = false, italic = false, strikethrough = false, underline = false,
                render = true;
        x *= 2;
        y = (y - 3) * 2;
        GL11.glPushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.resetColor();
        GlStateManager.color((float) ((color >> 16) & 255) / 255f, (float) ((color >> 8) & 255) / 255f,
                (float) (color & 255) / 255f, alpha);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(this.tex.getGlTextureId());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.tex.getGlTextureId());
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);

            if (character == '\u00a7') {
                int colorIndex = 21;

                try {
                    colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(index + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    randomCase = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;

                    if (colorIndex < 0)
						colorIndex = 15;

                    if (shadow)
						colorIndex += 16;

                    int colorcode = this.colorCode[colorIndex];
                    GlStateManager.color((float) ((colorcode >> 16) & 255) / 255f, (float) ((colorcode >> 8) & 255) / 255f,
                            (float) (colorcode & 255) / 255f, alpha);
                } else
					switch (colorIndex) {
					case 16:
						randomCase = true;
						break;
					case 17:
						bold = true;
						if (italic) {
						    GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
						    currentData = this.boldItalicChars;
						} else {
						    GlStateManager.bindTexture(this.texBold.getGlTextureId());
						    currentData = this.boldChars;
						}
						break;
					case 18:
						strikethrough = true;
						break;
					case 19:
						underline = true;
						break;
					case 20:
						italic = true;
						if (bold) {
						    GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
						    currentData = this.boldItalicChars;
						} else {
						    GlStateManager.bindTexture(this.texItalic.getGlTextureId());
						    currentData = this.italicChars;
						}
						break;
					default:
						bold = false;
						italic = false;
						randomCase = false;
						underline = false;
						strikethrough = false;
						GlStateManager.color((float) ((color >> 16) & 255) / 255f, (float) ((color >> 8) & 255) / 255f,
						        (float) (color & 255) / 255f, alpha);
						GlStateManager.bindTexture(this.tex.getGlTextureId());
						currentData = this.charData;
						break;
					}

                ++index;
            } else if (character < currentData.length) {
                GL11.glBegin(GL11.GL_TRIANGLES);
                this.drawChar(currentData, character, (float) x, (float) y);
                GL11.glEnd();

                if (strikethrough)
					this.drawLine(x, y + (double) (currentData[character].height / 2),
                            (x + (double) currentData[character].width) - 8,
                            y + (double) (currentData[character].height / 2), 1);

                if (underline)
					this.drawLine(x, (y + (double) currentData[character].height) - 2,
                            (x + (double) currentData[character].width) - 8,
                            (y + (double) currentData[character].height) - 2, 1);

                x += (currentData[character].width - 8.3f) + this.charOffset;
            }
        }

        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        return (float) x / 2f;
    }

    public double getStringWidth(String text) {
        if (text == null)
			return 0;

        float width = 0;
        CFont.CharData[] currentData = charData;
        boolean bold = false, italic = false;

        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);

            if (character == '\u00a7') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);

                bold = false;
                italic = false;

                ++index;
            } else if (character < currentData.length)
				width += (currentData[character].width - 8.3f) + charOffset;
        }

        return width / 2;
    }

    public double getStringWidth(String text, float kerning) {
        if (text == null)
			return 0;

        float width = 0;
        CFont.CharData[] currentData = charData;
        boolean bold = false, italic = false;

        for (int index = 0; index < text.length(); index++) {
            char c = text.charAt(index);

            if (c == '\u00a7') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(c);

                bold = false;
                italic = false;

                ++index;
            } else if (c < currentData.length)
				width += (currentData[c].width - kerning) + charOffset;
        }

        return width / 2;
    }

    public int getHeight() {
        return (this.fontHeight - 8) / 2;
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setAntiAlias(boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setFractionalMetrics(boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(Font.BOLD), this.antiAlias, this.fractionalMetrics,
                this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(Font.ITALIC), this.antiAlias, this.fractionalMetrics,
                this.italicChars);
        this.texItalicBold = this.setupTexture(this.font.deriveFont(Font.BOLD | Font.ITALIC), this.antiAlias,
                this.fractionalMetrics, this.boldItalicChars);
    }

    private void drawLine(double x2, double y2, double x1, double y1, float width) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public List<String> wrapWords(String text, double width) {
        ArrayList<String> finalWords = new ArrayList<>();

        if (getStringWidth(text) > width) {
            String[] words = text.split(" ");
            StringBuilder currentWord = new StringBuilder();
            char lastColorCode = 65535;

            for (String word : words) {
                for (int innerIndex = 0; innerIndex < word.toCharArray().length; innerIndex++) {
                    char c = word.toCharArray()[innerIndex];

                    if ((c == '\u00a7') && (innerIndex < (word.toCharArray().length - 1)))
						lastColorCode = word.toCharArray()[innerIndex + 1];
                }

                if (getStringWidth(currentWord + word + " ") < width)
					currentWord.append(word).append(" ");
				else {
                    finalWords.add(currentWord.toString());
                    currentWord = new StringBuilder("\u00a7" + lastColorCode + word + " ");
                }
            }

            if (currentWord.length() > 0)
				if (getStringWidth(currentWord.toString()) < width) {
                    finalWords.add("\u00a7" + lastColorCode + currentWord + " ");
                    currentWord = new StringBuilder();
                } else
					finalWords.addAll(formatString(currentWord.toString(), width));
        } else
			finalWords.add(text);

        return finalWords;
    }

    public List<String> formatString(String string, double width) {
        ArrayList<String> finalWords = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        char lastColorCode = 65535;
        char[] chars = string.toCharArray();

        for (int index = 0; index < chars.length; index++) {
            char c = chars[index];

            if ((c == '\u00a7') && (index < (chars.length - 1)))
				lastColorCode = chars[index + 1];

            if (getStringWidth(currentWord.toString() + c) < width)
				currentWord.append(c);
			else {
                finalWords.add(currentWord.toString());
                currentWord = new StringBuilder("\u00a7" + lastColorCode + c);
            }
        }

        if (currentWord.length() > 0)
			finalWords.add(currentWord.toString());

        return finalWords;
    }

    private void setupMinecraftColorcodes() {
        int index = 0;

        while (index < 32) {
            int noClue = ((index >> 3) & 1) * 85;
            int red = (((index >> 2) & 1) * 170) + noClue;
            int green = (((index >> 1) & 1) * 170) + noClue;
            int blue = ((index & 1) * 170) + noClue;

            if (index == 6)
				red += 85;

            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            this.colorCode[index] = ((red & 255) << 16) | ((green & 255) << 8) | (blue & 255);
            ++index;
        }
    }

    public String trimStringToWidth(String text, int width) {
        return this.trimStringToWidth(text, width, false);
    }

    public String trimStringToWidthPassword(String text, int width, boolean custom) {
        text = text.replaceAll(".", ".");
        return this.trimStringToWidth(text, width, custom);
    }

    private float getCharWidthFloat(char c) {
        if (c == 167)
			return -1;
		if (c == 32)
			return 2;
        int var2 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000"
                .indexOf(c);

        if ((c > 0) && (var2 != -1))
        	return ((charData[var2].width / 2.f) - 4.f);
        if (((charData[c].width / 2.f) - 4.f) != 0) {
            int var3 = ((int) ((charData[c].width / 2.f) - 4.f)) >>> 4;
            int var4 = ((int) ((charData[c].width / 2.f) - 4.f)) & 15;
            var3 &= 15;
            ++var4;
            return (float) (((var4 - var3) / 2) + 1);
        }
        return 0;
    }

    public String trimStringToWidth(String text, int width, boolean custom) {
        StringBuilder buffer = new StringBuilder();
        float lineWidth = 0.0F;
        int offset = custom ? text.length() - 1 : 0;
        int increment = custom ? -1 : 1;
        boolean var8 = false;
        boolean var9 = false;

        for (int index = offset; (index >= 0) && (index < text.length()) && (lineWidth < (float) width); index += increment) {
            char character = text.charAt(index);
            float charWidth = this.getCharWidthFloat(character);

            if (var8) {
                var8 = false;

                if ((character != 108) && (character != 76)) {
                    if ((character == 114) || (character == 82))
						var9 = false;
                } else
					var9 = true;
            } else if (charWidth < 0)
				var8 = true;
			else {
                lineWidth += charWidth;

                if (var9)
					++lineWidth;
            }

            if (lineWidth > (float) width)
				break;

            if (custom)
				buffer.insert(0, character);
			else
				buffer.append(character);
        }

        return buffer.toString();
    }
}