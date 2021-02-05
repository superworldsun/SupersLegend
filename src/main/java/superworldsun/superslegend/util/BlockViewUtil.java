package superworldsun.superslegend.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import superworldsun.superslegend.entities.mobs.poe.PoeEntity;

public class BlockViewUtil {

    public static BlockPos GetBlockInPoesView(PoeEntity poe, LivingEntity target) {
        float radius = 4 + poe.getRNG().nextInt(5);
        float neg = poe.getRNG().nextBoolean() ? 1 : -1;
        float angle = (0.01745329251F * (target.rotationYawHead + 90F + poe.getRNG().nextInt(180)));
        double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
        double extraZ = radius * MathHelper.cos(angle);
        BlockPos radialPos = new BlockPos(target.getPosX() + extraX, target.getPosY(), target.getPosZ() + extraZ);
        BlockPos ground = radialPos;
        if( poe.getDistanceSq(Vector3d.copyCentered(ground)) > 30) {
            return ground;
        }
        return poe.getPosition();
    }
}
