package com.superworldsun.superslegend.items.masks;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.DekuPlayerModel;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.interfaces.IPlayerModelChanger;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class DekuMask extends NonEnchantArmor implements IPlayerModelChanger, IEntityResizer
{
	public DekuMask(Properties properties)
	{
		super(ArmourInit.DEKU_MASK, EquipmentSlotType.HEAD, properties);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.DARK_GREEN + "The face of a Deku"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Your wooden skin is unlikely to be poisoned"));
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
	{
		player.removeEffect(Effects.POISON);
	}
	
	@OnlyIn(value = Dist.CLIENT)
	@Override
	public PlayerModel<AbstractClientPlayerEntity> getPlayerModel(AbstractClientPlayerEntity player)
	{
		return new DekuPlayerModel();
	}
	
	@OnlyIn(value = Dist.CLIENT)
	@Override
	public ResourceLocation getPlayerTexture(AbstractClientPlayerEntity player)
	{
		return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/deku_player.png");
	}
	
	@Override
	public float getScale(PlayerEntity player)
	{
		return 0.75F;
	}
	
	@Override
	public float getRenderScale(PlayerEntity player)
	{
		return 1.0F;
	}
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event)
	{
		if (!(event.getEntity() instanceof PlayerEntity))
		{
			return;
		}
		
		if (event.getEntityLiving().getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.MASK_DEKUMASK.get())
		{
			if (event.getSource() == DamageSource.LAVA)
			{
				event.setAmount(event.getAmount() * 4);
			}
			else if (event.getSource().isFire())
			{
				event.setAmount(event.getAmount() * 2);
			}
		}
	}
}