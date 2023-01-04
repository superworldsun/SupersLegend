package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.mana.ManaHelper;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MaskFiercedeitysmask extends NonEnchantItem implements IEntityResizer, ICurioItem {
	private static final float MANA_COST = 0.015F;

	public MaskFiercedeitysmask(Properties properties) {
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.RED + "Contains a dark, godlike power.."));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Grants Strength and removes some negative effects"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Magic upon use"));
	}

	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
		PlayerEntity player = (PlayerEntity) livingEntity;

		if (ManaHelper.hasMana(player, MANA_COST)) {
			ManaHelper.spendMana(player, MANA_COST);
			// player.causeFoodExhaustion(0.0175f);
			player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 10, 1, false, false, false));
			player.addEffect(new EffectInstance(Effects.JUMP, 10, 0, false, false, false));
			player.addEffect(new EffectInstance(Effects.LUCK, 10, 1, false, false, false));
			player.removeEffect(Effects.MOVEMENT_SLOWDOWN);
			player.removeEffect(Effects.CONFUSION);
			player.removeEffect(Effects.WEAKNESS);
			player.removeEffect(Effects.UNLUCK);
			player.removeEffect(Effects.BAD_OMEN);
		}
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event) {
		if (!(event.getEntityLiving() instanceof PlayerEntity)) {
			return;
		}

		if (event.getSource() != DamageSource.FALL) {
			return;
		}

		PlayerEntity player = (PlayerEntity) event.getEntityLiving();
		boolean hasMask = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_FIERCEDEITYSMASK.get(), player).isPresent();
		boolean hasMana = ManaHelper.hasMana(player, MANA_COST);

		if (hasMask && hasMana) {
			event.isCanceled();
		}
	}

	@Override
	public float getScale(PlayerEntity player) {
		return ManaHelper.hasMana(player, MANA_COST) ? 1.4F : 1.0F;
	}

	@Override
	public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
		return false;
	}
}
