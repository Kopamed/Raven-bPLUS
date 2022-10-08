package keystrokesmod.client.utils.collection;

import net.minecraft.entity.player.EntityPlayer;

@FunctionalInterface
public interface SortValue {
    Float value(EntityPlayer player);
}
