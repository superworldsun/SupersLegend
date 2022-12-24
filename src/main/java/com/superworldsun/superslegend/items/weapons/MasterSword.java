package com.superworldsun.superslegend.items.weapons;

import java.util.List;

import com.superworldsun.superslegend.entities.projectiles.mastersword.MasterSwordSwordEntity;
import com.superworldsun.superslegend.items.custom.ItemCustomSword;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MasterSword extends ItemCustomSword
{
	public MasterSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		if (playerIn.isCrouching() && !playerIn.getCooldowns().isOnCooldown(this))
		{
			if (playerIn.getHealth() >= playerIn.getMaxHealth() || playerIn.isCreative())
			{
				if (!worldIn.isClientSide)
				{
					BlockPos currentPos = playerIn.blockPosition();
					worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.SWORD_BEAM_SUMMON.get(), SoundCategory.PLAYERS, 0.5f, 1f);
					MasterSwordSwordEntity sword = new MasterSwordSwordEntity(playerIn.level, playerIn);
					sword.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.5F, 1.0F);
					playerIn.level.addFreshEntity(sword);
				}
			}
			else
			{
				playerIn.displayClientMessage(new TranslationTextComponent(TextFormatting.RED + "Try again with full health!"), true);			}
			
			playerIn.getCooldowns().addCooldown(this, 20);
			playerIn.swing(handIn);
		}
		
		return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.WHITE + "The Blade of Evil's Bane"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Sneak+Right-Click to Fire a Beam at full HP"));
	}
}
