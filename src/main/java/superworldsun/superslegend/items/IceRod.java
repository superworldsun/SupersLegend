package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowFire;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowIce;
//import superworldsun.superslegend.entities.projectiles.arrows.EntityIceBeam;
import superworldsun.superslegend.entities.projectiles.arrows.EntityIceBeam;
import superworldsun.superslegend.entities.projectiles.items.boomerang.BoomerangEntity;
import superworldsun.superslegend.entities.projectiles.items.boomerang.RegularBoomerang;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

import java.util.List;

public class IceRod extends Item
{

	public IceRod(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		//acts as a cooldown.
		if (playerIn.getFoodStats().getFoodLevel()!= 0)
		{
		if (playerIn.isSwingInProgress) {
			return new ActionResult<>(ActionResultType.PASS, stack);
		}
			playerIn.addExhaustion(6f);
			playerIn.swingArm(handIn);
			worldIn.playSound(null, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
			if (!playerIn.world.isRemote) {
				EntityIceBeam beam = new EntityIceBeam(playerIn.world, playerIn);
				float arrowVelocity = 2.0F;
				beam.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, arrowVelocity, 1.0F);
				playerIn.world.addEntity(beam);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.AQUA + "Uses Stamina to create a beam Ice"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
	}

}