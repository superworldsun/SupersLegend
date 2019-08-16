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
                event.getEntityLiving().entityDropItem(new ItemStack(ItemList.rupee, random.nextInt(3)));
            if(random.nextInt(6) == 0)
                event.getEntityLiving().entityDropItem(new ItemStack(ItemList.blue_rupee,1));
            
	        }
	    }
	}