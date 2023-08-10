package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class LensOfTruth extends Item {
    private static final List<LivingEntity> AFFECTED_ENTITIES = new ArrayList<>();
    private static final int MANA_SPENDING_FREQUENCY = 20;
    private static final float MANA_COST = 0.5F;
    public LensOfTruth(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    //TODO, turn on when magic is added back in
    /*@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (ManaHelper.hasMana(player, MANA_COST)) {
            player.level().playSound(null, player, SoundInit.LENS_OF_TRUTH_ON.get(), SoundCategory.PLAYERS, 1f, 1f);
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
        else
        {
            player.level().playSound(null, player, SoundInit.ZELDA_ERROR.get(), SoundCategory.PLAYERS, 1f, 1f);
            return InteractionResultHolder.fail(itemstack);
        }
    }*/

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity player, int timeInUse) {
        //player.level().playSound(null, player, SoundInit.LENS_OF_TRUTH_OFF.get(), SoundCategory.PLAYERS, 1f, 1f);
    }

    @Override
    public void onUseTick(Level level, LivingEntity user, ItemStack stack, int time) {
        if (!(user instanceof Player)) {
            return;
        }

        Player player = (Player) user;

        /*if (!ManaHelper.hasMana(player, MANA_COST)) {
            user.stopUsingItem();
        }

        if (time % MANA_SPENDING_FREQUENCY == 0) {
            ManaHelper.spendMana(player, MANA_COST);
        }*/
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onLivingPreRender(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
        if (event.getEntity().isInvisible()) {
            Minecraft client = Minecraft.getInstance();
            Player player = client.player;
            boolean isLocalPlayerUsingLens = player.isUsingItem() && player.getItemInHand(player.getUsedItemHand()).getItem() == ItemInit.LENS_OF_TRUTH.get();

            if (isLocalPlayerUsingLens) {
                removeEntityInvisibility(event.getEntity());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onLivingPostRender(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
        if (AFFECTED_ENTITIES.contains(event.getEntity())) {
            restoreEntityInvisibility(event.getEntity());
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void restoreEntityInvisibility(LivingEntity livingEntity) {
        AFFECTED_ENTITIES.remove(livingEntity);
        livingEntity.setInvisible(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void removeEntityInvisibility(LivingEntity livingEntity) {
        livingEntity.setInvisible(false);
        AFFECTED_ENTITIES.add(livingEntity);
    }
}
