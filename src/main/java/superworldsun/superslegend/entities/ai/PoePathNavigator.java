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

    public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn) {
        poe.getMoveHelper().setMoveTo(entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ(), speedIn);
        return true;
    }

    public boolean tryMoveToXYZ(double x, double y, double z, double speedIn) {
        poe.getMoveHelper().setMoveTo(x, y, z, speedIn);
        return true;
    }
}
