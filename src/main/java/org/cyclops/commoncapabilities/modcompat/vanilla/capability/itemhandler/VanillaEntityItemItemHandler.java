package org.cyclops.commoncapabilities.modcompat.vanilla.capability.itemhandler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.VanillaEntityItemCapabilityDelegator;

import javax.annotation.Nonnull;

/**
 * An item handler for entity items that have an item handler.
 * @author rubensworks
 */
public class VanillaEntityItemItemHandler extends VanillaEntityItemCapabilityDelegator<IItemHandler> implements IItemHandler {

    public VanillaEntityItemItemHandler(EntityItem entity, EnumFacing side) {
        super(entity, side);
    }

    @Override
    protected Capability<IItemHandler> getCapabilityType() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public int getSlots() {
        IItemHandler itemHandler = getCapability();
        if (itemHandler != null) {
            return itemHandler.getSlots();
        }
        return 0;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        IItemHandler itemHandler = getCapability();
        if (itemHandler != null) {
            return itemHandler.getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        ItemStack innerStack = getItemStack();
        IItemHandler itemHandler = getCapability(innerStack);
        if (itemHandler != null) {
            ItemStack ret = itemHandler.insertItem(slot, stack, simulate);
            if (!simulate) {
                updateItemStack(innerStack);
            }
            return ret;
        }
        return stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack innerStack = getItemStack();
        IItemHandler itemHandler = getCapability(innerStack);
        if (itemHandler != null) {
            ItemStack ret = itemHandler.extractItem(slot, amount, simulate);
            if (!simulate) {
                updateItemStack(innerStack);
            }
            return ret;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        IItemHandler itemHandler = getCapability();
        if (itemHandler != null) {
            return itemHandler.getSlotLimit(slot);
        }
        return 0;
    }
}
