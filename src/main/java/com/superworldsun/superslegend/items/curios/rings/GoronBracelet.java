package com.superworldsun.superslegend.items.curios.rings;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.HandItem;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class GoronBracelet extends HandItem
{
	public GoronBracelet()
	{
		super(new Properties());
	}
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event)
	{
		// Check if it is the Player who takes damage.
		if (event.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();			
			// Get the Ring as an ItemStack
			ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GORONS_BRACELET.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
			
			// Check if player is wearing it.
			if (!stack.isEmpty())
			{
				// Don't do a Check to see if the damage comes from DamageSource.GENERIC. I don't know what mob/block uses the "GENERIC" damage in the game so I normally do a (event.getSource !=
				// DamageSource.*Type*) if I don't want it to take less damage from something in particular.
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		//list.add(new StringTextComponent(TextFormatting.BLUE + "Damage taken is 1/2"));
	}
}