package superworldsun.superslegend.entities.ai;

//import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import superworldsun.superslegend.entities.mobs.poe.PoeEntity;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.util.BlockViewUtil;

import java.util.EnumSet;


/*public class PoeChargeAttack extends Goal {
    private PoeEntity poe;
    public boolean firstPhase = true;
    public Vector3d moveToPos = null;
    public Vector3d offsetOf = Vector3d.ZERO;

    public PoeChargeAttack(PoeEntity poe) {
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        this.poe = poe;
    }

    /*public boolean shouldExecute() {
        return poe.getAttackTarget() != null && !poe.isCharging();
    }

    public boolean shouldContinueExecuting() {
        return poe.getAttackTarget() != null && poe.getAttackTarget().isAlive();
    }

    public void startExecuting() {
        poe.setCharging(true);
    }

    public void resetTask() {
        firstPhase = true;
        this.moveToPos = null;
        poe.setCharging(false);
    }

    public void tick() {
        LivingEntity target = poe.getAttackTarget();
        if(target != null && this.poe.getAttackTarget().isAlive() && this.poe.isCharging()){
            if(this.poe.getAnimation() == IAnimatedEntity.NO_ANIMATION && this.poe.getDistance(target) < 1.4D) {
                this.poe.setAnimation(PoeEntity.ANIMATION_HIT);
                this.poe.playSound(SoundInit.POE_LAUGH, 0.7F, 1);
                this.poe.attackEntityAsMob(this.poe.getAttackTarget());
            }
            if(firstPhase){
                if(this.moveToPos == null){
                    BlockPos moveToPos = BlockViewUtil.GetBlockInPoesView(poe, target);
                    this.moveToPos = Vector3d.copyCentered(moveToPos);
                }else{
                    this.poe.getNavigator().tryMoveToXYZ(this.moveToPos.x + 0.5D, this.moveToPos.y + 0.5D, this.moveToPos.z + 0.5D, 1F);
                    if(this.poe.getDistanceSq(this.moveToPos.add(0.5D, 0.5D, 0.5D)) < 9D){
                        this.firstPhase = false;
                        this.moveToPos = null;
                        offsetOf = target.getPositionVec().subtract(this.poe.getPositionVec()).normalize();
                    }
                }
            }else{
                Vector3d fin = target.getPositionVec();
                this.moveToPos = new Vector3d(fin.x, target.getPosY() + target.getEyeHeight()/2, fin.z);
                this.poe.getNavigator().tryMoveToEntityLiving(target, 1.2F);
                if(this.poe.getDistanceSq(this.moveToPos.add(0.5D, 0.5D, 0.5D)) < 3D) {
                    this.resetTask();
                }
            }
        }
    }*/
