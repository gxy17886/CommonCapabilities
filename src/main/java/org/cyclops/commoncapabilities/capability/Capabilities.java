package org.cyclops.commoncapabilities.capability;

import net.minecraft.world.IWorldNameable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import org.cyclops.commoncapabilities.api.capability.work.DefaultWorker;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.api.capability.worldnameable.DefaultWorldNameable;
import org.cyclops.commoncapabilities.capability.worldnameable.WorldNameableCapabilityStorage;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityStorage;

/**
 * All capabilities.
 * @author rubensworks
 */
public class Capabilities {
    @CapabilityInject(IWorker.class)
    public static Capability<IWorker> WORKER = null;

    @CapabilityInject(IWorldNameable.class)
    public static Capability<IWorldNameable> WORLDNAMEABLE = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IWorker.class, new DefaultCapabilityStorage<IWorker>(), DefaultWorker.class);
        CapabilityManager.INSTANCE.register(IWorldNameable.class, new WorldNameableCapabilityStorage(), DefaultWorldNameable.class);
    }

}
