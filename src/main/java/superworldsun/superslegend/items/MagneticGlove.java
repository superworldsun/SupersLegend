package superworldsun.superslegend.items;

import java.util.List;


import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.util.EnableUtil;

public class MagneticGlove extends Item
{

	public MagneticGlove(Properties properties)
	{
		super(properties);
	}

    public static int getCooldown(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Cooldown");
    }

    public static void setCooldown(ItemStack stack, int cooldown) {
        stack.getOrCreateTag().putInt("Cooldown", cooldown);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        Vector3d playerPos = player.getPositionVec().add(0, 0.75, 0);

        int range = 15;
        List<ItemEntity> items = player.world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
        int pulled = 0;
        for (ItemEntity item : items) {
            if (item.isAlive() && !item.cannotPickup() && !item.getPersistentData().getBoolean("PreventRemoteMovement")) {
                if (pulled++ > 200) {
                    break;
                }

                Vector3d motion = playerPos.subtract(item.getPositionVec().add(0, item.getHeight() / 2, 0));
                if (Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z) > 1) {
                    motion = motion.normalize();
                }
                item.setMotion(motion.scale(0.7));
            }
        }

        return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
    }
    
    @Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLUE + "Pulls items toward the player"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Hold Right-click to Pull items"));
	}   

}