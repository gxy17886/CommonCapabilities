package org.cyclops.commoncapabilities.core.config;

import org.cyclops.commoncapabilities.core.config.configurabletypeaction.CapabilityAction;
import org.cyclops.commoncapabilities.core.config.extendedconfig.CapabilityConfig;
import org.cyclops.cyclopscore.config.ConfigurableType;

/**
 * The different types of {@link org.cyclops.cyclopscore.config.configurable.IConfigurable}.
 * @author rubensworks
 *
 */
public class ExtendedConfigurableType {
    /**
     * Degradation effect type.
     */
    public static final ConfigurableType CAPABILITY = new ConfigurableType(false, CapabilityConfig.class, new CapabilityAction(), "capability");
}
