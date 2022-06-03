package com.superworldsun.superslegend.items.curios.rings;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.RingItem;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class GreenLuckRing extends RingItem
{	
	public GreenLuckRing()
	{
		super(new Item.Properties());
	}
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event)
	{
		// Check if it is the Player who takes damage.
		if (event.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();			
			// Get the Ring as an ItemStack
			ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GREEN_LUCK_RING.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
			
			// Check if player is wearing it.
			if (!stack.isEmpty())
			{
				// Don't do a Check to see if the damage comes from DamageSource.GENERIC. I don't know what mob/block uses the "GENERIC" damage in the game so I normally do a (event.getSource !=
				// DamageSource.*Type*) if I don't want it to take less damage from something in particular.
				if (event.getSource() == DamageSource.HOT_FLOOR || event.getSource() == DamageSource.ANVIL)
				{
					event.setAmount(event.getAmount() / 2);
				}
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GREEN + "1/2 Damage From Traps"));
	}
}