package org.cyclops.commoncapabilities.modcompat.vanilla;

import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.capability.worker.WorkerConfig;

/**
 * A simple test mod which will print the work status for worker tiles.
 * @author rubensworks
 */
@Mod(modid="test.commoncapabilities.capability.worker",version="1.0")
public class TestCapabilityWorkerMod {
    
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent evt) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getItemStack() == null) return;
        if (event.getItemStack().getItem() != Items.BLAZE_ROD) return;

        TileEntity te = event.getWorld().getTileEntity(event.getPos());
        if (te != null && te.hasCapability(WorkerConfig.CAPABILITY, event.getFace())) {
            event.setCanceled(true);
            IWorker worker = te.getCapability(WorkerConfig.CAPABILITY, event.getFace());
            System.out.println("Has work: " + worker.hasWork());
            System.out.println("Can work: " + worker.canWork());
        }
    }
}