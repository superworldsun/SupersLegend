package com.superworldsun.superslegend.items.weapons;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.seeds.BeetrootSeedEntity;
import com.superworldsun.superslegend.entities.projectiles.seeds.CocoaBeanEntity;
import com.superworldsun.superslegend.entities.projectiles.seeds.DekuSeedEntity;
import com.superworldsun.superslegend.entities.projectiles.seeds.MelonSeedEntity;
import com.superworldsun.superslegend.entities.projectiles.seeds.PumpkinSeedEntity;
import com.superworldsun.superslegend.entities.projectiles.seeds.WheatSeedEntity;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
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
			ItemStack ammoStack = player.getProjectile(stack);
			int i = getUseDuration(stack) - timeLeft;
			i = ForgeEventFactory.onArrowLoose(stack, worldIn, player, i, !ammoStack.isEmpty() || infiniteAmmo);
			
			if(ammoStack.getItem() == Items.ARROW)
			{
				ammoStack = new ItemStack(ItemInit.DEKU_SEEDS.get());
			}
			
			if (i < 0)
			{
				return;
			}
			
			if (!ammoStack.isEmpty() || infiniteAmmo)
			{
				float shotPower = getPowerForTime(i) * 0.5f;
				
				if (shotPower >= 0.1D)
				{
					if (!worldIn.isClientSide)
					{
						AbstractArrowEntity projectile = createAmmoEntity(worldIn, ammoStack, player);
						projectile.shootFromRotation(player, player.xRot, player.yRot, 0.0F, shotPower * 3.0F, 1.0F);
						worldIn.addFreshEntity(projectile);
					}
					
					worldIn.playSound(null, player, SoundInit.SLINGSHOT_SHOOT.get(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + shotPower * 0.5F);
					
					if (!infiniteAmmo && !player.abilities.instabuild)
					{
						ammoStack.shrink(1);
						
						if (ammoStack.isEmpty())
						{
							player.inventory.removeItem(ammoStack);
						}
					}
					
					player.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}
	
	@Nonnull
	private AbstractArrowEntity createAmmoEntity(World world, ItemStack ammoStack, PlayerEntity player)
	{
		Item ammoItem = ammoStack.getItem();
		
		if (ammoItem instanceof ArrowItem)
		{
			return ((ArrowItem) ammoItem).createArrow(world, ammoStack, player);
		}
		else if (ammoItem == Items.BEETROOT_SEEDS)
		{
			return new BeetrootSeedEntity(world, player);
		}
		else if (ammoItem == Items.WHEAT_SEEDS)
		{
			return new WheatSeedEntity(world, player);
		}
		else if (ammoItem == Items.MELON_SEEDS)
		{
			return new MelonSeedEntity(world, player);
		}
		else if (ammoItem == Items.PUMPKIN_SEEDS)
		{
			return new PumpkinSeedEntity(world, player);
		}
		else if (ammoItem == Items.COCOA_BEANS)
		{
			return new CocoaBeanEntity(world, player);
		}
		
		return new DekuSeedEntity(world, player);
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