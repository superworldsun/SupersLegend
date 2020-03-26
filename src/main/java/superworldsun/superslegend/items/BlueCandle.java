package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlueCandle extends Item {
   public BlueCandle(Item.Properties builder) {
      super(builder);
   }

   /**
    * Called when this item is used when targetting a Block
    */
   public ActionResultType onItemUse(ItemUseContext context) {
      PlayerEntity playerentity = context.getPlayer();
      IWorld iworld = context.getWorld();
      BlockPos blockpos = context.getPos();
      BlockPos blockpos1 = blockpos.offset(context.getFace());
      if (func_219996_a(iworld.getBlockState(blockpos1), iworld, blockpos1)) {
         iworld.playSound(playerentity, blockpos1, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
         BlockState blockstate1 = ((FireBlock)Blocks.FIRE).getStateForPlacement(iworld, blockpos1);
         iworld.setBlockState(blockpos1, blockstate1, 11);
         ItemStack itemstack = context.getItem();
         if (playerentity instanceof ServerPlayerEntity) {
            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos1, itemstack);
            itemstack.damageItem(1, playerentity, (p_219999_1_) -> {
               p_219999_1_.sendBreakAnimation(context.getHand());
            });
         }

         return ActionResultType.SUCCESS;
      } else {
         BlockState blockstate = iworld.getBlockState(blockpos);
         if (isUnlitCampfire(blockstate)) {
            iworld.playSound(playerentity, blockpos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
            iworld.setBlockState(blockpos, blockstate.with(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
            if (playerentity != null) {
               context.getItem().damageItem(1, playerentity, (p_219998_1_) -> {
                  p_219998_1_.sendBreakAnimation(context.getHand());
               });
            }

            return ActionResultType.SUCCESS;
         } else {
            return ActionResultType.FAIL;
         }
      }
   }

   /**
    * Checks the passed blockstate for a campfire block, if it is not waterlogged and not lit.
    */
   public static boolean isUnlitCampfire(BlockState state) {
      return state.getBlock() == Blocks.CAMPFIRE && !state.get(BlockStateProperties.WATERLOGGED) && !state.get(BlockStateProperties.LIT);
   }

   @SuppressWarnings("deprecation")
public static boolean func_219996_a(BlockState p_219996_0_, IWorld p_219996_1_, BlockPos p_219996_2_) {
      BlockState blockstate = ((FireBlock)Blocks.FIRE).getStateForPlacement(p_219996_1_, p_219996_2_);
      boolean flag = false;

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         if (p_219996_1_.getBlockState(p_219996_2_.offset(direction)).getBlock() == Blocks.OBSIDIAN && ((NetherPortalBlock)Blocks.NETHER_PORTAL).isPortal(p_219996_1_, p_219996_2_) != null) {
            flag = true;
         }
      }

      return p_219996_0_.isAir() && (blockstate.isValidPosition(p_219996_1_, p_219996_2_) || flag);
   }
   
   @Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "Through Din, you can set the world ablaze"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina when Held"));
	}   
} 