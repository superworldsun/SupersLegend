package superworldsun.superslegend.init;


import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.entities.mobs.fairy.FairyEntity;

import java.util.Random;


public class EntityInit {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, SupersLegend.modid);

	public static final RegistryObject<EntityType<FairyEntity>> FAIRYENTITY = ENTITY_TYPES.register("fairyentity", () -> EntityType.Builder.create(FairyEntity::new, EntityClassification.CREATURE).size(.5f, .5f).setShouldReceiveVelocityUpdates(false).build("fairyentity"));



	public static boolean rollSpawn(int rolls, Random random, SpawnReason reason){
		if(reason == SpawnReason.SPAWNER){
			return true;
		}else{
			return rolls <= 0 || random.nextInt(rolls) == 0;
		}
	}
}