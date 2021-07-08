package superworldsun.superslegend.items.arrows;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowAncient;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowSilver;

import net.minecraft.item.Item.Properties;

public class ArrowSilver extends ArrowItem
{

    public ArrowSilver(Properties builder)
    {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        EntityArrowSilver entityArrowSilver = new EntityArrowSilver(worldIn, shooter);
        return entityArrowSilver;
    }
}
