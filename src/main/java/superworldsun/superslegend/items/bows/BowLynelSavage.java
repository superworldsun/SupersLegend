package superworldsun.superslegend.items.bows;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BowLynelSavage extends BowCustomBase{

    protected final Random rand = new Random();

    public float velocity = 2.95f;
    public float inaccuracy = 1f;

    public int shotType = 0;

    public BowLynelSavage(int shotType, Item.Properties builder) {
        super(builder);

        this.shotType = shotType;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            ItemStack itemstack = playerentity.findAmmo(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty());
            if (i < 0) return;

                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));

                    if(shotType == 1){
                        if (!worldIn.isRemote) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                            abstractarrowentity = customeArrow(abstractarrowentity);
                            abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * velocity, inaccuracy);
                            if (f == 1.0F) {
                                abstractarrowentity.setIsCritical(true);
                            }



                            stack.damageItem(1, playerentity, (p_220009_1_) -> {
                                p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                            });
                            if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                                abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                            }

                            worldIn.addEntity(abstractarrowentity);
                        }

                        if (!worldIn.isRemote) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                            abstractarrowentity = customeArrow(abstractarrowentity);
                            abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw + 7, 0.0F, f * velocity, inaccuracy);
                            if (f == 1.0F) {
                                abstractarrowentity.setIsCritical(true);
                            }



                            stack.damageItem(1, playerentity, (p_220009_1_) -> {
                                p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                            });

                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;


                            worldIn.addEntity(abstractarrowentity);
                        }

                        if (!worldIn.isRemote) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                            abstractarrowentity = customeArrow(abstractarrowentity);
                            abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw -7, 0.0F, f * velocity, inaccuracy);
                            if (f == 1.0F) {
                                abstractarrowentity.setIsCritical(true);
                            }



                            stack.damageItem(1, playerentity, (p_220009_1_) -> {
                                p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                            });

                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;


                            worldIn.addEntity(abstractarrowentity);
                        }
                    }else if (shotType == 2){
                        if (!worldIn.isRemote) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                            abstractarrowentity = customeArrow(abstractarrowentity);
                            abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * velocity, inaccuracy);
                            if (f == 1.0F) {
                                abstractarrowentity.setIsCritical(true);
                            }



                            stack.damageItem(1, playerentity, (p_220009_1_) -> {
                                p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                            });
                            if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                                abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                            }

                            worldIn.addEntity(abstractarrowentity);
                        }

                        if (!worldIn.isRemote) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                            abstractarrowentity = customeArrow(abstractarrowentity);
                            abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw + 4, 0.0F, f * velocity, inaccuracy);
                            if (f == 1.0F) {
                                abstractarrowentity.setIsCritical(true);
                            }



                            stack.damageItem(1, playerentity, (p_220009_1_) -> {
                                p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                            });

                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;


                            worldIn.addEntity(abstractarrowentity);
                        }

                        if (!worldIn.isRemote) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                            abstractarrowentity = customeArrow(abstractarrowentity);
                            abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw -4, 0.0F, f * velocity, inaccuracy);
                            if (f == 1.0F) {
                                abstractarrowentity.setIsCritical(true);
                            }



                            stack.damageItem(1, playerentity, (p_220009_1_) -> {
                                p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                            });

                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;


                            worldIn.addEntity(abstractarrowentity);
                        }

                        if (!worldIn.isRemote) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                            abstractarrowentity = customeArrow(abstractarrowentity);
                            abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw -8, 0.0F, f * velocity, inaccuracy);
                            if (f == 1.0F) {
                                abstractarrowentity.setIsCritical(true);
                            }



                            stack.damageItem(1, playerentity, (p_220009_1_) -> {
                                p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                            });

                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;


                            worldIn.addEntity(abstractarrowentity);
                        }

                        if (!worldIn.isRemote) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                            abstractarrowentity = customeArrow(abstractarrowentity);
                            abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw + 8, 0.0F, f * velocity, inaccuracy);
                            if (f == 1.0F) {
                                abstractarrowentity.setIsCritical(true);
                            }



                            stack.damageItem(1, playerentity, (p_220009_1_) -> {
                                p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                            });

                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;


                            worldIn.addEntity(abstractarrowentity);
                        }
                    }

                    //worldIn.playSound((PlayerEntity)null, playerentity.posX, playerentity.posY, playerentity.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !playerentity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.deleteStack(itemstack);
                        }
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        if(shotType == 1){
            tooltip.add(new StringTextComponent(TextFormatting.BLUE + "x3 Arrows"));
        }else if (shotType == 2) {
            tooltip.add(new StringTextComponent(TextFormatting.BLUE + "x5 Arrows"));
        }
    }
    
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        // TODO Auto-generated method stub
        return false;
	}

    @Override
    public void onCreated(ItemStack itemStack, World world, PlayerEntity player) {
        super.onCreated(itemStack, world, player);

        if (this.rand.nextInt(2) == 0) {
            shotType = 1;
        }else {
            shotType = 2;
        }

    }
}
