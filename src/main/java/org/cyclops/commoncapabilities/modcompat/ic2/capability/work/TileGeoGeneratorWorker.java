package org.cyclops.commoncapabilities.modcompat.ic2.capability.work;

import ic2.core.block.comp.Energy;
import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.core.Helpers;
import org.cyclops.commoncapabilities.modcompat.ic2.Ic2Helpers;

/**
 * Worker capability for {@link TileEntityGeoGenerator}.
 * @author rubensworks
 */
public class TileGeoGeneratorWorker implements IWorker {

    private final TileEntityGeoGenerator host;

    public TileGeoGeneratorWorker(TileEntityGeoGenerator host) {
        this.host = host;
    }

    @Override
    public boolean hasWork() {
        Energy energy = Helpers.getFieldValue(host, Ic2Helpers.FIELD_TILEGEOGENERATOR_ENERGY);
        int production = Helpers.getFieldValue(host, Ic2Helpers.FIELD_TILEGEOGENERATOR_PRODUCTION);
        return energy.getEnergy() + production <= energy.getCapacity();
    }

    @Override
    public boolean canWork() {
        return host.getTankAmount() >= 2;
    }
}
