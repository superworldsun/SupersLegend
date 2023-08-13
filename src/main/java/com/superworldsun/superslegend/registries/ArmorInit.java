package com.superworldsun.superslegend.registries;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.crafting.Ingredient;


public class ArmorInit {

    //first new Int is Durablity, second is armor defense
    //HEAD, CHEST, LEGS, FEET
    public static class ArmorTiers {
        public static final ArmorMaterialInit KOKIRI = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 5, 3, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "kokiri",
                0.0f,
                0.0f
        );

        public static final ArmorMaterialInit ZORA = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 5, 3, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "zora",
                0.0f,
                0.0f
        );

        public static final ArmorMaterialInit GORON = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 5, 3, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "goron",
                0.0f,
                0.0f
        );

        public static final ArmorMaterialInit PURPLE = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 5, 3, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "purple",
                0.0f,
                0.0f
        );

        public static final ArmorMaterialInit MAGIC = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 1, 1, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "magic",
                0.0f,
                0.0f
        );

        public static final ArmorMaterialInit HEROS_NEW = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 5, 3, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "heros_new",
                0.0f,
                0.0f
        );

        public static final ArmorMaterialInit DARK = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 3, 2, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "dark",
                0.0f,
                0.0f
        );

        public static final ArmorMaterialInit ZORAARMOR = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 3, 2, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "zoraarmor",
                0.0f,
                0.0f
        );

        public static final ArmorMaterialInit FLAMEBREAKER = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 5, 3, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "flamebreaker",
                0.0f,
                0.0f
        );
        public static final ArmorMaterialInit ANCIENT = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 5, 3, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "ancient",
                3.0f,
                0.0f
        );
        public static final ArmorMaterialInit BARBARIAN = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 2, 1, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "barbarian",
                0.0f,
                0.0f
        );
        public static final ArmorMaterialInit SNOWQUILL = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 3, 2, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "snowquill",
                0.0f,
                0.0f
        );
        public static final ArmorMaterialInit DESERTVOE = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 3, 2, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "desertvoe",
                0.0f,
                0.0f
        );
        public static final ArmorMaterialInit CLIMBING = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{1, 3, 2, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "climbing",
                0.0f,
                0.0f
        );
        public static final ArmorMaterialInit FLIPPERS = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{0, 0, 0, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "kokiri",
                0.0f,
                0.0f
        );
        public static final ArmorMaterialInit ROCSCAPE = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{0, 2, 0, 0},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "kokiri",
                0.0f,
                0.0f
        );
        public static final ArmorMaterialInit HOVER_BOOTS = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{0, 0, 0, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "kokiri",
                0.0f,
                0.0f
        );
        public static final ArmorMaterialInit IRON_BOOTS = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{0, 0, 0, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "kokiri",
                0.0f,
                0.0f
        );
        public static final ArmorMaterialInit PEGASUS_BOOTS = new ArmorMaterialInit(
                new int[]{0, 0, 0, 0},
                new int[]{0, 0, 0, 1},
                0,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(ItemInit.KOKIRI_TUNIC::get),
                "kokiri",
                0.0f,
                0.0f
        );
    }

}
