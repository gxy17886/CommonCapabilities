package org.cyclops.commoncapabilities.modcompat.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.cyclops.commoncapabilities.api.capability.inventorystate.IInventoryState;
import org.cyclops.commoncapabilities.capability.inventorystate.InventoryStateConfig;

/**
 * A simple test mod which will print inventory states.
 * @author rubensworks
 */
@Mod(modid="test.commoncapabilities.vanilla.capability.inventorystate",version="1.0")
public class TestCapabilityInventoryStateMod {
    
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent evt) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTileInteract(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getItemStack() == null) return;
        if (event.getItemStack().getItem() != Items.ARROW) return;

        TileEntity te = event.getWorld().getTileEntity(event.getPos());
        if (te != null && te.hasCapability(InventoryStateConfig.CAPABILITY, event.getFace())) {
            event.setCanceled(true);
            IInventoryState inventoryState = te.getCapability(InventoryStateConfig.CAPABILITY, event.getFace());
            System.out.println("Inventory state: " + inventoryState.getHash());
        }
    }

    @SubscribeEvent
    public void onEntityInteract(AttackEntityEvent event) {
        if (event.getEntityPlayer() == null) return;
        if (event.getEntityPlayer().getHeldItemMainhand() == null) return;
        if (event.getEntityPlayer().getHeldItemMainhand().getItem() != Items.ARROW) return;

        Entity target = event.getTarget();
        if (target != null && target.hasCapability(InventoryStateConfig.CAPABILITY, null)) {
            event.setCanceled(true);
            IInventoryState inventoryState = target.getCapability(InventoryStateConfig.CAPABILITY, null);
            System.out.println("Inventory state: " + inventoryState.getHash());
        }
    }

    @SubscribeEvent
    public void onItemInteract(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack() == null) return;
        if (event.getItemStack().getItem() != Items.ARROW) return;

        ItemStack itemStack = event.getItemStack();
        if (itemStack.hasCapability(InventoryStateConfig.CAPABILITY, null)) {
            event.setCanceled(true);
            IInventoryState inventoryState = itemStack.getCapability(InventoryStateConfig.CAPABILITY, null);
            System.out.println("Inventory state: " + inventoryState.getHash());
        }
    }
}