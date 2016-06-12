package org.cyclops.commoncapabilities.modcompat.tconstruct;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.CommonCapabilities;
import org.cyclops.commoncapabilities.Reference;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.capability.worker.WorkerConfig;
import org.cyclops.commoncapabilities.modcompat.tconstruct.capability.work.TileCastingWorker;
import org.cyclops.commoncapabilities.modcompat.tconstruct.capability.work.TileSmelteryWorker;
import org.cyclops.cyclopscore.modcompat.IModCompat;
import org.cyclops.cyclopscore.modcompat.capabilities.CapabilityConstructorRegistry;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityProvider;
import org.cyclops.cyclopscore.modcompat.capabilities.ICapabilityConstructor;
import slimeknights.tconstruct.smeltery.tileentity.TileCastingBasin;
import slimeknights.tconstruct.smeltery.tileentity.TileCastingTable;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;

import javax.annotation.Nullable;

/**
 * Capabilities for TConstruct.
 * @author rubensworks
 */
public class TConstructModCompat implements IModCompat {
    @Override
    public String getModID() {
        return Reference.MOD_TCONSTRUCT;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getComment() {
        return "Worker capabilities for TCon machines.";
    }

    @Override
    public void onInit(Step initStep) {
        if(initStep == Step.INIT) {
            CapabilityConstructorRegistry registry = CommonCapabilities._instance.getCapabilityConstructorRegistry();
            // Worker
            registry.registerTile(TileSmeltery.class,
                    new ICapabilityConstructor<IWorker, TileSmeltery>() {
                        @Override
                        public Capability<IWorker> getCapability() {
                            return WorkerConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(TileSmeltery host) {
                            return new DefaultCapabilityProvider<>(WorkerConfig.CAPABILITY, new TileSmelteryWorker(host));
                        }
                    });
            registry.registerTile(TileCastingTable.class,
                    new ICapabilityConstructor<IWorker, TileCastingTable>() {
                        @Override
                        public Capability<IWorker> getCapability() {
                            return WorkerConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(TileCastingTable host) {
                            return new DefaultCapabilityProvider<>(WorkerConfig.CAPABILITY, new TileCastingWorker(host));
                        }
                    });
            registry.registerTile(TileCastingBasin.class,
                    new ICapabilityConstructor<IWorker, TileCastingBasin>() {
                        @Override
                        public Capability<IWorker> getCapability() {
                            return WorkerConfig.CAPABILITY;
                        }

                        @Nullable
                        @Override
                        public ICapabilityProvider createProvider(TileCastingBasin host) {
                            return new DefaultCapabilityProvider<>(WorkerConfig.CAPABILITY, new TileCastingWorker(host));
                        }
                    });
        }
    }

}
