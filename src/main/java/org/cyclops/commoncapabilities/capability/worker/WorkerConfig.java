package org.cyclops.commoncapabilities.capability.worker;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import org.cyclops.commoncapabilities.api.capability.work.DefaultWorker;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.core.config.extendedconfig.CapabilityConfig;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityStorage;

/**
 * Config for the worker capability.
 * @author rubensworks
 *
 */
public class WorkerConfig extends CapabilityConfig {

    /**
     * The unique instance.
     */
    public static WorkerConfig _instance;

    @CapabilityInject(IWorker.class)
    public static Capability<IWorker> CAPABILITY = null;

    /**
     * Make a new instance.
     */
    public WorkerConfig() {
        super(
                true,
                "worker",
                "Indication if a machine is working",
                IWorker.class,
                new DefaultCapabilityStorage<IWorker>(),
                DefaultWorker.class
        );
    }
}
