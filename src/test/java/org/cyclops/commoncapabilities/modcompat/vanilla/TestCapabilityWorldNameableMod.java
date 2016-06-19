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
import org.cyclops.commoncapabilities.capability.worldnameable.WorldNameableConfig;

/**
 * A simple test mod which will print the world-nameable capability for tiles, entities and items.
 * @author rubensworks
 */
@Mod(modid="test.commoncapabilities.vanilla.capability.worldnameable",version="1.0")
public class TestCapabilityWorldNameableMod {
    
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent evt) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTileInteract(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getItemStack() == null) return;
        if (event.getItemStack().getItem() != Items.BONE) return;

        TileEntity te = event.getWorld().getTileEntity(event.getPos());
        if (te != null && te.hasCapability(WorldNameableConfig.CAPABILITY, event.getFace())) {
            event.setCanceled(true);
            IWorldNameable worldNameable = te.getCapability(WorldNameableConfig.CAPABILITY, event.getFace());
            System.out.println("World nameable: " + worldNameable.getName());
        }
    }

    @SubscribeEvent
    public void onEntityInteract(AttackEntityEvent event) {
        if (event.getEntityPlayer() == null) return;
        if (event.getEntityPlayer().getHeldItemMainhand() == null) return;
        if (event.getEntityPlayer().getHeldItemMainhand().getItem() != Items.BONE) return;

        Entity target = event.getTarget();
        if (target != null && target.hasCapability(WorldNameableConfig.CAPABILITY, null)) {
            event.setCanceled(true);
            IWorldNameable worldNameable = target.getCapability(WorldNameableConfig.CAPABILITY, null);
            System.out.println("World nameable: " + worldNameable.getName());
        }
    }

    @SubscribeEvent
    public void onItemInteract(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack() == null) return;
        if (event.getItemStack().getItem() != Items.BONE) return;

        ItemStack itemStack = event.getItemStack();
        if (itemStack.hasCapability(WorldNameableConfig.CAPABILITY, null)) {
            event.setCanceled(true);
            IWorldNameable worldNameable = itemStack.getCapability(WorldNameableConfig.CAPABILITY, null);
            System.out.println("World nameable: " + worldNameable.getName());
        }
    }
}