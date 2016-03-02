package org.cyclops.commoncapabilities.capability;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import org.cyclops.commoncapabilities.Reference;
import org.cyclops.commoncapabilities.api.capability.work.DefaultWorker;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;

/**
 * All capabilities.
 * @author rubensworks
 */
public class Capabilities {
    @CapabilityInject(IWorker.class)
    public static Capability<IWorker> WORKER = null;
    public static ResourceLocation WORKER_KEY = new ResourceLocation(Reference.MOD_ID, "worker");

    public static void register() {
        CapabilityManager.INSTANCE.register(IWorker.class, new DefaultCapabilityStorage<IWorker>(), DefaultWorker.class);
    }

}
