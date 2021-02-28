package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowFire;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowIce;
import superworldsun.superslegend.entities.projectiles.arrows.EntityIceBeam;
import superworldsun.superslegend.lists.ItemList;

import java.util.List;

public class IceRod extends Item
{

	public IceRod(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		//acts as a cooldown.
		if (player.isSwingInProgress) {
			return new ActionResult<>(ActionResultType.PASS, stack);
		}
		player.swingArm(hand);
		if (!player.isSneaking()) {
			world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
			if (!player.world.isRemote) {
				EntityIceBeam icebeam = new EntityIceBeam(player.world, player);
				float arrowVelocity = 0.9F;
				icebeam.func_234612_a_(player, player.rotationPitch, player.rotationYaw, -4.0F, arrowVelocity, 0.5F);
				player.world.addEntity(icebeam);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.AQUA + "Uses Stamina to create Ice"));
	}

}