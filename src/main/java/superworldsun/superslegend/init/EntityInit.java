package superworldsun.superslegend.init;


import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.entities.mobs.fairy.FairyEntity;

public class EntityInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			SupersLegend.modid);

	public static final RegistryObject<EntityType<FairyEntity>> FAIRYENTITY = ENTITY_TYPES.register("fairyentity", () -> EntityType.Builder.create(FairyEntity::new, EntityClassification.CREATURE)
			.size(.5f, .5f)
			.setShouldReceiveVelocityUpdates(false)
			.build("fairyentity"));


}