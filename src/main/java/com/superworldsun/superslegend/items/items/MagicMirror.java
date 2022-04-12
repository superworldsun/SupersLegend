package com.superworldsun.superslegend.items.items;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

import com.superworldsun.superslegend.SupersLegendMain;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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
                BlockPos returnLoc = getPosition(stack); //get saved position
                if(returnLoc != null) //saved location not null
                {
                    BlockPos currentPos = player.blockPosition();

                    if (player.isPassenger())
                    {
                        player.stopRiding();
                    }

                    setPositionAndUpdate(entity, world, returnLoc);
                    world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
                    //player.sendStatusMessage(new TranslationTextComponent("Returned to saved position"), true);
                }
                else
                {
                    player.displayClientMessage(new TranslationTextComponent("No position saved"), true);
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
        return getPosition(stack) != null;
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.AQUA + "Use the Magic Mirror to warp back to any entrance"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Hold Right-click to return"));
    }

    public static BlockPos getPosition(ItemStack stack)
    {
        if (!stack.hasTag())
        {
            return null;
        }

        CompoundNBT tags = stack.getTag();

        if (tags.contains("pos"))
        {
            return NBTUtil.readBlockPos((CompoundNBT) tags.get("pos"));
        }
        return null;
    }

    public static void setPosition(ItemStack stack, World world, BlockPos pos, PlayerEntity player)
    {
        if(world.isClientSide)
        {
            return;
        }

        CompoundNBT tags;

        if (!stack.hasTag())
        {
            tags = new CompoundNBT();
        }
        else
        {
            tags = stack.getTag();
        }

        if (pos == null)
        {
            tags.remove("pos");
            tags.remove("Dim");
        }
        else
        {
            tags.put("pos", NBTUtil.writeBlockPos(pos));
            if(world.dimension().equals(World.OVERWORLD)) tags.putInt("Dim", 0); //OVERWORLD
            if(world.dimension().equals(World.NETHER)) tags.putInt("Dim", -1); //NETHER
            if(world.dimension().equals(World.END)) tags.putInt("Dim", 1); //END
        }
        stack.setTag(tags);
    }

    private static boolean posSafe(BlockPos pos, World world) {
        boolean posSafe = !(world.getBlockState(pos.below()).getBlock() instanceof AirBlock);
        posSafe = posSafe || !(world.getBlockState(pos.below(2)).getBlock() instanceof AirBlock);
        return posSafe && world.getBlockState(pos.above()).getBlock().isPossibleToRespawnInThis();
    }

    @SubscribeEvent
    public static void onTick(PlayerTickEvent event) {
        World world = event.player.level;
        BlockPos pos = event.player.blockPosition();
        if(world.getBrightness(LightType.SKY, pos) >+ 11 && pos.getY() >= 60 && posSafe(pos, world)) {
            for(ItemStack itemStack : event.player.inventory.items) {
                if(itemStack.getItem() instanceof MagicMirror && getPosition(itemStack) != null) {
                    MagicMirror.setPosition(itemStack, world, null, null);
                }
            }
        }
        else
        {
            if(!posSafe(pos, world)) {
                if(posSafe(pos.east(), world)) {
                    pos = pos.east();
                }
                else if(posSafe(pos.west(), world)) {
                    pos = pos.west();
                }
                else if(posSafe(pos.south(), world)) {
                    pos = pos.south();
                }
                else if(posSafe(pos.north(), world)) {
                    pos = pos.north();
                }
                else if(!world.getBlockState(pos.above()).getBlock().isPossibleToRespawnInThis()) {
                    while(!posSafe(pos, world)) {
                        pos = pos.above();
                    }
                }
                else
                {
                    while(!posSafe(pos, world) && pos.getY() > 0)
                    {
                        pos = pos.below();
                    }
                    if(!posSafe(pos, world)) {
                        pos = null;
                    }
                }
            }
            for(ItemStack itemStack : event.player.inventory.items) {
                if(itemStack.getItem() instanceof MagicMirror && getPosition(itemStack) == null) {
                    MagicMirror.setPosition(itemStack, world, pos, null);
                }
            }
        }
    }

}