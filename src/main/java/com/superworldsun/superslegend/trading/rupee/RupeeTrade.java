package com.superworldsun.superslegend.trading.rupee;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable description of one wallet-backed trade.
 *
 * <p>The resource location is the trade's persistent identity. It must remain
 * stable once worlds have used the trade because per-trader stock is stored by
 * this ID.</p>
 */
public final class RupeeTrade {
    public static final int NO_NBT_COPY = -1;

    private final ResourceLocation id;
    private final Type type;
    private final int order;
    private final int unlockLevel;
    private final int rupeeCost;
    private final List<ItemStack> ingredients;
    private final ItemStack result;
    private final int maxUses;
    private final int villagerXp;
    private final int copyNbtFromIngredient;

    private RupeeTrade(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.order = builder.order;
        this.unlockLevel = builder.unlockLevel;
        this.rupeeCost = builder.rupeeCost;
        this.ingredients = immutableStackCopies(builder.ingredients);
        this.result = builder.result.copy();
        this.maxUses = builder.maxUses;
        this.villagerXp = builder.villagerXp;
        this.copyNbtFromIngredient = builder.copyNbtFromIngredient;
    }

    public static Builder builder(ResourceLocation id, ItemLike result) {
        return new Builder(id, new ItemStack(result));
    }

    public static Builder builder(ResourceLocation id, ItemStack result) {
        return new Builder(id, result);
    }

    public ResourceLocation id() {
        return id;
    }

    public Type type() {
        return type;
    }

    public boolean isSellTrade() {
        return type == Type.SELL;
    }

    public int order() {
        return order;
    }

    public int unlockLevel() {
        return unlockLevel;
    }

    public int rupeeCost() {
        return rupeeCost;
    }

    /** Returns defensive copies because ItemStack is mutable. */
    public List<ItemStack> ingredients() {
        return mutableStackCopies(ingredients);
    }

    /** Returns a defensive copy because ItemStack is mutable. */
    public ItemStack result() {
        return result.copy();
    }

    public int maxUses() {
        return maxUses;
    }

    public int villagerXp() {
        return villagerXp;
    }

    /**
     * Ingredient index whose NBT should be copied to the output, or
     * {@link #NO_NBT_COPY}. This preserves inventories, custom names,
     * enchantments, and durability for upgrade trades.
     */
    public int copyNbtFromIngredient() {
        return copyNbtFromIngredient;
    }

    public boolean isUnlockedAt(int traderLevel) {
        return traderLevel >= unlockLevel;
    }

    /**
     * Builds one output stack and applies the configured upgrade-data policy.
     * Input NBT is retained, while any keys deliberately supplied by the
     * result template remain authoritative when the same key exists in both.
     */
    public ItemStack createResult(ItemStack copiedIngredient) {
        ItemStack output = result.copy();
        if (copyNbtFromIngredient == NO_NBT_COPY || copiedIngredient == null || copiedIngredient.isEmpty()
                || !copiedIngredient.hasTag()) {
            return output;
        }

        CompoundTag mergedTag = copiedIngredient.getTag().copy();
        if (output.hasTag()) {
            mergedTag.merge(output.getTag().copy());
        }
        output.setTag(mergedTag);
        return output;
    }

    @Override
    public String toString() {
        return "RupeeTrade[" + id + "]";
    }

    private static List<ItemStack> immutableStackCopies(List<ItemStack> source) {
        return Collections.unmodifiableList(mutableStackCopies(source));
    }

    private static List<ItemStack> mutableStackCopies(List<ItemStack> source) {
        List<ItemStack> copies = new ArrayList<>(source.size());
        for (ItemStack stack : source) {
            copies.add(stack.copy());
        }
        return copies;
    }

    public static final class Builder {
        private final ResourceLocation id;
        private final List<ItemStack> ingredients = new ArrayList<>();
        private ItemStack result;
        private Type type = Type.BUY;
        private int order;
        private int unlockLevel = 1;
        private int rupeeCost;
        private int maxUses = 1;
        private int villagerXp;
        private int copyNbtFromIngredient = NO_NBT_COPY;

        private Builder(ResourceLocation id, ItemStack result) {
            this.id = Objects.requireNonNull(id, "id");
            this.result = Objects.requireNonNull(result, "result").copy();
        }

        public Builder order(int order) {
            this.order = order;
            return this;
        }

        /** Places this offer in the Buy, Sell, or Daily Deal tab. */
        public Builder type(Type type) {
            this.type = Objects.requireNonNull(type, "type");
            return this;
        }

        public Builder buy() {
            return type(Type.BUY);
        }

        public Builder sell() {
            return type(Type.SELL);
        }

        public Builder dailyDeal() {
            return type(Type.DAILY_DEAL);
        }

        /** Lower numbers appear first in the trader menu. Use gaps such as 10, 20, 30. */
        public Builder displayOrder(int displayOrder) {
            return order(displayOrder);
        }

        public Builder unlockLevel(int unlockLevel) {
            this.unlockLevel = unlockLevel;
            return this;
        }

        /** Villager tier required to display the offer: 1=Novice through 5=Master. */
        public Builder requiredVillagerLevel(int level) {
            return unlockLevel(level);
        }

        public Builder rupeeCost(int rupeeCost) {
            this.rupeeCost = rupeeCost;
            return this;
        }

        /** Rupees removed from the equipped wallet for one purchase. Zero is allowed. */
        public Builder priceInRupees(int price) {
            return rupeeCost(price);
        }

        /** Rupees added to the equipped wallet for each item sold. */
        public Builder payoutInRupees(int payout) {
            return rupeeCost(payout);
        }

        public Builder ingredient(ItemLike item, int count) {
            return ingredient(new ItemStack(Objects.requireNonNull(item, "item"), count));
        }

        /** Additional item payment removed from the player's inventory per purchase. */
        public Builder requires(ItemLike item, int count) {
            return ingredient(item, count);
        }

        public Builder ingredient(ItemStack ingredient) {
            ItemStack copy = Objects.requireNonNull(ingredient, "ingredient").copy();
            if (copy.isEmpty() || copy.getCount() <= 0) {
                throw new IllegalArgumentException("Trade ingredient must not be empty");
            }
            ingredients.add(copy);
            return this;
        }

        public Builder result(ItemStack result) {
            this.result = Objects.requireNonNull(result, "result").copy();
            return this;
        }

        public Builder maxUses(int maxUses) {
            this.maxUses = maxUses;
            return this;
        }

        /** Number of purchases this individual trader can sell before this offer is exhausted. */
        public Builder stock(int stock) {
            return maxUses(stock);
        }

        public Builder villagerXp(int villagerXp) {
            this.villagerXp = villagerXp;
            return this;
        }

        /** Villager profession XP awarded for one purchase. */
        public Builder xpReward(int xp) {
            return villagerXp(xp);
        }

        public Builder copyNbtFromIngredient(int ingredientIndex) {
            this.copyNbtFromIngredient = ingredientIndex;
            return this;
        }

        /** Convenience for the common one-input upgrade case. */
        public Builder preserveUpgradeData() {
            return copyNbtFromIngredient(0);
        }

        /** Copies the first required item's name, enchantments, durability, and stored contents to the result. */
        public Builder preserveFirstIngredientData() {
            return preserveUpgradeData();
        }

        public RupeeTrade build() {
            if (result.isEmpty() || result.getCount() <= 0) {
                throw new IllegalStateException("Trade result must not be empty: " + id);
            }
            if (unlockLevel < 1 || unlockLevel > 5) {
                throw new IllegalStateException("Unlock level must be between 1 and 5: " + id);
            }
            if (rupeeCost < 0) {
                throw new IllegalStateException("Rupee cost cannot be negative: " + id);
            }
            if (maxUses <= 0) {
                throw new IllegalStateException("Maximum uses must be positive: " + id);
            }
            if (villagerXp < 0) {
                throw new IllegalStateException("Villager XP cannot be negative: " + id);
            }
            if (copyNbtFromIngredient < NO_NBT_COPY || copyNbtFromIngredient >= ingredients.size()) {
                throw new IllegalStateException("NBT-copy ingredient index is invalid: " + id);
            }
            return new RupeeTrade(this);
        }
    }

    public enum Type {
        BUY,
        SELL,
        DAILY_DEAL
    }
}
