package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.interfaces.JumpingEntity;
import com.superworldsun.superslegend.items.armors.PegasusBootsArmor;
import com.superworldsun.superslegend.items.item.RocsFeather;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DoubleJumpMessage
{
	public DoubleJumpMessage()
	{
	}

	public static DoubleJumpMessage decode(FriendlyByteBuf buf)
	{
		DoubleJumpMessage result = new DoubleJumpMessage();
		return result;
	}

	public void encode(FriendlyByteBuf buf)
	{
	}

	public static void receive(DoubleJumpMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		ServerPlayer player = ctxSupplier.get().getSender();

		if (player != null && !PegasusBootsArmor.isCharging(player)
				&& (player.getItemBySlot(EquipmentSlot.CHEST).is(ItemInit.ROCS_CAPE.get())
				|| player.getMainHandItem().getItem() instanceof RocsFeather
				|| player.getOffhandItem().getItem() instanceof RocsFeather))
		{
			ctxSupplier.get().enqueueWork(() -> ((JumpingEntity) player).doubleJump());
		}

		ctxSupplier.get().setPacketHandled(true);
	}
}