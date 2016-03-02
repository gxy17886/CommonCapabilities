package org.cyclops.commoncapabilities.capability;

import net.minecraft.util.ResourceLocation;

/**
 * Default implementation of the capability constructor.
 * @param <H> The host that will contain the capability.
 * @author rubensworks
 */
public abstract class CapabilityConstructorBase<H> implements ICapabilityConstructor<H> {
    private final ResourceLocation key;

    public CapabilityConstructorBase(ResourceLocation key) {
        this.key = key;
    }

    @Override
    public ResourceLocation getKey() {
        return this.key;
    }
}
