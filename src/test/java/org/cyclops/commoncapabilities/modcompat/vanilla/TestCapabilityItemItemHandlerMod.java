package org.cyclops.commoncapabilities.modcompat.vanilla;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * A simple test mod which will print inventory states.
 * @author rubensworks
 */
@Mod(modid="test.commoncapabilities.vanilla.capability.itemitemhandler",version="1.0")
public class TestCapabilityItemItemHandlerMod {
    
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
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (event.getItemStack().isEmpty()) return;

        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
        if (block == Blocks.HAY_BLOCK) {
            ItemStack itemStack = event.getItemStack();
            if (itemStack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
                event.setCanceled(true);
                IItemHandler itemHandler = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                printSlots(itemHandler);

                System.out.println("Adding apple to random slot");
                ItemStack remaining = itemHandler.insertItem((int) (Math.random() * itemHandler.getSlots()), new ItemStack(Items.APPLE), false);
                System.out.println("Remaining: " + remaining);
            }
        } else if (block == Blocks.STONE) {
            ItemStack itemStack = event.getItemStack();
            if (itemStack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
                event.setCanceled(true);
                IItemHandler itemHandler = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                printSlots(itemHandler);

                int slot = (int) (Math.random() * itemHandler.getSlots());
                System.out.println("Removing apple from random slot " + slot);
                ItemStack extracted = itemHandler.extractItem(slot, 1, false);
                System.out.println("Extracted: " + extracted);
            }
        }
    }
}