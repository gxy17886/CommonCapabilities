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
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature;
import org.cyclops.commoncapabilities.capability.temperature.TemperatureConfig;

/**
 * A simple test mod which will print the temperature capability for tiles, entities and items.
 * @author rubensworks
 */
@Mod(modid="test.commoncapabilities.vanilla.capability.temperature",version="1.0")
public class TestCapabilityWorldTemperatureMod {
    
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent evt) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    protected void printTemperature(ITemperature temperature) {
        System.out.println("Current temperature: " + temperature.getTemperature());
        System.out.println("Maximum temperature: " + temperature.getMaximumTemperature());
        System.out.println("Minimum temperature: " + temperature.getMinimumTemperature());
        System.out.println("Default temperature: " + temperature.getDefaultTemperature());
    }

    @SubscribeEvent
    public void onTileInteract(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getItemStack().isEmpty()) return;
        if (event.getItemStack().getItem() != Items.BEETROOT) return;

        TileEntity te = event.getWorld().getTileEntity(event.getPos());
        if (te != null && te.hasCapability(TemperatureConfig.CAPABILITY, event.getFace())) {
            event.setCanceled(true);
            ITemperature temperature = te.getCapability(TemperatureConfig.CAPABILITY, event.getFace());
            printTemperature(temperature);
        }
    }

    @SubscribeEvent
    public void onEntityInteract(AttackEntityEvent event) {
        if (event.getEntityPlayer() == null) return;
        if (event.getEntityPlayer().getHeldItemMainhand().isEmpty()) return;
        if (event.getEntityPlayer().getHeldItemMainhand().getItem() != Items.BEETROOT) return;

        Entity target = event.getTarget();
        if (target != null && target.hasCapability(TemperatureConfig.CAPABILITY, null)) {
            event.setCanceled(true);
            ITemperature temperature = target.getCapability(TemperatureConfig.CAPABILITY, null);
            printTemperature(temperature);
        }
    }

    @SubscribeEvent
    public void onItemInteract(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().isEmpty()) return;
        if (!event.getEntityPlayer().isSneaking()) return;

        ItemStack itemStack = event.getItemStack();
        if (itemStack.hasCapability(TemperatureConfig.CAPABILITY, null)) {
            event.setCanceled(true);
            ITemperature temperature = itemStack.getCapability(TemperatureConfig.CAPABILITY, null);
            printTemperature(temperature);
        }
    }
}