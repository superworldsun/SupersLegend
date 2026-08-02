package com.superworldsun.superslegend.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

/** Replaces an existing loot result with a supplemental table when its chance succeeds. */
public final class ReplaceLootTableModifier extends LootModifier {
    public static final Codec<ReplaceLootTableModifier> CODEC = RecordCodecBuilder.create(instance ->
            codecStart(instance)
                    .and(ResourceLocation.CODEC.fieldOf("table").forGetter(modifier -> modifier.tableId))
                    .and(Codec.FLOAT.optionalFieldOf("chance", 1.0F).forGetter(modifier -> modifier.chance))
                    .apply(instance, ReplaceLootTableModifier::new));

    private final ResourceLocation tableId;
    private final float chance;

    public ReplaceLootTableModifier(LootItemCondition[] conditions, ResourceLocation tableId, float chance) {
        super(conditions);
        this.tableId = tableId;
        this.chance = chance;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
                                                           LootContext context) {
        if (context.getRandom().nextFloat() <= chance) {
            LootTable table = context.getResolver().getLootTable(tableId);
            generatedLoot.clear();
            // Raw generation is intentional: applying global modifiers to this nested table would recurse.
            table.getRandomItemsRaw(context, generatedLoot::add);
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
