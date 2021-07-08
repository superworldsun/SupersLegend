package superworldsun.superslegend.items.bows;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

import net.minecraft.item.Item.Properties;

public class LynelBowX3 extends BowItem {
    protected final Random rand = new Random();
    public float velocity = 2.95f;
    public float inaccuracy = 1f;
    public int shotType;

    public LynelBowX3(int shotType, Properties builder) {
        super(builder);

        this.shotType = shotType;
    }

    public void releaseUsing(@Nonnull ItemStack stack,@Nonnull World worldIn,@Nonnull LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            ItemStack itemstack = playerentity.getProjectile(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty());
            if (i < 0) return;

                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));

                    if(shotType == 1){
                        if (!worldIn.isClientSide) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                            abstractarrowentity = customArrow(abstractarrowentity);
                            abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * velocity, inaccuracy);

                            if (f == 1.0F) {
                                abstractarrowentity.setCritArrow(true);
                            }

                            stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));

                            if (flag1 || playerentity.abilities.instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                                abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                            }

                            worldIn.addFreshEntity(abstractarrowentity);
                        }

                        if (!worldIn.isClientSide) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                            abstractarrowentity = customArrow(abstractarrowentity);
                            abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot + 7, 0.0F, f * velocity, inaccuracy);

                            if (f == 1.0F) {
                                abstractarrowentity.setCritArrow(true);
                            }

                            stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));

                            abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;

                            worldIn.addFreshEntity(abstractarrowentity);
                        }

                        if (!worldIn.isClientSide) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                            abstractarrowentity = customArrow(abstractarrowentity);
                            abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot -7, 0.0F, f * velocity, inaccuracy);
                            if (f == 1.0F) {
                                abstractarrowentity.setCritArrow(true);
                            }

                            stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));

                            abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;

                            worldIn.addFreshEntity(abstractarrowentity);
                        }
                    }

                    worldIn.playSound(null, playerentity.xo, playerentity.yo, playerentity.zo,
                            SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1 && !playerentity.abilities.instabuild) { itemstack.shrink(1);
                        if (itemstack.isEmpty()) { playerentity.inventory.removeItem(itemstack); }
                    }

                    playerentity.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    

    public void appendHoverText(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> tooltip,@Nonnull ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
            tooltip.add(new StringTextComponent(TextFormatting.BLUE + "x3 Arrows"));
    }
    

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
	}

    @ParametersAreNonnullByDefault
    public void onCraftedBy(ItemStack itemStack, World world, PlayerEntity player) {
        super.onCraftedBy(itemStack, world, player);
            shotType = 1;
    }
}
