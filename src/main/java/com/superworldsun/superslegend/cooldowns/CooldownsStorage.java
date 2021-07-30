package com.superworldsun.superslegend.cooldowns;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.registries.ForgeRegistries;

public class CooldownsStorage implements IStorage<ICooldowns>
{
	@Override
	public CompoundNBT writeNBT(Capability<ICooldowns> capability, ICooldowns instance, Direction side)
	{
		CompoundNBT nbt = new CompoundNBT();
		ListNBT cooldownsNBTList = new ListNBT();
		
		instance.getItemsOnCooldown().forEach(item ->
		{
			CompoundNBT cooldownNBT = new CompoundNBT();
			cooldownNBT.putString("item", item.getRegistryName().toString());
			cooldownNBT.putInt("cooldown", instance.getCooldown(item));
			cooldownsNBTList.add(cooldownNBT);
		});
		
		nbt.put("cooldowns", cooldownsNBTList);
		return nbt;
	}
	
	@Override
	public void readNBT(Capability<ICooldowns> capability, ICooldowns instance, Direction side, INBT inbt)
	{
		CompoundNBT nbt = (CompoundNBT) inbt;
		ListNBT cooldownsNBTList = nbt.getList("cooldowns", new CompoundNBT().getId());
		
		cooldownsNBTList.forEach(cooldownINBT ->
		{
			CompoundNBT cooldownNBT = (CompoundNBT) cooldownINBT;
			ResourceLocation itemId = new ResourceLocation(cooldownNBT.getString("item"));
			Item item = ForgeRegistries.ITEMS.getValue(itemId);
			instance.setCooldown(item, cooldownNBT.getInt("cooldown"));
		});
	}
}
