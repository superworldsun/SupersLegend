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

public class FairyOcarina extends Item
{

	public FairyOcarina(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		 @SuppressWarnings("unused")
		ItemStack stack = player.getHeldItem(hand);

	     {
			 BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.BLOCK_NOTE_BLOCK_FLUTE, SoundCategory.PLAYERS, 1f, 1f);
			 
			 Random rand = player.world.rand;
		        for (int i = 0; i < 45; i++)
		        {
		        	player.world.addParticle(ParticleTypes.NOTE,
		                    player.posX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    player.posY + rand.nextFloat() * 3 - 2,
		                    player.posZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 2,
		                    0, 0.105D, 0);
		        }
		      player.getCooldownTracker().setCooldown(this, 8);
		 }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand)); 
	 }
			
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GOLD + "A standard Ocarina"));
	}   
	//world.setDayTime(0);
	
}
