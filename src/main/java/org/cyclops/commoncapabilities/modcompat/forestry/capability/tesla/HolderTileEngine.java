package org.cyclops.commoncapabilities.modcompat.forestry.capability.tesla;

import forestry.core.tiles.TileEngine;
import net.darkhax.tesla.api.ITeslaHolder;
import net.minecraft.util.EnumFacing;

/**
 * Tesla holder wrapper for {@link TileEngine}.
 * @author rubensworks
 */
public class HolderTileEngine implements ITeslaHolder {

    private final EnumFacing side;
    private final TileEngine tileEngine;

    public HolderTileEngine(EnumFacing side, TileEngine tileEngine) {
        this.side = side;
        this.tileEngine = tileEngine;
    }

    @Override
    public long getStoredPower() {
        return tileEngine.getEnergyManager().getEnergyStored(side);
    }

    @Override
    public long getCapacity() {
        return tileEngine.getEnergyManager().getMaxEnergyStored(side);
    }
}
