package com.superworldsun.superslegend.items.ammobags;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public abstract class AmmoContainerItem extends Item implements ICurioItem {
    private static final String STORED_ITEMS_TAG = "storedItems";
    private static final String STORED_ITEM_TAG = "Item";
    private static final String STORED_COUNT_TAG = "StoredCount";
    private static final String SELECTED_ITEM_TAG = "SelectedItem";
    private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);

    private final int capacity;
    private final int tooltipSlots;

    public AmmoContainerItem(int capacity) {
        this(capacity, 1);
    }

    public AmmoContainerItem(int capacity, int tooltipSlots) {
        super(new Properties().stacksTo(1));
        this.capacity = capacity;
        this.tooltipSlots = tooltipSlots;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack containerStack = player.getItemInHand(hand);
        List<ItemStack> contents = getStoredItems(containerStack);
        if (contents.isEmpty()) {
            return InteractionResultHolder.fail(containerStack);
        }

        if (player instanceof ServerPlayer) {
            for (ItemStack storedStack : contents) {
                int stackSize = storedStack.getMaxStackSize();
                for (int remaining = storedStack.getCount(); remaining > 0; remaining -= stackSize) {
                    player.drop(storedStack.copyWithCount(Math.min(stackSize, remaining)), true);
                }
            }
        }

        saveStoredItems(containerStack, List.of());
        playDropContentsSound(player);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(containerStack, level.isClientSide());
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack containerStack, Slot slot, ClickAction action, Player player) {
        if (containerStack.getCount() != 1 || action != ClickAction.SECONDARY) {
            return false;
        }

        ItemStack slotStack = slot.getItem();
        if (slotStack.isEmpty()) {
            removeStack(containerStack).ifPresent(removed -> {
                playRemoveOneSound(player);
                insert(containerStack, slot.safeInsert(removed));
            });
        } else if (canHoldItem(slotStack)) {
            int insertable = getInsertableCount(containerStack, slotStack);
            int inserted = insert(containerStack, slot.safeTake(slotStack.getCount(), insertable, player));
            if (inserted > 0) {
                playInsertSound(player);
            }
        }

        return true;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack containerStack, ItemStack carriedStack, Slot slot,
                                            ClickAction action, Player player, SlotAccess carriedAccess) {
        if (containerStack.getCount() != 1 || action != ClickAction.SECONDARY || !slot.allowModification(player)) {
            return false;
        }

        if (carriedStack.isEmpty()) {
            removeStack(containerStack).ifPresent(removed -> {
                playRemoveOneSound(player);
                carriedAccess.set(removed);
            });
        } else {
            int inserted = insert(containerStack, carriedStack);
            if (inserted > 0) {
                playInsertSound(player);
                carriedStack.shrink(inserted);
            }
        }

        return true;
    }

    public int insert(ItemStack containerStack, ItemStack insertedStack) {
        int insertedCount = getInsertableCount(containerStack, insertedStack);
        if (insertedCount <= 0) {
            return 0;
        }

        List<ItemStack> contents = getStoredItems(containerStack);
        int matchingIndex = findMatchingItem(contents, insertedStack);
        if (matchingIndex >= 0) {
            contents.get(matchingIndex).grow(insertedCount);
        } else {
            contents.add(insertedStack.copyWithCount(insertedCount));
        }

        saveStoredItems(containerStack, contents);
        return insertedCount;
    }

    public int getInsertableCount(ItemStack containerStack, ItemStack insertedStack) {
        if (insertedStack.isEmpty() || !canHoldItem(insertedStack)) {
            return 0;
        }

        List<ItemStack> contents = getStoredItems(containerStack);
        if (findMatchingItem(contents, insertedStack) < 0 && contents.size() >= tooltipSlots) {
            return 0;
        }

        int capacityCost = Math.max(1, getCapacityCost(insertedStack));
        int availableCapacity = capacity - getOccupiedCapacity(contents);
        return Math.max(0, Math.min(insertedStack.getCount(), availableCapacity / capacityCost));
    }

    public Optional<ItemStack> removeStack(ItemStack containerStack) {
        List<ItemStack> contents = getStoredItems(containerStack);
        if (contents.isEmpty()) {
            return Optional.empty();
        }

        int selectedIndex = getSelectedIndex(containerStack);
        ItemStack selectedStack = contents.get(selectedIndex);
        int removedCount = Math.min(selectedStack.getCount(), selectedStack.getMaxStackSize());
        ItemStack removed = selectedStack.copyWithCount(removedCount);
        selectedStack.shrink(removedCount);
        if (selectedStack.isEmpty()) {
            contents.remove(selectedIndex);
        }

        saveStoredItems(containerStack, contents);
        return Optional.of(removed);
    }

    public boolean cycleSelectedItem(ItemStack containerStack, int direction) {
        List<ItemStack> contents = getStoredItems(containerStack);
        if (tooltipSlots <= 1 || contents.size() <= 1 || direction == 0) {
            return false;
        }

        int selectedIndex = getSelectedIndex(containerStack);
        setSelectedIndex(containerStack, Math.floorMod(selectedIndex + Integer.signum(direction), contents.size()));
        return true;
    }

    public int getSelectedIndex(ItemStack containerStack) {
        int itemCount = getStoredItems(containerStack).size();
        if (itemCount <= 1) {
            return 0;
        }

        CompoundTag tag = containerStack.getTag();
        int selectedIndex = tag == null ? 0 : tag.getInt(SELECTED_ITEM_TAG);
        return Mth.clamp(selectedIndex, 0, itemCount - 1);
    }

    private void setSelectedIndex(ItemStack containerStack, int selectedIndex) {
        containerStack.getOrCreateTag().putInt(SELECTED_ITEM_TAG, selectedIndex);
    }

    public boolean consumeOneMatching(ItemStack containerStack, Predicate<ItemStack> predicate) {
        List<ItemStack> contents = getStoredItems(containerStack);
        for (int index = 0; index < contents.size(); index++) {
            ItemStack storedStack = contents.get(index);
            if (!predicate.test(storedStack)) {
                continue;
            }

            storedStack.shrink(1);
            if (storedStack.isEmpty()) {
                contents.remove(index);
            }
            saveStoredItems(containerStack, contents);
            return true;
        }
        return false;
    }

    public List<ItemStack> getStoredItems(ItemStack containerStack) {
        List<ItemStack> contents = new ArrayList<>();
        CompoundTag containerTag = containerStack.getTag();
        if (containerTag != null && containerTag.contains(STORED_ITEMS_TAG, Tag.TAG_LIST)) {
            ListTag storedItems = containerTag.getList(STORED_ITEMS_TAG, Tag.TAG_COMPOUND);
            for (int index = 0; index < storedItems.size(); index++) {
                CompoundTag entry = storedItems.getCompound(index);
                ItemStack storedStack = ItemStack.of(entry.getCompound(STORED_ITEM_TAG));
                int storedCount = entry.getInt(STORED_COUNT_TAG);
                if (!storedStack.isEmpty() && storedCount > 0) {
                    storedStack.setCount(storedCount);
                    contents.add(storedStack);
                }
            }
            return contents;
        }

        CompoundTag legacyItemTag = containerTag != null
                && containerTag.contains("storedItem", Tag.TAG_COMPOUND)
                ? containerTag.getCompound("storedItem") : null;
        if (legacyItemTag != null) {
            ItemStack legacyStack = ItemStack.of(legacyItemTag);
            int legacyCount = containerTag.getInt("itemCount");
            if (!legacyStack.isEmpty() && legacyCount > 0) {
                legacyStack.setCount(legacyCount);
                contents.add(legacyStack);
            }
        }
        return contents;
    }

    private void saveStoredItems(ItemStack containerStack, List<ItemStack> contents) {
        CompoundTag containerTag = containerStack.getOrCreateTag();
        containerTag.remove("storedItem");
        containerTag.remove("itemCount");
        containerTag.remove("Damage");

        if (contents.isEmpty()) {
            containerTag.remove(STORED_ITEMS_TAG);
            containerTag.remove(SELECTED_ITEM_TAG);
            return;
        }

        ListTag storedItems = new ListTag();
        for (ItemStack storedStack : contents) {
            if (storedStack.isEmpty() || storedStack.getCount() <= 0) {
                continue;
            }

            CompoundTag entry = new CompoundTag();
            CompoundTag itemTag = new CompoundTag();
            storedStack.copyWithCount(1).save(itemTag);
            entry.put(STORED_ITEM_TAG, itemTag);
            entry.putInt(STORED_COUNT_TAG, storedStack.getCount());
            storedItems.add(entry);
        }

        if (storedItems.isEmpty()) {
            containerTag.remove(STORED_ITEMS_TAG);
            containerTag.remove(SELECTED_ITEM_TAG);
        } else {
            containerTag.put(STORED_ITEMS_TAG, storedItems);
            int selectedIndex = Mth.clamp(containerTag.getInt(SELECTED_ITEM_TAG), 0, storedItems.size() - 1);
            containerTag.putInt(SELECTED_ITEM_TAG, selectedIndex);
        }
    }

    private int findMatchingItem(List<ItemStack> contents, ItemStack stack) {
        for (int index = 0; index < contents.size(); index++) {
            if (ItemStack.isSameItemSameTags(contents.get(index), stack)) {
                return index;
            }
        }
        return -1;
    }

    private int getOccupiedCapacity(List<ItemStack> contents) {
        int occupiedCapacity = 0;
        for (ItemStack storedStack : contents) {
            occupiedCapacity += storedStack.getCount() * Math.max(1, getCapacityCost(storedStack));
        }
        return occupiedCapacity;
    }

    private int getTotalItemCount(List<ItemStack> contents) {
        int count = 0;
        for (ItemStack storedStack : contents) {
            count += storedStack.getCount();
        }
        return count;
    }

    public int getTotalStoredItemCount(ItemStack containerStack) {
        return getTotalItemCount(getStoredItems(containerStack));
    }

    public void setItemStack(ItemStack containerStack, ItemStack ammoStack) {
        ItemStack storedStack = ammoStack.copy();
        storedStack.setCount(Math.min(ammoStack.getCount(), getMaximumItemCount(ammoStack)));
        saveStoredItems(containerStack, storedStack.isEmpty() ? List.of() : List.of(storedStack));
    }

    public void setCount(ItemStack containerStack, int count) {
        List<ItemStack> contents = getStoredItems(containerStack);
        if (contents.isEmpty() || count <= 0) {
            if (!contents.isEmpty()) {
                contents.remove(0);
            }
            saveStoredItems(containerStack, contents);
            return;
        }

        contents.get(0).setCount(count);
        saveStoredItems(containerStack, contents);
    }

    @Nullable
    public Pair<ItemStack, Integer> getContents(ItemStack containerStack) {
        List<ItemStack> contents = getStoredItems(containerStack);
        if (contents.isEmpty()) {
            return null;
        }

        ItemStack firstStack = contents.get(0);
        return Pair.of(firstStack.copyWithCount(1), firstStack.getCount());
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCapacityCost(ItemStack itemStack) {
        return 1;
    }

    public int getMaximumItemCount(ItemStack itemStack) {
        return capacity / Math.max(1, getCapacityCost(itemStack));
    }

    public boolean containsSameItem(ItemStack containerStack, ItemStack ammoStack) {
        return findMatchingItem(getStoredItems(containerStack), ammoStack) >= 0;
    }

    public abstract boolean canHoldItem(ItemStack itemStack);

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new AmmoContainerTooltip(getStoredItems(stack), tooltipSlots, getSelectedIndex(stack)));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        List<ItemStack> contents = getStoredItems(stack);
        int displayedCapacity = contents.size() == 1 ? getMaximumItemCount(contents.get(0)) : capacity;
        tooltip.add(Component.translatable("item.minecraft.bundle.fullness", getTotalItemCount(contents), displayedCapacity)
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return !getStoredItems(stack).isEmpty();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.min(1 + 12 * getOccupiedCapacity(getStoredItems(stack)) / capacity, 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BAR_COLOR;
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        List<ItemStack> contents = getStoredItems(itemEntity.getItem());
        ItemUtils.onContainerDestroyed(itemEntity, contents.stream().flatMap(storedStack -> {
            int stackSize = storedStack.getMaxStackSize();
            int stacks = Mth.ceil((double) storedStack.getCount() / stackSize);
            return IntStream.range(0, stacks).mapToObj(index -> {
                int stackCount = Math.min(stackSize, storedStack.getCount() - index * stackSize);
                return storedStack.copyWithCount(stackCount);
            });
        }));
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F,
                0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F,
                0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F,
                0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        if (event.getEntity().getAbilities().instabuild) {
            return;
        }

        ProjectileWeaponItem shootableItem = (ProjectileWeaponItem) event.getBow().getItem();
        CuriosApi.getCuriosHelper().getEquippedCurios(event.getEntity()).ifPresent(curios -> {
            for (int index = 0; index < curios.getSlots(); index++) {
                ItemStack curioStack = curios.getStackInSlot(index);
                if (!curioStack.isEmpty() && curioStack.getItem() instanceof AmmoContainerItem containerItem
                        && containerItem.consumeOneMatching(curioStack, shootableItem.getSupportedHeldProjectiles())) {
                    return;
                }
            }
        });
    }

    @SubscribeEvent
    public static void onEntityItemPickup(EntityItemPickupEvent event) {
        ItemStack pickedStack = event.getItem().getItem();
        Player player = event.getEntity();

        CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent(curios -> {
            for (int index = 0; index < curios.getSlots() && !pickedStack.isEmpty(); index++) {
                ItemStack curioStack = curios.getStackInSlot(index);
                if (curioStack.isEmpty() || !(curioStack.getItem() instanceof AmmoContainerItem containerItem)) {
                    continue;
                }

                int inserted = containerItem.insert(curioStack, pickedStack);
                if (inserted <= 0) {
                    continue;
                }

                pickedStack.shrink(inserted);
                if (pickedStack.isEmpty()) {
                    RandomSource random = player.getRandom();
                    player.level().playSound(null, player, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F,
                            (random.nextFloat() - random.nextFloat()) * 1.4F + 2.0F);
                }
            }
        });
    }
}
