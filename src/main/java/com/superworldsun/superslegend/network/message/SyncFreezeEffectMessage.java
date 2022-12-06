package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.registries.EffectInit;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SyncFreezeEffectMessage
{
	public int entityId;
	
	public SyncFreezeEffectMessage(LivingEntity livingEntity)
	{
		entityId = livingEntity.getId();
	}
	
	private SyncFreezeEffectMessage()
	{
	}
	
	public static SyncFreezeEffectMessage decode(PacketBuffer buf)
	{
		SyncFreezeEffectMessage result = new SyncFreezeEffectMessage();
		result.entityId = buf.readInt();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeInt(entityId);
	}
	
	public static void receive(SyncFreezeEffectMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.setPacketHandled(true);
		ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SyncFreezeEffectMessage.handlePacket(message, ctx)));
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void handlePacket(SyncFreezeEffectMessage message, Context ctx)
	{
		Minecraft client = Minecraft.getInstance();
		Entity entity = client.level.getEntity(message.entityId);
		((LivingEntity) entity).addEffect(new EffectInstance(EffectInit.FREEZE.get(), 2));
	}
}
