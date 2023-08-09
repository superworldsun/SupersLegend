package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MagicMirror extends Item {

    private static int duration = 25;

    public MagicMirror(Properties pProperties) {
        super(pProperties);
    }


    //TODO, the item doesn't have any errors but the magic mirror isn't working.
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
    }

    public void onUsingTick (ItemStack stack, LivingEntity entity, int count)
    {
        Random rand = (Random) entity.level().random;
        for (int i = 0; i < 45; i++)
        {
            entity.level().addParticle(ParticleTypes.CLOUD,
                    entity.xo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
                    entity.yo + rand.nextFloat() * 3 - 2,
                    entity.zo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
                    0, 0.105D, 0);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity)
    {
        if (!level.isClientSide)
        {
            ServerPlayer player = (ServerPlayer) entity;

            if(level.dimension().equals(level.OVERWORLD)) //if dimension is Overworld
            {
                BlockPos returnLoc = getPosition(stack); //get saved position
                if(returnLoc != null) //saved location not null
                {
                    BlockPos currentPos = player.blockPosition();

                    if (player.isPassenger())
                    {
                        player.stopRiding();
                    }

                    setPositionAndUpdate(entity, level, returnLoc);
                    level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1f, 1f);
                    //TODO, i dont know what the new "TranslationTextComponent" is for 1.20.1, fix these 3 messages
                    //player.sendStatusMessage(new TranslationTextComponent("Returned to saved position"), true);
                }
                else
                {
                    //player.displayClientMessage(new TranslationTextComponent("No position saved"), true);
                    return stack;
                }
            }
            else
            {
                //player.displayClientMessage(new TranslationTextComponent("Can only use in OverWorld"), true);
            }
        }

        return stack;
    }

    @Override
    public UseAnim getUseAnimation (ItemStack stack)
    {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return duration;
    }

    public void setPositionAndUpdate(LivingEntity entity, Level level, BlockPos bedLoc)
    {
        entity.teleportTo(bedLoc.getX() + 0.5F, bedLoc.getY() + 0.6F, bedLoc.getZ() + 0.5F);
        entity.fallDistance = 0;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return getPosition(stack) != null;
    }

    public static BlockPos getPosition(ItemStack stack)
    {
        if (!stack.hasTag())
        {
            return null;
        }

        CompoundTag tags = stack.getTag();

        if (tags.contains("pos"))
        {
            return NbtUtils.readBlockPos((CompoundTag) tags.get("pos"));
        }
        return null;
    }

}
