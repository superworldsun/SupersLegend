package com.superworldsun.superslegend.items;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookProvider;
import com.superworldsun.superslegend.items.capabilities.SacredShieldCapability;
import com.superworldsun.superslegend.items.capabilities.SacredShieldProvider;
import com.superworldsun.superslegend.items.capabilities.SacredShieldState;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SacredShieldItem extends ExtendedShieldItem
{
	private static int regenerateTime = 200;
	public SacredShieldItem(Properties properties)
	{
		super(properties.stacksTo(1).tab(SupersLegendMain.RESOURCES).durability(50));
	}
	
	@Override
	protected void onShieldBlock(World level, PlayerEntity player, LivingEntity attacker, Entity projectile, DamageSource damage)
	{
		
	}
	
	@SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent event) {
        if (event.getObject() instanceof ItemStack) {
        	ItemStack stack = (ItemStack) event.getObject();
        	
        	if(stack.getItem() instanceof SacredShieldItem)
        	{
	            SacredShieldState shieldState = new SacredShieldState();
	
	            SacredShieldProvider provider = new SacredShieldProvider(shieldState);
	
	            event.addCapability(new ResourceLocation("zelda_hs", "cap_sacred_shield"), provider);
	
	            event.addListener(provider::invalidate);
        	}
        }
    }
	
	@SubscribeEvent
	public static void onTick(PlayerTickEvent event)
	{
		
		PlayerInventory inventory = event.player.inventory;
		for(int i = 0; i < inventory.getContainerSize(); ++i)
		{
			ItemStack item = inventory.getItem(i); 
			if(item.getItem() instanceof SacredShieldItem)
			{
				LazyOptional<SacredShieldState> stateOptional = item.getCapability(SacredShieldCapability.INSTANCE);
				if(stateOptional.isPresent())
				{
					SacredShieldState state = stateOptional.orElse(null);
					int ticksToRegenerate = state.getTicksToRegenerate();
					if(ticksToRegenerate == 0)
					{
						ticksToRegenerate = regenerateTime;
						if(item.getDamageValue() > 0)
						{
							item.setDamageValue(item.getDamageValue() - 1);
						}
						state.setTicksToRegenerate(ticksToRegenerate);
					}
					else
					{
						state.setTicksToRegenerate(ticksToRegenerate - 1);
					}
				}
			}
		}
	}
}
