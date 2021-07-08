package superworldsun.superslegend.CustomLootMobs;

import java.util.Random;

import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.lists.ItemList;

public class CustomLootMagmacube 
{
	@SubscribeEvent
    public void CustomLootDrops(LivingDropsEvent event) {

        Random random = new Random();

        if(event.getEntityLiving() instanceof MagmaCubeEntity) {
            if(random.nextInt(8) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.rupee, random.nextInt(3)));
            if(random.nextInt(10) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.blue_rupee,1));
            
            if(random.nextInt(90) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.triforce_power_shard,1));
            if(random.nextInt(20) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.red_jelly,1));
            if(random.nextInt(60) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.blue_jelly,1));
	        }
	    }
	}