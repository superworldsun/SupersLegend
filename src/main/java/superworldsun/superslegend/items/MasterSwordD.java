package superworldsun.superslegend.items;

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
import net.minecraft.world.World;
import superworldsun.superslegend.entities.projectiles.beam.EntitySwordBeam;
import superworldsun.superslegend.init.SoundInit;

import java.util.List;

import net.minecraft.item.Item.Properties;

public class MasterSwordD extends ItemCustomSword
{

	public MasterSwordD(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	//TODO CHANGE ENTITY TO NEW ONE (ARROW PLACE HOLDER)

	/*public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getItemInHand(handIn);
		playerIn.swing(handIn);
		if (!worldIn.isClientSide && !playerIn.isCreative() && !playerIn.isHurt())
		{
			playerIn.getCooldowns().addCooldown(this, 15);

			BlockPos currentPos = playerIn.getPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BITBOW_ARROW, SoundCategory.PLAYERS, 3f, 1f);

			EntitySwordBeam beam = new EntitySwordBeam(playerIn.level, playerIn);
			float arrowVelocity = 1.5F;
			beam.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, arrowVelocity, 1.0F);
			playerIn.level.addFreshEntity(beam);
		}
		else if (!worldIn.isClientSide && playerIn.isCreative()) {
			playerIn.getCooldowns().addCooldown(this, 15);

			BlockPos currentPos = playerIn.getPosition();
			worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BITBOW_ARROW, SoundCategory.PLAYERS, 3f, 1f);

			EntitySwordBeam beam = new EntitySwordBeam(playerIn.level, playerIn);
			float arrowVelocity = 1.5F;
			beam.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, arrowVelocity, 1.0F);
			playerIn.level.addFreshEntity(beam);
		}
		return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
	}


	/*public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(entity instanceof PlayerEntity && !world.isClientSide)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getHeldItemMainhand();
			if(!world.isClientSide)
			{
				if(stack == equipped && !player.shouldHeal())
				{
					if player.swingArm(Hand.MAIN_HAND);
					{
						EntityArrowFire firearrow = new EntityArrowFire(player.world, player);
						firearrow.shootFromRotation(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
						player.world.addEntity(firearrow);
					}
				}
			}
		}
	}*/


	/*@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity playerIn)
	{
			{
				if (playerIn.getHealth() >= playerIn.getMaxHealth()) {

					EntityArrowFire firearrow = new EntityArrowFire(playerIn.world, playerIn);
					firearrow.shootFromRotation(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
					playerIn.world.addEntity(firearrow);
				}
		}
		return true;
	}*/


	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.DARK_RED + "A Sword Infused with the Flames of"));
		list.add(new StringTextComponent(TextFormatting.RED + "Din"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Right-Click to Fire a Beam at full HP"));
	}
}
