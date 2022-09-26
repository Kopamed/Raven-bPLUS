package keystrokesmod.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class CombatUtils {
    public static boolean canTarget(Entity entity, boolean idk) {
        if (entity != null && entity != Minecraft.getMinecraft().thePlayer) {
            EntityLivingBase entityLivingBase = null;

            if (entity instanceof EntityLivingBase) {
                entityLivingBase = (EntityLivingBase) entity;
            }

            boolean isTeam = isTeam(Minecraft.getMinecraft().thePlayer, entity);
            boolean isVisible = (!entity.isInvisible());

            return !(entity instanceof EntityArmorStand) && isVisible
                    && (entity instanceof EntityPlayer && !isTeam && !idk || entity instanceof EntityAnimal
                            || entity instanceof EntityMob
                            || entity instanceof EntityLivingBase && entityLivingBase.isEntityAlive());
        } else {
            return false;
        }
    }

    public static boolean isTeam(EntityPlayer player, Entity entity) {
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).getTeam() != null && player.getTeam() != null) {
            Character entity_3 = entity.getDisplayName().getFormattedText().charAt(3);
            Character player_3 = player.getDisplayName().getFormattedText().charAt(3);
            Character entity_2 = entity.getDisplayName().getFormattedText().charAt(2);
            Character player_2 = player.getDisplayName().getFormattedText().charAt(2);
            boolean isTeam = false;
            if (entity_3.equals(player_3) && entity_2.equals(player_2)) {
                isTeam = true;
            } else {
                Character entity_1 = entity.getDisplayName().getFormattedText().charAt(1);
                Character player_1 = player.getDisplayName().getFormattedText().charAt(1);
                Character entity_0 = entity.getDisplayName().getFormattedText().charAt(0);
                Character player_0 = player.getDisplayName().getFormattedText().charAt(0);
                if (entity_1.equals(player_1) && Character.isDigit(0) && entity_0.equals(player_0)) {
                    isTeam = true;
                }
            }

            return isTeam;
        } else {
            return true;
        }
    }

}
