package org.cyclops.commoncapabilities.modcompat.vanilla;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.UniversalBucket;
import org.cyclops.commoncapabilities.CommonCapabilities;
import org.cyclops.commoncapabilities.Reference;
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.capability.temperature.TemperatureConfig;
import org.cyclops.commoncapabilities.capability.worker.WorkerConfig;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.temperature.VanillaFurnaceTemperature;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.temperature.VanillaUniversalBucketTemperature;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.work.VanillaBrewingStandWorker;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.work.VanillaFurnaceWorker;
import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.cyclopscore.modcompat.capabilities.CapabilityConstructorRegistry;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityProvider;
import org.cyclops.cyclopscore.modcompat.capabilities.ICapabilityConstructor;
import org.cyclops.cyclopscore.modcompat.capabilities.SimpleCapabilityConstructor;

import javax.annotation.Nullable;

/**
 * Capabilities for Vanilla.
 * @author rubensworks
 */
public class VanillaModCompat implements IModCompat {
    @Override
    public String getModID() {
        return Reference.MOD_VANILLA;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getComment() {
        return "Furnace and Brewing stand capabilities.";
    }

    @Override
    public void onInit(Step initStep) {
        if(initStep == Step.INIT) {
            CapabilityConstructorRegistry registry = CommonCapabilities._instance.getCapabilityConstructorRegistry();
            // Worker
            registry.registerTile(TileEntityFurnace.class,
                    new SimpleCapabilityConstructor<IWorker, TileEntityFurnace>() {
                        @Override
                        public Capability<IWorker> getCapability() {
                            return WorkerConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(TileEntityFurnace host) {
                            return new DefaultCapabilityProvider<>(WorkerConfig.CAPABILITY, new VanillaFurnaceWorker(host));
                        }
                    });
            registry.registerTile(TileEntityBrewingStand.class,
                    new SimpleCapabilityConstructor<IWorker, TileEntityBrewingStand>() {
                        @Override
                        public Capability<IWorker> getCapability() {
                            return WorkerConfig.CAPABILITY;
                        }

                        @Override
                        public ICapabilityProvider createProvider(TileEntityBrewingStand host) {
                            return new DefaultCapabilityProvider<>(WorkerConfig.CAPABILITY, new VanillaBrewingStandWorker(host));
                        }
                    });

            // Temperature
            registry.registerTile(TileEntityFurnace.class,
                    new SimpleCapabilityConstructor<ITemperature, TileEntityFurnace>() {
                        @Override
                        public Capability<ITemperature> getCapability() {
                            return TemperatureConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(TileEntityFurnace host) {
                            return new DefaultCapabilityProvider<>(TemperatureConfig.CAPABILITY, new VanillaFurnaceTemperature(host));
                        }
                    });
            registry.registerItem(UniversalBucket.class,
                    new ICapabilityConstructor<ITemperature, UniversalBucket, ItemStack>() {
                        @Override
                        public Capability<ITemperature> getCapability() {
                            return TemperatureConfig.CAPABILITY;
                        }

                        @Override
                        public ICapabilityProvider createProvider(UniversalBucket hostType, ItemStack host) {
                            return new DefaultCapabilityProvider<>(TemperatureConfig.CAPABILITY, new VanillaUniversalBucketTemperature(host));
                        }
                    });
        }
    }
}
