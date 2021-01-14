package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

import java.util.List;
import java.util.Random;

public class MoonPearl extends Item {

    public MoonPearl(Properties properties) {
        super(properties.maxStackSize(16));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerIn, Hand hand) {

        ItemStack stack = playerIn.getHeldItem(hand);

        if (!world.isRemote) {
            if(world.getDimensionKey().equals(World.OVERWORLD)) {
                Random rand = playerIn.world.rand;
                for (int i = 0; i < 45; i++) {
                    playerIn.world.addParticle(ParticleTypes.DRAGON_BREATH,
                            playerIn.prevPosX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            playerIn.prevPosY + rand.nextFloat() * 3 - 2,
                            playerIn.prevPosZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            0, 0.105D, 0);
                }

                BlockPos currentPos = playerIn.getPosition();
                world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
                playerIn.changeDimension(world.getServer().getWorld(World.THE_NETHER), new ITeleporter() {});
                return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(hand));

            } else if(world.getDimensionKey().equals(World.THE_NETHER)) {
                Random rand = playerIn.world.rand;
                for (int i = 0; i < 45; i++) {
                    playerIn.world.addParticle(ParticleTypes.DRAGON_BREATH,
                            playerIn.prevPosX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            playerIn.prevPosY + rand.nextFloat() * 3 - 2,
                            playerIn.prevPosZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            0, 0.105D, 0);
                }

                BlockPos currentPos = playerIn.getPosition();
                world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
                playerIn.changeDimension(world.getServer().getWorld(World.OVERWORLD), new ITeleporter() {});
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
    }


}