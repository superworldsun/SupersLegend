package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.items.masks.BlastMask;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MaskAbilityMessage
{
	public MaskAbilityMessage()
	{
	}
	
	public static MaskAbilityMessage decode(PacketBuffer buf)
	{
		MaskAbilityMessage result = new MaskAbilityMessage();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
	}
	
	public static void receive(MaskAbilityMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ServerPlayerEntity player = ctx.getSender();
		ctx.setPacketHandled(true);
		
		if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.MASK_BLASTMASK.get())
		{
			ctx.enqueueWork(() -> BlastMask.abilityUsed(player.level, player));
		}
	}
}
