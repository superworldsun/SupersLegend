package com.superworldsun.superslegend.items.ammobags;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.util.ItemNBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public abstract class AmmoContainerItem extends Item implements ICurioItem {
    private final int capacity;

    public AmmoContainerItem(int capacity) {
        super(new Properties().durability(capacity));
        this.capacity = capacity;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Pair<ItemStack, Integer> contents = getContents(stack);

        if (contents == null) {
            return InteractionResultHolder.fail(stack);
        }

        if (contents.getRight() <= 0) {
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide()) {
            for (int i = 0; i < contents.getRight(); i++) {
                ItemEntity itemEntity = new ItemEntity(level, player.getX(), player.getEyeY() - 0.3, player.getZ(), contents.getLeft().copy());
                itemEntity.setDeltaMovement(0, 0.1, 0);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
        }

        setCount(stack, 0);
        stack.setDamageValue(0);
        return super.use(level, player, hand);
    }

    public void setItemStack(ItemStack itemStack, ItemStack arrowsStack) {
        ItemStack copy = arrowsStack.copy();
        copy.setCount(1);
        CompoundTag nbt = new CompoundTag();
        copy.save(nbt);
        ItemNBTHelper.setCompound(itemStack, "storedItem", nbt);
        setCount(itemStack, arrowsStack.getCount());
    }

    public void setCount(ItemStack itemStack, int count) {
        if (count == 0) {
            itemStack.getTag().remove("storedItem");
            itemStack.getTag().remove("itemCount");
            itemStack.setDamageValue(0);
            return;
        }

        ItemNBTHelper.setInt(itemStack, "itemCount", count);
        itemStack.setDamageValue(getCapacity() - count);
    }

    @Nullable
    public Pair<ItemStack, Integer> getContents(ItemStack itemStack) {
        CompoundTag nbt = ItemNBTHelper.getCompound(itemStack, "storedItem", true);

        if (nbt == null) {
            return null;
        }

        ItemStack contained = ItemStack.of(nbt);
        int count = ItemNBTHelper.getInt(itemStack, "itemCount", 0);
        return Pair.of(contained, count);
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean containsSameItem(ItemStack itemStack, ItemStack arrowsStack) {
        Pair<ItemStack, Integer> quiverContents = getContents(itemStack);

        if (quiverContents == null) {
            return true;
        }

        return ItemStack.isSameItemSameTags(quiverContents.getLeft(), arrowsStack);
    }

    public abstract boolean canHoldItem(ItemStack itemStack);

    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        // do not consume arrows from containers in creative mode
        if (event.getEntity().getAbilities().instabuild) {
            return;
        }

        CuriosApi.getCuriosHelper().getEquippedCurios(event.getEntity()).ifPresent(curios -> {
            for (int i = 0; i < curios.getSlots(); i++) {
                ItemStack curioStack = curios.getStackInSlot(i);

                if (!curioStack.isEmpty() && curioStack.getItem() instanceof AmmoContainerItem ammoContainerItem) {
                    Pair<ItemStack, Integer> quiverContents = ammoContainerItem.getContents(curioStack);

                    if (quiverContents == null) {
                        continue;
                    }

                    int arrowsCount = quiverContents.getRight();

                    if (arrowsCount == 0) {
                        continue;
                    }

                    ProjectileWeaponItem shootableItem = (ProjectileWeaponItem) event.getBow().getItem();

                    if (shootableItem.getSupportedHeldProjectiles().test(quiverContents.getLeft())) {
                        ammoContainerItem.setCount(curioStack, arrowsCount - 1);
                    }
                }
            }
        });
    }

    @SubscribeEvent
    public static void onEntityItemPickup(EntityItemPickupEvent event) {
        ItemStack pickedStack = event.getItem().getItem();
        Player player = event.getEntity();

        CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent(curios -> {
            for (int i = 0; i < curios.getSlots(); i++) {
                ItemStack curioStack = curios.getStackInSlot(i);

                if (curioStack.isEmpty() || !(curioStack.getItem() instanceof AmmoContainerItem containerItem))
                    continue;

                if (!containerItem.canHoldItem(pickedStack))
                    continue;

                if (!containerItem.containsSameItem(curioStack, pickedStack))
                    continue;

                Pair<ItemStack, Integer> containerContents = containerItem.getContents(curioStack);
                int ammoCount = containerContents == null ? 0 : containerContents.getRight();

                if (ammoCount >= containerItem.getCapacity())
                    continue;

                int pickedCount = pickedStack.getCount();
                containerItem.setItemStack(curioStack, pickedStack);

                if (pickedCount + ammoCount > containerItem.getCapacity()) {
                    containerItem.setCount(curioStack, containerItem.getCapacity());
                    pickedStack.shrink(containerItem.getCapacity() - ammoCount);
                } else {
                    containerItem.setCount(curioStack, pickedCount + ammoCount);
                    pickedStack.setCount(0);
                    RandomSource random = player.getRandom();
                    player.level().playSound(null, player, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, (random.nextFloat() - random.nextFloat()) * 1.4F + 2.0F);
                }
            }
        });
    }
}
