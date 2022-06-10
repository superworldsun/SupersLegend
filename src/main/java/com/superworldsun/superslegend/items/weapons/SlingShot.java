package com.superworldsun.superslegend.items.weapons;

import java.util.function.Predicate;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.seeds.DekuSeedEntity;
import com.superworldsun.superslegend.items.items.DekuSeed;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SlingShot extends BowItem
{
	public SlingShot()
	{
		super(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles()
	{
		return stack -> stack.getItem().is(TagInit.PELLETS);
	}
	
	@Override
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
	{
		if (entityLiving instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) entityLiving;
			boolean infiniteAmmo = player.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
			ItemStack ammoItem = player.getProjectile(stack);
			
			int i = this.getUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, player, i, !ammoItem.isEmpty() || infiniteAmmo);
			
			if (i < 0)
			{
				return;
			}
			
			if (!ammoItem.isEmpty() || infiniteAmmo)
			{
				if (ammoItem.isEmpty())
				{
					ammoItem = new ItemStack(ItemInit.DEKU_SEEDS.get());
				}
				
				float shotPower = getPowerForTime(i) * 0.5f;
				
				if (!((double) shotPower < 0.1D))
				{
					boolean flag1 = player.abilities.instabuild || (ammoItem.getItem() instanceof DekuSeed && ((DekuSeed) ammoItem.getItem()).isInfinite(ammoItem, stack, player));
					
					if (!worldIn.isClientSide)
					{
						DekuSeed DekuSeed = (DekuSeed) (ammoItem.getItem() instanceof DekuSeed ? ammoItem.getItem() : ItemInit.DEKU_SEEDS.get());
						DekuSeedEntity shot = (DekuSeedEntity) DekuSeed.createArrow(worldIn, ammoItem, player);
						shot.shootFromRotation(player, player.xRot, player.yRot, 0.0F, shotPower * 3.0F, 1.0F);
						worldIn.addFreshEntity(shot);
					}
					
					if (i >= 20)
					{
						worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundInit.SLINGSHOT_SHOOT.get(), SoundCategory.PLAYERS, 1.0F,
								1.0F / (random.nextFloat() * 0.4F + 1.2F) + shotPower * 0.5F);
					}
					else
					{
						worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundInit.SLINGSHOT_SHOOT.get(), SoundCategory.PLAYERS, 1.0F,
								1.0F / (random.nextFloat() * 0.4F + 1.2F) + shotPower * 0.5F);
					}
					
					if (!flag1 && !player.abilities.instabuild)
					{
						ammoItem.shrink(1);
						
						if (ammoItem.isEmpty())
						{
							player.inventory.removeItem(ammoItem);
						}
					}
					
					player.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}
	
	public static float getPowerForTime(int timeInUse)
	{
		float power = (float) timeInUse / 10.0F;
		power = (power * power + power * 2.0F) / 3.0F;
		
		if (power > 1.0F)
		{
			power = 1.0F;
		}
		
		return power;
	}
	
	@SubscribeEvent
	public static void onLivingEntityUseItem(LivingEntityUseItemEvent event)
	{
		if (event.getItem().getItem() instanceof SlingShot)
		{
			if (event.getEntityLiving().isUsingItem())
			{
				if (event.getDuration() == 72000)
				{
					event.getEntityLiving().level.playSound(null, event.getEntityLiving(), SoundInit.SLINGSHOT_PULL.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
				}
				
				if (event.getDuration() > 71980)
				{
					event.setDuration(event.getDuration() - 1);
				}
			}
		}
	}
}