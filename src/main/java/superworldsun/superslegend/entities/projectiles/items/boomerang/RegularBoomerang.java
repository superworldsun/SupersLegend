package superworldsun.superslegend.entities.projectiles.items.boomerang;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import superworldsun.superslegend.config.SupersLegendConfig;
import superworldsun.superslegend.init.EntityInit;

public class RegularBoomerang extends BoomerangEntity {

    public RegularBoomerang(EntityType<BoomerangEntity> type, World world) {
        super(type, world);
        this.timeBeforeTurnAround = SupersLegendConfig.COMMON.RegularBoomerangRange.get() <= 0 ? 20 : SupersLegendConfig.COMMON.RegularBoomerangRange.get();
    }

    public RegularBoomerang(World worldIn, PlayerEntity entity, ItemStack itemstack, Hand hand) {
        super(EntityInit.REGULAR_BOOMERANG.get(), worldIn, entity, itemstack, hand);
        this.timeBeforeTurnAround = SupersLegendConfig.COMMON.RegularBoomerangRange.get() <= 0 ? 20 : SupersLegendConfig.COMMON.RegularBoomerangRange.get();
    }

    @Override
    protected int getDamage(Entity hitEntity, PlayerEntity player) {
        if (SupersLegendConfig.COMMON.RegularBoomerangDamage.get() > 0) {
            return SupersLegendConfig.COMMON.RegularBoomerangDamage.get();
        }

        return 0;
    }

    @Override
    public DamageSource causeNewDamage(BoomerangEntity entityboomerang, Entity entity) {
        return (new IndirectEntityDamageSource("regular_boomerang", entityboomerang, entity)).setProjectile();
    }

    @Override
    public void beforeTurnAround(PlayerEntity player) {
        // Following is diamond boomerang only
        // Follows where the entity is looking
        if (!isBouncing && SupersLegendConfig.COMMON.RegularBoomerangFollows.get()) {
            double x = -MathHelper.sin((player.yRot * 3.141593F) / 180F);
            double z = MathHelper.cos((player.yRot * 3.141593F) / 180F);

            double motionX = 0.5D * x * (double) MathHelper.cos((player.xRot / 180F) * 3.141593F);
            double motionY = -0.5D * (double) MathHelper.sin((player.xRot / 180F) * 3.141593F);
            double motionZ = 0.5D * z * (double) MathHelper.cos((player.xRot / 180F) * 3.141593F);
            this.setDeltaMovement(motionX, motionY, motionZ);
        }
    }
}
