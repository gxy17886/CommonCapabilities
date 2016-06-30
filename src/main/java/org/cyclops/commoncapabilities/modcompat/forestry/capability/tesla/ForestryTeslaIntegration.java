package org.cyclops.commoncapabilities.modcompat.forestry.capability.tesla;

import forestry.core.tiles.TileEngine;
import forestry.energy.tiles.TileEngineBiogas;
import forestry.energy.tiles.TileEngineClockwork;
import forestry.energy.tiles.TileEngineElectric;
import forestry.energy.tiles.TileEnginePeat;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.CommonCapabilities;
import org.cyclops.cyclopscore.modcompat.capabilities.CapabilityConstructorRegistry;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultSidedCapabilityProvider;
import org.cyclops.cyclopscore.modcompat.capabilities.SimpleCapabilityConstructor;

import javax.annotation.Nullable;

/**
 * Loader for Forestry Tesla capabilities.
 * @author rubensworks
 */
public class ForestryTeslaIntegration {

    public static void load() {
        CapabilityConstructorRegistry registry = CommonCapabilities._instance.getCapabilityConstructorRegistry();

        // Tiles
        registerTileEngineTeslaHolder(registry, TileEngineBiogas.class);
        registerTileEngineTeslaHolder(registry, TileEngineClockwork.class);
        registerTileEngineTeslaHolder(registry, TileEngineElectric.class);
        registerTileEngineTeslaHolder(registry, TileEnginePeat.class);
    }

    protected static <T extends TileEngine> void registerTileEngineTeslaHolder(
            CapabilityConstructorRegistry registry, Class<T> clazz) {
        registry.registerTile(clazz,
                new SimpleCapabilityConstructor<ITeslaHolder, T>() {
                    @Override
                    public Capability<ITeslaHolder> getCapability() {
                        return TeslaCapabilities.CAPABILITY_HOLDER;
                    }

                    @Nullable
                    @Override
                    public ICapabilityProvider createProvider(final TileEngine host) {
                        return new DefaultSidedCapabilityProvider<>(DefaultSidedCapabilityProvider.forAllSides(
                                TeslaCapabilities.CAPABILITY_HOLDER,
                                new DefaultSidedCapabilityProvider.ISidedCapabilityConstructor<ITeslaHolder>() {
                                    @Override
                                    public ITeslaHolder createForSide(EnumFacing side) {
                                        return new HolderTileEngine(side, host);
                                    }
                                }));
                    }
                });
    }

}
