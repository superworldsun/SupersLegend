package com.superworldsun.superslegend.items.items;

import java.util.List;

import javax.annotation.Nonnull;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.mana.ManaHelper;
import com.superworldsun.superslegend.entities.projectiles.magic.GustEntity;
import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class DekuLeaf extends Item
{
	private static final float MANA_COST = 0.02F;
	
	public DekuLeaf()
	{
		super(new Item.Properties().stacksTo(1).tab(ItemGroupInit.RESOURCES));
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event)
	{
		Minecraft client = Minecraft.getInstance();
		ClientPlayerEntity player = client.player;

		if (event.phase == TickEvent.Phase.END && player != null && player.input != null)
		{
			if (player.isUsingItem() && player.getUseItem().getItem() instanceof DekuLeaf)
			{
				// manually moving player tovards look vector if pressing "forward" key
				if (player.input.up && !player.isOnGround())
				{
					double speed = 0.2;
					player.move(MoverType.SELF, player.getLookAngle().multiply(speed, 0, speed));
				}
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack heldItem = player.getItemInHand(hand);
		boolean hasMana = ManaHelper.hasMana(player, MANA_COST);
		
		if (player.isOnGround())
		{
			if (!world.isClientSide && hasMana)
			{
				float gustSpeed = 0.5F;
				Vector3d playerLookVec = player.getLookAngle();
				Vector3d gustPosition = player.getEyePosition(1F).add(playerLookVec);
				Vector3d gustMotion = playerLookVec.multiply(gustSpeed, gustSpeed, gustSpeed);
				GustEntity gustEntity = new GustEntity(gustPosition, gustMotion, world, player);
				world.addFreshEntity(gustEntity);
				player.getCooldowns().addCooldown(this, 16);
			}
			
			return new ActionResult<>(ActionResultType.PASS, heldItem);
		}
		else
		{
			if (hasMana)
			{
				player.startUsingItem(hand);
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
		boolean hasMana = ManaHelper.hasMana(player, MANA_COST);
		
		if (!player.isFallFlying() && !player.isOnGround() && !player.isInWater() && hasMana)
		{
			ManaHelper.spendMana(player, MANA_COST);
			player.fallDistance = 0F;
			
			// slows fall speed
			if (player.getDeltaMovement().y < -0.1)
			{
				Vector3d movement = player.getDeltaMovement();
				player.setDeltaMovement(new Vector3d(movement.x, -0.05, movement.z));
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
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(@Nonnull ItemStack stack, World world, @Nonnull List<ITextComponent> list, @Nonnull ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GREEN + "Hold Right-Click in the air, this will slow your decent"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses mana when airborne"));
	}
}