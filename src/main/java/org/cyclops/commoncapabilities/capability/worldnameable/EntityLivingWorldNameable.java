package org.cyclops.commoncapabilities.capability.worldnameable;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IWorldNameable;

/**
 * EntityLiving {@link IWorldNameable} wrapper.
 * @author rubensworks
 */
public class EntityLivingWorldNameable implements IWorldNameable {

    private final EntityLiving entityLiving;

    public EntityLivingWorldNameable(EntityLiving entityLiving) {
        this.entityLiving = entityLiving;
    }

    @Override
    public String getName() {
        return entityLiving.getName();
    }

    @Override
    public boolean hasCustomName() {
        return entityLiving.hasCustomName();
    }

    @Override
    public IChatComponent getDisplayName() {
        return entityLiving.getDisplayName();
    }
}
