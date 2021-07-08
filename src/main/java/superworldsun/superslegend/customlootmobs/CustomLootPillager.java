package superworldsun.superslegend.customlootmobs;

import java.util.Random;

import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.lists.ItemList;

public class CustomLootPillager {
	
	@SubscribeEvent
    public void CustomLootDrops(LivingDropsEvent event) {

        Random random = new Random();

        if(event.getEntityLiving() instanceof PillagerEntity) {
            if(random.nextInt(2) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.rupee, random.nextInt(3)));
            if(random.nextInt(4) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.blue_rupee,1));
            
            if(random.nextInt(60) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.triforce_wisdom_shard,1));
            
	        }
	    }
	}
