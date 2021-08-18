package com.superworldsun.superslegend.loot;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class FishingLootModifier extends LootModifier
{
	protected final TableLootEntry table;
	protected final float chance;
	
	public FishingLootModifier(ILootCondition[] conditions, TableLootEntry lootTable, float replaceChance)
	{
		super(conditions);
		table = lootTable;
		chance = replaceChance;
	}
	
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
	{
		Set<LootTable> set = context.visitedTables;
		
		if (set.isEmpty() && context.getRandom().nextFloat() <= chance)
		{
			List<ItemStack> loot = Lists.newArrayList();
			table.createItemStack(loot::add, context);
			return loot;
		}
		else
		{
			return generatedLoot;
		}
	}
	
	public static class Serializer extends GlobalLootModifierSerializer<FishingLootModifier>
	{
		@Override
		public FishingLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] lootConditions)
		{
			String resLoc = JSONUtils.getAsString(object, "table");
			TableLootEntry table = (TableLootEntry) TableLootEntry.lootTableReference(new ResourceLocation(resLoc)).build();
			float chance = JSONUtils.getAsFloat(object, "chance");
			return new FishingLootModifier(lootConditions, table, chance);
		}
		
		@Override
		public JsonObject write(FishingLootModifier instance)
		{
			JsonObject json = makeConditions(instance.conditions);
			json.addProperty("table", instance.table.name.toString());
			json.addProperty("chance", instance.chance);
			return json;
		}
	}
}
