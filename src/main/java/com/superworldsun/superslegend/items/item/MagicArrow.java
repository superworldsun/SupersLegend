package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.magic.MagicProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public abstract class MagicArrow extends ArrowItem {
    private static final float MAGIC_COST = 4F;

    public MagicArrow() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public boolean isInfinite(@NotNull ItemStack stack, @NotNull ItemStack bow, @NotNull Player player) {
        return true;
    }

    public @NotNull AbstractArrow createArrow(@NotNull Level level, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
        boolean isPlayer = shooter instanceof Player;

        if (!isPlayer) {
            //TODO (level, shooter) is causing errors
            return new Arrow(level, shooter);
        }

        Player player = (Player) shooter;
        boolean hasMana = MagicProvider.hasMagic(player, MAGIC_COST);

        if (hasMana) {
            MagicProvider.spendMagic(player, MAGIC_COST);
            return createMagicArrow(level, stack, shooter);
        } else {
            return new Arrow(level, shooter);
        }
    }

    @SubscribeEvent
    public static void onArrowNock(ArrowNockEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = event.getEntity();
        ItemStack projectile = event.getEntity().getProjectile(event.getBow());

        if (projectile.getItem() instanceof MagicArrow) {
            boolean hasRegularArrows = player.getInventory().contains(new ItemStack(Items.ARROW));
            boolean isCreative = player.isCreative();

            if (!isCreative && !hasRegularArrows) {
                event.setAction(new InteractionResultHolder<>(InteractionResult.FAIL, event.getBow()));
            }
        }
    }

    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        ItemStack arrowsStack = event.getEntity().getProjectile(event.getBow());
        boolean isPlayerCreative = event.getEntity().isCreative();
        boolean isShootingMagicArrows = arrowsStack.getItem() instanceof MagicArrow;

        if (!isPlayerCreative && isShootingMagicArrows) {
            spendRegularArrows(event);
        }
    }

    private static void spendRegularArrows(ArrowLooseEvent event) {
        int regularArrowsSlot = event.getEntity().getInventory().findSlotMatchingUnusedItem(new ItemStack(Items.ARROW));
        ItemStack regularArrowsStack = event.getEntity().getInventory().getItem(regularArrowsSlot);
        regularArrowsStack.shrink(1);
    }

    protected abstract AbstractArrow createMagicArrow(Level level, ItemStack stack, LivingEntity shooter);
}
