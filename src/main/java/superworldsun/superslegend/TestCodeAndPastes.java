package superworldsun.superslegend;

//import net.minecraft.arrows.Entity;
//import net.minecraft.arrows.passive.PigEntity;
//import net.minecraft.arrows.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
import net.minecraft.item.Item.Properties;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.util.SoundEvents;

public class TestCodeAndPastes 
{

	public TestCodeAndPastes(Properties properties)
	{
		super();
	}

	
	/*public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity arrows) {
        
        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1f, 1f);
        if(!player.world.isRemote)
            if(arrows.isAlive())
                if(arrows instanceof PigEntity)
                    if(arrows.isBurning())
                        arrows.entityDropItem(Items.COOKED_PORKCHOP, 1);
                    else
                        arrows.entityDropItem(Items.PORKCHOP, 1);
        return false;
        
    }*/
	
	/*@Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity arrows)
     {
     EntityLivingBase living = (EntityLivingBase) arrows;
     if(!(living instanceof EntityLiving)) {
             living.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 120, 1, true, true));
     }

     return true;
     }*/
	
	
	//world.playSound(player, player.getPosition(), ModSounds.HIDDEN_BLADE_KILL, SoundCategory.MASTER, 70, 1);
    //arrows.setDead();
	
	
	//arrows.changeDimension(DimensionType.THE_NETHER);
	
	//player.posY += 10.0D;
	
	
}
