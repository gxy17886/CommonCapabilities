package org.cyclops.commoncapabilities.modcompat.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.IWorldNameable;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.Reference;
import org.cyclops.commoncapabilities.capability.Capabilities;
import org.cyclops.commoncapabilities.capability.CapabilityConstructorBase;
import org.cyclops.commoncapabilities.capability.CapabilityConstructorRegistry;
import org.cyclops.commoncapabilities.capability.DefaultCapabilityProvider;
import org.cyclops.commoncapabilities.capability.worldnameable.EntityLivingWorldNameable;
import org.cyclops.commoncapabilities.capability.worldnameable.ItemStackWorldNameable;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.work.VanillaBrewingStandWorker;
import org.cyclops.commoncapabilities.modcompat.vanilla.capability.work.VanillaFurnaceWorker;
import org.cyclops.cyclopscore.modcompat.IModCompat;

/**
 * Capabilities for Vanilla.
 * @author rubensworks
 */
public class VanillaModCompat implements IModCompat {
    @Override
    public String getModID() {
        return Reference.MOD_VANILLA;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getComment() {
        return "Furnace and Brewing stand capabilities.";
    }

    @Override
    public void onInit(Step initStep) {
        if(initStep == Step.INIT) {
            // Worker
            CapabilityConstructorRegistry.registerTile(TileEntityFurnace.class,
                    new CapabilityConstructorBase<TileEntityFurnace>(Capabilities.WORKER_KEY) {
                        @Override
                        public ICapabilityProvider createProvider(TileEntityFurnace host) {
                            return new DefaultCapabilityProvider<>(Capabilities.WORKER, new VanillaFurnaceWorker(host));
                        }
                    });
            CapabilityConstructorRegistry.registerTile(TileEntityBrewingStand.class,
                    new CapabilityConstructorBase<TileEntityBrewingStand>(Capabilities.WORKER_KEY) {
                        @Override
                        public ICapabilityProvider createProvider(TileEntityBrewingStand host) {
                            return new DefaultCapabilityProvider<>(Capabilities.WORKER, new VanillaBrewingStandWorker(host));
                        }
                    });

            // WorldNameable
            CapabilityConstructorRegistry.registerInheritableTile(IWorldNameable.class,
                    new CapabilityConstructorBase<TileEntity>(Capabilities.WORLDNAMEABLE_KEY) {
                        @Override
                        public ICapabilityProvider createProvider(TileEntity host) {
                            return new DefaultCapabilityProvider<>(Capabilities.WORLDNAMEABLE, (IWorldNameable) host);
                        }
                    });
            CapabilityConstructorRegistry.registerInheritableEntity(IWorldNameable.class,
                    new CapabilityConstructorBase<Entity>(Capabilities.WORLDNAMEABLE_KEY) {
                        @Override
                        public ICapabilityProvider createProvider(Entity host) {
                            return new DefaultCapabilityProvider<>(Capabilities.WORLDNAMEABLE, (IWorldNameable) host);
                        }
                    });
            CapabilityConstructorRegistry.registerInheritableEntity(EntityLiving.class,
                    new CapabilityConstructorBase<EntityLiving>(Capabilities.WORLDNAMEABLE_KEY) {
                        @Override
                        public ICapabilityProvider createProvider(EntityLiving host) {
                            return new DefaultCapabilityProvider<>(Capabilities.WORLDNAMEABLE, new EntityLivingWorldNameable(host));
                        }
                    });
            CapabilityConstructorRegistry.registerInheritableItem(Item.class,
                    new CapabilityConstructorBase<ItemStack>(Capabilities.WORLDNAMEABLE_KEY) {
                        @Override
                        public ICapabilityProvider createProvider(ItemStack host) {
                            if(host.hasDisplayName()) {
                                return new DefaultCapabilityProvider<>(Capabilities.WORLDNAMEABLE, new ItemStackWorldNameable(host));
                            }
                            return null;
                        }
                    });
        }
    }
}
