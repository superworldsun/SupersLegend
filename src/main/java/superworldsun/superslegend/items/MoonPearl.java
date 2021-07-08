package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

import net.minecraft.item.Item.Properties;

public class MoonPearl extends Item {

    public MoonPearl(Properties properties) {
        super(properties.stacksTo(16));
    }

    // Huge issue with player teleporting across dimensions.
    // We can get them in the dimensions, but were failing to position the player in a safe place.




    /*public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerIn, Hand hand) {

        ItemStack stack = playerIn.getHeldItem(hand);

        if (!world.isRemote) {
            if(playerIn instanceof ServerPlayerEntity && world.getDimensionKey().equals(World.OVERWORLD)) {
                Random rand = playerIn.world.rand;
                //Particle Effects when using item
                for (int i = 0; i < 45; i++) {
                    playerIn.world.addParticle(ParticleTypes.DRAGON_BREATH,
                            playerIn.prevPosX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            playerIn.prevPosY + rand.nextFloat() * 3 - 2,
                            playerIn.prevPosZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            0, 0.105D, 0);
                }
                BlockPos currentPos = playerIn.getPosition();
                world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);


                //Dimension changing setup
                ServerPlayerEntity player = (ServerPlayerEntity) playerIn;

                //BlockPos destinationPos = player.getPosition();
                BlockPos destinationPos = new BlockPos(currentPos.getX(), currentPos.getY(), currentPos.getZ());
                ServerWorld targetWorld = world.getServer().getWorld(world.THE_NETHER);
                BlockPos heightpos = world.getHeight(Heightmap.Type.WORLD_SURFACE, destinationPos);
                targetWorld.getServer().enqueue(new TickDelayedTask(1, () -> {
                    targetWorld.getHeight(Heightmap.Type.WORLD_SURFACE, currentPos).down();
                    targetWorld.getChunk(destinationPos.getX(), destinationPos.getZ());
                    //Teleporting the player to the Nether and updating position
                    player.teleport(targetWorld, heightpos.getX() >> 4, heightpos.getY(), heightpos.getZ() >> 4, player.rotationYaw, player.prevRotationPitch);
                }));

                //playerIn.changeDimension(targetWorld, new ITeleporter() {}).setPositionAndUpdate(targetVec.getX(), targetVec.getY(), targetVec.getZ());
                return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(hand));

            } else if(playerIn instanceof ServerPlayerEntity && world.getDimensionKey().equals(World.THE_NETHER)) {
                Random rand = playerIn.world.rand;
                //Particle Effects when using item
                for (int i = 0; i < 45; i++) {
                    playerIn.world.addParticle(ParticleTypes.DRAGON_BREATH,
                            playerIn.prevPosX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            playerIn.prevPosY + rand.nextFloat() * 3 - 2,
                            playerIn.prevPosZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            0, 0.105D, 0);
                }
                BlockPos currentPos = playerIn.getPosition();
                world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);


                //Dimension changing setup
                ServerPlayerEntity player = (ServerPlayerEntity) playerIn;
                //BlockPos destinationPos = player.getPosition();
                BlockPos destinationPos = new BlockPos(0, 70, 0);

                ServerWorld targetWorld = world.getServer().getWorld(world.OVERWORLD);
                targetWorld.getServer().enqueue(new TickDelayedTask(0, () -> {
                    targetWorld.getChunk(destinationPos.getX(), destinationPos.getZ());
                    //Teleporting the player to the Nether and updating position
                    player.teleport(targetWorld, destinationPos.getX(), destinationPos.getY(), destinationPos.getZ(), player.rotationYaw, player.prevRotationPitch);
                }));

                //playerIn.changeDimension(world.getServer().getWorld(World.OVERWORLD), new ITeleporter() {});
                return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(hand));
            } else if (world.getDimensionKey().equals(World.THE_END)) {
                playerIn.sendMessage(new StringTextComponent("You stupid"), Util.DUMMY_UUID);
            }
        }

        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(hand));
    }


    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.RED + "Teleports the player to the Nether"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "When used in the Nether it will Return player to Portal"));
    }*/
}