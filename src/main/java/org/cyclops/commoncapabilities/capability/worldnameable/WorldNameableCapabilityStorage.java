package org.cyclops.commoncapabilities.capability.worldnameable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IWorldNameable;
import net.minecraftforge.common.capabilities.Capability;
import org.cyclops.commoncapabilities.api.capability.worldnameable.DefaultWorldNameable;

/**
 * A storage implementation for the {@link IWorldNameable} capability.
 * @author rubensworks
 */
public class WorldNameableCapabilityStorage implements Capability.IStorage<IWorldNameable> {
    @Override
    public NBTBase writeNBT(Capability<IWorldNameable> capability, IWorldNameable instance, EnumFacing side) {
        return instance.hasCustomName() ? new NBTTagString(instance.getName()) : null;
    }

    @Override
    public void readNBT(Capability<IWorldNameable> capability, IWorldNameable instance, EnumFacing side, NBTBase nbt) {
        if(nbt != null) {
            ((DefaultWorldNameable) instance).setName(((NBTTagString) nbt).getString());
        }
    }
}
