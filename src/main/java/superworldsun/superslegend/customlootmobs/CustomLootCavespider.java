package superworldsun.superslegend.customlootmobs;

import java.util.Random;

import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.lists.ItemList;

public class CustomLootCavespider 
{
	@SubscribeEvent
    public void CustomLootDrops(LivingDropsEvent event) {

        Random random = new Random();

        if(event.getEntityLiving() instanceof CaveSpiderEntity) {
            if(random.nextInt(6) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.rupee, random.nextInt(3)));
            if(random.nextInt(14) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.blue_rupee,1));
            
            if(random.nextInt(50) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.triforce_courage_shard,1));
            
	        }
	    }
	}