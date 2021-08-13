package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.interfaces.IMaskAbility;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MaskAbilityMessage
{
	private boolean started;
	
	public MaskAbilityMessage(boolean started)
	{
		this.started = started;
	}
	
	private MaskAbilityMessage()
	{
	}
	
	public static MaskAbilityMessage decode(PacketBuffer buf)
	{
		MaskAbilityMessage result = new MaskAbilityMessage();
		result.started = buf.readBoolean();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeBoolean(started);
	}
	
	public static void receive(MaskAbilityMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ServerPlayerEntity player = ctx.getSender();
		ctx.setPacketHandled(true);
		Item helmetItem = player.getItemBySlot(EquipmentSlotType.HEAD).getItem();
		
		if (helmetItem instanceof IMaskAbility)
		{
			if (message.started)
			{
				ctx.enqueueWork(() -> ((IMaskAbility) helmetItem).startUsingAbility(player));
			}
			else
			{
				ctx.enqueueWork(() -> ((IMaskAbility) helmetItem).stopUsingAbility(player));
			}
		}
	}
}
