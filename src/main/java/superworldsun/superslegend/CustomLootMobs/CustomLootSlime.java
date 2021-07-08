package superworldsun.superslegend.CustomLootMobs;

import java.util.Random;

import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.lists.ItemList;

public class CustomLootSlime 
{
	@SubscribeEvent
    public void CustomLootDrops(LivingDropsEvent event) {

        Random random = new Random();

        if(event.getEntityLiving() instanceof SlimeEntity && !(event.getEntityLiving() instanceof MagmaCubeEntity)) {
            if(random.nextInt(9) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.rupee, random.nextInt(3)));
            if(random.nextInt(11) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.blue_rupee,1));
            if(random.nextInt(14) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.green_jelly,1));
            
            if(random.nextInt(65) == 0)
                event.getEntityLiving().spawnAtLocation(new ItemStack(ItemList.blue_jelly,1));
            
	        }
	    }
	}