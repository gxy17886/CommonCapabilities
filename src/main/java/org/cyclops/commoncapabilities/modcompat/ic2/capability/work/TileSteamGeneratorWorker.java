package org.cyclops.commoncapabilities.modcompat.ic2.capability.work;

import ic2.core.block.machine.tileentity.TileEntitySteamGenerator;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;

/**
 * Worker capability for {@link TileEntitySteamGenerator}.
 * @author rubensworks
 */
public class TileSteamGeneratorWorker implements IWorker {

    private final TileEntitySteamGenerator host;

    public TileSteamGeneratorWorker(TileEntitySteamGenerator host) {
        this.host = host;
    }

    @Override
    public boolean hasWork() {
        return host.getActive() || (host.waterTank.getFluidAmount() > 0);
    }

    @Override
    public boolean canWork() {
        return host.getActive() || (!host.isCalcified());
    }

}
