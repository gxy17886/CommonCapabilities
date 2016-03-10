package org.cyclops.commoncapabilities.modcompat.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IWorldNameable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.capability.Capabilities;

/**
 * A simple test mod which will print the world-nameable capability for tiles, entities and items.
 * @author rubensworks
 */
@Mod(modid="test.commoncapabilities.capability.worldnameable",version="1.0")
public class TestCapabilityWorldNameableMod {
    
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent evt) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTileInteract(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) return;
        if (event.entityPlayer.getHeldItem() == null) return;
        if (event.entityPlayer.getHeldItem().getItem() != Items.bone) return;

        TileEntity te = event.world.getTileEntity(event.pos);
        if (te != null && te.hasCapability(Capabilities.WORLDNAMEABLE, event.face)) {
            event.setCanceled(true);
            IWorldNameable worldNameable = te.getCapability(Capabilities.WORLDNAMEABLE, event.face);
            System.out.println("World nameable: " + worldNameable.getName());
        }
    }

    @SubscribeEvent
    public void onEntityInteract(AttackEntityEvent event) {
        if (event.entityPlayer == null) return;
        if (event.entityPlayer.getHeldItem() == null) return;
        if (event.entityPlayer.getHeldItem().getItem() != Items.bone) return;

        Entity target = event.target;
        if (target != null && target.hasCapability(Capabilities.WORLDNAMEABLE, null)) {
            event.setCanceled(true);
            IWorldNameable worldNameable = target.getCapability(Capabilities.WORLDNAMEABLE, null);
            System.out.println("World nameable: " + worldNameable.getName());
        }
    }

    @SubscribeEvent
    public void onItemInteract(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR) return;
        if (event.entityPlayer.getHeldItem() == null) return;
        if (event.entityPlayer.getHeldItem().getItem() != Items.bone) return;

        ItemStack itemStack = event.entityPlayer.getHeldItem();
        if (itemStack.hasCapability(Capabilities.WORLDNAMEABLE, null)) {
            event.setCanceled(true);
            IWorldNameable worldNameable = itemStack.getCapability(Capabilities.WORLDNAMEABLE, null);
            System.out.println("World nameable: " + worldNameable.getName());
        }
    }
}