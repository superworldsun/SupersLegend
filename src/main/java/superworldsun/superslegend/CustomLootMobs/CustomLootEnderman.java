package superworldsun.superslegend.CustomLootMobs;

import java.util.Random;

import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.lists.ItemList;

public class CustomLootEnderman 
{
	@SubscribeEvent
    public void CustomLootDrops(LivingDropsEvent event) {

        Random random = new Random();

        if(event.getEntityLiving() instanceof EndermanEntity) {
            if(random.nextInt(3) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.rupee, random.nextInt(4)));
            if(random.nextInt(8) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.blue_rupee,1));
            
	        }
	    }
	}