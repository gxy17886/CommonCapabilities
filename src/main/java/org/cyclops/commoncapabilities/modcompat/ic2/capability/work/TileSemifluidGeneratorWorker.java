package org.cyclops.commoncapabilities.modcompat.ic2.capability.work;

import ic2.core.block.comp.Energy;
import ic2.core.block.generator.tileentity.TileEntitySemifluidGenerator;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.core.Helpers;
import org.cyclops.commoncapabilities.modcompat.ic2.Ic2Helpers;

/**
 * Worker capability for {@link TileEntitySemifluidGenerator}.
 * @author rubensworks
 */
public class TileSemifluidGeneratorWorker implements IWorker {

    private final TileEntitySemifluidGenerator host;

    public TileSemifluidGeneratorWorker(TileEntitySemifluidGenerator host) {
        this.host = host;
    }

    @Override
    public boolean hasWork() {
        Energy energy = Helpers.getFieldValue(host, Ic2Helpers.FIELD_TILESEMIFLUIDGENERATOR_ENERGY);
        double production = Helpers.getFieldValue(host, Ic2Helpers.FIELD_TILESEMIFLUIDGENERATOR_PRODUCTION);
        return energy.getEnergy() + production <= energy.getCapacity();
    }

    @Override
    public boolean canWork() {
        return host.getTankAmount() > 0;
    }
}
