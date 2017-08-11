package org.cyclops.commoncapabilities.modcompat.vanilla;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.cyclops.commoncapabilities.CommonCapabilities;
import org.cyclops.commoncapabilities.Reference;
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.capability.temperature.TemperatureConfig;
import org.cyclops.commoncapabilities.capability.worker.WorkerConfig;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.energystorage.VanillaEntityItemEnergyStorage;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.energystorage.VanillaEntityItemFrameEnergyStorage;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.fluidhandler.VanillaEntityItemFluidHandler;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.fluidhandler.VanillaEntityItemFrameFluidHandler;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.itemhandler.VanillaEntityItemFrameItemHandler;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.itemhandler.VanillaEntityItemItemHandler;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.itemhandler.VanillaItemShulkerBoxItemHandler;
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

            // ItemHandler
            registry.registerItem(ItemShulkerBox.class,
                    new ICapabilityConstructor<IItemHandler, ItemShulkerBox, ItemStack>() {
                        @Override
                        public Capability<IItemHandler> getCapability() {
                            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(ItemShulkerBox hostType, ItemStack host) {
                            return new DefaultCapabilityProvider<>(getCapability(), new VanillaItemShulkerBoxItemHandler(host));
                        }
                    });
            registry.registerEntity(EntityItem.class,
                    new ICapabilityConstructor<IItemHandler, EntityItem, EntityItem>() {
                        @Override
                        public Capability<IItemHandler> getCapability() {
                            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(EntityItem hostType, final EntityItem host) {
                            return new ICapabilityProvider() {
                                @Override
                                public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                                            && host.getItem().hasCapability(capability, facing);
                                }

                                @Nullable
                                @Override
                                public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                                            && host.getItem().hasCapability(capability, facing)
                                            ? (T) new VanillaEntityItemItemHandler(host, facing) : null;
                                }
                            };
                        }
                    });
            registry.registerEntity(EntityItemFrame.class,
                    new ICapabilityConstructor<IItemHandler, EntityItemFrame, EntityItemFrame>() {
                        @Override
                        public Capability<IItemHandler> getCapability() {
                            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(EntityItemFrame hostType, final EntityItemFrame host) {
                            return new ICapabilityProvider() {
                                @Override
                                public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                                            && host.getDisplayedItem().hasCapability(capability, facing);
                                }

                                @Nullable
                                @Override
                                public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                                            && host.getDisplayedItem().hasCapability(capability, facing)
                                            ? (T) new VanillaEntityItemFrameItemHandler(host, facing) : null;
                                }
                            };
                        }
                    });

            // FluidHandler
            registry.registerEntity(EntityItem.class,
                    new ICapabilityConstructor<IFluidHandler, EntityItem, EntityItem>() {
                        @Override
                        public Capability<IFluidHandler> getCapability() {
                            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(EntityItem hostType, final EntityItem host) {
                            return new ICapabilityProvider() {
                                @Override
                                public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                                            && host.getItem().hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, facing);
                                }

                                @Nullable
                                @Override
                                public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                                            && host.getItem().hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, facing)
                                            ? (T) new VanillaEntityItemFluidHandler(host, facing) : null;
                                }
                            };
                        }
                    });
            registry.registerEntity(EntityItemFrame.class,
                    new ICapabilityConstructor<IFluidHandler, EntityItemFrame, EntityItemFrame>() {
                        @Override
                        public Capability<IFluidHandler> getCapability() {
                            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(EntityItemFrame hostType, final EntityItemFrame host) {
                            return new ICapabilityProvider() {
                                @Override
                                public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                                            && host.getDisplayedItem().hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, facing);
                                }

                                @Nullable
                                @Override
                                public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                                            && host.getDisplayedItem().hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, facing)
                                            ? (T) new VanillaEntityItemFrameFluidHandler(host, facing) : null;
                                }
                            };
                        }
                    });

            // EnergyStorage
            registry.registerEntity(EntityItem.class,
                    new ICapabilityConstructor<IEnergyStorage, EntityItem, EntityItem>() {
                        @Override
                        public Capability<IEnergyStorage> getCapability() {
                            return CapabilityEnergy.ENERGY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(EntityItem hostType, final EntityItem host) {
                            return new ICapabilityProvider() {
                                @Override
                                public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityEnergy.ENERGY
                                            && host.getItem().hasCapability(capability, facing);
                                }

                                @Nullable
                                @Override
                                public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityEnergy.ENERGY
                                            && host.getItem().hasCapability(capability, facing)
                                            ? (T) new VanillaEntityItemEnergyStorage(host, facing) : null;
                                }
                            };
                        }
                    });
            registry.registerEntity(EntityItemFrame.class,
                    new ICapabilityConstructor<IEnergyStorage, EntityItemFrame, EntityItemFrame>() {
                        @Override
                        public Capability<IEnergyStorage> getCapability() {
                            return CapabilityEnergy.ENERGY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(EntityItemFrame hostType, final EntityItemFrame host) {
                            return new ICapabilityProvider() {
                                @Override
                                public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityEnergy.ENERGY
                                            && host.getDisplayedItem().hasCapability(capability, facing);
                                }

                                @Nullable
                                @Override
                                public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
                                    return capability == CapabilityEnergy.ENERGY
                                            && host.getDisplayedItem().hasCapability(capability, facing)
                                            ? (T) new VanillaEntityItemFrameEnergyStorage(host, facing) : null;
                                }
                            };
                        }
                    });
        }
    }
}
