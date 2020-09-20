package superworldsun.superslegend.CustomLootMobs;

import java.util.Random;

import net.minecraft.entity.monster.ElderGuardianEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.lists.ItemList;

public class CustomLootElderguardian 
{
	@SubscribeEvent
    public void CustomLootDrops(LivingDropsEvent event) {

        Random random = new Random();

        if(event.getEntityLiving() instanceof ElderGuardianEntity) {
            if(random.nextInt(2) == 0)
                event.getEntityLiving().entityDropItem(new ItemStack(ItemList.red_rupee,3));
            
            if(random.nextInt(1) == 0)
                event.getEntityLiving().entityDropItem(new ItemStack(ItemList.triforce_courage_shard,1));
            
	        }
	    }
	}