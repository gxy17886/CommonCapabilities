package org.cyclops.commoncapabilities.modcompat.forestry;

import forestry.core.config.Constants;
import forestry.core.items.ItemWrench;
import forestry.core.tiles.TileAnalyzer;
import forestry.core.tiles.TileEngine;
import forestry.core.tiles.TilePowered;
import forestry.energy.tiles.TileEngineBiogas;
import forestry.energy.tiles.TileEngineClockwork;
import forestry.energy.tiles.TileEngineElectric;
import forestry.energy.tiles.TileEnginePeat;
import forestry.factory.tiles.*;
import forestry.farming.tiles.TileFarmGearbox;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.ModAPIManager;
import org.cyclops.commoncapabilities.CommonCapabilities;
import org.cyclops.commoncapabilities.Reference;
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.api.capability.wrench.DefaultWrench;
import org.cyclops.commoncapabilities.api.capability.wrench.IWrench;
import org.cyclops.commoncapabilities.capability.temperature.TemperatureConfig;
import org.cyclops.commoncapabilities.capability.worker.WorkerConfig;
import org.cyclops.commoncapabilities.capability.wrench.WrenchConfig;
import org.cyclops.commoncapabilities.modcompat.forestry.capability.temperature.TileEngineTemperature;
import org.cyclops.commoncapabilities.modcompat.forestry.capability.tesla.ForestryTeslaIntegration;
import org.cyclops.commoncapabilities.modcompat.forestry.capability.work.*;
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
        return "Worker, Wrench and Temperature capabilities for Forestry machines.";
    }

    @Override
    public void onInit(Step initStep) {
        if(initStep == Step.INIT) {
            if (ModAPIManager.INSTANCE.hasAPI(Reference.MOD_TESLA_API)) {
                ForestryTeslaIntegration.load();
            }
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

            // Worker
            registerTilePoweredWorker(registry, TileStill.class);
            registerTilePoweredWorker(registry, TileCarpenter.class);
            registerTilePoweredWorker(registry, TileFermenter.class);
            registerTilePoweredWorker(registry, TileSqueezer.class);
            registerTilePoweredWorker(registry, TileBottler.class);
            registerTilePoweredWorker(registry, TileCentrifuge.class);
            registerTilePoweredWorker(registry, TileAnalyzer.class);
            registerTilePoweredWorker(registry, TileFabricator.class);

            registerTileEngineWorker(registry, TileEngineBiogas.class);
            registerTileEngineWorker(registry, TileEngineClockwork.class);
            registerTileEngineWorker(registry, TileEngineElectric.class);
            registerTileEngineWorker(registry, TileEnginePeat.class);

            registry.registerTile(TileMoistener.class,
                    new SimpleCapabilityConstructor<IWorker, TileMoistener>() {
                        @Override
                        public Capability<IWorker> getCapability() {
                            return WorkerConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(TileMoistener host) {
                            return new DefaultCapabilityProvider<>(WorkerConfig.CAPABILITY, new TileMoistenerWorker(host));
                        }
                    });
            registry.registerTile(TileMillRainmaker.class,
                    new SimpleCapabilityConstructor<IWorker, TileMillRainmaker>() {
                        @Override
                        public Capability<IWorker> getCapability() {
                            return WorkerConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(TileMillRainmaker host) {
                            return new DefaultCapabilityProvider<>(WorkerConfig.CAPABILITY, new TileRainmakerWorker(host));
                        }
                    });
            registry.registerTile(TileRaintank.class,
                    new SimpleCapabilityConstructor<IWorker, TileRaintank>() {
                        @Override
                        public Capability<IWorker> getCapability() {
                            return WorkerConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(TileRaintank host) {
                            return new DefaultCapabilityProvider<>(WorkerConfig.CAPABILITY, new TileRaintankWorker(host));
                        }
                    });
            registry.registerTile(TileFarmGearbox.class,
                    new SimpleCapabilityConstructor<IWorker, TileFarmGearbox>() {
                        @Override
                        public Capability<IWorker> getCapability() {
                            return WorkerConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(TileFarmGearbox host) {
                            return new DefaultCapabilityProvider<>(WorkerConfig.CAPABILITY, new TileFarmGearboxWorker(host));
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

    protected static <T extends TilePowered> void registerTilePoweredWorker(
            CapabilityConstructorRegistry registry, Class<T> clazz) {
        registry.registerTile(clazz,
                new SimpleCapabilityConstructor<IWorker, T>() {
                    @Override
                    public Capability<IWorker> getCapability() {
                        return WorkerConfig.CAPABILITY;
                    }

                    @Nullable
                    @Override
                    public ICapabilityProvider createProvider(T host) {
                        return new DefaultCapabilityProvider<>(WorkerConfig.CAPABILITY, new TilePoweredWorker(host));
                    }
                });
    }

    protected static <T extends TileEngine> void registerTileEngineWorker(
            CapabilityConstructorRegistry registry, Class<T> clazz) {
        registry.registerTile(clazz,
                new SimpleCapabilityConstructor<IWorker, T>() {
                    @Override
                    public Capability<IWorker> getCapability() {
                        return WorkerConfig.CAPABILITY;
                    }

                    @Nullable
                    @Override
                    public ICapabilityProvider createProvider(T host) {
                        return new DefaultCapabilityProvider<>(WorkerConfig.CAPABILITY, new TileEngineWorker(host));
                    }
                });
    }
}
