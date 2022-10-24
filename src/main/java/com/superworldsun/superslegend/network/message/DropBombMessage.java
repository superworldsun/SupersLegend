package com.superworldsun.superslegend.network.message;

import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;

import com.superworldsun.superslegend.entities.projectiles.bombs.EntityBomb;
import com.superworldsun.superslegend.items.ammobags.BombBagItem;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;

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
		Predicate<ItemStack> stackFilter = stack -> stack.getItem() instanceof BombBagItem;
		ItemStack bombBag = CuriosApi.getCuriosHelper().findEquippedCurio(stackFilter, player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		
		if (!bombBag.isEmpty())
		{
			if (message.started)
			{
				BombBagItem bombBagItem = (BombBagItem) bombBag.getItem();
				Pair<ItemStack, Integer> bagContents = bombBagItem.getContents(bombBag);
				int bombsCount = bagContents.getRight();
				
				if (bombsCount != 0)
				{
					World level = player.level;
					EntityBomb bombEntity = new EntityBomb(player, level);
					float pitch = 0;
					float throwingForce = 0.7F;
					bombEntity.shootFromRotation(player, player.xRot, player.yRot, pitch, throwingForce, 0.9F);
					level.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
					level.addFreshEntity(bombEntity);
					bombBagItem.setCount(bombBag, bombsCount - 1);
				}
			}
		}
	}
}
