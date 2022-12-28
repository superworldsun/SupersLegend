package com.superworldsun.superslegend.items.weapons;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.TagInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.ClickInputEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public abstract class HammerItem extends TieredItem implements IVanishable
{
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;
	
	public HammerItem(IItemTier itemTier, int attackDamageBonus, Properties properties)
	{
		super(itemTier, properties);
		float attackDamage = attackDamageBonus + itemTier.getAttackDamageBonus();
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
		defaultModifiers = builder.build();
	}

	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}
	
	@SubscribeEvent
	public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event)
	{
		// only for hammers
		if (!(event.getItemStack().getItem() instanceof HammerItem))
		{
			return;
		}
		
		HammerItem hammerItem = (HammerItem) event.getItemStack().getItem();
		
		if (event.getPlayer().getCooldowns().isOnCooldown(hammerItem))
		{
			// can't click when on cooldown
			event.setUseItem(Result.DENY);
			event.setUseBlock(Result.DENY);
			event.setCanceled(true);
			return;
		}
		
		BlockState blockState = event.getWorld().getBlockState(event.getPos());
		event.getPlayer().getCooldowns().addCooldown(hammerItem, hammerItem.getLeftClickCooldown());
		
		if (blockState.is(TagInit.FRAGILE))
		{
			event.getWorld().destroyBlock(event.getPos(), false, event.getPlayer());
			Block.dropResources(blockState, event.getWorld(), event.getPos());
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onClickInput(ClickInputEvent event)
	{
		if (!event.isAttack())
		{
			return;
		}
		
		Minecraft minecraft = Minecraft.getInstance();
		Item mainHandItem = minecraft.player.getMainHandItem().getItem();
		
		// only for hammers
		if (!(mainHandItem instanceof HammerItem))
		{
			return;
		}
		
		if (minecraft.player.getCooldowns().isOnCooldown(mainHandItem))
		{
			// can't click when on cooldown
			event.setCanceled(true);
			event.setSwingHand(false);
			return;
		}
		
		HammerItem hammerItem = (HammerItem) mainHandItem;
		minecraft.player.getCooldowns().addCooldown(mainHandItem, hammerItem.getLeftClickCooldown());
	}
	
	@Override
	public boolean canAttackBlock(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity)
	{
		return !playerEntity.isCreative();
	}
	
	@Override
	public float getDestroySpeed(ItemStack itemStack, BlockState blockState)
	{
		if (blockState.is(Blocks.COBWEB))
		{
			return 15.0F;
		}
		else
		{
			Material material = blockState.getMaterial();
			return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && !blockState.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F
					: 1.5F;
		}
	}
	
	@Override
	public boolean isCorrectToolForDrops(BlockState blockState)
	{
		return blockState.is(Blocks.COBWEB);
	}
	
	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity attackingEntity, LivingEntity targetEntity)
	{
		itemStack.hurtAndBreak(1, targetEntity, entity -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		return true;
	}
	
	@Override
	public boolean mineBlock(ItemStack itemStack, World world, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity)
	{
		if (blockState.getDestroySpeed(world, blockPos) != 0.0F)
		{
			itemStack.hurtAndBreak(2, livingEntity, entity -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		}
		
		return true;
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slotType)
	{
		return slotType == EquipmentSlotType.MAINHAND ? this.defaultModifiers : ImmutableMultimap.of();
	}
	
	protected abstract int getLeftClickCooldown();
}
