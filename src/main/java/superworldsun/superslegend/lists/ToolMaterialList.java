package superworldsun.superslegend.lists;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

public enum ToolMaterialList implements IItemTier
{
	//Weapons
		kokiri_sword		(2, -2f, 0, 0, 0, ItemList.kokiri_sword),
		razor_sword			(3, -2f, 200, 1, 0, ItemList.razor_sword),
		gilded_sword		(4, -2f, 0, 2, 0, ItemList.gilded_sword),
		master_sword		(5, -2f, 0, 0, 0, ItemList.master_sword),
		giants_knife		(10, -2f, 8, 2, 0, ItemList.giants_knife),
		broken_giants_knife	(4, -2f, 0, 1, 0, ItemList.broken_giants_knife),
		biggorons_sword		(10, -2f, 0, 2, 0, ItemList.biggorons_sword),

		gaurdian_sword		(5, -2f, 1200, 3, 0, ItemList.gaurdian_sword),
		
		rupee_sword			(2, -2f, 64, 0, 0, ItemList.rupee_sword),
		blue_rupee_sword	(3, -2f, 122, 1, 0, ItemList.blue_rupee_sword),
		red_rupee_sword		(4, -2f, 220, 2, 0, ItemList.red_rupee_sword),
		silver_rupee_sword	(5, -2f, 822, 3, 0, ItemList.silver_rupee_sword),
		gold_rupee_sword	(5, -2f, 1658, 3, 0, ItemList.gold_rupee_sword),
		
		//Tools
		rupee_pickaxe		(1, 2f, 64, 0, 0, ItemList.rupee_pickaxe),
		blue_rupee_pickaxe	(2, 3f, 122, 1, 0, ItemList.blue_rupee_pickaxe),
		red_rupee_pickaxe	(2, 4f, 220, 2, 0, ItemList.red_rupee_pickaxe),
		silver_rupee_pickaxe(3, 5f, 822, 3, 0, ItemList.silver_rupee_pickaxe),
		gold_rupee_pickaxe	(3, 5f, 1658, 3, 0, ItemList.gold_rupee_pickaxe),

		
		rupee_axe			(1, 2f, 64, 0, 0, ItemList.rupee_axe),
		blue_rupee_axe		(2, 3f, 122, 1, 0, ItemList.blue_rupee_axe),
		red_rupee_axe		(2, 4f, 220, 2, 0, ItemList.red_rupee_axe),
		silver_rupee_axe	(3, 5f, 822, 3, 0, ItemList.silver_rupee_axe),
		gold_rupee_axe		(3, 5f, 1658, 3, 0, ItemList.gold_rupee_axe),

		
		rupee_shovel		(1, 2f, 64, 0, 0, ItemList.rupee_shovel),
		blue_rupee_shovel	(2, 3f, 122, 1, 0, ItemList.blue_rupee_shovel),
		red_rupee_shovel	(2, 4f, 220, 2, 0, ItemList.red_rupee_shovel),
		silver_rupee_shovel	(3, 5f, 822, 3, 0, ItemList.silver_rupee_shovel),
		gold_rupee_shovel	(3, 5f, 1658, 3, 0, ItemList.gold_rupee_shovel),

		
		rupee_hoe			(1, 2f, 84, 0, 0, ItemList.rupee_hoe),
		blue_rupee_hoe		(2, 3f, 164, 1, 0, ItemList.blue_rupee_hoe),
		red_rupee_hoe		(2, 4f, 310, 2, 0, ItemList.red_rupee_hoe),
		silver_rupee_hoe	(3, 5f, 822, 3, 0, ItemList.silver_rupee_hoe),
		gold_rupee_hoe		(3, 5f, 1733, 3, 0, ItemList.gold_rupee_hoe);

	
	private float attackDamage, efficiency;
	private int durability, harvestLevel, enchantability;
	private Item repairMaterial;
	
	private ToolMaterialList(float attackDamage, float efficiency, int durability, int harvestlevel, int enchantability, Item repairMaterial)
	{
		this.attackDamage = attackDamage;
		this.efficiency = efficiency;
		this.durability = durability;
		this.harvestLevel = harvestlevel;
		this.enchantability = enchantability;
		this.repairMaterial = repairMaterial;
		}

	@Override
	public float getAttackDamage() 
	{
		return this.attackDamage;
	}

	@Override
	public float getEfficiency() 
	{
		return this.efficiency;
	}

	@Override
	public int getEnchantability() 
	{
		return this.enchantability;
	}

	@Override
	public int getHarvestLevel() 
	{
		return this.harvestLevel;
	}

	@Override
	public int getMaxUses() 
	{
		return this.durability;
	}

	@Override
	public Ingredient getRepairMaterial() 
	{
		return Ingredient.fromItems(this.repairMaterial);
	}
}
