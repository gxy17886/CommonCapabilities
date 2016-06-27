package org.cyclops.commoncapabilities.modcompat.ic2.capability.work;

import ic2.core.block.machine.tileentity.TileEntityMiner;
import ic2.core.item.tool.ItemScanner;
import ic2.core.util.Ic2BlockPos;
import org.cyclops.commoncapabilities.core.Helpers;
import org.cyclops.commoncapabilities.modcompat.ic2.Ic2Helpers;

/**
 * Worker capability for {@link TileEntityMiner}.
 * @author rubensworks
 */
public class TileMinerWorker extends TileElectricMachineWorkerBase<TileEntityMiner> {
    public TileMinerWorker(TileEntityMiner tile) {
        super(tile);
    }

    @Override
    public boolean canWork() {
        return super.canWork()
                || (getEnergy().getEnergy() > 0
                && !getTile().getWorld().isBlockPowered(getTile().getPos())
                && !getTile().scannerSlot.isEmpty()
                && !(getTile().scannerSlot.get().getItem() instanceof ItemScanner && !((ItemScanner)getTile().scannerSlot.get().getItem()).haveChargeforScan(getTile().scannerSlot.get()))
                && !((Ic2BlockPos) Helpers.invokeMethod(getTile(), Ic2Helpers.METHOD_TILEMINER_PROCESSOUTPUTSLOT)).isBelowMap()
        );
    }
}
