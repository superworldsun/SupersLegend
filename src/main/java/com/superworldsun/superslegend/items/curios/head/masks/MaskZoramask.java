package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.player.ZoraPlayerModel;
import com.superworldsun.superslegend.interfaces.IPlayerModelChanger;
import com.superworldsun.superslegend.mana.ManaProvider;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class MaskZoramask extends Item implements IPlayerModelChanger, ICurioItem
{
	float manaCost = 0.03F;
	
	public MaskZoramask(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "The face of a Zora"));
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "You can swim with the grace of a Zora"));
	}
	
	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
	{
		PlayerEntity player = (PlayerEntity) livingEntity;
		boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
		
		if (player.isSwimming() && hasMana && player.isAlive())
		{
			ManaProvider.get(player).spendMana(manaCost);
			player.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 4, 0, true, true, true));
		}
	}
	
	@Override
	public PlayerModel<AbstractClientPlayerEntity> getPlayerModel(AbstractClientPlayerEntity player)
	{
		return new ZoraPlayerModel();
	}
	
	@Override
	public ResourceLocation getPlayerTexture(AbstractClientPlayerEntity player)
	{
		return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/zora_player.png");
	}
}