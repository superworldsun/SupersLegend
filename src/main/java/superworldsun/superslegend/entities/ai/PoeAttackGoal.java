package superworldsun.superslegend.entities.ai;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.monster.ZombieEntity;
import superworldsun.superslegend.entities.mobs.poe.PoeEntity;

public class PoeAttackGoal extends MeleeAttackGoal {

    private final PoeEntity zombie;
    private int raiseArmTicks;

    public PoeAttackGoal(PoeEntity zombieIn, double speedIn, boolean longMemoryIn) {
        super(zombieIn, speedIn, longMemoryIn);
        this.zombie = zombieIn;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        super.startExecuting();
        this.raiseArmTicks = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        super.resetTask();
        this.zombie.setAggroed(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();
        ++this.raiseArmTicks;
        if (this.raiseArmTicks >= 5 && this.func_234041_j_() < this.func_234042_k_() / 2) {
            this.zombie.setAggroed(true);
        } else {
            this.zombie.setAggroed(false);
        }

    }
}
