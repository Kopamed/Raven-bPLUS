package keystrokesmod.client.utils;

import net.minecraft.client.Minecraft;

public class DimensionHelper {
    enum DIMENSIONS {
        NETHER(-1), OVERWORLD(0), END(1);

        private final int dimensionID;

        DIMENSIONS(int n) {
            this.dimensionID = n;
        }

        public int getDimensionID() {
            return dimensionID;
        }
    }

    public static boolean isPlayerInNether() {
        if (!Utils.Player.isPlayerInGame())
            return false;
        return (Minecraft.getMinecraft().thePlayer.dimension == DIMENSIONS.NETHER.getDimensionID());
    }

    public static boolean isPlayerInEnd() {
        if (!Utils.Player.isPlayerInGame())
            return false;
        return (Minecraft.getMinecraft().thePlayer.dimension == DIMENSIONS.END.getDimensionID());
    }

    public static boolean isPlayerInOverworld() {
        if (!Utils.Player.isPlayerInGame())
            return false;
        return (Minecraft.getMinecraft().thePlayer.dimension == DIMENSIONS.OVERWORLD.getDimensionID());
    }
}
