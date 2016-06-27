package org.cyclops.commoncapabilities.modcompat.ic2.capability.work;

import ic2.core.block.machine.tileentity.TileEntityAdvMiner;
import ic2.core.item.tool.ItemScanner;

/**
 * Worker capability for {@link TileEntityAdvMiner}.
 * @author rubensworks
 */
public class TileAdvMinerWorker extends TileElectricMachineWorkerBase<TileEntityAdvMiner> {
    public TileAdvMinerWorker(TileEntityAdvMiner tile) {
        super(tile);
    }

    @Override
    public boolean canWork() {
        return super.canWork()
                || (getEnergy().getEnergy() >= getTile().energyConsume
                && !getTile().getWorld().isBlockPowered(getTile().getPos())
                && !getTile().scannerSlot.isEmpty()
                && !(getTile().scannerSlot.get().getItem() instanceof ItemScanner && !((ItemScanner)getTile().scannerSlot.get().getItem()).haveChargeforScan(getTile().scannerSlot.get()))
        );
    }
}
