package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.container.CookingPotContainer;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;
import com.superworldsun.superslegend.util.SupersLegendKeyboardUtil;
import com.superworldsun.superslegend.util.cookingpot.CookingPotCookingRecipe;
import com.superworldsun.superslegend.util.cookingpot.CookingPotCookingRecipeInput;
import com.superworldsun.superslegend.util.cookingpot.FoodValues;
import com.superworldsun.superslegend.util.cookingpot.FoodValuesDefinition;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CookingPotTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    //Code credited to Si_hen, this code is a modified version of their crockpot mod
    private final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (slot < 5) {
                if (!isValidIngredient(stack)) {
                   return stack;
                }

            }
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            hasChanged = true;
        }
    };

    private final RangedWrapper itemHandlerInput = new RangedWrapper(itemHandler, 0, 5);
    private final RangedWrapper itemHandlerOutput = new RangedWrapper(itemHandler, 5, 6);

    private int cookingTime;
    private int cookingTotalTime;
    private ItemStack result = ItemStack.EMPTY;

    private boolean hasChanged;

    public CookingPotTileEntity() {
        super(TileEntityInit.COOKING_POT.get());
    }

    public static TileEntityType<CookingPotTileEntity> createType()
    {
        return TileEntityType.Builder.of(CookingPotTileEntity::new, BlockInit.COOKING_POT.get()).build(null);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.superslegend.cooking_pot");
    }

    @Nullable
    @Override
    public Container createMenu(int containerId, PlayerInventory inventory, PlayerEntity player) {
        return new CookingPotContainer(containerId, inventory, this);
    }

    @Override
    public void tick() {
         if(SupersLegendKeyboardUtil.isHoldingSpace() && !level.isClientSide)
         {
             if (!this.isCooking() && itemHandlerOutput.getStackInSlot(0).isEmpty()) {
                 CookingPotCookingRecipeInput recipeInput = this.getRecipeInput();
                 if (recipeInput != null) {
                     CookingPotCookingRecipe recipe = CookingPotCookingRecipe.getRecipeFor(recipeInput, level.random, level.getRecipeManager());
                     if (recipe != null) {
                         cookingTotalTime = this.getActualCookingTotalTime(recipe);
                         result = recipe.assemble();
                         this.shrinkInputs();
                         hasChanged = true;
                     }
                 }
             }
         }
        if (!level.isClientSide) {
            if (!this.isCooking() && itemHandlerOutput.getStackInSlot(0).isEmpty()) {
                CookingPotCookingRecipeInput recipeInput = this.getRecipeInput();
                if (recipeInput != null) {
                    CookingPotCookingRecipe recipe = CookingPotCookingRecipe.getRecipeFor(recipeInput, level.random, level.getRecipeManager());
                    if (recipe != null) {
                        cookingTotalTime = this.getActualCookingTotalTime(recipe);
                        result = recipe.assemble();
                        this.shrinkInputs();
                        hasChanged = true;
                    }
                }
            }
            if (this.isCooking()) {
                hasChanged = true;
            }
            if (this.isCooking() && itemHandlerOutput.getStackInSlot(0).isEmpty()) {
                cookingTime++;
                if (cookingTime == cookingTotalTime) {
                    cookingTime = 0;
                    itemHandlerOutput.setStackInSlot(0, result);
                    result = ItemStack.EMPTY;
                }
                hasChanged = true;
            }
        }
        if (hasChanged) {
            this.markUpdated();
            hasChanged = false;
        }
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    @Nullable
    public CookingPotCookingRecipeInput getRecipeInput() {
        int size = itemHandlerInput.getSlots();
        List<ItemStack> stacks = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ItemStack stackInSlot = itemHandlerInput.getStackInSlot(i);
            if (stackInSlot.isEmpty()) {
                return null;
            }
            ItemStack stack = stackInSlot.copy();
            stack.setCount(1);
            stacks.add(stack);
        }
        FoodValues mergedIngredientValues = FoodValues.merge(stacks.stream()
                .map(stack -> FoodValuesDefinition.getFoodValues(stack.getItem(), level.getRecipeManager())).collect(Collectors.toList()));
        return new CookingPotCookingRecipeInput(mergedIngredientValues, stacks);
    }

    public boolean isValidIngredient(ItemStack stack) {
        return !FoodValuesDefinition.getFoodValues(stack.getItem(), level.getRecipeManager()).isEmpty();
    }

    public boolean isCooking() {
        return result != null && !result.isEmpty();
    }

    public float getCookingProgress() {
        return cookingTotalTime != 0 ? (float) cookingTime / (float) cookingTotalTime : 0.0F;
    }

    public ItemStack getResult() {
        return result;
    }

    private void shrinkInputs() {
        for (int i = 0; i < itemHandlerInput.getSlots(); i++) {
            itemHandlerInput.getStackInSlot(i).shrink(1);
        }
    }

    private int getActualCookingTotalTime(CookingPotCookingRecipe recipe) {
        return Math.max((int) (recipe.getCookingTime() * (1.0 - 0.15)), 1);
    }

    private void sendTileEntityUpdatePacket() {
        if (!level.isClientSide) {
            SUpdateTileEntityPacket pkt = this.getUpdatePacket();
            if (pkt != null) {
                ((ServerWorld) level).getChunkSource().chunkMap.getPlayers(new ChunkPos(worldPosition), false).forEach(p -> p.connection.send(pkt));
            }
        }
    }

    private void markUpdated() {
        this.setChanged();
        this.sendTileEntityUpdatePacket();
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        itemHandler.deserializeNBT(tag.getCompound("ItemHandler"));
        cookingTime = tag.getInt("CookingTime");
        cookingTotalTime = tag.getInt("CookingTotalTime");
        result.deserializeNBT(tag.getCompound("Result"));
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        super.save(tag);
        tag.put("ItemHandler", itemHandler.serializeNBT());
        tag.putInt("CookingTime", cookingTime);
        tag.putInt("CookingTotalTime", cookingTotalTime);
        tag.put("Result", result.serializeNBT());
        return tag;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition, -1, this.serializeNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.deserializeNBT(pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.serializeNBT();
    }

    private final LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IItemHandler> itemHandlerInputCap = LazyOptional.of(() -> itemHandlerInput);
    private final LazyOptional<IItemHandler> itemHandlerOutputCap = LazyOptional.of(() -> itemHandlerOutput);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) {
                return itemHandlerCap.cast();
            }
            switch (side) {
                case UP:
                    return itemHandlerInputCap.cast();
                case DOWN:
                    return itemHandlerOutputCap.cast();
                default:
                    return itemHandlerOutputCap.cast();
            }
        }
        return super.getCapability(cap, side);
    }
}