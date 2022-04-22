package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.entities.projectiles.bombs.EntityBomb;
import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.items.*;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public class DropBombMessage
{
	private boolean started;

	public DropBombMessage(boolean started)
	{
		this.started = started;
	}

	private DropBombMessage()
	{
	}
	
	public static DropBombMessage decode(PacketBuffer buf)
	{
		DropBombMessage result = new DropBombMessage();
		result.started = buf.readBoolean();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeBoolean(started);
	}
	
	public static void receive(DropBombMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ServerPlayerEntity player = ctx.getSender();
		ctx.setPacketHandled(true);
		ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.BOMB_BAG.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		ItemStack stack1 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.BIG_BOMB_BAG.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		ItemStack stack2 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.BIGGEST_BOMB_BAG.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		if(!stack0.isEmpty()) {
			if (message.started)
			{
				if(SmallBombBag.getContents(stack0).getRight() != 0) {
					EntityBomb entity = new EntityBomb(EntityTypeInit.BOMB.get(), player.level);
					entity.setPos(player.getX(), player.getY(), player.getZ());
					player.level.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
					player.level.addFreshEntity(entity);
					SmallBombBag.setCount(stack0, -1);
				}
			}
		}
		else if(!stack1.isEmpty()) {
			if (message.started)
			{
				if(MediumBombBag.getContents(stack1).getRight() != 0) {
					EntityBomb entity = new EntityBomb(EntityTypeInit.BOMB.get(), player.level);
					entity.setPos(player.getX(), player.getY(), player.getZ());
					player.level.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
					player.level.addFreshEntity(entity);
					MediumBombBag.setCount(stack1, -1);
				}
			}
		}
		else if(!stack2.isEmpty()) {
			if (message.started)
			{
				if(BigBombBag.getContents(stack2).getRight() != 0) {
					EntityBomb entity = new EntityBomb(EntityTypeInit.BOMB.get(), player.level);
					entity.setPos(player.getX(), player.getY(), player.getZ());
					player.level.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
					player.level.addFreshEntity(entity);
					BigBombBag.setCount(stack2, -1);
				}
			}
		}
	}
}
