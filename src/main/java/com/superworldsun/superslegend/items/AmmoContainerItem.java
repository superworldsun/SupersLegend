package com.superworldsun.superslegend.items;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.util.ItemNBTHelper;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public abstract class AmmoContainerItem extends Item
{
	private final int capacity;
	
	public AmmoContainerItem(int capacity)
	{
		super(new Properties().durability(capacity).tab(SupersLegendMain.APPAREL));
		this.capacity = capacity;
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getMainHandItem();
		Pair<ItemStack, Integer> contents = getContents(stack);
		
		if (contents == null)
		{
			return ActionResult.fail(stack);
		}
		
		if (contents.getRight() <= 0)
		{
			return ActionResult.fail(stack);
		}
		
		if (!playerIn.level.isClientSide)
		{
			for (int i = 0; i < contents.getRight(); i++)
			{
				ItemEntity itemEntity = new ItemEntity(playerIn.level, playerIn.getX(), playerIn.getEyeY() - 0.3, playerIn.getZ(), contents.getLeft());
				itemEntity.setDeltaMovement(0, 0.1, 0);
				itemEntity.setDefaultPickUpDelay();
				playerIn.level.addFreshEntity(itemEntity);
			}
		}
		
		setCount(stack, 0);
		stack.setDamageValue(0);
		return super.use(worldIn, playerIn, handIn);
	}
	
	public void setItemStack(ItemStack itemStack, ItemStack arrowsStack)
	{
		ItemStack copy = arrowsStack.copy();
		copy.setCount(1);
		CompoundNBT nbt = new CompoundNBT();
		copy.save(nbt);
		ItemNBTHelper.setCompound(itemStack, "storedItem", nbt);
		setCount(itemStack, arrowsStack.getCount());
	}
	
	public void setCount(ItemStack itemStack, int count)
	{
		if (count == 0)
		{
			itemStack.getTag().remove("storedItem");
			itemStack.getTag().remove("itemCount");
			itemStack.setDamageValue(0);
			return;
		}
		
		ItemNBTHelper.setInt(itemStack, "itemCount", count);
		itemStack.setDamageValue(getCapacity() - count);
	}
	
	@Nullable
	public Pair<ItemStack, Integer> getContents(ItemStack itemStack)
	{
		CompoundNBT nbt = ItemNBTHelper.getCompound(itemStack, "storedItem", true);
		
		if (nbt == null)
		{
			return null;
		}
		
		ItemStack contained = ItemStack.of(nbt);
		int count = ItemNBTHelper.getInt(itemStack, "itemCount", 0);
		return Pair.of(contained, count);
	}
	
	public int getCapacity()
	{
		return capacity;
	}
	
	public boolean containsSameItem(ItemStack itemStack, ItemStack arrowsStack)
	{
		Pair<ItemStack, Integer> quiverContents = getContents(itemStack);
		
		if (quiverContents == null)
		{
			return true;
		}
		
		ItemStack arrowsInside = quiverContents.getLeft();
		
		if (arrowsInside.sameItem(arrowsStack) && ItemStack.tagMatches(arrowsInside, arrowsStack))
		{
			return true;
		}
		
		return false;
	}
	
	public abstract boolean canHoldItem(ItemStack itemStack);
	
	@SubscribeEvent
	public static void onArrowLoose(ArrowLooseEvent event)
	{
		// do not consume arrows from containers in creative mode
		if (event.getPlayer().abilities.instabuild)
		{
			return;
		}
		
		CuriosApi.getCuriosHelper().getEquippedCurios(event.getPlayer()).ifPresent(curios ->
		{
			for (int i = 0; i < curios.getSlots(); i++)
			{
				ItemStack curioStack = curios.getStackInSlot(i);
				
				if (!curioStack.isEmpty() && curioStack.getItem() instanceof AmmoContainerItem)
				{
					AmmoContainerItem ammoContainerItem = (AmmoContainerItem) curioStack.getItem();
					Pair<ItemStack, Integer> quiverContents = ammoContainerItem.getContents(curioStack);
					
					if (quiverContents == null)
					{
						continue;
					}
					
					int arrowsCount = quiverContents == null ? 0 : quiverContents.getRight();
					
					if (arrowsCount == 0)
					{
						continue;
					}
					
					ShootableItem shootableItem = (ShootableItem) event.getBow().getItem();
					
					if (shootableItem.getSupportedHeldProjectiles().test(quiverContents.getLeft()))
					{
						ammoContainerItem.setCount(curioStack, arrowsCount - 1);
					}
				}
			}
		});
	}
	
	@SubscribeEvent
	public static void onEntityItemPickup(EntityItemPickupEvent event)
	{
		ItemStack pickedStack = event.getItem().getItem();
		
		CuriosApi.getCuriosHelper().getEquippedCurios(event.getEntityLiving()).ifPresent(curios ->
		{
			for (int i = 0; i < curios.getSlots(); i++)
			{
				ItemStack curioStack = curios.getStackInSlot(i);
				
				if (!curioStack.isEmpty() && curioStack.getItem() instanceof AmmoContainerItem)
				{
					AmmoContainerItem ammoContainerItem = (AmmoContainerItem) curioStack.getItem();
					Pair<ItemStack, Integer> quiverContents = ammoContainerItem.getContents(curioStack);
					
					if (!ammoContainerItem.containsSameItem(curioStack, pickedStack))
					{
						continue;
					}
					
					int arrowsCount = quiverContents == null ? 0 : quiverContents.getRight();
					
					if (arrowsCount < ammoContainerItem.getCapacity() && ammoContainerItem.canHoldItem(pickedStack))
					{
						if (pickedStack.getCount() + arrowsCount > ammoContainerItem.getCapacity())
						{
							int newCount = pickedStack.getCount() + arrowsCount;
							pickedStack.setCount(ammoContainerItem.getCapacity() - arrowsCount);
							ammoContainerItem.setItemStack(curioStack, pickedStack);
							pickedStack.setCount(newCount - ammoContainerItem.getCapacity());
						}
						else
						{
							ammoContainerItem.setItemStack(curioStack, pickedStack);
							pickedStack.setCount(0);
							float soundPitch = (random.nextFloat() - random.nextFloat()) * 1.4F + 2.0F;
							event.getEntity().level.playSound(null, event.getEntity(), SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, soundPitch);
						}
					}
				}
			}
		});
	}
}
