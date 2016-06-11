package org.cyclops.commoncapabilities.modcompat.rf;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.CommonCapabilities;
import org.cyclops.cyclopscore.modcompat.capabilities.CapabilityConstructorRegistry;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityProvider;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultSidedCapabilityProvider;
import org.cyclops.cyclopscore.modcompat.capabilities.ICapabilityConstructor;

import javax.annotation.Nullable;

/**
 * Loader for RF Tesla capabilities.
 * @author rubensworks
 */
public class RfTeslaIntegration {

    public static void load() {
        CapabilityConstructorRegistry registry = CommonCapabilities._instance.getCapabilityConstructorRegistry();

        // Tiles
        registry.registerInheritableTile(IEnergyHandler.class,
                new ICapabilityConstructor<ITeslaHolder, IEnergyHandler>() {
                    @Override
                    public Capability<ITeslaHolder> getCapability() {
                        return TeslaCapabilities.CAPABILITY_HOLDER;
                    }

                    @Nullable
                    @Override
                    public ICapabilityProvider createProvider(final IEnergyHandler host) {
                        return new DefaultSidedCapabilityProvider<>(DefaultSidedCapabilityProvider.forAllSides(
                                TeslaCapabilities.CAPABILITY_HOLDER,
                                new DefaultSidedCapabilityProvider.ISidedCapabilityConstructor<ITeslaHolder>() {
                                    @Override
                                    public ITeslaHolder createForSide(EnumFacing side) {
                                        return new HolderEnergyHandler(side, host);
                                    }
                                }));
                    }
                });
        registry.registerInheritableTile(IEnergyProvider.class,
                new ICapabilityConstructor<ITeslaProducer, IEnergyProvider>() {
                    @Override
                    public Capability<ITeslaProducer> getCapability() {
                        return TeslaCapabilities.CAPABILITY_PRODUCER;
                    }

                    @Nullable
                    @Override
                    public ICapabilityProvider createProvider(final IEnergyProvider host) {
                        return new DefaultSidedCapabilityProvider<>(DefaultSidedCapabilityProvider.forAllSides(
                                TeslaCapabilities.CAPABILITY_PRODUCER,
                                new DefaultSidedCapabilityProvider.ISidedCapabilityConstructor<ITeslaProducer>() {
                                    @Override
                                    public ITeslaProducer createForSide(EnumFacing side) {
                                        return new ProducerEnergyProvider(side, host);
                                    }
                                }));
                    }
                });
        registry.registerInheritableTile(IEnergyReceiver.class,
                new ICapabilityConstructor<ITeslaConsumer, IEnergyReceiver>() {
                    @Override
                    public Capability<ITeslaConsumer> getCapability() {
                        return TeslaCapabilities.CAPABILITY_CONSUMER;
                    }

                    @Nullable
                    @Override
                    public ICapabilityProvider createProvider(final IEnergyReceiver host) {
                        return new DefaultSidedCapabilityProvider<>(DefaultSidedCapabilityProvider.forAllSides(
                                TeslaCapabilities.CAPABILITY_CONSUMER,
                                new DefaultSidedCapabilityProvider.ISidedCapabilityConstructor<ITeslaConsumer>() {
                                    @Override
                                    public ITeslaConsumer createForSide(EnumFacing side) {
                                        return new ConsumerEnergyReceiver(side, host);
                                    }
                                }));
                    }
                });

        // Items
        registry.registerInheritableItem(IEnergyContainerItem.class,
                new ICapabilityConstructor<ITeslaHolder, ItemStack>() {
                    @Override
                    public Capability<ITeslaHolder> getCapability() {
                        return TeslaCapabilities.CAPABILITY_HOLDER;
                    }

                    @Nullable
                    @Override
                    public ICapabilityProvider createProvider(final ItemStack host) {
                        return new DefaultCapabilityProvider<>(TeslaCapabilities.CAPABILITY_HOLDER,
                                new HolderEnergyContainerItem(host));
                    }
                });
        registry.registerInheritableItem(IEnergyContainerItem.class,
                new ICapabilityConstructor<ITeslaProducer, ItemStack>() {
                    @Override
                    public Capability<ITeslaProducer> getCapability() {
                        return TeslaCapabilities.CAPABILITY_PRODUCER;
                    }

                    @Nullable
                    @Override
                    public ICapabilityProvider createProvider(final ItemStack host) {
                        return new DefaultCapabilityProvider<>(TeslaCapabilities.CAPABILITY_PRODUCER,
                                new ProducerEnergyContainerItem(host));
                    }
                });
        registry.registerInheritableItem(IEnergyContainerItem.class,
                new ICapabilityConstructor<ITeslaConsumer, ItemStack>() {
                    @Override
                    public Capability<ITeslaConsumer> getCapability() {
                        return TeslaCapabilities.CAPABILITY_CONSUMER;
                    }

                    @Nullable
                    @Override
                    public ICapabilityProvider createProvider(final ItemStack host) {
                        return new DefaultCapabilityProvider<>(TeslaCapabilities.CAPABILITY_CONSUMER,
                                new ConsumerEnergyContainerItem(host));
                    }
                });
    }

}
