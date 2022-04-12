package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.container.MediumQuiverContainer;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.ItemNBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)

public class MediumQuiver extends Item {
	public static int STORAGE = 150;
	public static final String TAG_STORED_ITEM = "storedItem";
	public static final String TAG_COUNT = "itemCount";

	public MediumQuiver()
	{
		super(new Properties()
				.durability(STORAGE)
				.tab(SupersLegendMain.RESOURCES));
	}

	@SubscribeEvent
	public static void onItemPickup(EntityItemPickupEvent event) {
		ItemStack stack = event.getItem().getItem();

		ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.BIG_QUIVER.get(), (LivingEntity) event.getEntity()).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		int count = ItemNBTHelper.getInt(stack0, TAG_COUNT, 0);
		if (!stack0.isEmpty() && count < STORAGE && stack.sameItem(Items.ARROW.getDefaultInstance())) {
			if((stack.getCount() + count) > STORAGE) {
				int newCount = stack.getCount() + count;
				stack.setCount(STORAGE - count);
				setItemStack(stack0, stack);
				stack.setCount(newCount - STORAGE);
			} else {
				setItemStack(stack0, stack);
				stack.setCount(0);
			}
		}

	}

	public static void setItemStack(ItemStack stack, ItemStack target) {
		ItemStack copy = target.copy();
		copy.setCount(1);

		CompoundNBT nbt = new CompoundNBT();
		copy.save(nbt);

		ItemNBTHelper.setCompound(stack, TAG_STORED_ITEM, nbt);
		setCount(stack, target.getCount());
	}

	public static void setCount(ItemStack stack, int count) {
		if(count == 0) {
			stack.getTag().remove(TAG_STORED_ITEM);
			stack.getTag().remove(TAG_COUNT);
			stack.setDamageValue(0);

			return;
		}
		count = count + Objects.requireNonNull(getContents(stack)).getRight();
		ItemNBTHelper.setInt(stack, TAG_COUNT, count);
		stack.setDamageValue(STORAGE - count);
	}

	public static Pair<ItemStack, Integer> getContents(ItemStack stack) {
		CompoundNBT nbt = ItemNBTHelper.getCompound(stack, TAG_STORED_ITEM, true);
		if(nbt == null)
			return null;

		ItemStack contained = ItemStack.of(nbt);
		int count = ItemNBTHelper.getInt(stack, TAG_COUNT, 0);
		return Pair.of(contained, count);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

		ItemStack stack = playerIn.getMainHandItem();
		Pair<ItemStack, Integer> contents = getContents(stack);

		if(getContents(stack) == null)
			return ActionResult.fail(stack);

		if(Objects.requireNonNull(getContents(stack)).getRight() <= 0)
			return ActionResult.fail(stack);

		double d0 = playerIn.getEyeY() - (double) 0.3F;
		if(!playerIn.level.isClientSide)
			for(int i = 0; i < Objects.requireNonNull(contents).getRight(); i++) {
				ItemEntity itementity = new ItemEntity(playerIn.level, playerIn.getX(), d0, playerIn.getZ(), contents.getLeft());
				itementity.setDeltaMovement(0, 0.1, 0);
				itementity.setDefaultPickUpDelay();
				playerIn.level.addFreshEntity(itementity);
			}
		setCount(stack,-Objects.requireNonNull(contents).getRight());
		stack.setDamageValue(0);
		return super.use(worldIn, playerIn, handIn);
	}

	@Override
	public void appendHoverText(@Nullable ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, @Nullable ITooltipFlag flagIn) {
		if (getContents(stack) != null && getContents(stack) != null) {
			tooltip.add(new StringTextComponent(TextFormatting.AQUA + Objects.requireNonNull(getContents(stack)).getRight().toString()));
			tooltip.add(new StringTextComponent(TextFormatting.WHITE + WordUtils.capitalize(StringUtils.substringAfter(Objects.requireNonNull(getContents(stack)).getLeft().toString(), " "))));
			tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "Right click to get arrows."));
		}
	}
}
