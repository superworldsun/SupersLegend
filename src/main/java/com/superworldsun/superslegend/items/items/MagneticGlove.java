package com.superworldsun.superslegend.items.items;

import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        Vector3d playerPos = player.position().add(0, 0.75, 0);

        int range = 15;
        List<ItemEntity> itemEntityList = player.level.getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
        List<LivingEntity> livingEntityList = player.level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
        int pulled = 0;
        for (ItemEntity item : itemEntityList) {
            if (item.isAlive() && !item.hasPickUpDelay() && !item.getPersistentData().getBoolean("PreventRemoteMovement")) {
                if (pulled++ > 200) {
                    break;
                }

                Vector3d motion = playerPos.subtract(item.position().add(0, item.getBbHeight() / 2, 0));
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
                    Vector3d motion = playerPos.subtract(entity.position().add(0, entity.getBbHeight() / 2, 0));
                    if (Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z) > 1) {
                        motion = motion.normalize();
                    }
                    motion = motion.scale(0.15 * pullStrength);
                    entity.setDeltaMovement(motion);
                }
            }
        }

        return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLUE + "Pulls items toward the player"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Hold Right-click to Pull items"));
	}
}