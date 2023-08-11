package com.superworldsun.superslegend.items.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MagneticGlove extends Item
{
    public MagneticGlove(Properties properties)
    {
        super(properties);
    }

    //TODO When the user is wearing metal armor, the user pulls themselves. Make it so the user does not.

    //TODO Add some sound for when item is in use

    //TODO Model for offhand is inconsistent with mainhand?

    public static int getCooldown(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Cooldown");
    }

    public static void setCooldown(ItemStack stack, int cooldown) {
        stack.getOrCreateTag().putInt("Cooldown", cooldown);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        Vec3 playerPos = player.position().add(0, 0.75, 0);

        int range = 15;
        List<ItemEntity> itemEntityList = player.level().getEntitiesOfClass(ItemEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
        List<LivingEntity> livingEntityList = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
        int pulled = 0;
        for (ItemEntity item : itemEntityList) {
            if (item.isAlive() && !item.hasPickUpDelay() && !item.getPersistentData().getBoolean("PreventRemoteMovement")) {
                if (pulled++ > 200) {
                    break;
                }

                Vec3 motion = playerPos.subtract(item.position().add(0, item.getBbHeight() / 2, 0));
                if (Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z) > 1) {
                    motion = motion.normalize();
                }
                item.setDeltaMovement(motion.scale(0.7));
            }
        }

        for (LivingEntity entity : livingEntityList) {
            if (entity.isAlive()) {
                int pullStrength = 0;
                for (ItemStack armor : entity.getArmorSlots()) {
                    if (armor.getItem() == Items.IRON_HELMET) {
                        pullStrength += 1;
                    } else if (armor.getItem() == Items.IRON_CHESTPLATE) {
                        pullStrength += 2;
                    } else if (armor.getItem() == Items.IRON_LEGGINGS) {
                        pullStrength += 1;
                    } else if (armor.getItem() == Items.IRON_BOOTS) {
                        pullStrength += 1;
                    }
                    else if (armor.getItem() == Items.GOLDEN_HELMET) {
                        pullStrength += 1;
                    } else if (armor.getItem() == Items.GOLDEN_CHESTPLATE) {
                        pullStrength += 2;
                    } else if (armor.getItem() == Items.GOLDEN_LEGGINGS) {
                        pullStrength += 1;
                    } else if (armor.getItem() == Items.GOLDEN_BOOTS) {
                        pullStrength += 1;
                    }
                    else if (armor.getItem() == Items.CHAINMAIL_HELMET) {
                        pullStrength += 1;
                    } else if (armor.getItem() == Items.CHAINMAIL_CHESTPLATE) {
                        pullStrength += 2;
                    } else if (armor.getItem() == Items.CHAINMAIL_LEGGINGS) {
                        pullStrength += 1;
                    } else if (armor.getItem() == Items.CHAINMAIL_BOOTS) {
                        pullStrength += 1;
                    }
                    else if (armor.getItem() == Items.NETHERITE_HELMET) {
                        pullStrength += 1;
                    } else if (armor.getItem() == Items.NETHERITE_CHESTPLATE) {
                        pullStrength += 2;
                    } else if (armor.getItem() == Items.NETHERITE_LEGGINGS) {
                        pullStrength += 1;
                    } else if (armor.getItem() == Items.NETHERITE_BOOTS) {
                        pullStrength += 1;
                    }
                }

                if (pullStrength > 0) {
                    Vec3 motion = playerPos.subtract(entity.position().add(0, entity.getBbHeight() / 2, 0));
                    if (Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z) > 1) {
                        motion = motion.normalize();
                    }
                    motion = motion.scale(0.15 * pullStrength);
                    entity.setDeltaMovement(motion);
                }
            }
        }

        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
    }
}