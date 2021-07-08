package superworldsun.superslegend.items.arrows;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowFire;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowShock;

import net.minecraft.item.Item.Properties;

public class ArrowShock extends ArrowItem
{

    public ArrowShock(Properties builder)
    {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        EntityArrowShock entityArrowShock = new EntityArrowShock(worldIn, shooter);
        return entityArrowShock;
    }
}
