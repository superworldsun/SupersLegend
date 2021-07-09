package superworldsun.superslegend.customlootmobs;

import java.util.Random;

import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.lists.ItemList;

public class CustomLootStray 
{
	@SubscribeEvent
    public void CustomLootDrops(LivingDropsEvent event) {

        Random random = new Random();

        if(event.getEntityLiving() instanceof StrayEntity) {
            if(random.nextInt(7) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.rupee, random.nextInt(3)));
            if(random.nextInt(12) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.blue_rupee,1));
            
	        }
	    }
	}