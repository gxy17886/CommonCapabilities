package org.cyclops.commoncapabilities.modcompat.ic2.capability.work;

import ic2.core.block.machine.tileentity.TileEntityChunkloader;
import org.cyclops.commoncapabilities.core.Helpers;
import org.cyclops.commoncapabilities.modcompat.ic2.Ic2Helpers;

import java.util.Set;

/**
 * Worker capability for {@link TileEntityChunkloader}.
 * @author rubensworks
 */
public class TileChunkloaderWorker extends TileElectricMachineWorkerBase<TileEntityChunkloader> {
    public TileChunkloaderWorker(TileEntityChunkloader tile) {
        super(tile);
    }

    @Override
    public boolean hasWork() {
        return super.hasWork() || !((Set) Helpers.getFieldValue(getTile(), Ic2Helpers.FIELD_TILECHUNKLOADER_LOADEDCHUNKS)).isEmpty();
    }

    @Override
    public boolean canWork() {
        return super.canWork() || getEnergy().getEnergy() >= ((Set) Helpers.getFieldValue(getTile(), Ic2Helpers.FIELD_TILECHUNKLOADER_LOADEDCHUNKS)).size();
    }
}
