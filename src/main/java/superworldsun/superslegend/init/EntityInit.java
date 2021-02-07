package superworldsun.superslegend.init;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.entities.mobs.fairy.FairyEntity;
import superworldsun.superslegend.entities.mobs.poe.PoeEntity;
import superworldsun.superslegend.entities.projectiles.items.bomb.BombEntity;
import superworldsun.superslegend.entities.projectiles.items.boomerang.RegularBoomerang;
import superworldsun.superslegend.entities.projectiles.items.boomerang.BoomerangEntity;

import java.util.Random;


public class EntityInit {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SupersLegend.modid);

	public static final RegistryObject<EntityType<FairyEntity>> FAIRYENTITY = ENTITIES.register("fairyentity", () -> EntityType.Builder.<FairyEntity>create(FairyEntity::new, EntityClassification.CREATURE).size(0.9f, 1.3f).build(new ResourceLocation(SupersLegend.modid, "fairyentity").toString()));
	public static final RegistryObject<EntityType<PoeEntity>> POEENTITY = ENTITIES.register("poeentity", () -> EntityType.Builder.<PoeEntity>create(PoeEntity::new, EntityClassification.MONSTER).size(.5f, .5f).build(new ResourceLocation(SupersLegend.modid, "poeentity").toString()));

	public static final RegistryObject<EntityType<BombEntity>> BOMBENTITY = ENTITIES.register("bombentity", () -> EntityType.Builder.<BombEntity>create(BombEntity::new, EntityClassification.MISC).size(.5f, .5f).trackingRange(4).func_233608_b_(5).build("bombentity"));

	public static final RegistryObject<EntityType<BoomerangEntity>> REGULAR_BOOMERANG =  ENTITIES.register("regular_boomerang", () -> EntityType.Builder.<BoomerangEntity>create(RegularBoomerang::new, EntityClassification.MISC).size(0.5f, 0.5f).setTrackingRange(32).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build("regular_boomerang"));


	public static boolean rollSpawn(int rolls, Random random, SpawnReason reason){
		if(reason == SpawnReason.SPAWNER){
			return true;
		}else{
			return rolls <= 0 || random.nextInt(rolls) == 0;
		}
	}
}