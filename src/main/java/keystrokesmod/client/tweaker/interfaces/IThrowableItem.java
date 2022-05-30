package keystrokesmod.client.tweaker.interfaces;

import net.minecraft.item.ItemStack;

public interface IThrowableItem {
    /**
     * Interface
     * @see keystrokesmod.client.tweaker.transformers.TransformerThrowableItem
     * @param is optional (only for ItemPotion)
     * @return true if the item is throwable
     */
    boolean isThrowable(ItemStack is);
}
