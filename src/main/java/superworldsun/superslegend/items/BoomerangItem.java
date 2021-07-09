package superworldsun.superslegend.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import superworldsun.superslegend.config.SupersLegendConfig;
import superworldsun.superslegend.entities.projectiles.items.boomerang.RegularBoomerang;
import superworldsun.superslegend.entities.projectiles.items.boomerang.BoomerangEntity;
import superworldsun.superslegend.init.SoundInit;

import net.minecraft.item.Item.Properties;

public class BoomerangItem extends Item {

    public BoomerangItem(Properties properties) {
        super(properties);
    }

    /*@Override
    protected boolean isInGroup(ItemGroup group) {
        return SupersLegendConfig.COMMON.boomerangsEnabled.get() && super.isInGroup(group);
    }*/

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.getItemInHand(handIn).getDamageValue() == 0) {
            BoomerangEntity boom = new RegularBoomerang(worldIn, playerIn, playerIn.getItemInHand(handIn), handIn);

            BlockPos currentPos = playerIn.blockPosition();
            worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOMERANG_THROW, SoundCategory.PLAYERS, 0.6f, 1.0f);

            worldIn.addFreshEntity(boom);
            playerIn.setItemInHand(handIn, ItemStack.EMPTY);
        }
        return ActionResult.success(playerIn.getItemInHand(handIn));
    }

}