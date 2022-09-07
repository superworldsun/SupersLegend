package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.entities.projectiles.hooks.HookshotEntity;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class DinsFire extends Item {
   public DinsFire(Properties builder) {
      super(builder);
   }

   /**
    * This function is called when the player stops using the item.
    */
   @Override
   public ItemStack finishUsingItem(ItemStack itemStack, World world, LivingEntity player) {
      return super.finishUsingItem(itemStack, world, player);
   }

   @Override
   public UseAction getUseAnimation(ItemStack itemStack) {
      return UseAction.BOW;
   }

   float manaCostArea = 0.50F;

   /**
    * If the player has enough mana, and the item has been used for more than 72000 ticks, then set all nearby entities on
    * fire
    */
   @Override
   public void releaseUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int remainingUseTicks) {
      if(livingEntity instanceof PlayerEntity) {
         PlayerEntity player = (PlayerEntity) livingEntity;
         if (!world.isClientSide) {
            boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;


            AxisAlignedBB targetBox = new AxisAlignedBB(player.position(), player.position()).inflate(32);

            List<LivingEntity> foundTarget =
                    world.getEntitiesOfClass(LivingEntity.class, targetBox, AVOID_PLAYERS);

            if (!foundTarget.isEmpty() && hasMana && getUseDuration(itemStack) > 72000) {
               world.playSound(player, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
               for (LivingEntity living : foundTarget) {
                  ManaProvider.get(player).spendMana(manaCostArea);
                  living.setSecondsOnFire(100);
               }
            }
         }
      }
   }

   @Override
   public int getUseDuration(ItemStack itemStack) {
      return 72000;
   }


   // It's a filter that checks if the entity is a player, and if it is, it checks if the player is in creative mode or is
   // a spectator. If it is, it returns false, otherwise it returns true.
   private static final Predicate<Entity> AVOID_PLAYERS = (player) -> {
      return !player.isDiscrete() && EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(player);
   };

   /**
    * If the player is using the item in their hand, start using it.
    */
   @Override
   public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      player.startUsingItem(hand);
      return ActionResult.consume(itemstack);
   }

   /**
    * Called when this item is used when targeting a Block
    */

   float manaCost = 3.00F;
   public ActionResultType useOn(ItemUseContext context) {
      boolean hasMana = ManaProvider.get(context.getPlayer()).getMana() >= manaCost || context.getPlayer().abilities.instabuild;
      PlayerEntity playerentity = context.getPlayer();
      World world = context.getLevel();
      BlockPos blockpos = context.getClickedPos();
      BlockState blockstate = world.getBlockState(blockpos);
      if (CampfireBlock.canLight(blockstate) && hasMana) {
         world.playSound(playerentity, blockpos, SoundEvents.FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
         world.setBlock(blockpos, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
         ManaProvider.get(context.getPlayer()).spendMana(manaCost);

         return ActionResultType.sidedSuccess(world.isClientSide());
      } else {
         BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
         if (AbstractFireBlock.canBePlacedAt(world, blockpos1, context.getHorizontalDirection()) && hasMana) {
            world.playSound(playerentity, blockpos1, SoundEvents.FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
            BlockState blockstate1 = AbstractFireBlock.getState(world, blockpos1);
            world.setBlock(blockpos1, blockstate1, 11);
            ManaProvider.get(context.getPlayer()).spendMana(manaCost);

            return ActionResultType.sidedSuccess(world.isClientSide());
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
      return state.getBlock() == Blocks.CAMPFIRE && !state.getValue(BlockStateProperties.WATERLOGGED) && !state.getValue(BlockStateProperties.LIT);
   }

	public void appendHoverText(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "Through Din, you can set the world ablaze"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
	}
}