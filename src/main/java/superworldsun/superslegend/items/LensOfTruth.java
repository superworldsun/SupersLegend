package superworldsun.superslegend.items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import superworldsun.superslegend.init.SoundInit;

public class LensOfTruth extends Item
{

	public LensOfTruth(Properties properties)
	{
		super(properties);
	}

	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(entity instanceof PlayerEntity && !world.isRemote)
		{
			PlayerEntity player = (PlayerEntity)entity;

			ItemStack equipped = player.getHeldItemMainhand();

			if(!world.isRemote)
			{
				if(stack == equipped)
				{
					double x = player.getPosX();
					double y = player.getPosY();
					double z = player.getPosZ();

					//How Far away
					double d0 = 30.0D;
					//How Far Up/Down
					double d1 = 20.0D;

					Entity mobEntity = scanForHostileMobs(world, x, y, z, d0, d1);

					if(entity != null && entity.isInvisible())
					{
						if (!world.isRemote)
						{
							((PlayerEntity) entity).addPotionEffect(new EffectInstance(Effect.get(24), 2, 0, false, false));
						}
					}
				}
			}
		}
		if(entity instanceof PlayerEntity && !world.isRemote)
		{
			PlayerEntity player = (PlayerEntity)entity;

			ItemStack equipped = player.getHeldItemMainhand();

			if(!world.isRemote)
			{
				if(stack == equipped)
				{
					double x = player.getPosX();
					double y = player.getPosY();
					double z = player.getPosZ();

					//How Far away
					double d0 = 30.0D;
					//How Far Up/Down
					double d1 = 20.0D;

					Entity mobEntity = scanForHostileMobs(world, x, y, z, d0, d1);

					if(entity != null && entity.isInvisible())
					{
						if (!world.isRemote)
						{
							((PlayerEntity) entity).addPotionEffect(new EffectInstance(Effect.get(24), 2, 0, false, false));
						}
					}
				}
			}
		}
		if(entity instanceof PlayerEntity && !world.isRemote)
		{
			PlayerEntity player = (PlayerEntity)entity;

			ItemStack equipped = player.getHeldItemMainhand();

			if(!world.isRemote)
			{
				if(stack == equipped)
				{
					double x = player.getPosX();
					double y = player.getPosY();
					double z = player.getPosZ();

					//How Far away
					double d0 = 30.0D;
					//How Far Up/Down
					double d1 = 20.0D;

					MobEntity hostileMob = scanForHostileMobs(world, x, y, z, d0, d1);


					if(hostileMob != null && hostileMob.isInvisible())
					{
						if (!world.isRemote)
						{
							hostileMob.addPotionEffect(new EffectInstance(Effect.get(24), 2, 0, false, false));
						}
					}
				}
			}
		}
		if(entity instanceof PlayerEntity && !world.isRemote)
		{
			PlayerEntity player = (PlayerEntity)entity;

			ItemStack equipped = player.getHeldItemOffhand();

			if(!world.isRemote)
			{
				if(stack == equipped)
				{
					double x = player.getPosX();
					double y = player.getPosY();
					double z = player.getPosZ();

					//How Far away
					double d0 = 30.0D;
					//How Far Up/Down
					double d1 = 20.0D;

					MobEntity hostileMob = scanForHostileMobs(world, x, y, z, d0, d1);

					if(hostileMob != null && hostileMob.isInvisible())
					{
						if (!world.isRemote)
						{
							hostileMob.addPotionEffect(new EffectInstance(Effect.get(24), 2, 0, false, false));
						}
					}
				}
			}
		}
	}

	private MobEntity scanForHostileMobs(World world, double xpos, double ypos, double zpos, double d0, double d1)
	{
		List<MobEntity> list = world.<MobEntity>getEntitiesWithinAABB(MobEntity.class, new AxisAlignedBB
				((double) xpos - d0,
						(double) ypos - d1,
						(double) zpos - d0,
						(double) xpos + d0, ypos + d1,
						(double) zpos + d0));

		MobEntity closestMob = null;

		for (MobEntity entitymob : list)
		{
			//Mobs being Picked
			if (entitymob instanceof Entity)
			{
				closestMob = entitymob;
				return closestMob;
			}
		}
		return null;
	}

	
	/*@Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        
            if(entity.isAlive())
                if(entity instanceof Entity)
                    if(entity.isInvisible() && player.getFoodStats().getFoodLevel()>= 2)
                    {
                    	((LivingEntity) entity).addPotionEffect(new EffectInstance(Effect.get(24), 400, 10, false, false));
                    	//entity.setInvisible(false);
                        //entity.setGlowing(true);
                    	
                    	Random rand = entity.world.rand;
            	        for (int i = 0; i < 45; i++)
            	        {
            	        	entity.world.addParticle(ParticleTypes.CLOUD,
            	        			entity.prevPosX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 1,
            	        			entity.prevPosY + rand.nextFloat() * 3 - 2,
            	        			entity.prevPosZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 1,
            	                    0, 0.105D, 0);
            	        }
            	        
            	        player.getCooldownTracker().setCooldown(this, 15);
            	        
            	        player.addExhaustion(2f);
            	        
            	        BlockPos currentPos = player.getPosition();
            	        entity.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.LENS_OF_TRUTH_ON, SoundCategory.PLAYERS, 1f, 1f);
            	        
                    }
                    else if(entity.isInvisible())
                    {
                    	player.sendStatusMessage(new TranslationTextComponent(TextFormatting.DARK_PURPLE + "They are already visible"), true);
                    	
                    	BlockPos currentPos = player.getPosition();
            	        entity.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ZELDA_ERROR, SoundCategory.PLAYERS, 1f, 1f);
                    }
                    else if (!entity.isInvisible())
                    {
                    	player.sendStatusMessage(new TranslationTextComponent(TextFormatting.DARK_PURPLE + "They are already visible"), true);
                    	
                    	BlockPos currentPos = player.getPosition();
            	        entity.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ZELDA_ERROR, SoundCategory.PLAYERS, 1f, 1f);
                    }
                    else if (player.getFoodStats().getFoodLevel()<= 1)
                    {
                    	BlockPos currentPos = player.getPosition();
            	        entity.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ZELDA_ERROR, SoundCategory.PLAYERS, 1f, 1f);
                    }
                    	
        return false;
        
    }*/


	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Hold in Hands the hidden to reveal"));
		//list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
	}

}
