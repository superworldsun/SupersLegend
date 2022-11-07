package com.superworldsun.superslegend.items.items;

import java.util.List;

import javax.annotation.Nonnull;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.magic.GustEntity;
import com.superworldsun.superslegend.mana.ManaProvider;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class DekuLeaf extends Item
{
	private final float manacost = 0.1F;
	
	public DekuLeaf()
	{
		super(new Item.Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES));
	}

	//TODO Show the Model of the leaf when the player has magic in creative mode always,
	// if a player has no magic and is in creative mode it will show the leaf as the no magic model

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
	{
		ItemStack heldItem = playerEntity.getItemInHand(hand);
		boolean hasMana = ManaProvider.get(playerEntity).getMana() >= manacost || playerEntity.abilities.instabuild;
		
		if (playerEntity.isOnGround())
		{
			if (!world.isClientSide && hasMana)
			{
				float gustSpeed = 0.5F;
				Vector3d playerLookVec = playerEntity.getLookAngle();
				Vector3d gustPosition = playerEntity.getEyePosition(1F).add(playerLookVec);
				Vector3d gustMotion = playerLookVec.multiply(gustSpeed, gustSpeed, gustSpeed);
				GustEntity gustEntity = new GustEntity(gustPosition, gustMotion, world, playerEntity);
				world.addFreshEntity(gustEntity);
				playerEntity.getCooldowns().addCooldown(this, 16);
			}
			
			return new ActionResult<>(ActionResultType.PASS, heldItem);
		}
		else
		{
			
			if (hasMana)
			{
				playerEntity.startUsingItem(hand);
				return new ActionResult<>(ActionResultType.PASS, heldItem);
			}
		}
		
		return new ActionResult<>(ActionResultType.FAIL, heldItem);
	}
	
	@Override
	public void onUsingTick(ItemStack stack, LivingEntity livingEntity, int count)
	{
		if (!(livingEntity instanceof PlayerEntity))
		{
			return;
		}
		
		PlayerEntity player = (PlayerEntity) livingEntity;
		boolean hasMana = ManaProvider.get(player).getMana() >= manacost || player.abilities.instabuild;
		
		if (!player.isFallFlying() && !player.isOnGround() && !player.isInWater() && hasMana)
		{
			player.fallDistance = 0F;
			
			// slows fall speed
			if (player.getDeltaMovement().y < -0.1)
			{
				// TODO: manually move player on keyboard input
				Vector3d movement = player.getDeltaMovement();
				player.setDeltaMovement(new Vector3d(movement.x, -0.05, movement.z));
			}
			
			// do not spend mana in creeative mode
			if (!player.abilities.instabuild)
			{
				ManaProvider.get(player).spendMana(manacost);
			}
			
			int particlesDensity = 5;
			
			for (int i = 0; i < particlesDensity; i++)
			{
				double particleX = player.getX() + (player.getRandom().nextBoolean() ? -1 : 1) * Math.pow(player.getRandom().nextFloat(), 1) * 1;
				double particleY = player.getY() + player.getRandom().nextFloat() * 1 - 2;
				double particleZ = player.getZ() + (player.getRandom().nextBoolean() ? -1 : 1) * Math.pow(player.getRandom().nextFloat(), 1) * 1;
				player.level.addParticle(ParticleTypes.CLOUD, particleX, particleY, particleZ, 0, 0.105D, 0);
			}
		}
	}
	
	@Override
	public int getUseDuration(ItemStack itemStack)
	{
		return 72000;
	}
	
	@Override
	public void appendHoverText(@Nonnull ItemStack stack, World world, @Nonnull List<ITextComponent> list, @Nonnull ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GREEN + "Hold Right-Click in the air, this will slow your decent"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses mana when airborne"));
	}
}