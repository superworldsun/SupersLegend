package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class BottledBee extends Item
{
	public BottledBee(Properties properties)
	{
		super(properties);
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);

		if(!world.isClientSide && !player.isCreative())
		{
			BlockPos currentPos = player.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOTTLE_POP.get(), SoundCategory.PLAYERS, 1f, 1f);

			if (!player.level.isClientSide)
			{
				BeeEntity beeEntity = EntityType.BEE.create(player.level);
				beeEntity.moveTo(player.getX(), player.getY(), player.getZ(), player.yRot, 0.0F);
				player.level.addFreshEntity(beeEntity);
				player.getCooldowns().addCooldown(this, 6);
			}
			stack.shrink(1);
			player.addItem(new ItemStack(Items.GLASS_BOTTLE));
		}

		if(!world.isClientSide && player.isCreative())
		{
			BlockPos currentPos = player.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOTTLE_POP.get(), SoundCategory.PLAYERS, 1f, 1f);

			if (!player.level.isClientSide)
			{
				BeeEntity beeEntity = EntityType.BEE.create(player.level);
				//beeEntity.getAngerTarget()
				beeEntity.moveTo(player.getX(), player.getY(), player.getZ(), player.yRot, 0.0F);
				player.level.addFreshEntity(beeEntity);
				player.getCooldowns().addCooldown(this, 6);
			}
		}
		return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));
	}


	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.YELLOW + "A captive Bee in a bottle"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-Click to Release"));
	}   
}
