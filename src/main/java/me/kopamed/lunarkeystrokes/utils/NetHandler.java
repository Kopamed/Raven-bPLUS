package me.kopamed.lunarkeystrokes.utils;

import me.kopamed.lunarkeystrokes.module.modules.world.PingSpoof;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.*;
import net.minecraft.util.IChatComponent;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

// credits to Sebsb, from ExplicitClient.


public class NetHandler extends NetHandlerPlayClient {

    private NetHandlerPlayClient parent;
    private static String[] normal;

    public NetHandler(NetHandlerPlayClient nethandlerplayclient) {
        super(Minecraft.getMinecraft(), getGuiScreen(nethandlerplayclient), nethandlerplayclient.getNetworkManager(), Minecraft.getMinecraft().thePlayer.getGameProfile());
        this.parent = nethandlerplayclient;
    }

    private static GuiScreen getGuiScreen(NetHandlerPlayClient nethandlerplayclient) {
        Field[] afield = nethandlerplayclient.getClass().getDeclaredFields();
        int i = afield.length;

        for (int j = 0; j < i; ++j) {
            Field field = afield[j];

            if (field.getType().equals(GuiScreen.class)) {
                field.setAccessible(true);

                try {
                    return (GuiScreen) field.get(nethandlerplayclient);
                } catch (Exception exception) {
                    return null;
                }
            }
        }

        return null;
    }

    private double a(float f, float f1) {
        float f2 = Math.abs(f - f1) % 360.0F;

        if (f2 > 180.0F) {
            f2 = 360.0F - f2;
        }

        return (double) f2;
    }

    private float a(double d0, double d1) {
        double d2 = d0 - Minecraft.getMinecraft().thePlayer.posX;
        double d3 = d1 - Minecraft.getMinecraft().thePlayer.posZ;
        float f = (float) Math.toDegrees(-Math.atan(d2 / d3));

        if (d3 < 0.0D && d2 < 0.0D) {
            f = (float) (90.0D + Math.toDegrees(Math.atan(d3 / d2)));
        } else if (d3 < 0.0D && d2 > 0.0D) {
            f = (float) (-90.0D + Math.toDegrees(Math.atan(d3 / d2)));
        }

        return f;
    }

    public void handleJoinGame(S01PacketJoinGame s01packetjoingame) {
        this.parent.handleJoinGame(s01packetjoingame);
    }

    public void handleSpawnObject(S0EPacketSpawnObject s0epacketspawnobject) {
        this.parent.handleSpawnObject(s0epacketspawnobject);
    }

    public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb s11packetspawnexperienceorb) {
        this.parent.handleSpawnExperienceOrb(s11packetspawnexperienceorb);
    }

    public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity s2cpacketspawnglobalentity) {
        this.parent.handleSpawnGlobalEntity(s2cpacketspawnglobalentity);
    }

    public void handleSpawnPainting(S10PacketSpawnPainting s10packetspawnpainting) {
        this.parent.handleSpawnPainting(s10packetspawnpainting);
    }

    public void handleEntityVelocity(S12PacketEntityVelocity s12packetentityvelocity) {
        // todo
        this.parent.handleEntityVelocity(s12packetentityvelocity);
    }

    public void handleEntityMetadata(S1CPacketEntityMetadata s1cpacketentitymetadata) {
        this.parent.handleEntityMetadata(s1cpacketentitymetadata);
    }

    public void handleSpawnPlayer(S0CPacketSpawnPlayer s0cpacketspawnplayer) {
        this.parent.handleSpawnPlayer(s0cpacketspawnplayer);
    }

    public void handleEntityTeleport(S18PacketEntityTeleport s18packetentityteleport) {
        this.parent.handleEntityTeleport(s18packetentityteleport);
    }

    public void handleHeldItemChange(S09PacketHeldItemChange s09packethelditemchange) {
        this.parent.handleHeldItemChange(s09packethelditemchange);
    }

    public void handleEntityMovement(S14PacketEntity s14packetentity) {
        this.parent.handleEntityMovement(s14packetentity);
    }

    public void handleEntityHeadLook(S19PacketEntityHeadLook s19packetentityheadlook) {
        this.parent.handleEntityHeadLook(s19packetentityheadlook);
    }

    public void handleDestroyEntities(S13PacketDestroyEntities s13packetdestroyentities) {
        this.parent.handleDestroyEntities(s13packetdestroyentities);
    }

    public void handlePlayerPosLook(S08PacketPlayerPosLook s08packetplayerposlook) {
        this.parent.handlePlayerPosLook(s08packetplayerposlook);
    }

    public void handleMultiBlockChange(S22PacketMultiBlockChange s22packetmultiblockchange) {
        this.parent.handleMultiBlockChange(s22packetmultiblockchange);
    }

    public void handleChunkData(S21PacketChunkData s21packetchunkdata) {
        this.parent.handleChunkData(s21packetchunkdata);
    }

    public void handleBlockChange(S23PacketBlockChange s23packetblockchange) {
        this.parent.handleBlockChange(s23packetblockchange);
    }

    public void handleDisconnect(S40PacketDisconnect s40packetdisconnect) {
        this.parent.handleDisconnect(s40packetdisconnect);
    }

    public void onDisconnect(IChatComponent ichatcomponent) {
        this.parent.onDisconnect(ichatcomponent);
    }

    public void handleCollectItem(S0DPacketCollectItem s0dpacketcollectitem) {
        this.parent.handleCollectItem(s0dpacketcollectitem);
    }

    public void handleChat(S02PacketChat s02packetchat) {
        this.parent.handleChat(s02packetchat);
    }

    public void handleAnimation(S0BPacketAnimation s0bpacketanimation) {
        this.parent.handleAnimation(s0bpacketanimation);
    }

    public void handleUseBed(S0APacketUseBed s0apacketusebed) {
        this.parent.handleUseBed(s0apacketusebed);
    }

    public void handleSpawnMob(S0FPacketSpawnMob s0fpacketspawnmob) {
        this.parent.handleSpawnMob(s0fpacketspawnmob);
    }

    public void handleTimeUpdate(S03PacketTimeUpdate s03packettimeupdate) {
            this.parent.handleTimeUpdate(s03packettimeupdate);
    }

    public void handleSpawnPosition(S05PacketSpawnPosition s05packetspawnposition) {
        this.parent.handleSpawnPosition(s05packetspawnposition);
    }

    public void handleEntityAttach(S1BPacketEntityAttach s1bpacketentityattach) {
        this.parent.handleEntityAttach(s1bpacketentityattach);
    }

    public void handleEntityStatus(S19PacketEntityStatus s19packetentitystatus) {
        this.parent.handleEntityStatus(s19packetentitystatus);
    }

    public void handleUpdateHealth(S06PacketUpdateHealth s06packetupdatehealth) {
        this.parent.handleUpdateHealth(s06packetupdatehealth);
    }

    public void handleSetExperience(S1FPacketSetExperience s1fpacketsetexperience) {
        this.parent.handleSetExperience(s1fpacketsetexperience);
    }

    public void handleRespawn(S07PacketRespawn s07packetrespawn) {
        this.parent.handleRespawn(s07packetrespawn);
    }

    public void handleExplosion(S27PacketExplosion s27packetexplosion) {
        this.parent.handleExplosion(s27packetexplosion);
    }

    public void handleOpenWindow(S2DPacketOpenWindow s2dpacketopenwindow) {
        this.parent.handleOpenWindow(s2dpacketopenwindow);
    }

    public void handleSetSlot(S2FPacketSetSlot s2fpacketsetslot) {
        this.parent.handleSetSlot(s2fpacketsetslot);
    }

    public void handleConfirmTransaction(S32PacketConfirmTransaction s32packetconfirmtransaction) {
        this.parent.handleConfirmTransaction(s32packetconfirmtransaction);
    }

    public void handleWindowItems(S30PacketWindowItems s30packetwindowitems) {
        this.parent.handleWindowItems(s30packetwindowitems);
    }

    public void handleSignEditorOpen(S36PacketSignEditorOpen s36packetsigneditoropen) {
        this.parent.handleSignEditorOpen(s36packetsigneditoropen);
    }

    public void handleUpdateSign(S33PacketUpdateSign s33packetupdatesign) {
        this.parent.handleUpdateSign(s33packetupdatesign);
    }

    public void handleUpdateTileEntity(S35PacketUpdateTileEntity s35packetupdatetileentity) {
        this.parent.handleUpdateTileEntity(s35packetupdatetileentity);
    }

    public void handleWindowProperty(S31PacketWindowProperty s31packetwindowproperty) {
        this.parent.handleWindowProperty(s31packetwindowproperty);
    }

    public void handleEntityEquipment(S04PacketEntityEquipment s04packetentityequipment) {
        this.parent.handleEntityEquipment(s04packetentityequipment);
    }

    public void handleCloseWindow(S2EPacketCloseWindow s2epacketclosewindow) {
        this.parent.handleCloseWindow(s2epacketclosewindow);
    }

    public void handleBlockAction(S24PacketBlockAction s24packetblockaction) {
        this.parent.handleBlockAction(s24packetblockaction);
    }

    public void handleBlockBreakAnim(S25PacketBlockBreakAnim s25packetblockbreakanim) {
        this.parent.handleBlockBreakAnim(s25packetblockbreakanim);
    }

    public void handleMapChunkBulk(S26PacketMapChunkBulk s26packetmapchunkbulk) {
        this.parent.handleMapChunkBulk(s26packetmapchunkbulk);
    }

    public void handleChangeGameState(S2BPacketChangeGameState s2bpacketchangegamestate) {
        this.parent.handleChangeGameState(s2bpacketchangegamestate);
    }

    public void handleMaps(S34PacketMaps s34packetmaps) {
        this.parent.handleMaps(s34packetmaps);
    }

    public void handleEffect(S28PacketEffect s28packeteffect) {
        this.parent.handleEffect(s28packeteffect);
    }

    public void handleStatistics(S37PacketStatistics s37packetstatistics) {
        this.parent.handleStatistics(s37packetstatistics);
    }

    public void handleEntityEffect(S1DPacketEntityEffect s1dpacketentityeffect) {
        this.parent.handleEntityEffect(s1dpacketentityeffect);
    }

    public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect s1epacketremoveentityeffect) {
        this.parent.handleRemoveEntityEffect(s1epacketremoveentityeffect);
    }

    public void handlePlayerListItem(S38PacketPlayerListItem s38packetplayerlistitem) {
        this.parent.handlePlayerListItem(s38packetplayerlistitem);
    }

    public void handleKeepAlive(final S00PacketKeepAlive s00packetkeepalive) {
        if (PingSpoof.toggled) {
            javax.swing.Timer timer = new Timer((int)PingSpoof.spoof.getInput(), new ActionListener() {
                final S00PacketKeepAlive val$packetIn = s00packetkeepalive;
                final NetHandler this$0 = NetHandler.this;

                public void actionPerformed(ActionEvent actionevent) {
                    this.this$0.parent.handleKeepAlive(this.val$packetIn);
                }
            });

            timer.setRepeats(false);
            timer.start();
        } else {
            this.parent.handleKeepAlive(s00packetkeepalive);
        }

    }

    public void handlePlayerAbilities(S39PacketPlayerAbilities s39packetplayerabilities) {
        this.parent.handlePlayerAbilities(s39packetplayerabilities);
    }

    public void handleTabComplete(S3APacketTabComplete s3apackettabcomplete) {
        this.parent.handleTabComplete(s3apackettabcomplete);
    }

    public void handleSoundEffect(S29PacketSoundEffect s29packetsoundeffect) {
        this.parent.handleSoundEffect(s29packetsoundeffect);
    }

    public void handleCustomPayload(S3FPacketCustomPayload s3fpacketcustompayload) {
        this.parent.handleCustomPayload(s3fpacketcustompayload);
    }

    public void handleScoreboardObjective(S3BPacketScoreboardObjective s3bpacketscoreboardobjective) {
        this.parent.handleScoreboardObjective(s3bpacketscoreboardobjective);
    }

    public void handleUpdateScore(S3CPacketUpdateScore s3cpacketupdatescore) {
        this.parent.handleUpdateScore(s3cpacketupdatescore);
    }

    public void handleDisplayScoreboard(S3DPacketDisplayScoreboard s3dpacketdisplayscoreboard) {
        this.parent.handleDisplayScoreboard(s3dpacketdisplayscoreboard);
    }

    public void handleWorldBorder(S44PacketWorldBorder s44packetworldborder) {
        this.parent.handleWorldBorder(s44packetworldborder);
    }

    public void handleTeams(S3EPacketTeams s3epacketteams) {
        this.parent.handleTeams(s3epacketteams);
    }

    public void handleParticles(S2APacketParticles s2apacketparticles) {
        this.parent.handleParticles(s2apacketparticles);
    }

    public void handleEntityProperties(S20PacketEntityProperties s20packetentityproperties) {
        this.parent.handleEntityProperties(s20packetentityproperties);
    }

    static NetHandlerPlayClient getParent(NetHandler nethandler) {
        return nethandler.parent;
    }

    static {
        //normalStringSetter();
        //shitter();
    }

    private static void shitter() {
        //shitterFinal = new String[19];
        /*NetHandler.shitterFinal[0] = lIlIlIlllI(NetHandler.normal[0], NetHandler.normal[1]);
        NetHandler.shitterFinal[1] = lIlIlIlllI(NetHandler.normal[2], NetHandler.normal[3]);
        NetHandler.shitterFinal[2] = lIlIlIllll(NetHandler.normal[4], NetHandler.normal[5]);
        NetHandler.shitterFinal[3] = lIlIlIllll(NetHandler.normal[6], NetHandler.normal[7]);
        NetHandler.shitterFinal[4] = lIlIlIlllI(NetHandler.normal[8], NetHandler.normal[9]);
        NetHandler.shitterFinal[5] = lIlIlIlllI(NetHandler.normal[10], NetHandler.normal[11]);
        NetHandler.shitterFinal[6] = lIlIlIlllI(NetHandler.normal[12], NetHandler.normal[13]);
        NetHandler.shitterFinal[7] = lIlIllIIII(NetHandler.normal[14], NetHandler.normal[15]);
        NetHandler.shitterFinal[8] = lIlIllIIII(NetHandler.normal[16], NetHandler.normal[17]);
        NetHandler.shitterFinal[9] = lIlIlIlllI(NetHandler.normal[18], NetHandler.normal[19]);
        NetHandler.shitterFinal[10] = lIlIlIllll(NetHandler.normal[20], NetHandler.normal[21]);
        NetHandler.shitterFinal[11] = lIlIlIlllI(NetHandler.normal[22], NetHandler.normal[23]);
        NetHandler.shitterFinal[12] = lIlIlIllll(NetHandler.normal[24], NetHandler.normal[25]);
        NetHandler.shitterFinal[13] = lIlIlIllll(NetHandler.normal[26], NetHandler.normal[27]);
        NetHandler.shitterFinal[14] = lIlIlIllll(NetHandler.normal[28], NetHandler.normal[29]);
        NetHandler.shitterFinal[15] = lIlIlIllll(NetHandler.normal[30], NetHandler.normal[31]);
        NetHandler.shitterFinal[16] = lIlIlIllll(NetHandler.normal[32], NetHandler.normal[33]);
        NetHandler.shitterFinal[17] = lIlIlIlllI(NetHandler.normal[34], NetHandler.normal[35]);
        NetHandler.shitterFinal[18] = lIlIlIllll(NetHandler.normal[36], NetHandler.normal[37]);*/
        NetHandler.normal = null;
    }

    private static void normalStringSetter() {
        String s = (new Exception()).getStackTrace()[0].getFileName();

        NetHandler.normal = s.substring(s.indexOf("ä") + 1, s.lastIndexOf("ü")).split("ö");
    }

    private static String lIlIlIllll(String s, String s1) {
        try {
            SecretKeySpec secretkeyspec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s1.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            Cipher cipher = Cipher.getInstance("DES");

            cipher.init(2, secretkeyspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static String lIlIllIIII(String s, String s1) {
        try {
            SecretKeySpec secretkeyspec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s1.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");

            cipher.init(2, secretkeyspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static String lIlIlIlllI(String s, String s1) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder stringbuilder = new StringBuilder();
        char[] achar = s1.toCharArray();
        int i = 0;
        char[] achar1 = s.toCharArray();
        int j = achar1.length;

        for (int k = 0; k < j; ++k) {
            char c0 = achar1[k];

            stringbuilder.append((char) (c0 ^ achar[i % achar.length]));
            ++i;
        }

        return String.valueOf(stringbuilder);
    }
}

