package superworldsun.superslegend.CustomLootMobs;

import java.util.Random;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.lists.ItemList;

public class CustomLootDragon 
{
	@SubscribeEvent
    public void CustomLootDrops(LivingDropsEvent event) {

        Random random = new Random();

        if(event.getEntityLiving() instanceof EnderDragonEntity) {
            if(random.nextInt(1) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.silver_rupee,1));
            
            if(random.nextInt(1) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.triforce_wisdom_shard,1));
            
	        }
	    }
	}