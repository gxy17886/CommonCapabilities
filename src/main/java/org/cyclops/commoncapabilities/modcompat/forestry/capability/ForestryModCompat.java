package org.cyclops.commoncapabilities.modcompat.forestry.capability;

import forestry.core.config.Constants;
import forestry.core.items.ItemWrench;
import forestry.core.tiles.TileEngine;
import forestry.energy.tiles.TileEngineBiogas;
import forestry.energy.tiles.TileEngineClockwork;
import forestry.energy.tiles.TileEngineElectric;
import forestry.energy.tiles.TileEnginePeat;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.CommonCapabilities;
import org.cyclops.commoncapabilities.Reference;
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature;
import org.cyclops.commoncapabilities.api.capability.wrench.DefaultWrench;
import org.cyclops.commoncapabilities.api.capability.wrench.IWrench;
import org.cyclops.commoncapabilities.capability.temperature.TemperatureConfig;
import org.cyclops.commoncapabilities.capability.wrench.WrenchConfig;
import org.cyclops.commoncapabilities.modcompat.forestry.capability.temperature.TileEngineTemperature;
import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.cyclopscore.modcompat.capabilities.CapabilityConstructorRegistry;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityProvider;
import org.cyclops.cyclopscore.modcompat.capabilities.ICapabilityConstructor;
import org.cyclops.cyclopscore.modcompat.capabilities.SimpleCapabilityConstructor;

import javax.annotation.Nullable;

/**
 * Capabilities for EnderIO.
 * @author rubensworks
 */
public class ForestryModCompat implements IModCompat {
    @Override
    public String getModID() {
        return Reference.MOD_FORESTRY;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getComment() {
        return "Worker and Temperature capabilities for Forestry machines.";
    }

    @Override
    public void onInit(Step initStep) {
        if(initStep == Step.INIT) {
            CapabilityConstructorRegistry registry = CommonCapabilities._instance.getCapabilityConstructorRegistry();
            // Temperature
            registerEngineTemperature(registry, TileEngineBiogas.class, ITemperature.ZERO_CELCIUS + Constants.ENGINE_BRONZE_HEAT_MAX);
            registerEngineTemperature(registry, TileEngineClockwork.class, ITemperature.ZERO_CELCIUS + 300000);
            registerEngineTemperature(registry, TileEngineElectric.class, ITemperature.ZERO_CELCIUS + Constants.ENGINE_ELECTRIC_HEAT_MAX);
            registerEngineTemperature(registry, TileEnginePeat.class, ITemperature.ZERO_CELCIUS + Constants.ENGINE_COPPER_HEAT_MAX);

            // Wrench
            registry.registerItem(ItemWrench.class,
                    new ICapabilityConstructor<IWrench, ItemWrench, ItemStack>() {
                        @Override
                        public Capability<IWrench> getCapability() {
                            return WrenchConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(ItemWrench hostType, final ItemStack host) {
                            return new DefaultCapabilityProvider<>(WrenchConfig.CAPABILITY, new DefaultWrench());
                        }
                    });
        }
    }

    protected static <T extends TileEngine> void registerEngineTemperature(
            CapabilityConstructorRegistry registry, Class<T> clazz, final double maximumTemperature) {
        registry.registerTile(clazz,
                new SimpleCapabilityConstructor<ITemperature, T>() {
                    @Override
                    public Capability<ITemperature> getCapability() {
                        return TemperatureConfig.CAPABILITY;
                    }

                    @Nullable
                    @Override
                    public ICapabilityProvider createProvider(T host) {
                        return new DefaultCapabilityProvider<>(TemperatureConfig.CAPABILITY,
                                new TileEngineTemperature(host, maximumTemperature,
                                        ITemperature.ZERO_CELCIUS, ITemperature.ZERO_CELCIUS));
                    }
                });
    }
}
