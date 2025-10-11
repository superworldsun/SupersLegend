package com.superworldsun.superslegend.datagen.loot;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.loot.AddItemModifier;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.Random;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, SupersLegendMain.MOD_ID);
    }

    @Override
    protected void start() {
        add("deku_seeds_from_jungle_leaves", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.JUNGLE_LEAVES).build(),
                LootItemRandomChanceCondition.randomChance(0.20f).build()}, ItemInit.DEKU_SEEDS.get()));

        add("deku_seeds_from_creeper", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/creeper"))
                        .build(), LootItemRandomChanceCondition.randomChance(0.10f).build() },
                new ItemStack(ItemInit.DEKU_SEEDS.get(), 1 + new Random().nextInt(5)).getItem()));

        add("rupees_from_creeper", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/creeper"))
                        .build(), LootItemRandomChanceCondition.randomChance(0.15f).build() }, ItemInit.RUPEE.get()));

        add("metal_detector_from_jungle_temples", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build() }, ItemInit.DEKU_SEEDS.get()));


    }
}
