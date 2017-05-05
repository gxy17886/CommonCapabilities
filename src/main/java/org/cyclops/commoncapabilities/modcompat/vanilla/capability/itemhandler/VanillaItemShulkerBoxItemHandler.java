package org.cyclops.commoncapabilities.modcompat.vanilla.capability.itemhandler;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import org.cyclops.cyclopscore.helper.ItemStackHelpers;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;

import javax.annotation.Nonnull;

/**
 * An item handler wrapper for the shulker box in item form.
 * @author rubensworks
 */
public class VanillaItemShulkerBoxItemHandler implements IItemHandlerModifiable {

    private final ItemStack itemStack;

    public VanillaItemShulkerBoxItemHandler(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    protected NonNullList<ItemStack> getItemList() {
        NonNullList<ItemStack> itemStacks = NonNullList.withSize(getSlots(), ItemStack.EMPTY);
        NBTTagCompound rootTag = itemStack.getTagCompound();
        if (rootTag != null && rootTag.hasKey("BlockEntityTag", MinecraftHelpers.NBTTag_Types.NBTTagCompound.ordinal())) {
            NBTTagCompound entityTag = rootTag.getCompoundTag("BlockEntityTag");
            if (entityTag.hasKey("Items", MinecraftHelpers.NBTTag_Types.NBTTagList.ordinal())) {
                ItemStackHelper.loadAllItems(entityTag, itemStacks);
            }
        }
        return itemStacks;
    }

    protected void setItemList(NonNullList<ItemStack> itemStacks) {
        NBTTagCompound rootTag = ItemStackHelpers.getSafeTagCompound(itemStack);
        if (!rootTag.hasKey("BlockEntityTag", MinecraftHelpers.NBTTag_Types.NBTTagCompound.ordinal())) {
            rootTag.setTag("BlockEntityTag", new NBTTagCompound());
        }
        ItemStackHelper.saveAllItems(rootTag.getCompoundTag("BlockEntityTag"), itemStacks);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        NonNullList<ItemStack> itemStacks = getItemList();
        itemStacks.set(slot, stack);
        setItemList(itemStacks);
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return getItemList().get(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        NonNullList<ItemStack> itemStacks = getItemList();
        ItemStack existingStack = itemStacks.get(slot);

        int maxStackSize;
        if (!existingStack.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existingStack))
                return stack;

            maxStackSize = Math.min(stack.getMaxStackSize(), getSlotLimit(slot)) - existingStack.getCount();

            if (stack.getCount() <= maxStackSize) {
                if (!simulate) {
                    ItemStack copy = stack.copy();
                    copy.grow(existingStack.getCount());
                    setStackInSlot(slot, copy);
                }

                return ItemStack.EMPTY;
            } else  {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate) {
                    ItemStack copy = stack.splitStack(maxStackSize);
                    copy.grow(existingStack.getCount());
                    setStackInSlot(slot, copy);
                    return stack;
                }  else {
                    stack.shrink(maxStackSize);
                    return stack;
                }
            }
        } else {
            maxStackSize = Math.min(stack.getMaxStackSize(), getSlotLimit(slot));
            if (maxStackSize < stack.getCount()) {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate) {
                    setStackInSlot(slot, stack.splitStack(maxStackSize));
                    return stack;
                } else {
                    stack.shrink(maxStackSize);
                    return stack;
                }
            } else {
                if (!simulate) {
                    setStackInSlot(slot, stack);
                }
                return ItemStack.EMPTY;
            }
        }
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        ItemStack existingStack = getStackInSlot(slot);

        if (existingStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack extracted = existingStack.splitStack(amount);
        if (!simulate) {
            setStackInSlot(slot, ItemStack.EMPTY);
        }
        return extracted;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }
}
