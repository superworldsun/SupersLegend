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
import superworldsun.superslegend.lists.ItemList;

import java.util.List;

import net.minecraft.item.Item.Properties;

public class IceRod extends Item
{

	public IceRod(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		//acts as a cooldown.
		if (player.swinging) {
			return new ActionResult<>(ActionResultType.PASS, stack);
		}
		player.swing(hand);
		if (!player.isShiftKeyDown()) {
			world.playSound(null, player.blockPosition(), SoundEvents.ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
			if (!player.level.isClientSide) {
				EntityIceBeam icebeam = new EntityIceBeam(player.world, player);
				float arrowVelocity = 0.9F;
				icebeam.shootFromRotation(player, player.rotationPitch, player.rotationYaw, -4.0F, arrowVelocity, 0.5F);
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