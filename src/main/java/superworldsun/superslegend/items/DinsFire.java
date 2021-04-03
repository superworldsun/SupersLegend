package superworldsun.superslegend.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class DinsFire extends Item {
   public DinsFire(Properties builder) {
      super(builder);
   }

   /**
    * Called when this item is used when targeting a Block
    */
   @Nonnull
   public ActionResultType onItemUse(ItemUseContext context) {
      PlayerEntity playerentity = context.getPlayer();
      World world = context.getWorld();
      BlockPos blockpos = context.getPos();
      BlockState blockstate = world.getBlockState(blockpos);
      if (CampfireBlock.canBeLit(blockstate) && Objects.requireNonNull(playerentity).getFoodStats().getFoodLevel()>= 1) {
         world.playSound(playerentity, blockpos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
         world.setBlockState(blockpos, blockstate.with(BlockStateProperties.LIT, Boolean.TRUE), 11);
         context.getItem().damageItem(1, playerentity, (p_219999_1_) -> p_219999_1_.sendBreakAnimation(context.getHand()));

         return ActionResultType.func_233537_a_(world.isRemote());
      } else {
         //noinspection SpellCheckingInspection
         BlockPos blockpos1 = blockpos.offset(context.getFace());
         if (AbstractFireBlock.canLightBlock(world, blockpos1, context.getPlacementHorizontalFacing() ) && Objects.requireNonNull(playerentity).getFoodStats().getFoodLevel()>= 1) {
            world.playSound(playerentity, blockpos1, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
            //noinspection SpellCheckingInspection
            BlockState blockstate1 = AbstractFireBlock.getFireForPlacement(world, blockpos1);
            playerentity.addExhaustion(4f);
            world.setBlockState(blockpos1, blockstate1, 11);
            ItemStack itemstack = context.getItem();
            if (playerentity instanceof ServerPlayerEntity) {
               CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos1, itemstack);
               itemstack.damageItem(1, playerentity, (p_219998_1_) -> p_219998_1_.sendBreakAnimation(context.getHand()));
            }

            return ActionResultType.func_233537_a_(world.isRemote());
         } else {
            return ActionResultType.FAIL;
         }
      }
   }

   /**
    * Checks the passed block state for a campfire block, if it is not waterlogged and not lit.
    */
   @SuppressWarnings("unused")
   public static boolean isUnlitCampfire(BlockState state) {
      return state.getBlock() == Blocks.CAMPFIRE && !state.get(BlockStateProperties.WATERLOGGED) && !state.get(BlockStateProperties.LIT);
   }

	public void addInformation(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "Through Din, you can set the world ablaze"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
	}
}