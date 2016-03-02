package org.cyclops.commoncapabilities.capability;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

/**
 * Registry for capabilities created by this mod.
 * @author rubensworks
 */
public class CapabilityConstructorRegistry {
    private static final CapabilityConstructorRegistry INSTANCE = new CapabilityConstructorRegistry();
    private static final Multimap<Class<? extends TileEntity>, ICapabilityConstructor<? extends TileEntity>>
            CAPABILITY_CONSTRUCTORS_TILE = HashMultimap.create();

    /**
     * Register a tile capability constructor.
     * @param clazz The tile class.
     * @param constructor The capability constructor.
     * @param <T> The tile type.
     */
    public static <T extends TileEntity> void register(Class<T> clazz, ICapabilityConstructor<T> constructor) {
        CAPABILITY_CONSTRUCTORS_TILE.put(clazz, constructor);
    }

    @SuppressWarnings("unchecked")
    protected static <H, HE> ICapabilityProvider createProvider(HE host, ICapabilityConstructor<H> capabilityConstructor) {
        return capabilityConstructor.createProvider((H) host);
    }

    private CapabilityConstructorRegistry() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTileLoad(AttachCapabilitiesEvent.TileEntity event) {
        Collection<ICapabilityConstructor<? extends TileEntity>> constructors = CAPABILITY_CONSTRUCTORS_TILE
                .get(event.getTileEntity().getClass());
        for(ICapabilityConstructor<? extends TileEntity> constructor : constructors) {
            event.addCapability(constructor.getKey(), createProvider(event.getTileEntity(), constructor));
        }
    }
}
