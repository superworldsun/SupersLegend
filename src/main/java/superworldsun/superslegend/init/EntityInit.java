package superworldsun.superslegend.init;

public class EntityInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			SupersLegend.modid);

	public static final RegistryObject<EntityType<FairyEntity>> FAIRYENTITY = ENTITY_TYPES.register("fairyentity", () -> EntityType.Builder.create(FairyEntity::new, EntityClassification.CREATURE)
			.size(.5f, .5f)
			.setShouldReceiveVelocityUpdates(false)
			.build("fairyentity"));


}