package org.cyclops.commoncapabilities.modcompat.vanilla.capability.fluidhandler;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.VanillaEntityItemFrameCapabilityDelegator;

import javax.annotation.Nullable;

/**
 * A fluid handler for entity item frames that have a fluid handler.
 * @author rubensworks
 */
public class VanillaEntityItemFrameFluidHandler extends VanillaEntityItemFrameCapabilityDelegator<IFluidHandlerItem> implements IFluidHandler {

    public VanillaEntityItemFrameFluidHandler(EntityItemFrame entity, EnumFacing side) {
        super(entity, side);
    }

    @Override
    protected Capability<IFluidHandlerItem> getCapabilityType() {
        return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        IFluidHandlerItem fluidHandler = getCapability();
        if (fluidHandler != null) {
            return fluidHandler.getTankProperties();
        }
        return new IFluidTankProperties[0];
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        IFluidHandlerItem fluidHandler = getCapability();
        if (fluidHandler != null) {
            int ret = fluidHandler.fill(resource, doFill);
            if (ret > 0 && doFill) {
                updateItemStack(fluidHandler.getContainer());
            }
            return ret;
        }
        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        IFluidHandlerItem fluidHandler = getCapability();
        if (fluidHandler != null) {
            FluidStack ret = fluidHandler.drain(resource, doDrain);
            if (ret != null && doDrain) {
                updateItemStack(fluidHandler.getContainer());
            }
            return ret;
        }
        return null;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        IFluidHandlerItem fluidHandler = getCapability();
        if (fluidHandler != null) {
            FluidStack ret = fluidHandler.drain(maxDrain, doDrain);
            if (ret != null && doDrain) {
                updateItemStack(fluidHandler.getContainer());
            }
            return ret;
        }
        return null;
    }
}
