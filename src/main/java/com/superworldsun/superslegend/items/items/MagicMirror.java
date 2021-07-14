package com.superworldsun.superslegend.items.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import java.util.List;
import java.util.Random;

public class MagicMirror extends Item {

    private static int duration = 25;

    public MagicMirror(Properties properties) {
        super(properties);

       
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }

    @Override
    public void onUsingTick (ItemStack stack, LivingEntity entity,int count)
    {

        Random rand = entity.level.random;
        for (int i = 0; i < 45; i++)
        {
            entity.level.addParticle(ParticleTypes.CLOUD,
                    entity.xo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
                    entity.yo + rand.nextFloat() * 3 - 2,
                    entity.zo + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
                    0, 0.105D, 0);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity)
    {
        if (!world.isClientSide)
        {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;

            if(world.dimension().equals(World.OVERWORLD)) //if dimension is Overworld
            {
                if(player.getRespawnPosition() != null) //player bed location not null
                {
                    BlockPos bedLoc = player.getRespawnPosition(); //get player bed position
                    BlockPos currentPos = player.blockPosition();

                    if (player.isPassenger())
                    {
                        player.stopRiding();
                    }

                    setPositionAndUpdate(entity, world, bedLoc);
                    world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
                    //player.sendStatusMessage(new TranslationTextComponent("Returned to bed"), true);
                }
                else
                {
                    player.displayClientMessage(new TranslationTextComponent("No Bed to return to"), true);
                    return stack;
                }
            }
            else
            {
                player.displayClientMessage(new TranslationTextComponent("Can only use in OverWorld"), true);
            }
        }

        return stack;
    }

    @Override
    public UseAction getUseAnimation (ItemStack stack)
    {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return duration;
    }

    public void setPositionAndUpdate(LivingEntity entity, World world, BlockPos bedLoc)
    {
        entity.teleportTo(bedLoc.getX() + 0.5F, bedLoc.getY() + 0.6F, bedLoc.getZ() + 0.5F);
        entity.fallDistance = 0;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
    
    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.AQUA + "When lost, use this mirror to return home"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Hold Right-click to return to bed"));
	}

}