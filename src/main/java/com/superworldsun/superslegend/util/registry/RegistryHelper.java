package com.superworldsun.superslegend.util.registry;

import com.google.common.collect.ArrayListMultimap;
import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RegistryHelper {

    private static final Map<String, ModData> modData = new HashMap<>();

    public static void registerItem(Item item, String resloc) {
        register(item, resloc);
    }

    private static ModData getCurrentModData() {
        return getModData(ModLoadingContext.get().getActiveNamespace());
    }

    private static ModData getModData(String modid) {
        ModData data = modData.get(modid);
        if(data == null) {
            data = new ModData();
            modData.put(modid, data);

            FMLJavaModLoadingContext.get().getModEventBus().register(RegistryHelper.class);
        }

        return data;
    }

    public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistryEntry<T> obj, String resloc) {
        if(obj == null)
            throw new IllegalArgumentException("Can't register null object.");

        obj.setRegistryName(GameData.checkPrefix(resloc, true));
        getCurrentModData().defers.put(obj.getRegistryType(), () -> obj);
    }

    private static class ModData {

        private Map<ResourceLocation, ItemGroup> groups = new LinkedHashMap<>();

        private ArrayListMultimap<Class<?>, Supplier<IForgeRegistryEntry<?>>> defers = ArrayListMultimap.create();

        @SuppressWarnings({ "rawtypes", "unchecked" })
        private void register(IForgeRegistry registry) {
            Class<?> type = registry.getRegistrySuperType();

            if(defers.containsKey(type)) {
                Collection<Supplier<IForgeRegistryEntry<?>>> ourEntries = defers.get(type);
                for(Supplier<IForgeRegistryEntry<?>> supplier : ourEntries) {
                    IForgeRegistryEntry<?> entry = supplier.get();
                    registry.register(entry);
                    SupersLegendMain.LOGGER.debug("Registering to " + registry.getRegistryName() + " - " + entry.getRegistryName());
                }

                defers.removeAll(type);
            }
        }

    }

}
