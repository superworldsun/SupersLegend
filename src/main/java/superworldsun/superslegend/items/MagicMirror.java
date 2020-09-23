package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }

    @Override
    public void onUsingTick (ItemStack stack, LivingEntity entity,int count)
    {

        /*Random rand = entity.world.rand;
        for (int i = 0; i < 45; i++)
        {
            entity.world.addParticle(ParticleTypes.CLOUD,
                    entity.posX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
                    entity.posY + rand.nextFloat() * 3 - 2,
                    entity.posZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 2,
                    0, 0.105D, 0);
        }*/
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity)
    {
        if (!world.isRemote)
        {
            PlayerEntity player = (PlayerEntity) entity;
            BlockPos bedPos = player.getBedLocation(player.dimension);
            BlockPos backPos = bedPos;
            @SuppressWarnings("unused")
			BlockPos currentPos = player.getPosition();

            if(!world.dimension.isSurfaceWorld())
            {
            	world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
                player.sendStatusMessage(new TranslationTextComponent("Cannot be used right now"), true);
                
                return stack;
            }
            if (bedPos == null)
            {
            	world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
                player.sendStatusMessage(new TranslationTextComponent("You have no Bed to return to"), true);
                
                return stack;
            }

            if (entity.getRidingEntity() != null)
            {
                entity.stopRiding();
            }
            entity.setPositionAndUpdate(
                    backPos.getX() + 0.5f,
                    backPos.getY() + 0.6f,
                    backPos.getZ() + 0.5f);
            entity.fallDistance = 0;

            world.playSound(null, backPos.getX(), backPos.getY(), backPos.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
        }
        return stack;
    }

    @Override
    public UseAction getUseAction (ItemStack stack)
    {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return duration;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
    
    @Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.AQUA + "When lost, use this mirror to return home"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Hold Right-click to return to bed"));
	}   

}