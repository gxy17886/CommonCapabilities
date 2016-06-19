package org.cyclops.commoncapabilities.capability.wrench;

import net.minecraft.world.IWorldNameable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import org.cyclops.commoncapabilities.api.capability.wrench.DefaultWrench;
import org.cyclops.commoncapabilities.api.capability.wrench.IWrench;
import org.cyclops.commoncapabilities.core.config.extendedconfig.CapabilityConfig;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityStorage;

/**
 * Config for the wrench capability.
 * @author rubensworks
 *
 */
public class WrenchConfig extends CapabilityConfig {

    /**
     * The unique instance.
     */
    public static WrenchConfig _instance;

    @CapabilityInject(IWorldNameable.class)
    public static Capability<IWrench> CAPABILITY = null;

    /**
     * Make a new instance.
     */
    public WrenchConfig() {
        super(
                true,
                "wrench",
                "Indicates if something is a wrench",
                IWrench.class,
                new DefaultCapabilityStorage(),
                DefaultWrench.class
        );
    }
}
