package com.superworldsun.superslegend.container;

import com.google.common.collect.ImmutableList;
import com.superworldsun.superslegend.container.slot.LockedSlot;
import com.superworldsun.superslegend.container.slot.SelectSlot;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SyncMenuMessage;
import com.superworldsun.superslegend.registries.ContainerInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class SelectContainer extends Container
{
    IInventory swordInv = new Inventory(27);
    IInventory equipmentInv = new Inventory(40);
    IInventory ringInv = new Inventory(64);
    IInventory maskInv = new Inventory(24);
    IInventory statusInv = new Inventory(1);

    List<Slot> defaultSlots = new ArrayList<>();

    // Put all the items you'd like in the menus here

    List<ItemStack> swords = new ArrayList<ItemStack>()
    {{
        add(new ItemStack(ItemInit.MASTER_SWORD.get()));
        add(new ItemStack(ItemInit.BIGGORONS_SWORD.get()));
        add(new ItemStack(ItemInit.GILDED_SWORD.get()));
        add(new ItemStack(ItemInit.RAZOR_SWORD.get()));
    }};

    List<ItemStack> collectables = new ArrayList<ItemStack>()
    {{
        add(new ItemStack(ItemInit.ANCIENT_CORE.get()));
    }};

    List<ItemStack> equipment = new ArrayList<ItemStack>()
    {{
        add(new ItemStack(ItemInit.ANCIENT_BOOTS.get()));
        add(new ItemStack(ItemInit.ANCIENT_GREAVES.get()));
        add(new ItemStack(ItemInit.ANCIENT_CUIRASS.get()));
        add(new ItemStack(ItemInit.ANCIENT_HELMET.get()));
    }};

    List<ItemStack> masks = new ArrayList<ItemStack>()
    {{
        add(new ItemStack(ItemInit.MASK_BUNNYHOOD.get()));
    }};

    List<ItemStack> songs = new ArrayList<ItemStack>(){{}};


    // And any additional menus you'd like to add should go here (and in the titles array under SelectScreen)
    public List<?>[] inventories = new List[]{swords, equipment, collectables, masks, songs};

    /** onTake is where the slot gives the player the item, and it isn't trigger if picking up the item is disabled,
     * so this event is just overridden to trigger the method anyways. */
    @Override
    public ItemStack clicked(int slotIndex, int p_184996_2_, ClickType p_184996_3_, PlayerEntity player)
    {
        if(slots.size() > slotIndex)
        {
            Slot slot = slots.get(slotIndex);
            if (slot instanceof SelectSlot)
            {
                slot.onTake(player, slot.getItem());
            }
        }

        if(player instanceof ServerPlayerEntity) this.player = (ServerPlayerEntity) player;

        return super.clicked(slotIndex, p_184996_2_, p_184996_3_, player);
    }

    ServerPlayerEntity player;
    PlayerInventory playerInventory;
    public SelectContainer(int id, PlayerInventory playerInventory)
    {
        super(ContainerInit.SELECT_CONTAINER.get(), id);

        defaultSlots = ImmutableList.copyOf(slots);

        setupSwordInv(playerInventory);

        for(int k = 0; k < 27; k++)
        {
            if(inventories[menuIndex].size() > k) swordInv.setItem(k, swords.get(k));
            else swordInv.setItem(k, ItemStack.EMPTY);
        }


        this.playerInventory = playerInventory;
        if(playerInventory.player instanceof ServerPlayerEntity) this.player = (ServerPlayerEntity) playerInventory.player;
    }

    public SelectContainer(int id, PlayerInventory playerInventory, PacketBuffer additionalData)
    {
        this(id, playerInventory);
    }

    @Override
    public boolean stillValid(PlayerEntity player)
    {
        if(player instanceof ServerPlayerEntity) this.player = (ServerPlayerEntity) player;
        return true;
    }

    IInventory[] invs = new IInventory[]{ swordInv, equipmentInv, ringInv, maskInv, statusInv };

    public int menuIndex = 0;
    public void changeMenu(int offset)
    {
        menuIndex = Math.floorMod((offset + menuIndex), inventories.length);

        slots.clear();
        slots.addAll(defaultSlots);

        switch(menuIndex)
        {
            case 0:
                setupSwordInv(playerInventory);
                break;

            case 1:
                setupEquipmentInv(playerInventory);
                break;

            case 2:
                setupRingInv(playerInventory);
                break;

            case 3:
                setupMaskInv(playerInventory);
                break;

            case 4:
                setupStatusInv(playerInventory);
                break;
        }

        for(int k = 0; k < invs[menuIndex].getContainerSize(); k++)
        {
            if(inventories[menuIndex].size() > k) invs[menuIndex].setItem(k, (ItemStack) (inventories[menuIndex].get(k)));
            else invs[menuIndex].setItem(k, ItemStack.EMPTY);
        }

        if(player == null) return;
        NetworkDispatcher.networkChannel.send(PacketDistributor.PLAYER.with(() -> player), new SyncMenuMessage(menuIndex));
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int slot)
    {
        moveItemStackTo(invs[menuIndex].getItem(slot), slot, slot, false);
        return ItemStack.EMPTY;
    }

    // Placing slots

    private void setupSwordInv(PlayerInventory playerInventory)
    {
        for(int k = 0; k < 9; k++)
        {
            addSlot(new LockedSlot(playerInventory, k, 27 + 18 * k, 76));
        }

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                addSlot(new SelectSlot(swordInv, x + y * 9, 27 + 18 * (x % 9), 18 + 18 * y));
            }
        }
    }

    private void setupEquipmentInv(PlayerInventory playerInventory)
    {
        for(int k = 0; k < 9; k++)
        {
            addSlot(new LockedSlot(playerInventory, k, 27 + 18 * k, 99));
        }

        for(int y = 0; y < 4; y++)
        {
            for(int x = 0; x < 10; x++)
            {
                addSlot(new SelectSlot(equipmentInv, x + y * 9, 18 + 18 * (x % 9), 18 + 18 * y));
            }
        }
    }

    private void setupRingInv(PlayerInventory playerInventory)
    {
        for(int k = 0; k < 9; k++)
        {
            addSlot(new LockedSlot(playerInventory, k, 27 + 18 * k, 166));
        }

        for(int y = 0; y < 8; y++)
        {
            for(int x = 0; x < 8; x++)
            {
                addSlot(new SelectSlot(ringInv, x + y * 9, 37 + 18 * (x % 9), 18 + 18 * y));
            }
        }
    }

    private void setupMaskInv(PlayerInventory playerInventory)
    {
        for(int k = 0; k < 9; k++)
        {
            addSlot(new LockedSlot(playerInventory, k, 27 + 18 * k, 99));
        }

        for(int y = 0; y < 4; y++)
        {
            for(int x = 0; x < 6; x++)
            {
                addSlot(new SelectSlot(maskInv, x + y * 9, 55 + 18 * (x % 9), 18 + 18 * y));
            }
        }
    }

    private void setupStatusInv(PlayerInventory playerInventory)
    {
        addSlot(new SelectSlot(statusInv, 0, 4, 50));
    }
}
