//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "conf"!

package me.kopamed.lunarkeystrokes.module.modules.render;


import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.utils.CombatUtils;
import me.kopamed.lunarkeystrokes.utils.RenderUtils;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.client.event.RenderLivingEvent.Specials.Pre;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class ExplicitB9NameTags extends Module {
    boolean armor;
    boolean dura;
    boolean players = true;
    boolean invis;
    boolean mobs = false;
    boolean animals = false;
    float _x;
    float _y;
    float _z;
    private float scale;
    private String mode;
    private ArrayList entities;

    private final Slider modeSetting;
    private final Slider scaleSetting;
    private final Slider rangeSetting;
    private final Tick armorSetting;
    private final Tick durabilitySetting;
    private final Tick distanceSetting;

    public ExplicitB9NameTags() {
        super("(ExplicitB9)NameTags", category.render);

        modeSetting = new Slider("Mode (Hearts/Percentage)", 1.0D, 1.0D, 2.0D, 1.0D);
        mode = "Percentage"; // default value
        scaleSetting = new Slider("Scale", 5.0D, 0.1D, 10.0D, 0.1D);
        rangeSetting = new Slider("Range", 0.0D, 0.0D, 512.0D, 1.0D);
        armorSetting = new Tick("Armor", true);
        durabilitySetting = new Tick("Durability", false);
        distanceSetting = new Tick("Distance", false);

        registerSetting(modeSetting);
        registerSetting(scaleSetting);
        registerSetting(rangeSetting);
        registerSetting(armorSetting);
        registerSetting(durabilitySetting);
        registerSetting(distanceSetting);
    }

    @Override
    public void guiUpdate() {
        scale = (float) scaleSetting.getInput();
        armor = armorSetting.isToggled();
        dura = durabilitySetting.isToggled();
        mode = modeSetting.getInput() == 1.0D ? "Hearts" : "Percentage";
    }

    @SubscribeEvent
    public void nameTag(Pre<? extends EntityLivingBase> player) {
        boolean _0 = player.entity.getDisplayName().getFormattedText() != null;
        boolean _1 = !player.entity.getDisplayName().getFormattedText().equals("");
        boolean _2 = player.entity instanceof EntityPlayer && CombatUtils.canTarget(player.entity, true);
        boolean _3 = ((double) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(player.entity) <= rangeSetting.getInput() || rangeSetting.getInput() == 0.0D);
        if ( _0 && _1  && _2 && _3) {
            player.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void render3d(RenderWorldLastEvent renderWorldLastEvent) {
        ArrayList<EntityLivingBase> playersArr = new ArrayList<>();

        Iterator playerIterator = Minecraft.getMinecraft().theWorld.playerEntities.iterator();

        while (playerIterator.hasNext()) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) playerIterator.next();
            if ((double) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entityLivingBase) > rangeSetting.getInput() && rangeSetting.getInput() != 0.0D) {
                playersArr.remove(entityLivingBase);
            } else if (entityLivingBase.getName().contains("[NPC]")) {
                playersArr.remove(entityLivingBase);
            } else if (entityLivingBase.isEntityAlive()) {
                if (entityLivingBase.isInvisible()) {
                    playersArr.remove(entityLivingBase);
                } else if (entityLivingBase == Minecraft.getMinecraft().thePlayer) {
                    playersArr.remove(entityLivingBase);
                } else {
                    if (playersArr.size() > 100) {
                        break;
                    }

                    if (!playersArr.contains(entityLivingBase)) {
                        playersArr.add(entityLivingBase);
                    }
                }
            } else playersArr.remove(entityLivingBase);
        }

        _x = 0.0F;
        _y = 0.0F;
        _z = 0.0F;
        playerIterator = playersArr.iterator();

        while (playerIterator.hasNext()) {
            EntityPlayer player = (EntityPlayer) playerIterator.next();
            if (CombatUtils.canTarget(player, true)) {
                player.setAlwaysRenderNameTag(false);
                _x = (float) (player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) Utils.Client.getTimer().renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosX);
                _y = (float) (player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) Utils.Client.getTimer().renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosY);
                _z = (float) (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) Utils.Client.getTimer().renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosZ);
                renderNametag(player, _x, _y, _z);
            }
        }

    }

    private String getHealth(EntityPlayer player) {
        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        return mode.equalsIgnoreCase("Percentage") ? decimalFormat.format(player.getHealth() * 5.0F + player.getAbsorptionAmount() * 5.0F) : decimalFormat.format(player.getHealth() / 2.0F + player.getAbsorptionAmount() / 2.0F);
    }

    private void drawNames(EntityPlayer player) {
        float llllIIllllIlllI = (float) getWidth(getPlayerName(player)) / 2.0F + 2.2F;
        float llllIIllllIllIl;
        llllIIllllIlllI = llllIIllllIllIl = (float) ((double) llllIIllllIlllI + (getWidth(" " + getHealth(player)) / 2) + 2.5D);
        float llllIIllllIllII = -llllIIllllIlllI - 2.2F;
        float llllIIllllIlIll = (float) (getWidth(getPlayerName(player)) + 4);
        if (mode.equalsIgnoreCase("Percentage")) {
            RenderUtils.drawBorderedRect(llllIIllllIllII, -3.0F, llllIIllllIlllI, 10.0F, 1.0F, (new Color(20, 20, 20, 180)).getRGB(), (new Color(10, 10, 10, 200)).getRGB());
        } else {
            RenderUtils.drawBorderedRect(llllIIllllIllII + 5.0F, -3.0F, llllIIllllIlllI, 10.0F, 1.0F, (new Color(20, 20, 20, 180)).getRGB(), (new Color(10, 10, 10, 200)).getRGB());
        }

        GlStateManager.disableDepth();
        if (mode.equalsIgnoreCase("Percentage")) {
            llllIIllllIlIll += (float) (getWidth(getHealth(player)) + getWidth(" %") - 1);
        } else {
            llllIIllllIlIll += (float) (getWidth(getHealth(player)) + getWidth(" ") - 1);
        }

        drawString(getPlayerName(player), llllIIllllIllIl - llllIIllllIlIll, 0.0F, 16777215);

        int blendColor;
        if (player.getHealth() > 10.0F) {
            blendColor = RenderUtils.blend(new Color(-16711936), new Color(-256), (1.0F / player.getHealth() / 2.0F * (player.getHealth() - 10.0F))).getRGB();
        } else {
            blendColor = RenderUtils.blend(new Color(-256), new Color(-65536), 0.1F * player.getHealth()).getRGB();
        }

        if (mode.equalsIgnoreCase("Percentage")) {
            drawString(getHealth(player) + "%", llllIIllllIllIl - (float) getWidth(getHealth(player) + " %"), 0.0F, blendColor);
        } else {
            drawString(getHealth(player), llllIIllllIllIl - (float) getWidth(getHealth(player) + " "), 0.0F, blendColor);
        }

        GlStateManager.enableDepth();
    }

    private void drawString(String string, float x, float y, int z) {
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(string, x, y, z);
    }

    private int getWidth(String string) {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(string);
    }

    private void startDrawing(float x, float y, float z, EntityPlayer player) {
        float rotateX = Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1.0F : 1.0F;
        double scaleRatio = (double) (getSize(player) / 10.0F * scale) * 1.5D;
        GL11.glPushMatrix();
        RenderUtils.startDrawing();
        GL11.glTranslatef(x, y, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(Minecraft.getMinecraft().getRenderManager().playerViewX, rotateX, 0.0F, 0.0F);
        GL11.glScaled(-0.01666666753590107D * scaleRatio, -0.01666666753590107D * scaleRatio, 0.01666666753590107D * scaleRatio);
    }

    private void stopDrawing() {
        RenderUtils.stopDrawing();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private void renderNametag(EntityPlayer player, float x, float y, float z) {
        y += (float) (1.55D + (player.isSneaking() ? 0.5D : 0.7D));
        startDrawing(x, y, z, player);
        drawNames(player);
        GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
        if (armor) {
            renderArmor(player);
        }

        stopDrawing();
    }

    private void renderArmor(EntityPlayer player) {
        ItemStack[] armor = player.inventory.armorInventory;
        int pos = 0;

        for (ItemStack is : armor) {
            if (is != null) {
                pos -= 8;
            }
        }

        if (player.getHeldItem() != null) {
            pos -= 8;
            ItemStack var10 = player.getHeldItem().copy();
            if (var10.hasEffect() && (var10.getItem() instanceof ItemTool || var10.getItem() instanceof ItemArmor)) {
                var10.stackSize = 1;
            }

            renderItemStack(var10, pos, -20);
            pos += 16;
        }

        armor = player.inventory.armorInventory;

        for (int i = 3; i >= 0; --i) {
            ItemStack var11 = armor[i];
            if (var11 != null) {
                renderItemStack(var11, pos, -20);
                pos += 16;
            }
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private String getPlayerName(EntityPlayer player) {
        boolean isDistanceSettingToggled = distanceSetting.isToggled();
        return (isDistanceSettingToggled ? (new DecimalFormat("#.##")).format(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(player)) + "m " : "") + player.getDisplayName().getFormattedText();
    }

    private float getSize(EntityPlayer player) {
        return Minecraft.getMinecraft().thePlayer.getDistanceToEntity(player) / 4.0F <= 2.0F ? 2.0F : Minecraft.getMinecraft().thePlayer.getDistanceToEntity(player) / 4.0F;
    }

    private void renderItemStack(ItemStack is, int xPos, int yPos) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().zLevel = -150.0F;
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(is, xPos, yPos);
        Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, is, xPos, yPos);
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0F;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.disableDepth();
        renderEnchantText(is, xPos, yPos);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.popMatrix();
    }

    private void renderEnchantText(ItemStack is, int xPos, int yPos) {
        int newYPos = yPos - 24;
        if (is.getEnchantmentTagList() != null && is.getEnchantmentTagList().tagCount() >= 6) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("god", (float) (xPos * 2), (float) newYPos, 16711680);
        } else {
            if (is.getItem() instanceof ItemArmor) {
                int protection = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, is);
                int projectileProtection = EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, is);
                int blastProtectionLvL = EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, is);
                int fireProtection = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, is);
                int thornsLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, is);
                int unbreakingLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, is);
                int remainingDurability = is.getMaxDamage() - is.getItemDamage();
                if (dura) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(String.valueOf(remainingDurability), (float) (xPos * 2), (float) yPos, 16777215);
                }

                if (protection > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("prot" + protection, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (projectileProtection > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("proj" + projectileProtection, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (blastProtectionLvL > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("bp" + blastProtectionLvL, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (fireProtection > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("frp" + fireProtection, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (thornsLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("th" + thornsLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (unbreakingLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("unb" + unbreakingLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }
            }

            if (is.getItem() instanceof ItemBow) {
                int powerLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, is);
                int punchLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, is);
                int flameLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, is);
                int unbreakingLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, is);
                if (powerLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("pow" + powerLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (punchLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("pun" + punchLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (flameLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("flame" + flameLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (unbreakingLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("unb" + unbreakingLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }
            }

            if (is.getItem() instanceof ItemSword) {
                int sharpnessLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, is);
                int knockbackLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, is);
                int fireAspectLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, is);
                int unbreakingLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, is);
                if (sharpnessLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("sh" + sharpnessLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (knockbackLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("kb" + knockbackLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (fireAspectLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("fire" + fireAspectLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (unbreakingLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("unb" + unbreakingLvl, (float) (xPos * 2), (float) newYPos, -1);
                }
            }

            if (is.getItem() instanceof ItemTool) {
                int unbreakingLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, is);
                int efficiencyLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, is);
                int fortuneLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, is);
                int silkTouchLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, is);
                if (efficiencyLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("eff" + efficiencyLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (fortuneLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("fo" + fortuneLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (silkTouchLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("silk" + silkTouchLvl, (float) (xPos * 2), (float) newYPos, -1);
                    newYPos += 8;
                }

                if (unbreakingLvl > 0) {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("ub" + unbreakingLvl, (float) (xPos * 2), (float) newYPos, -1);
                }
            }

            if (is.getItem() == Items.golden_apple && is.hasEffect()) {
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("god", (float) (xPos * 2), (float) newYPos, -1);
            }

        }
    }
}
