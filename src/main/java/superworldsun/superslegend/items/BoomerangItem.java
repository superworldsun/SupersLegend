package superworldsun.superslegend.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import superworldsun.superslegend.config.SupersLegendConfig;
import superworldsun.superslegend.entities.projectiles.items.boomerang.RegularBoomerang;
import superworldsun.superslegend.entities.projectiles.items.boomerang.BoomerangEntity;

public class BoomerangItem extends Item {

    public BoomerangItem(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isInGroup(ItemGroup group) {
        return SupersLegendConfig.COMMON.boomerangsEnabled.get() && super.isInGroup(group);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.getHeldItem(handIn).getDamage() == 0) {
            BoomerangEntity boom = new RegularBoomerang(worldIn, playerIn, playerIn.getHeldItem(handIn), handIn);

            worldIn.addEntity(boom);
            playerIn.setHeldItem(handIn, ItemStack.EMPTY);
        }
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

}