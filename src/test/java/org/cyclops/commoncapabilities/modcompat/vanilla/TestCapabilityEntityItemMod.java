package org.cyclops.commoncapabilities.modcompat.vanilla;

import com.google.common.collect.Iterables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.cyclops.cyclopscore.helper.FluidHelpers;

/**
 * A simple test mod which tests entity item (frame) capabilities.
 * @author rubensworks
 */
@Mod(modid="test.commoncapabilities.vanilla.capability.entityitem",version="1.0")
public class TestCapabilityEntityItemMod {
    
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent evt) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    protected void printSlots(IItemHandler itemHandler) {
        System.out.println("Item handler slots: " + itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            System.out.println("Slot " + i + " :" + itemHandler.getStackInSlot(i));
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntityPlayer() == null) return;
        if (event.getEntityPlayer().getHeldItemMainhand().isEmpty()) return;

        Entity target = Iterables.get(event.getEntityPlayer().getEntityWorld().getEntitiesWithinAABB(Entity.class,
                new AxisAlignedBB(event.getPos().offset(event.getFace()))), 0, null);
        if (target != null) {
            Item heldItem = event.getEntityPlayer().getHeldItemMainhand().getItem();
            if (!(target instanceof EntityItem) && !(target instanceof EntityItemFrame)) return;
            if (target.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, event.getFace())) {
                event.setCanceled(true);
                IItemHandler itemHandler = target.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, event.getFace());
                printSlots(itemHandler);
                if (heldItem == Items.APPLE) {
                    System.out.println("Adding apple to random slot");
                    ItemStack remaining = itemHandler.insertItem((int) (Math.random() * itemHandler.getSlots()), new ItemStack(Items.APPLE), false);
                    System.out.println("Remaining: " + remaining);
                } else if (heldItem == Items.BOWL) {
                    int slot = (int) (Math.random() * itemHandler.getSlots());
                    System.out.println("Removing apple from random slot " + slot);
                    ItemStack extracted = itemHandler.extractItem(slot, 1, false);
                    System.out.println("Extracted: " + extracted);
                }
            } else if (target.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, event.getFace())) {
                event.setCanceled(true);
                IFluidHandler fluidHandler = target.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, event.getFace());
                System.out.println(FluidHelpers.getAmount(FluidHelpers.getFluid(fluidHandler)));
                if (heldItem == Items.APPLE) {
                    System.out.println("Adding water");
                    int filled = fluidHandler.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
                    System.out.println("Filled: " + filled);
                } else if (heldItem == Items.BOWL) {
                    System.out.println("Removing water");
                    FluidStack drained = fluidHandler.drain(new FluidStack(FluidRegistry.WATER, 1000), true);
                    System.out.println("Drained: " + FluidHelpers.getAmount(drained));
                }
            } else if (target.hasCapability(CapabilityEnergy.ENERGY, event.getFace())) {
                event.setCanceled(true);
                IEnergyStorage energyStorage = target.getCapability(CapabilityEnergy.ENERGY, event.getFace());
                System.out.println(energyStorage.getEnergyStored() + " / " + energyStorage.getMaxEnergyStored());
                if (heldItem == Items.APPLE) {
                    System.out.println("Adding energy");
                    int added = energyStorage.receiveEnergy(1000, false);
                    System.out.println("Added: " + added);
                } else if (heldItem == Items.BOWL) {
                    System.out.println("Removing energy");
                    int removed = energyStorage.extractEnergy(1000, false);
                    System.out.println("Removed: " + removed);
                }
            }
        }
    }
}