package org.cyclops.commoncapabilities.capability;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

/**
 * Constructor for capabilities.
 * @param <H> The host that will contain the capability.
 * @author rubensworks
 */
public interface ICapabilityConstructor<H> {

    /**
     * @return The unique key of the capability.
     */
    public ResourceLocation getKey();

    /**
     * @param host The host for capabilities
     * @return A new capability provider for the given host.
     */
    public @Nullable ICapabilityProvider createProvider(H host);

}
