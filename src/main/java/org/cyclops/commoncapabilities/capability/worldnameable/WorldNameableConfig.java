package org.cyclops.commoncapabilities.capability.worldnameable;

import net.minecraft.world.IWorldNameable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import org.cyclops.commoncapabilities.api.capability.worldnameable.DefaultWorldNameable;
import org.cyclops.commoncapabilities.core.config.extendedconfig.CapabilityConfig;

/**
 * Config for the world nameable capability.
 * @author rubensworks
 *
 */
public class WorldNameableConfig extends CapabilityConfig {

    /**
     * The unique instance.
     */
    public static WorldNameableConfig _instance;

    @CapabilityInject(IWorldNameable.class)
    public static Capability<IWorldNameable> CAPABILITY = null;

    /**
     * Make a new instance.
     */
    public WorldNameableConfig() {
        super(
                true,
                "worldNameable",
                "Naming of tiles, entities and items",
                IWorldNameable.class,
                new WorldNameableCapabilityStorage(),
                DefaultWorldNameable.class
        );
    }
}
