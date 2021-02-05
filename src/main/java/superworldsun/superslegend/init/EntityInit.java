package superworldsun.superslegend.init;


import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.entities.mobs.fairy.FairyEntity;
import superworldsun.superslegend.entities.mobs.poe.PoeEntity;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = SupersLegend.modid, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityInit {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, SupersLegend.modid);



	public static final RegistryObject<EntityType<FairyEntity>> FAIRYENTITY = ENTITY_TYPES.register("fairyentity", () -> EntityType.Builder.create(FairyEntity::new, EntityClassification.CREATURE).size(.5f, .5f).setShouldReceiveVelocityUpdates(false).build("fairyentity"));
	public static final RegistryObject<EntityType<PoeEntity>> POEENTITY = ENTITY_TYPES.register("poeentity", () -> EntityType.Builder.create(PoeEntity::new, EntityClassification.CREATURE).size(0.8f, 1.9f).immuneToFire().build("poeentity"));






	public static boolean rollSpawn(int rolls, Random random, SpawnReason reason){
		if(reason == SpawnReason.SPAWNER){
			return true;
		}else{
			return rolls <= 0 || random.nextInt(rolls) == 0;
		}
	}

}