package org.cyclops.commoncapabilities.modcompat.vanilla;

import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.Reference;
import org.cyclops.commoncapabilities.capability.Capabilities;
import org.cyclops.commoncapabilities.capability.CapabilityConstructorBase;
import org.cyclops.commoncapabilities.capability.CapabilityConstructorRegistry;
import org.cyclops.commoncapabilities.capability.DefaultCapabilityProvider;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.work.VanillaBrewingStandWorker;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.work.VanillaFurnaceWorker;
import org.cyclops.cyclopscore.modcompat.IModCompat;

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
            CapabilityConstructorRegistry.register(TileEntityFurnace.class,
                    new CapabilityConstructorBase<TileEntityFurnace>(Capabilities.WORKER_KEY) {
                        @Override
                        public ICapabilityProvider createProvider(TileEntityFurnace host) {
                            return new DefaultCapabilityProvider<>(Capabilities.WORKER, new VanillaFurnaceWorker(host));
                        }
                    });

            CapabilityConstructorRegistry.register(TileEntityBrewingStand.class,
                    new CapabilityConstructorBase<TileEntityBrewingStand>(Capabilities.WORKER_KEY) {
                        @Override
                        public ICapabilityProvider createProvider(TileEntityBrewingStand host) {
                            return new DefaultCapabilityProvider<>(Capabilities.WORKER, new VanillaBrewingStandWorker(host));
                        }
                    });
        }
    }
}
