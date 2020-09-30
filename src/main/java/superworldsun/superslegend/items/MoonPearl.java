package superworldsun.superslegend.items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
//import net.minecraft.world.dimension.DimensionType;

public class MoonPearl extends Item {

    public MoonPearl(Properties properties) {
        super(properties);
    }

    /*@Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entity, Hand hand) {
        ItemStack stack = entity.getHeldItem(hand);

        int currentDim = entity.dimension.getId();

        if (!world.isRemote) {
            if (!(currentDim == 1)) {
                Random rand = entity.world.rand;
                for (int i = 0; i < 45; i++) {
                    entity.world.addParticle(ParticleTypes.DRAGON_BREATH,
                            entity.prevPosX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            entity.prevPosY + rand.nextFloat() * 3 - 2,
                            entity.prevPosZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            0, 0.105D, 0);
                }

                BlockPos currentPos = entity.getPosition();
                world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
                entity.changeDimension(DimensionType.THE_NETHER);
                return ActionResult.resultSuccess(stack);
            } else if (((currentDim == 1)) && (entity.isCrouching())) {
                Random rand = entity.world.rand;
                for (int i = 0; i < 45; i++) {
                    entity.world.addParticle(ParticleTypes.DRAGON_BREATH,
                            entity.prevPosX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            entity.prevPosY + rand.nextFloat() * 3 - 2,
                            entity.prevPosZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
                            0, 0.105D, 0);
                }

                BlockPos currentPos = entity.getPosition();
                world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
                entity.changeDimension(DimensionType.OVERWORLD);
                return ActionResult.resultSuccess(stack);
            }
        }

        return ActionResult.resultFail(stack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.RED + "Teleports the player to the Nether"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "When used in the Nether it will Return player to Portal"));
    }*/
}