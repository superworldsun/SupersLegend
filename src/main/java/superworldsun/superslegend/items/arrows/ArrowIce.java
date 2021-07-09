package superworldsun.superslegend.items.arrows;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowIce;

import net.minecraft.item.Item.Properties;

public class ArrowIce extends ArrowItem
{

    public ArrowIce(Properties builder)
    {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        EntityArrowIce entityArrowIce = new EntityArrowIce(worldIn, shooter);
        return entityArrowIce;
    }
}
