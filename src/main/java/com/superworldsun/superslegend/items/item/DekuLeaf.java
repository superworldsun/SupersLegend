package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.magic.MagicProvider;
import com.superworldsun.superslegend.util.PlayerAnimationUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class DekuLeaf extends Item
{
    private static final float MANA_COST = 0.02F;

    public DekuLeaf(Properties pProperties) {
        super(pProperties);
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;

        if (event.phase == TickEvent.Phase.END && player != null && player.input != null)
        {
            if (player.isUsingItem() && player.getUseItem().getItem() instanceof DekuLeaf)
            {
                if (player.input.up && !player.onGround())
                {
                    double speed = 0.2;
                    player.move(MoverType.SELF, player.getLookAngle().multiply(speed, 0, speed));
                }
            }
        }
    }

    /** Keeps the shared raised-arms pose synchronized for exactly as long as the leaf is gliding. */
    @SubscribeEvent
    public static void updateFlyingPose(TickEvent.PlayerTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide)
        {
            return;
        }

        Player player = event.player;
        boolean flyingWithLeaf = player.isUsingItem()
                && player.getUseItem().getItem() instanceof DekuLeaf
                && !player.isFallFlying()
                && !player.onGround()
                && !player.isInWater()
                && MagicProvider.hasMagic(player, MANA_COST);
        PlayerAnimationUtil.setArmsRaised(player, PlayerAnimationUtil.ArmsRaisedSource.DEKU_LEAF,
                flyingWithLeaf);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand)
    {
        ItemStack heldItem = player.getItemInHand(hand);
        boolean hasMana = MagicProvider.hasMagic(player, MANA_COST);

        if (player.onGround())
        {
            if (!world.isClientSide && hasMana)
            {
                float gustSpeed = 0.5F;
                Vec3 playerLookVec = player.getLookAngle();
                Vec3 gustPosition = player.getEyePosition().add(playerLookVec);
                Vec3 gustMotion = playerLookVec.scale(gustSpeed);
//                GustEntity gustEntity = new GustEntity(world, player);
//                gustEntity.setPos(gustPosition.x, gustPosition.y, gustPosition.z);
//                gustEntity.setDeltaMovement(gustMotion);
//                world.addFreshEntity(gustEntity);
                player.getCooldowns().addCooldown(this, 16);
                player.playSound(SoundEvents.BAT_TAKEOFF, 1.0F, 1.0F);
            }

            return InteractionResultHolder.pass(heldItem);
        }
        else
        {
            if (hasMana)
            {
                player.startUsingItem(hand);
                return InteractionResultHolder.pass(heldItem);
            }
        }

        return InteractionResultHolder.fail(heldItem);
    }

    @Override
    public void onUseTick(Level world, LivingEntity livingEntity, ItemStack stack, int count)
    {
        if (!(livingEntity instanceof Player))
        {
            return;
        }

        Player player = (Player) livingEntity;
        boolean hasMana = MagicProvider.hasMagic(player, MANA_COST);

        if (!player.isFallFlying() && !player.onGround() && !player.isInWater() && hasMana)
        {
            MagicProvider.spendMagic(player, MANA_COST);
            player.fallDistance = 0F;

            if (player.getDeltaMovement().y < -0.1)
            {
                Vec3 movement = player.getDeltaMovement();
                player.setDeltaMovement(new Vec3(movement.x, -0.05, movement.z));
            }

            int particlesDensity = 5;

            for (int i = 0; i < particlesDensity; i++)
            {
                double particleX = player.getX() + (player.getRandom().nextBoolean() ? -1 : 1) * Math.pow(player.getRandom().nextFloat(), 1) * 1;
                double particleY = player.getY() + player.getRandom().nextFloat() * 1 - 2;
                double particleZ = player.getZ() + (player.getRandom().nextBoolean() ? -1 : 1) * Math.pow(player.getRandom().nextFloat(), 1) * 1;
                player.level().addParticle(ParticleTypes.CLOUD, particleX, particleY, particleZ, 0, 0.105D, 0);
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack itemStack)
    {
        return 72000;
    }
}
