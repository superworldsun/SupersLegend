package superworldsun.superslegend.entities.ai;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.world.World;
import superworldsun.superslegend.entities.mobs.poe.PoeEntity;

public class PoePathNavigator extends FlyingPathNavigator {

    public PoeEntity poe;

    public PoePathNavigator(PoeEntity entityIn, World worldIn) {
        super(entityIn, worldIn);
        poe = entityIn;

    }

    public boolean moveTo(Entity entityIn, double speedIn) {
        poe.getMoveControl().setWantedPosition(entityIn.getX(), entityIn.getY(), entityIn.getZ(), speedIn);
        return true;
    }

    public boolean moveTo(double x, double y, double z, double speedIn) {
        poe.getMoveControl().setWantedPosition(x, y, z, speedIn);
        return true;
    }
}
