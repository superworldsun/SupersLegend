package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import superworldsun.superslegend.config.SupersLegendConfig;
import superworldsun.superslegend.init.SoundInit;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class OcarinaOfTime extends Item
{

	public OcarinaOfTime(Properties properties)
	{
		super(properties);
	}

	/*@Override
	protected boolean isInGroup(ItemGroup group) {
		return SupersLegendConfig.COMMON.OcarinaOfTimeEnabled.get() && super.isInGroup(group);
	}*/

	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player,@Nonnull Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getHeldItem(hand);
		  
		 if(world.isDaytime() && player instanceof ServerPlayerEntity && world instanceof ServerWorld)
	     {
			 BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.SUNS_SONG, SoundCategory.PLAYERS, 1f, 1f);
			 
			 Random rand = player.world.rand;
		        for (int i = 0; i < 45; i++)
		        {
		        	player.world.addParticle(ParticleTypes.NOTE,
		                    player.prevPosX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    player.prevPosY + rand.nextFloat() * 3 - 2,
		                    player.prevPosZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    0, 0.105D, 0);
		        }

		        // Get the command source
			 CommandSource source = player.getCommandSource();
			 //noinspection SpellCheckingInspection
			 for(ServerWorld playerworld : source.getServer().getWorlds()) {
			 	// Set the time to Night
				 playerworld.setDayTime(13000L)/*13000 is == to Night time*/;
			 }
			 player.getCooldownTracker().setCooldown(this, 5 * 22);
	     }
		 else if(!world.isDaytime() && player instanceof ServerPlayerEntity && world instanceof ServerWorld)
		 {
			 BlockPos currentPos = player.getPosition();
			 // Plays pretty sounds at the player
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.SUNS_SONG, SoundCategory.PLAYERS, 1f, 1f);
			 
			 Random rand = player.world.rand;
			 // Music note particle effects
		        for (int i = 0; i < 45; i++)
		        {
		        	player.world.addParticle(ParticleTypes.NOTE,
		                    player.prevPosX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    player.prevPosY + rand.nextFloat() * 3 - 2,
		                    player.prevPosZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    0, 0.105D, 0);
		        }

			 // Get the command source
			 CommandSource source = player.getCommandSource();
			 //noinspection SpellCheckingInspection
			 for(ServerWorld playerworld : source.getServer().getWorlds()) {
				 // Set the time to Day
				 playerworld.setDayTime(1000)/*1000 is == to Day time*/;
			 }

		        // Setting Item cool down
			 player.getCooldownTracker().setCooldown(this, 5 * 22);
		 }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand)); 
	 }

	public void addInformation(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLUE + "A Ocarina to control time"));
	}   
	//world.setDayTime(0);
	
}
