package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.world.structure.BuriedJigsawStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class StructureTypeInit {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, SupersLegendMain.MOD_ID);

    public static final RegistryObject<StructureType<BuriedJigsawStructure>> BURIED_JIGSAW =
            STRUCTURE_TYPES.register("buried_jigsaw", () -> () -> BuriedJigsawStructure.CODEC);

    private StructureTypeInit() {
    }

    public static void register(IEventBus eventBus) {
        STRUCTURE_TYPES.register(eventBus);
    }
}
