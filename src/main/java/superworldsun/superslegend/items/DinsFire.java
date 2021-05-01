package superworldsun.superslegend.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.DepthStriderEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.entities.projectiles.beam.EntityFireBeam;
import superworldsun.superslegend.entities.projectiles.items.boomerang.BoomerangEntity;
import superworldsun.superslegend.entities.projectiles.items.boomerang.RegularBoomerang;
import superworldsun.superslegend.entities.projectiles.items.dinsfire.EntityDinsFire;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class DinsFire extends Item {
   public DinsFire(Properties builder) {
      super(builder);
   }



   @Override
   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
      ItemStack stack = playerIn.getHeldItem(handIn);
      //acts as a cooldown.
      if (playerIn.getFoodStats().getFoodLevel()!= 0)
      {
         if (playerIn.isSwingInProgress) {
            return new ActionResult<>(ActionResultType.PASS, stack);
         }
         playerIn.addExhaustion(1f);
         playerIn.swingArm(handIn);
         worldIn.playSound(null, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
         if (!playerIn.world.isRemote) {



         }
      }
      return super.onItemRightClick(worldIn, playerIn, handIn);
   }

	public void addInformation(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "Through Din, you can set the world ablaze"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
	}
}