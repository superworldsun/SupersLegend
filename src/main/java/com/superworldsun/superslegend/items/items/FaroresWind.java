package com.superworldsun.superslegend.items.items;

import java.util.List;
import java.util.Random;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.util.DimBlockPos;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class FaroresWind extends Item {

    public FaroresWind(Properties properties)
    {
        super(properties);
    }

    //Set the location in the item
    public ActionResultType useOn(ItemUseContext context)
    {
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        PlayerEntity player = context.getPlayer();
        Direction direction = context.getClickedFace();
        ItemStack stackWind = context.getPlayer().getMainHandItem();

        if(getPosition(stackWind) == null && player.isShiftKeyDown())
        {
            setPosition(stackWind, world, pos.relative(direction), player);
            player.displayClientMessage(new TranslationTextComponent(TextFormatting.GREEN + "Location set!"), true);
            BlockPos currentPos = player.blockPosition();
            world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.EVOKER_CAST_SPELL, SoundCategory.PLAYERS, 1f, 1f);
            player.getCooldowns().addCooldown(this, 60);
            return ActionResultType.SUCCESS;
        }

        if(getPosition(stackWind) != null)
        {
            player.displayClientMessage(new TranslationTextComponent(TextFormatting.GREEN + "Location already set."), true);
            BlockPos currentPos = player.blockPosition();
            world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.DISPENSER_FAIL, SoundCategory.PLAYERS, 1f, 1f);
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

    float manaCost = 5.00F;

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
    {
        boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
        ItemStack stack = player.getItemInHand(hand);

        if(getPosition(stack) != null && !player.isShiftKeyDown() && hasMana)
        {
            Random rand = player.level.random;
            for (int i = 0; i < 45; i++)
            {
                player.level.addParticle(ParticleTypes.CLOUD,
                        player.xo + (rand.nextBoolean() ? 2 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
                        player.yo + rand.nextFloat() * 3 - 2,
                        player.zo + (rand.nextBoolean() ? 2 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
                        0.3, 0.105D, 0.3);
            }

            ManaProvider.get(player).spendMana(manaCost);
            teleport(player, world, stack);
            world.playSound(null, player.xo, player.yo, player.zo, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
            player.getCooldowns().addCooldown(this, 120);
        }

        if(getPosition(stack) != null && player.isShiftKeyDown())
        {
            setPosition(stack, world, null, player);
            player.displayClientMessage(new TranslationTextComponent(TextFormatting.GREEN + "Location cleared!"), true);
        }

        return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));
    }

    // The Items Teleport Function
    public void teleport(PlayerEntity player, World world, ItemStack stack)
    {
        if(world.isClientSide)
        {
            return;
        }
        int Dim = getDimension(stack);
        BlockPos pos = getPosition(stack);

        if(world.dimension().equals(World.OVERWORLD) && Dim == 0)
        {
            player.teleportTo(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);
        }
        else if(world.dimension().equals(World.NETHER) && Dim == -1)
        {
            player.teleportTo(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);
        }
        else if(world.dimension().equals(World.END) && Dim == 1)
        {
            player.teleportTo(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);
        }
        else
        {
            player.displayClientMessage(new TranslationTextComponent(TextFormatting.DARK_GREEN + "You are not currently in the stored dimension"), true);
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

    public void appendHoverText(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
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

        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.GREEN + "Allows you to teleport to a saved location on Right-click"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Does not work across dimensions"));
        list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Magic on use"));
        list.add(new StringTextComponent(TextFormatting.WHITE + "Set: " + TextFormatting.AQUA + "Point at a block and sneak + Right-click"));
        list.add(new StringTextComponent(TextFormatting.WHITE + "Clear: " + TextFormatting.AQUA + "Point in the air and sneak + Right-click"));

        if(getPosition(stack) != null)
        {
            BlockPos pos = getPosition(stack);

            list.add(new StringTextComponent(TextFormatting.GOLD + "Location Stored:"));
            list.add(new StringTextComponent(TextFormatting.YELLOW + "Dim: " + dimName + "  X: " + pos.getX() + "  Y: " + pos.getY() + "  Z: " + pos.getZ()));
        }
    }
/*
    @SubscribeEvent
    public static void handleChunkUnload(ChunkEvent.Unload event) {
        World world = (World) event.getWorld();
        if (!world.isClientSide && (world instanceof ServerWorld)) {
            ServerWorld worldServer = (ServerWorld) world;

            Chunk chunk = (Chunk) event.getChunk();
            // Handle this directly instead of through EntityTracking for speed
            EntityData data = EntityTracking.getData();

            for (ClassInheritanceMultiMap<Entity> entityList : chunk.getEntitySections()) {
                for (Entity entity : entityList) {
                    if (data.trackedEntities.contains(entity.getUUID())) {
                        DimBlockPos dbp = new DimBlockPos(entity.blockPosition(), world.dimensionType().logicalHeight());
                        EntityTracking.storeEntity(entity, dbp);
                    }
                }
            }
        }
    }
*/
}