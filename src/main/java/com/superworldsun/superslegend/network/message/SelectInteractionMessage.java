package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.container.SelectContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectInteractionMessage
{
    public final int changePage;
    public final boolean openInventory;

    /**
     * @param changePage 1 is when the right button is pressed, and -1 is when the left button is pressed
     * @param openInventory Only for opening the inventory from a keybind
     */
    public SelectInteractionMessage(int changePage, boolean openInventory)
    {
        this.changePage = changePage;
        this.openInventory = openInventory;
    }

    public static SelectInteractionMessage decode(PacketBuffer buf)
    {
        SelectInteractionMessage result = new SelectInteractionMessage(buf.readInt(), buf.readBoolean());
        return result;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeInt(changePage);
        buf.writeBoolean(openInventory);
    }

    public static void handle(SelectInteractionMessage msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            ServerPlayerEntity sender = ctx.get().getSender();

            if(sender == null) return;
            if(msg.openInventory) sender.openMenu(new SimpleNamedContainerProvider((id, inventory, type) ->
                    new SelectContainer(id, inventory), new StringTextComponent("Swords")));
            else if(msg.changePage != 0)
            {
                if(sender.containerMenu instanceof SelectContainer)
                {
                    ((SelectContainer) sender.containerMenu).changeMenu(msg.changePage);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
