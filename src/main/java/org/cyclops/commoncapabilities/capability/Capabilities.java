package org.cyclops.commoncapabilities.capability;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldNameable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import org.cyclops.commoncapabilities.Reference;
import org.cyclops.commoncapabilities.api.capability.work.DefaultWorker;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.api.capability.worldnameable.DefaultWorldNameable;
import org.cyclops.commoncapabilities.capability.worldnameable.WorldNameableCapabilityStorage;

/**
 * All capabilities.
 * @author rubensworks
 */
public class Capabilities {
    @CapabilityInject(IWorker.class)
    public static Capability<IWorker> WORKER = null;
    public static ResourceLocation WORKER_KEY = new ResourceLocation(Reference.MOD_ID, "worker");

    @CapabilityInject(IWorldNameable.class)
    public static Capability<IWorldNameable> WORLDNAMEABLE = null;
    public static ResourceLocation WORLDNAMEABLE_KEY = new ResourceLocation(Reference.MOD_ID, "worldnameable");

    public static void register() {
        CapabilityManager.INSTANCE.register(IWorker.class, new DefaultCapabilityStorage<IWorker>(), DefaultWorker.class);
        CapabilityManager.INSTANCE.register(IWorldNameable.class, new WorldNameableCapabilityStorage(), DefaultWorldNameable.class);
    }

}
