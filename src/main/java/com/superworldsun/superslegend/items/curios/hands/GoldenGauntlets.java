package com.superworldsun.superslegend.items.curios.hands;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class GoldenGauntlets extends StrengthHandItem {
	public GoldenGauntlets() {
		super(new Properties(), 0);
	}

	@Override
	public boolean canPickUpEntity(EntityType<?> type) {
		return type != EntityType.WITHER && type != EntityType.ENDER_DRAGON && type != EntityType.ELDER_GUARDIAN;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(stack, world, list, flag);
		// list.add(new StringTextComponent(TextFormatting.BLUE + "Damage taken is 1/2"));
	}
}