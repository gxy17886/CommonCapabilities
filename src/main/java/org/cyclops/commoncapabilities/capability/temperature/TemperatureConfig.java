package org.cyclops.commoncapabilities.capability.temperature;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature;
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature;
import org.cyclops.commoncapabilities.core.config.extendedconfig.CapabilityConfig;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityStorage;

/**
 * Config for the temperature capability.
 * @author rubensworks
 *
 */
public class TemperatureConfig extends CapabilityConfig {

    /**
     * The unique instance.
     */
    public static TemperatureConfig _instance;

    @CapabilityInject(ITemperature.class)
    public static Capability<ITemperature> CAPABILITY = null;

    /**
     * Make a new instance.
     */
    public TemperatureConfig() {
        super(
                true,
                "temperature",
                "Indicates if something has a temperature",
                ITemperature.class,
                new DefaultCapabilityStorage<ITemperature>(),
                DefaultTemperature.class
        );
    }
}
