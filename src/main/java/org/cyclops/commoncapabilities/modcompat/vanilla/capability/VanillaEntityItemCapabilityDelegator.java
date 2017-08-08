package org.cyclops.commoncapabilities.modcompat.vanilla.capability;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * An abstract capability capability delegator from entity item to inner itemstack.
 * @param <C> The capability type.
 * @author rubensworks
 */
public abstract class VanillaEntityItemCapabilityDelegator<C> {

    private final EntityItem entity;
    private final EnumFacing side;

    public VanillaEntityItemCapabilityDelegator(EntityItem entity, EnumFacing side) {
        this.entity = entity;
        this.side = side;
    }

    public EntityItem getEntity() {
        return entity;
    }

    public EnumFacing getSide() {
        return side;
    }

    protected ItemStack getItemStack() {
        return entity.getEntityItem();
    }

    protected void updateItemStack(ItemStack itemStack) {
        entity.setEntityItemStack(itemStack);
    }

    protected abstract Capability<C> getCapabilityType();

    @Nullable
    protected C getCapability(ItemStack itemStack) {
        if (itemStack.hasCapability(getCapabilityType(), getSide())) {
            return itemStack.getCapability(getCapabilityType(), getSide());
        }
        return null;
    }

    @Nullable
    protected C getCapability() {
        return getCapability(getItemStack());
    }
}
