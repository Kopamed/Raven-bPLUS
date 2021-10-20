package me.kopamed.lunarkeystrokes.discordRPC;

public enum RPCMode {
    BADLION("882260516121231412"),
    LUNAR("882245315393114223"),
    MINECRAFT("882245446335082528"),
    RAVEN_BP("880735714805968896");

    final String appID;

    RPCMode(String appID) {
        this.appID = appID;
    }
}
