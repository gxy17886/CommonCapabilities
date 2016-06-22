package org.cyclops.commoncapabilities.modcompat.ic2;

import org.cyclops.commoncapabilities.GeneralConfig;

/**
 * Helpers for IC2.
 * @author rubensworks
 */
public class Ic2Helpers {

    public static double teslaToEu(long tesla) {
        return tesla / GeneralConfig.euToTeslaRate;
    }

    public static long euToTesla(double eu) {
        return (long) Math.floor(eu * GeneralConfig.euToTeslaRate);
    }

}
