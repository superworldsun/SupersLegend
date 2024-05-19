package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.world.entity.ai.attributes.*;

import net.minecraftforge.registries.*;

public class AttributeInit {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, SupersLegendMain.MOD_ID);

    //TODO, turn on when fixed for temp system
    //public static final RegistryObject<Attribute> HEAT_RESISTANCE = rangedAttribute("player", "heat_resistance", 1.0D, 0.0D, 2.0D);
    //public static final RegistryObject<Attribute> COLD_RESISTANCE = rangedAttribute("player", "cold_resistance", 1.0D, 0.0D, 2.0D);
    //public static final RegistryObject<Attribute> HELL_HEAT_RESISTANCE = rangedAttribute("player", "hell_heat_resistance", 1.0D, 0.0D, 2.0D);

    private static RegistryObject<Attribute> rangedAttribute(String category, String name, double defaultValue, double minValue, double maxValue) {
        return ATTRIBUTES.register(name, () -> new RangedAttribute(category + "." + name, defaultValue, minValue, maxValue).setSyncable(true));
    }
}