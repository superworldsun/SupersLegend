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

/** Adds the contents of a supplemental loot table to an existing loot table. */
public final class AddLootTableModifier extends LootModifier {
    public static final Codec<AddLootTableModifier> CODEC = RecordCodecBuilder.create(instance ->
            codecStart(instance)
                    .and(ResourceLocation.CODEC.fieldOf("table").forGetter(modifier -> modifier.tableId))
                    .and(Codec.FLOAT.optionalFieldOf("chance", 1.0F).forGetter(modifier -> modifier.chance))
                    .apply(instance, AddLootTableModifier::new));

    private final ResourceLocation tableId;
    private final float chance;

    public AddLootTableModifier(LootItemCondition[] conditions, ResourceLocation tableId, float chance) {
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
