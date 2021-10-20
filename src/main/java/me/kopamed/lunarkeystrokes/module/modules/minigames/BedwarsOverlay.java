//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.minigames;

import com.google.gson.JsonObject;
import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;

import static me.kopamed.lunarkeystrokes.utils.Utils.Java.round;

public class BedwarsOverlay extends Module {
    public static Slider overlayX, overlayY, margin, marginTextY, marginTextX;
    public static Description bombiesMomento;
    public static boolean active, reset;
    public static double overlayWidth, overlayHeight, textY;
    public static int mainTextColour, backgroundColour, linesDrawn, errorColour;
    public static HashMap<String, int[]> playerStats = new HashMap<>();
    public static HashMap<StatType, Integer> statStart = new HashMap<>();

    public BedwarsOverlay() {

        super("Bedwars Overlay", Module.category.minigames, 0);
        this.registerSetting(bombiesMomento = new Description("B0MBIES moment"));
        overlayHeight = 170;
        overlayWidth = 300;
        this.registerSetting(overlayX = new Slider("X", 4, 0, mc.displayWidth, 1));
        this.registerSetting(overlayY = new Slider("Y", 4, 0, mc.displayHeight, 1));
        this.registerSetting(margin = new Slider("Margin", 4, 0, 100, 1));
        this.registerSetting(marginTextX = new Slider("Margin Text X", 21, 0, 100, 1));
        this.registerSetting(marginTextY = new Slider("Margin Text Y", 8, 0, 100, 1));
        //overlayX = 4;
        //overlayY = 4;
        mainTextColour  = 0xffFEC5E5;
        backgroundColour = 0x903c3f41;
        errorColour = 0xffff0033;
        //margin = 4;
        //marginTextY = 8;
        //marginTextX = 21;
    }

    @SubscribeEvent
    public void onChatMessageRecieved(ClientChatReceivedEvent event) {
        if (Utils.Player.isPlayerInGame()) {
            if (Utils.Java.str(event.message.getUnformattedText()).startsWith("Sending you to")) {
                playerStats.clear();
            }
        }
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent e) {
        if (!active) {
            return;
        }
        if (this.mc.currentScreen != null) {
            return;

        }
        if (!this.mc.inGameHasFocus || this.mc.gameSettings.showDebugInfo) {
            return;
        }


        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        linesDrawn = 0;
        drawMain(sr, fr);
        linesDrawn++;

        if(drawError(sr, fr)) {
            linesDrawn++;
           // return;
        }



        for(NetworkPlayerInfo player : Utils.Client.getPlayers()){
            drawStats(player, fr);
        }
        overlayHeight = margin.getInput() * 2 + fr.FONT_HEIGHT * linesDrawn + marginTextY.getInput() * --linesDrawn;

        fr.drawString("", 0, 0, 0xffffffff);
        // LEVEL   PLAYERS   PARTY   WS   BBBLR   FKDR   WLR   FINALS   WINS   THREAT
        // 420     KOPAMED     -     23   84    9.3    6.3    30002     1
        // 1       Jpzinn      -     0    1     0.07   0.01   22394    10

        // Colour pallette
        // grey
        // white
        // green
        // aqua
        // yellow
        // orange
        // red
        // purple

    }

    private void drawStats(NetworkPlayerInfo player, FontRenderer fr) {
        /*if(!playerStats.containsKey(player.getGameProfile().getId())){
            Ravenbplus.getExecutor().execute(() -> {
                playerStats.put(player.getGameProfile().getId().toString(), getBedwarsStats(player.getGameProfile().getId().toString()));
            });
        } else {

        }*/
        String name = player.getGameProfile().getName();
        String UUID = player.getGameProfile().getId().toString();
        if(Utils.URLS.hypixelApiKey.isEmpty()){
            fr.drawString(name, (int)statStart.get(StatType.PLAYER_NAME), (int)textY, 0xff05C3DD);
            textY += fr.FONT_HEIGHT + marginTextY.getInput();
            linesDrawn++;
        } else {
            double bbblr, wlr, fkdr;
            if(!playerStats.containsKey(UUID)){
                Ravenbplus.getExecutor().execute(() -> {
                    getBedwarsStats(UUID);
                });
                playerStats.put(UUID, new int[] {-16});
                return;
            }

            int[] stats = playerStats.get(UUID);

            if(stats.length == 1 && stats[0] == -16){
                //we are loading player stats so return
                return;
            }

            bbblr = stats[4] != 0 ? round((double)stats[3] / (double)stats[4], 2) : stats[3];
            fkdr = stats[6] != 0 ? round((double)stats[5] / (double)stats[6], 2) : stats[5];
            wlr = stats[8] != 0 ? round((double)stats[7] / (double)stats[8], 2) : stats[7];
            fr.drawString(stats[0] + "", (int)statStart.get(StatType.LEVEL), (int)textY, getStarColour(stats[0]));
            fr.drawString(name, (int)statStart.get(StatType.PLAYER_NAME), (int)textY, Colours.WHITE);
            if(stats[1] == 0) {
                fr.drawString("  -", (int)statStart.get(StatType.NICKED), (int)textY, Colours.GREY);
            } else {
                fr.drawString("  +", (int)statStart.get(StatType.NICKED), (int)textY, Colours.RED);
            }
            fr.drawString(stats[2] + "", (int)statStart.get(StatType.WS), (int)textY, getWSColour(stats[2]));

            fr.drawString(bbblr + "", (int)statStart.get(StatType.BBBLR), (int)textY, getBBBLRColour(bbblr));
            fr.drawString(fkdr + "", (int)statStart.get(StatType.FKDR), (int)textY, getFKDRColour(fkdr));
            fr.drawString(wlr + "", (int)statStart.get(StatType.WLR), (int)textY, getWLRColour(wlr));
            fr.drawString(stats[6] + "", (int)statStart.get(StatType.FINALS), (int)textY, getFinalColour(stats[6]));
            fr.drawString(stats[7] + "", (int)statStart.get(StatType.WINS), (int)textY, getFinalColour(stats[7]));
            String bad = DuelsStats.gtl(stats[7], stats[8], (double)(stats[5])/100, stats[2]).substring(2);
            fr.drawString(bad, (int)statStart.get(StatType.OVERALLTHREAT), (int)textY, getTreatColour(bad));

            textY += marginTextY.getInput() + fr.FONT_HEIGHT;
            linesDrawn++;
        }
    }

    private int getTreatColour(String bad) {
        //"&4VERY HIGH", "&cHIGH", "&6MODERATE", "&aLOW", "&2VERY LOW"
        if(bad.equalsIgnoreCase("very high")) {
            return Colours.RED;
        } else if(bad.equalsIgnoreCase("high")) {
            return Colours.ORANGE;
        }else if(bad.equalsIgnoreCase("moderate")) {
            return Colours.YELLOW;
        }else if(bad.equalsIgnoreCase("LOW")) {
            return Colours.GREEN;
        }else if(bad.equalsIgnoreCase("very low")) {
            return Colours.GREY;
        }
        return Colours.GREY;
    }

    private int getFinalColour(int stat) {
        if(stat < 50){
            return Colours.GREY;
        } else if(stat < 100) {
            return Colours.WHITE;
        } else if(stat < 150) {
            return Colours.GREEN;
        } else if(stat < 200) {
            return Colours.AQUA;
        } else if(stat < 500) {
            return Colours.YELLOW;
        } else if(stat < 1000) {
            return Colours.ORANGE;
        } else if(stat < 5000) {
            return Colours.RED;
        } else if(stat >= 5000) {
            return Colours.PURPLE;
        }
        return Colours.PURPLE;
    }

    private int getFKDRColour(double stat) {
        if(stat < 0.31){
            return Colours.GREY;
        } else if(stat < 0.51) {
            return Colours.WHITE;
        } else if(stat < 1) {
            return Colours.GREEN;
        } else if(stat < 1.5) {
            return Colours.AQUA;
        } else if(stat < 2.5) {
            return Colours.YELLOW;
        } else if(stat < 4) {
            return Colours.ORANGE;
        } else if(stat < 10) {
            return Colours.RED;
        } else if(stat >= 20) {
            return Colours.PURPLE;
        }
        return Colours.PURPLE;
    }

    private int getBBBLRColour(double stat) {
        if(stat < 0.31){
            return Colours.GREY;
        } else if(stat < 0.51) {
            return Colours.WHITE;
        } else if(stat < 1) {
            return Colours.GREEN;
        } else if(stat < 1.5) {
            return Colours.AQUA;
        } else if(stat < 2.5) {
            return Colours.YELLOW;
        } else if(stat < 4) {
            return Colours.ORANGE;
        } else if(stat < 10) {
            return Colours.RED;
        } else if(stat >= 20) {
            return Colours.PURPLE;
        }
        return Colours.PURPLE;
    }

    private int getWLRColour(double stat) {
        if(stat < 0.51){
            return Colours.GREY;
        } else if(stat < 1.01) {
            return Colours.WHITE;
        } else if(stat < 1.5) {
            return Colours.GREEN;
        } else if(stat < 2) {
            return Colours.AQUA;
        } else if(stat < 4) {
            return Colours.YELLOW;
        } else if(stat < 8) {
            return Colours.ORANGE;
        } else if(stat < 15) {
            return Colours.RED;
        } else if(stat >= 15) {
            return Colours.PURPLE;
        }
        return Colours.PURPLE;
    }

    private int getWSColour(int stat) {
            if(stat < 5){
                return Colours.GREY;
            } else if(stat < 10) {
                return Colours.WHITE;
            } else if(stat < 15) {
                return Colours.GREEN;
            } else if(stat < 20) {
                return Colours.AQUA;
            } else if(stat < 30) {
                return Colours.YELLOW;
            } else if(stat < 50) {
                return Colours.ORANGE;
            } else if(stat < 100) {
                return Colours.RED;
            } else if(stat >= 100) {
                return Colours.PURPLE;
            }
            return Colours.PURPLE;
    }

    private void getBedwarsStats(String uuid) {
        // credit to hevex
        // https://github.com/hevex/bedwars-lobby-checker/blob/d30a0495ca35ea0085c0a6913c17628e334aec64/FastScanner.java#L158
        // Stars, FK, FD, Wins, Losses, Winstreak
        final int[] stats = new int[9];
        // open connection to api
        String connection = Utils.URLS.getTextFromURL("https://api.hypixel.net/player?key=" + Utils.URLS.hypixelApiKey + "&uuid=" + uuid);
        // error getting contents of link
        if (connection.isEmpty()) {
            return;
        }
        // faster than contains
        if (connection.equals("{\"success\":true,\"player\":null}")) {
            // player is nicked
            stats[0] = -1;
            playerStats.put(uuid, stats);
        }
        // parse the text from the api
        JsonObject profile, bw, ach;
        try {
            profile = Utils.Java.getStringAsJson(connection).getAsJsonObject("player");
            bw = profile.getAsJsonObject("stats").getAsJsonObject("Bedwars");
            ach = profile.getAsJsonObject("achievements");
            //System.out.println(profile.toString());
        } catch (NullPointerException er) {
            // never played bedwars or joined lobby
            playerStats.put(uuid, stats);
            return;
        }
        // get stats from parsed objects (check for null)
        stats[0] = Utils.Java.getValue(ach, "bedwars_level");
        stats[1] = stats[0] < 0 ? -1 : 0;
        stats[2] = Utils.Java.getValue(bw, "winstreak");
        stats[3] = Utils.Java.getValue(bw, "beds_broken_bedwars");
        stats[4] = Utils.Java.getValue(bw, "beds_lost_bedwars");
        //stats[3] = (int)(round(Utils.Java.getValue(bw, "beds_broken_bedwars") / Utils.Java.getValue(bw, "beds_lost_bedwars") * 100, 2));
        //stats[4] = (int)(round(Utils.Java.getValue(bw, "final_kills_bedwars") / Utils.Java.getValue(bw, "final_deaths_bedwars") * 100, 2));
        //stats[5] = (int)(round(Utils.Java.getValue(bw, "wins_bedwars") / Utils.Java.getValue(bw, "losses_bedwars") * 100, 2));
        stats[6] = Utils.Java.getValue(bw, "final_deaths_bedwars");
        stats[5] = Utils.Java.getValue(bw, "final_kills_bedwars");
        stats[7] = Utils.Java.getValue(bw, "wins_bedwars");
        stats[8] = Utils.Java.getValue(bw, "losses_bedwars");
        playerStats.put(uuid, stats);


    }

    private boolean drawError(ScaledResolution sr, FontRenderer fr) {
        String noApiKey = "API key is not set!";
        String noPlayers = "No players in lobby!";
        if(Utils.URLS.hypixelApiKey.isEmpty()){
            fr.drawString(noApiKey, (int)(overlayWidth + overlayX.getInput() - overlayWidth/2 - fr.getStringWidth(noApiKey)/2), (int)textY, errorColour);
            textY += fr.FONT_HEIGHT + marginTextY.getInput();
            return true;
        } else if(!Utils.Client.othersExist()){
            fr.drawString(noPlayers, (int)(overlayWidth + overlayX.getInput() - overlayWidth/2 - fr.getStringWidth(noPlayers)/2), (int)textY, errorColour);
            textY += fr.FONT_HEIGHT + marginTextY.getInput();
            return true;
        }

        return false;
    }

    private void drawMain(ScaledResolution sr, FontRenderer fr) {
        Gui.drawRect((int)overlayX.getInput(), (int)overlayY.getInput(), (int)(overlayWidth + overlayX.getInput()), (int)(overlayHeight + overlayY.getInput()), backgroundColour);

        double textX = margin.getInput() + overlayX.getInput();
        textY = margin.getInput() + overlayY.getInput();
        int stringWidth = 0;
        for(StatType statType : StatType.values()) {
            fr.drawString(statType + "", (int)textX, (int)textY, mainTextColour);
            statStart.put(statType, (int)textX);
            stringWidth = fr.getStringWidth(statType + "");
            textX += stringWidth + marginTextX.getInput();
        }
        textY += marginTextY.getInput() + fr.FONT_HEIGHT;
        overlayWidth = textX + margin.getInput() - (marginTextX.getInput()) - overlayX.getInput();
    }

    public static int getStarColour(int stat){
        if(stat < 20){
            return Colours.GREY;
        } else if(stat < 50) {
            return Colours.WHITE;
        } else if(stat < 150) {
            return Colours.GREEN;
        } else if(stat < 200) {
            return Colours.AQUA;
        } else if(stat < 400) {
            return Colours.YELLOW;
        } else if(stat < 500) {
            return Colours.ORANGE;
        } else if(stat < 1000) {
            return Colours.RED;
        } else if(stat >= 1000) {
            return Colours.PURPLE;
        }
        return Colours.PURPLE;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        active = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        active = false;
    }

    public static enum StatType {
        LEVEL,
        PLAYER_NAME,
        NICKED,
        WS,
        BBBLR,
        FKDR,
        WLR,
        FINALS,
        WINS,
        OVERALLTHREAT;
    }

    public static class Colours {
        public static final int GREY = 0xffAAAAAA;
        public static final int WHITE = 0xffffffff;
        public static final int GREEN = 0xff00AA00;
        public static final int AQUA = 0xff55FFFF;
        public static final int YELLOW = 0xffFFFF55;
        public static final int ORANGE = 0xffFFAA00;
        public static final int RED = 0xffAA0000;
        public static final int PURPLE = 0xffAA00AA;
    }
}
