package org.cyclops.commoncapabilities.capability.worldnameable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorldNameable;

/**
 * ItemStack {@link IWorldNameable} wrapper.
 * @author rubensworks
 */
public class ItemStackWorldNameable implements IWorldNameable {

    private final ItemStack itemStack;

    public ItemStackWorldNameable(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public String getName() {
        return itemStack.getDisplayName();
    }

    @Override
    public boolean hasCustomName() {
        return itemStack.hasDisplayName();
    }

    @Override
    public ITextComponent getDisplayName() {
        return itemStack.getTextComponent();
    }
}
