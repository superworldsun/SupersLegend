package superworldsun.superslegend.items.arrows;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowAncient;

import net.minecraft.item.Item.Properties;

public class ArrowAncient extends ArrowItem
{

    public ArrowAncient(Properties builder)
    {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        EntityArrowAncient entityArrowAncient = new EntityArrowAncient(worldIn, shooter);
        return entityArrowAncient;
    }
}
