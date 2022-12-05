package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;

import com.superworldsun.superslegend.items.ammobags.BombBagItem;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class DropBombMessage
{
	public DropBombMessage()
	{
	}
	
	public static DropBombMessage decode(PacketBuffer buf)
	{
		return new DropBombMessage();
	}
	
	public void encode(PacketBuffer buf)
	{
	}
	
	public static void receive(DropBombMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ServerPlayerEntity player = ctx.getSender();
		ctx.setPacketHandled(true);
		ItemStack bombBag = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof BombBagItem, player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		
		if (bombBag.isEmpty())
			return;
		
		BombBagItem bombBagItem = (BombBagItem) bombBag.getItem();
		Pair<ItemStack, Integer> bagContents = bombBagItem.getContents(bombBag);
		int bombsCount = bagContents.getRight();
		
		if (bombsCount == 0)
			return;
		
		Hand emptyHand;
		
		if (player.getMainHandItem().isEmpty())
		{
			emptyHand = Hand.MAIN_HAND;
		}
		else if (player.getOffhandItem().isEmpty())
		{
			emptyHand = Hand.OFF_HAND;
		}
		else
		{
			player.sendMessage(new StringTextComponent("You'll need to free your hands for that"), null);
			return;
		}
		
		player.setItemInHand(emptyHand, new ItemStack(bagContents.getKey().getItem()));
		bombBagItem.setCount(bombBag, bombsCount - 1);		
	}
}
