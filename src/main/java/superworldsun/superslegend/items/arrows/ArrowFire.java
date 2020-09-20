package superworldsun.superslegend.items.arrows;

//import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowFire;
import superworldsun.superslegend.lists.ItemList;

public class ArrowFire extends ArrowItem
{

    public ArrowFire(Properties builder)
    {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        EntityArrowFire entityFireArrow = new EntityArrowFire(worldIn, shooter);
        return entityFireArrow;
    }
    
    /*public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.PlayerEntity player) {
        @SuppressWarnings("unused")
		ItemStack itemStack = (new ItemStack(ItemList.heros_bow));
        return true;
     }*/
}
