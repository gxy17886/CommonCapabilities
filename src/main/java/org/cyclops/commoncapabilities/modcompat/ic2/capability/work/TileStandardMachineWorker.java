package org.cyclops.commoncapabilities.modcompat.ic2.capability.work;

import forestry.core.errors.EnumErrorCode;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;

/**
 * Worker capability for TileEntityStandardMachine machines.
 * @author rubensworks
 */
public class TileStandardMachineWorker implements IWorker {

    private final TileEntityStandardMachine tile;

    public TileStandardMachineWorker(TileEntityStandardMachine tile) {
        this.tile = tile;
    }

    @Override
    public boolean hasWork() {
        return tile.getActive() || tile.getOutput() != null;
    }

    @Override
    public boolean canWork() {
        return tile.getActive() || tile.getEnergy() >= tile.energyConsume;
    }

}
