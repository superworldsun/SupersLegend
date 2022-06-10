package com.superworldsun.superslegend.recipe;

import org.apache.commons.lang3.tuple.Pair;

import com.superworldsun.superslegend.items.AmmoContainerItem;
import com.superworldsun.superslegend.registries.RecipeSerializerInit;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AmmoContainersFillingRecipe extends SpecialRecipe
{
	public AmmoContainersFillingRecipe(ResourceLocation id)
	{
		super(id);
	}
	
	@Override
	public boolean matches(CraftingInventory inventory, World world)
	{
		AmmoContainerItem ammoContainer = null;
		ItemStack result = null;
		int ammoContainerSlot = 0;
		int ammoStacksCount = 0;
		
		for (int i = 0; i < inventory.getContainerSize(); i++)
		{
			ItemStack stackInSlot = inventory.getItem(i);
			
			if (stackInSlot.getItem() instanceof AmmoContainerItem)
			{
				// multiple ammo containers
				if (ammoContainer != null)
				{
					return false;
				}
				else
				{
					AmmoContainerItem ammoContainerItem = (AmmoContainerItem) stackInSlot.getItem();
					
					if (ammoContainerItem.getContents(stackInSlot) != null)
					{
						// ammo container is already full
						if (ammoContainerItem.getContents(stackInSlot).getRight() == ammoContainerItem.getCapacity())
						{
							return false;
						}
					}
					
					ammoContainer = ammoContainerItem;
					ammoContainerSlot = i;
					result = stackInSlot.copy();
				}
			}
		}
		
		// no ammo containers found
		if (ammoContainer == null)
		{
			return false;
		}
		
		for (int i = 0; i < inventory.getContainerSize(); i++)
		{
			if (i == ammoContainerSlot)
			{
				continue;
			}
			
			if (inventory.getItem(i).isEmpty())
			{
				continue;
			}
			
			// can't put this item into container
			if (!ammoContainer.canHoldItem(inventory.getItem(i)) || !ammoContainer.containsSameItem(result, inventory.getItem(i)))
			{
				return false;
			}
			else
			{
				if (ammoContainer.getContents(result) == null)
				{
					ammoContainer.setItemStack(result, inventory.getItem(i));
				}
				
				ammoStacksCount++;
			}
		}
		
		// no ammo found
		if (ammoStacksCount == 0)
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public ItemStack assemble(CraftingInventory inventory)
	{
		AmmoContainerItem ammoContainer = null;
		Pair<ItemStack, Integer> ammoContainerContents = null;
		ItemStack result = null;
		int ammoContainerSlot = 0;
		
		for (int i = 0; i < inventory.getContainerSize(); i++)
		{
			ItemStack stackInSlot = inventory.getItem(i);
			
			if (stackInSlot.getItem() instanceof AmmoContainerItem)
			{
				AmmoContainerItem ammoContainerItem = (AmmoContainerItem) stackInSlot.getItem();
				ammoContainerContents = ammoContainerItem.getContents(stackInSlot);
				ammoContainer = ammoContainerItem;
				ammoContainerSlot = i;
				result = stackInSlot.copy();
				break;
			}
		}
		
		for (int i = 0; i < inventory.getContainerSize(); i++)
		{
			if (i == ammoContainerSlot)
			{
				continue;
			}
			
			if (inventory.getItem(i).isEmpty())
			{
				continue;
			}
			
			ItemStack ammo = inventory.getItem(i);
			
			if (ammoContainerContents == null)
			{
				ammoContainer.setItemStack(result, ammo);
				ammoContainerContents = ammoContainer.getContents(result);
			}
			else if (ammoContainerContents.getRight() + ammo.getCount() <= ammoContainer.getCapacity())
			{
				ammoContainer.setCount(result, ammoContainerContents.getRight() + ammo.getCount());
				ammoContainerContents = ammoContainer.getContents(result);
			}
			else
			{
				ammoContainer.setCount(result, ammoContainer.getCapacity());
				break;
			}
		}
		
		return result;
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInventory inventory)
	{
		NonNullList<ItemStack> remainingItems = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);
		AmmoContainerItem ammoContainer = null;
		Pair<ItemStack, Integer> ammoContainerContents = null;
		ItemStack result = null;
		int ammoContainerSlot = 0;
		
		for (int i = 0; i < inventory.getContainerSize(); i++)
		{
			ItemStack stackInSlot = inventory.getItem(i);
			
			if (stackInSlot.getItem() instanceof AmmoContainerItem)
			{
				AmmoContainerItem ammoContainerItem = (AmmoContainerItem) stackInSlot.getItem();
				ammoContainerContents = ammoContainerItem.getContents(stackInSlot);
				ammoContainer = ammoContainerItem;
				ammoContainerSlot = i;
				result = stackInSlot.copy();
				break;
			}
		}
		
		for (int i = 0; i < inventory.getContainerSize(); i++)
		{
			if (i == ammoContainerSlot)
			{
				continue;
			}
			
			if (inventory.getItem(i).isEmpty())
			{
				continue;
			}
			
			ItemStack ammo = inventory.getItem(i);
			
			if (ammoContainerContents == null)
			{
				ammoContainer.setItemStack(result, ammo);
				ammoContainerContents = ammoContainer.getContents(result);
				inventory.setItem(i, ItemStack.EMPTY);
			}
			else if (ammoContainerContents.getRight() + ammo.getCount() <= ammoContainer.getCapacity())
			{
				ammoContainer.setCount(result, ammoContainerContents.getRight() + ammo.getCount());
				ammoContainerContents = ammoContainer.getContents(result);
				inventory.setItem(i, ItemStack.EMPTY);
			}
			else
			{
				int extraAmmo = ammoContainerContents.getRight() + ammo.getCount() - ammoContainer.getCapacity();
				ammoContainer.setCount(result, ammoContainer.getCapacity());
				ammoContainerContents = ammoContainer.getContents(result);
				ammo.setCount(extraAmmo);
				remainingItems.set(i, ammo.copy());
				inventory.setItem(i, ItemStack.EMPTY);
			}
		}
		
		return remainingItems;
	}
	
	@Override
	public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_)
	{
		return true;
	}
	
	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return RecipeSerializerInit.AMMO_CONTAINERS_FILLING.get();
	}
	
	public static class Serializer extends SpecialRecipeSerializer<AmmoContainersFillingRecipe>
	{
		public Serializer()
		{
			super(AmmoContainersFillingRecipe::new);
		}
	}
}
