package superworldsun.superslegend.items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class FaroresWind extends Item
{

    public FaroresWind(Properties properties)
    {
        super(properties);
    }

    //Set the location in the item
    public ActionResultType onItemUse(ItemUseContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        PlayerEntity player = context.getPlayer();
        Direction direction = context.getFace();
        ItemStack stackWind = context.getPlayer().getHeldItemMainhand();

        if(getPosition(stackWind) == null && player.isSneaking())
        {
            setPosition(stackWind, world, pos.offset(direction), player);
            player.sendStatusMessage(new TranslationTextComponent(TextFormatting.GREEN + "Location set!"), true);
            BlockPos currentPos = player.getPosition();
            world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.PLAYERS, 1f, 1f);
            player.getCooldownTracker().setCooldown(this, 60);
            return ActionResultType.SUCCESS;
        }

        if(getPosition(stackWind) != null)
        {
            player.sendStatusMessage(new TranslationTextComponent(TextFormatting.GREEN + "Location already set."), true);
            BlockPos currentPos = player.getPosition();
            world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.PLAYERS, 1f, 1f);
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }


    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(getPosition(stack) != null && !player.isSneaking() && player.getFoodStats().getFoodLevel()>= 4)
        {
            Random rand = player.world.rand;
            for (int i = 0; i < 45; i++)
            {
                player.world.addParticle(ParticleTypes.CLOUD,
                        player.prevPosX + (rand.nextBoolean() ? 2 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
                        player.prevPosY + rand.nextFloat() * 3 - 2,
                        player.prevPosZ + (rand.nextBoolean() ? 2 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
                        0.3, 0.105D, 0.3);
            }

            player.addExhaustion(18);
            teleport(player, world, stack);
            world.playSound(null, player.prevPosX, player.prevPosY, player.prevPosZ, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
            player.getCooldownTracker().setCooldown(this, 120);
        }

        if(getPosition(stack) != null && player.isSneaking())
        {
            setPosition(stack, world, null, player);
            player.sendStatusMessage(new TranslationTextComponent(TextFormatting.GREEN + "Location cleared!"), true);
        }

        return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
    }

    // The Items Teleport Function
    public void teleport(PlayerEntity player, World world, ItemStack stack)
    {
        if(world.isRemote)
        {
            return;
        }
        int Dim = getDimension(stack);
        BlockPos pos = getPosition(stack);

        if(world.getDimensionKey().equals(World.OVERWORLD) && Dim == 0)
        {
            player.setPositionAndUpdate(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);
        }
        else if(world.getDimensionKey().equals(World.THE_NETHER) && Dim == -1)
        {
            player.setPositionAndUpdate(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);
        }
        else if(world.getDimensionKey().equals(World.THE_END) && Dim == 1)
        {
            player.setPositionAndUpdate(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);
        }
        else
        {
            player.sendStatusMessage(new TranslationTextComponent(TextFormatting.DARK_GREEN + "You are not currently in the stored dimension"), true);
        }
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
        if(world.isRemote)
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
            if(world.getDimensionKey().equals(World.OVERWORLD)) tags.putInt("Dim", 0); //OVERWORLD
            if(world.getDimensionKey().equals(World.THE_NETHER)) tags.putInt("Dim", -1); //NETHER
            if(world.getDimensionKey().equals(World.THE_END)) tags.putInt("Dim", 1); //END
        }
        stack.setTag(tags);
    }

    public static int getDimension(ItemStack stack)
    {
        if (!stack.hasTag())
        {
            return Integer.MAX_VALUE;
        }

        CompoundNBT tags = stack.getTag();

        if (tags.contains("Dim"))
        {
            return tags.getInt("Dim");
        }
        return Integer.MAX_VALUE;
    }

    public void addInformation(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
    {// Switch Dimension integer values for easily read names
        String dimName;
        switch(getDimension(stack))
        {
            case 1:
                dimName = "End";
                break;
            case 0:
                dimName = "OverWorld";
                break;
            case -1:
                dimName = "Nether";
                break;
            default:
                dimName = "Unknown";
                break;
        }

        super.addInformation(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.GREEN + "Allows you to teleport to a saved location on Right-click"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Does not work across dimensions"));
        list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
        list.add(new StringTextComponent(TextFormatting.WHITE + "Set: " + TextFormatting.AQUA + "Point at a block and sneak + Right-click"));
        list.add(new StringTextComponent(TextFormatting.WHITE + "Clear: " + TextFormatting.AQUA + "Point in the air and sneak + Right-click"));

        if(getPosition(stack) != null)
        {
            BlockPos pos = getPosition(stack);

            list.add(new StringTextComponent(TextFormatting.GOLD + "Location Stored:"));
            list.add(new StringTextComponent(TextFormatting.YELLOW + "Dim: " + dimName + "  X: " + pos.getX() + "  Y: " + pos.getY() + "  Z: " + pos.getZ()));
        }
    }
}