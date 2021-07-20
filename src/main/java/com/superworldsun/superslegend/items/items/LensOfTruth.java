package com.superworldsun.superslegend.items.items;

import java.util.ArrayList;
import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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
public class LensOfTruth extends Item
{
	private static final List<LivingEntity> RENDERED_ENTITIES = new ArrayList<>();
	
	public LensOfTruth(Properties properties)
	{
		super(properties);
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onLivingPreRender(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event)
	{
		if (event.getEntity().isInvisible())
		{
			Minecraft client = Minecraft.getInstance();
			PlayerEntity player = client.player;
			
			if (player.isUsingItem() && player.getItemInHand(player.getUsedItemHand()).getItem() == ItemInit.LENS_OF_TRUTH.get())
			{
				// Remove invisibility
				event.getEntity().setInvisible(false);
				// Store entity so we can restore invisibility later
				RENDERED_ENTITIES.add(event.getEntity());
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onLivingPostRender(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event)
	{
		if (RENDERED_ENTITIES.contains(event.getEntity()))
		{
			RENDERED_ENTITIES.remove(event.getEntity());
			// Restore invisibility
			event.getEntity().setInvisible(true);
		}
	}
	
	@Override
	public int getUseDuration(ItemStack stack)
	{
		return 72000;
	}
	
	@Override
	public UseAction getUseAnimation(ItemStack stack)
	{
		return UseAction.NONE;
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack itemstack = player.getItemInHand(hand);
		
		// Can't use without mana (only in creative)
		if (ManaProvider.get(player).getMana() < getManaCost() && !player.abilities.instabuild)
		{
			return ActionResult.fail(itemstack);
		}
		else
		{
			player.startUsingItem(hand);
			return ActionResult.consume(itemstack);
		}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Hold in Hands to reveal a hidden target"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Mana on use"));
	}
	
	@Override
	public void onUseTick(World world, LivingEntity user, ItemStack stack, int time)
	{
		// If used not by player (somehow) we don't want errors, we have mana
		// only on players
		if (!(user instanceof PlayerEntity))
		{
			return;
		}
		
		PlayerEntity player = (PlayerEntity) user;
		
		// Stop using if out of mana
		if (ManaProvider.get(player).getMana() < getManaCost())
		{
			user.stopUsingItem();
		}
		
		// Spend mana every 20 ticks (every one second) (not in creative mod)
		if (time % 20 == 0 && !player.abilities.instabuild)
		{
			ManaProvider.get(player).spendMana(getManaCost());
		}
	}
	
	private float getManaCost()
	{
		return 0.5F;
	}
}
