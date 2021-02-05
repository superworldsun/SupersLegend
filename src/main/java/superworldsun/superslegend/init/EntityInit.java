package superworldsun.superslegend.init;


import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.entities.mobs.fairy.FairyEntity;
import superworldsun.superslegend.entities.mobs.poe.PoeEntity;
import superworldsun.superslegend.entities.projectiles.items.EntityBomb;

import java.util.Random;


public class EntityInit {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, SupersLegend.modid);

	//public static final RegistryObject<EntityType<FairyEntity>> FAIRYENTITY = ENTITY_TYPES.register("fairyentity", () -> EntityType.Builder.create(FairyEntity::new, EntityClassification.CREATURE).size(.5f, .5f).setShouldReceiveVelocityUpdates(false).build("fairyentity"));
	//public static final RegistryObject<EntityType<PoeEntity>> POEENTITY = ENTITY_TYPES.register("poeentity", () -> EntityType.Builder.create(PoeEntity::new, EntityClassification.MONSTER).size(.5f, .5f).setShouldReceiveVelocityUpdates(false).build("poeentity"));

	public static final RegistryObject<EntityType<FairyEntity>> FAIRYENTITY = ENTITY_TYPES.register("fairyentity", () -> EntityType.Builder.<FairyEntity>create(FairyEntity::new, EntityClassification.CREATURE).size(0.9f, 1.3f).build(new ResourceLocation(SupersLegend.modid, "fairyentity").toString()));
	public static final RegistryObject<EntityType<PoeEntity>> POEENTITY = ENTITY_TYPES.register("poeentity", () -> EntityType.Builder.<PoeEntity>create(PoeEntity::new, EntityClassification.MONSTER).size(.5f, .5f).build(new ResourceLocation(SupersLegend.modid, "poeentity").toString()));

	//public static final RegistryObject<EntityType<EntityBomb>> BOMBENTITY = ENTITY_TYPES.register("bombentity", () -> EntityType.Builder.create(BombEntity, EntityClassification.MISC).size(.5f, .5f).setShouldReceiveVelocityUpdates(false).build("bombentity"));
	//private static Object BombEntity;
	//public static final RegistryObject<EntityType<EntityBomb>> BOMBENTITY = ENTITY_TYPES.register("bomb", () -> EntityType.Builder.create(EntityBomb::new, EntityClassification.MISC).size(0.25F, 0.25F).setShouldReceiveVelocityUpdates(false).build("bombentity"));


	public static boolean rollSpawn(int rolls, Random random, SpawnReason reason){
		if(reason == SpawnReason.SPAWNER){
			return true;
		}else{
			return rolls <= 0 || random.nextInt(rolls) == 0;
		}
	}
}