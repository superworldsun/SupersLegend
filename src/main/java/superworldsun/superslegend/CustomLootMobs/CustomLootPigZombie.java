package superworldsun.superslegend.CustomLootMobs;

import java.util.Random;

import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.lists.ItemList;

public class CustomLootPigZombie 
{
	@SubscribeEvent
    public void CustomLootDrops(LivingDropsEvent event) {

        Random random = new Random();

        if(event.getEntityLiving() instanceof ZombifiedPiglinEntity) {
            if(random.nextInt(6) == 0)
                event.getEntityLiving().entityDropItem(new ItemStack(ItemList.rupee, random.nextInt(3)));
            if(random.nextInt(13) == 0)
                event.getEntityLiving().entityDropItem(new ItemStack(ItemList.blue_rupee,1));
            
            if(random.nextInt(70) == 0)
                event.getEntityLiving().entityDropItem(new ItemStack(ItemList.triforce_power_shard,1));
            
	        }
	    }
	}