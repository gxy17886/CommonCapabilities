package org.cyclops.commoncapabilities.modcompat.vanilla.capability.energystorage;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.VanillaEntityItemCapabilityDelegator;

/**
 * An energy handler for entity items that have an energy handler.
 * @author rubensworks
 */
public class VanillaEntityItemEnergyStorage extends VanillaEntityItemCapabilityDelegator<IEnergyStorage> implements IEnergyStorage {

    public VanillaEntityItemEnergyStorage(EntityItem entity, EnumFacing side) {
        super(entity, side);
    }

    @Override
    protected Capability<IEnergyStorage> getCapabilityType() {
        return CapabilityEnergy.ENERGY;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        ItemStack itemStack = getItemStack();
        IEnergyStorage energyStorage = getCapability(itemStack);
        if (energyStorage != null) {
            int ret = energyStorage.receiveEnergy(maxReceive, simulate);
            updateItemStack(itemStack);
            return ret;
        }
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        ItemStack itemStack = getItemStack();
        IEnergyStorage energyStorage = getCapability(itemStack);
        if (energyStorage != null) {
            int ret = energyStorage.extractEnergy(maxExtract, simulate);
            updateItemStack(itemStack);
            return ret;
        }
        return 0;
    }

    @Override
    public int getEnergyStored() {
        IEnergyStorage energyStorage = getCapability();
        if (energyStorage != null) {
            return energyStorage.getEnergyStored();
        }
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        IEnergyStorage energyStorage = getCapability();
        if (energyStorage != null) {
            return energyStorage.getMaxEnergyStored();
        }
        return 0;
    }

    @Override
    public boolean canExtract() {
        IEnergyStorage energyStorage = getCapability();
        return energyStorage != null && energyStorage.canExtract();
    }

    @Override
    public boolean canReceive() {
        IEnergyStorage energyStorage = getCapability();
        return energyStorage != null && energyStorage.canReceive();
    }
}
