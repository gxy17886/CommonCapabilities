package org.cyclops.commoncapabilities.modcompat.ic2;

import ic2.core.item.tool.ItemToolWrench;
import ic2.core.item.tool.ItemToolWrenchElectric;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.ModAPIManager;
import org.cyclops.commoncapabilities.CommonCapabilities;
import org.cyclops.commoncapabilities.GeneralConfig;
import org.cyclops.commoncapabilities.Reference;
import org.cyclops.commoncapabilities.api.capability.wrench.DefaultWrench;
import org.cyclops.commoncapabilities.api.capability.wrench.IWrench;
import org.cyclops.commoncapabilities.capability.wrench.WrenchConfig;
import org.cyclops.commoncapabilities.modcompat.ic2.capability.tesla.Ic2TeslaIntegration;
import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.cyclopscore.modcompat.capabilities.CapabilityConstructorRegistry;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityProvider;
import org.cyclops.cyclopscore.modcompat.capabilities.ICapabilityConstructor;

import javax.annotation.Nullable;

/**
 * Capabilities for IC2.
 * @author rubensworks
 */
public class Ic2ModCompat implements IModCompat {
    @Override
    public String getModID() {
        return Reference.MOD_IC2;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getComment() {
        return "Tesla and wrench capabilities for IC2 tiles and items.";
    }

    @Override
    public void onInit(Step initStep) {
        if(initStep == Step.PREINIT && ModAPIManager.INSTANCE.hasAPI(Reference.MOD_TESLA_API) && GeneralConfig.ic2EuToTesla) {
            Ic2TeslaIntegration.load();
            CapabilityConstructorRegistry registry = CommonCapabilities._instance.getCapabilityConstructorRegistry();

            // Wrench
            registry.registerItem(ItemToolWrenchElectric.class,
                    new ICapabilityConstructor<IWrench, ItemToolWrenchElectric, ItemStack>() {
                        @Override
                        public Capability<IWrench> getCapability() {
                            return WrenchConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(ItemToolWrenchElectric hostType, final ItemStack host) {
                            return new DefaultCapabilityProvider<>(WrenchConfig.CAPABILITY, new DefaultWrench());
                        }
                    });
            registry.registerItem(ItemToolWrench.class,
                    new ICapabilityConstructor<IWrench, ItemToolWrench, ItemStack>() {
                        @Override
                        public Capability<IWrench> getCapability() {
                            return WrenchConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(ItemToolWrench hostType, final ItemStack host) {
                            return new DefaultCapabilityProvider<>(WrenchConfig.CAPABILITY, new DefaultWrench());
                        }
                    });
        }
    }

}
