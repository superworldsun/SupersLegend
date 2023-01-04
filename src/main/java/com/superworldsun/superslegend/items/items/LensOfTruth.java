package com.superworldsun.superslegend.items.items;

import java.util.ArrayList;
import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.mana.ManaHelper;
import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class LensOfTruth extends NonEnchantItem {
	private static final List<LivingEntity> AFFECTED_ENTITIES = new ArrayList<>();
	private static final int MANA_SPENDING_FREQUENCY = 20;
	private static final float MANA_COST = 0.5F;

	public LensOfTruth(Properties properties) {
		super(properties);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);

		if (ManaHelper.hasMana(player, MANA_COST)) {
			player.level.playSound(null, player, SoundInit.LENS_OF_TRUTH_ON.get(), SoundCategory.PLAYERS, 1f, 1f);
			player.startUsingItem(hand);
			return ActionResult.consume(itemstack);
		} else {
			player.level.playSound(null, player, SoundInit.ZELDA_ERROR.get(), SoundCategory.PLAYERS, 1f, 1f);
			return ActionResult.fail(itemstack);
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, World world, LivingEntity player, int timeInUse) {
		player.level.playSound(null, player, SoundInit.LENS_OF_TRUTH_OFF.get(), SoundCategory.PLAYERS, 1f, 1f);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Hold in Hands to reveal a hidden target"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Mana on use"));
	}

	@Override
	public void onUseTick(World world, LivingEntity user, ItemStack stack, int time) {
		if (!(user instanceof PlayerEntity)) {
			return;
		}

		PlayerEntity player = (PlayerEntity) user;

		if (!ManaHelper.hasMana(player, MANA_COST)) {
			user.stopUsingItem();
		}

		if (time % MANA_SPENDING_FREQUENCY == 0) {
			ManaHelper.spendMana(player, MANA_COST);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onLivingPreRender(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
		if (event.getEntity().isInvisible()) {
			Minecraft client = Minecraft.getInstance();
			PlayerEntity player = client.player;
			boolean isLocalPlayerUsingLens = player.isUsingItem() && player.getItemInHand(player.getUsedItemHand()).getItem() == ItemInit.LENS_OF_TRUTH.get();

			if (isLocalPlayerUsingLens) {
				removeEntityInvisibility(event.getEntity());
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onLivingPostRender(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
		if (AFFECTED_ENTITIES.contains(event.getEntity())) {
			restoreEntityInvisibility(event.getEntity());
		}
	}

	@OnlyIn(Dist.CLIENT)
	private static void restoreEntityInvisibility(LivingEntity livingEntity) {
		AFFECTED_ENTITIES.remove(livingEntity);
		livingEntity.setInvisible(true);
	}

	@OnlyIn(Dist.CLIENT)
	private static void removeEntityInvisibility(LivingEntity livingEntity) {
		livingEntity.setInvisible(false);
		AFFECTED_ENTITIES.add(livingEntity);
	}
}
