package superworldsun.superslegend.CustomLootMobs;

import java.util.Random;

import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.lists.ItemList;

public class CustomLootWither 
{
	@SubscribeEvent
    public void CustomLootDrops(LivingDropsEvent event) {

        Random random = new Random();

        if(event.getEntityLiving() instanceof WitherEntity) {
            if(random.nextInt(1) == 0)
                event.getEntityLiving().entityDropItem(new ItemStack(ItemList.orange_rupee,1));
            
            if(random.nextInt(1) == 0)
                event.getEntityLiving().entityDropItem(new ItemStack(ItemList.triforce_power_shard,1));
            
	        }
	    }
	}