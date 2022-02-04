package com.superworldsun.superslegend;

import com.mojang.serialization.Codec;
import com.superworldsun.superslegend.config.SupersLegendConfig;
import com.superworldsun.superslegend.cooldowns.Cooldowns;
import com.superworldsun.superslegend.cooldowns.CooldownsStorage;
import com.superworldsun.superslegend.cooldowns.ICooldowns;
import com.superworldsun.superslegend.entities.projectiles.arrows.PoisonArrowEntity;
import com.superworldsun.superslegend.hookshotCap.Hook;
import com.superworldsun.superslegend.hookshotCap.SyncToClient;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookStorage;
import com.superworldsun.superslegend.interfaces.IHasNoItem;
import com.superworldsun.superslegend.items.SacredShieldItem;
import com.superworldsun.superslegend.items.capabilities.SacredShieldState;
import com.superworldsun.superslegend.items.capabilities.SacredShieldStorage;
import com.superworldsun.superslegend.items.weapons.SlingShot;
import com.superworldsun.superslegend.mana.IMana;
import com.superworldsun.superslegend.mana.Mana;
import com.superworldsun.superslegend.mana.ManaStorage;
import com.superworldsun.superslegend.registries.*;
import com.superworldsun.superslegend.songs.ILearnedSongs;
import com.superworldsun.superslegend.songs.LearnedSongs;
import com.superworldsun.superslegend.songs.LearnedSongsStorage;
import com.superworldsun.superslegend.util.ClientHandler;
import com.superworldsun.superslegend.util.events.EntityEventHandler;
import com.superworldsun.superslegend.util.events.ModEventHandler;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.minecraft.item.ItemModelsProperties.register;

@Mod(SupersLegendMain.MOD_ID)
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SupersLegendMain
{
	// Our instance, referenced in the below sub-class
	public static SupersLegendMain instance;
	// The strings for our name and modid + logger
	public static SimpleChannel NETWORK;
	public static final String NAME = "SupersLegend";
	public static final String MOD_ID = "superslegend";
	public static final Logger LOGGER = LogManager.getLogger();
	
	// This sub-class below is the start where we'll add registry and stuff
	// later on
	public SupersLegendMain()
	{
		instance = this;

		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		// Our listener for setup, it will pick up on anything put into setup
		// and notify Forge of it
		SupersLegendStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::doClientStuff);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SupersLegendConfig.SPEC, "superslegend.toml");

		// Remember to register items before blocks, problems can occur
		// otherwise if you don't
		ItemInit.ITEMS.register(modEventBus);
		BlockInit.BLOCKS.register(modEventBus);
		SoundInit.SOUNDS.register(modEventBus);
		BiomeInit.BIOMES.register(modEventBus);
		BiomeInit.registerBiomes();
		PaintingInit.PAINTING_TYPES.register(modEventBus);
		EntityTypeInit.ENTITIES.register(modEventBus);
		TileEntityInit.TILES.register(modEventBus);
		ContainerInit.CONTAINERS.register(modEventBus);
		FluidInit.FLUIDS.register(modEventBus);
		LootInit.REGISTRY.register(modEventBus);
		OcarinaSongInit.REGISTRY.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		MinecraftForge.EVENT_BUS.register(new Hook());
		MinecraftForge.EVENT_BUS.register(new SacredShieldItem(new Properties()));
		MinecraftForge.EVENT_BUS.register(new SlingShot(new Properties()));

		//The structure gen listeners must use MinecraftForge.EVENT_BUS and
		// not MLJavaModLoadingContext.get().getModEventBus(), otherwise, you will cause MC to crash
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);

		// The comments for BiomeLoadingEvent and StructureSpawnListGatherEvent says to do HIGH for additions.
		forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
	}
	
	public static ResourceLocation locate(String name)
	{
		return new ResourceLocation(SupersLegendMain.MOD_ID, name);
	}
	
	@SubscribeEvent
	public static void createBlockItems(final RegistryEvent.Register<Item> event)
	{
		final IForgeRegistry<Item> registry = event.getRegistry();
		
		BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block ->
		{
			if(block instanceof IHasNoItem)
			{
				return;
			}
			
			final Item.Properties properties = new Item.Properties().tab(SupersLegendMain.RESOURCES);
			final BlockItem blockItem = new BlockItem(block, properties);
			blockItem.setRegistryName(block.getRegistryName());
			registry.register(blockItem);
		});
	}
	
	/* The FMLCommonSetupEvent (FML - Forge Mod Loader) */
	private void setup(final FMLCommonSetupEvent event)
	{
		CapabilityManager.INSTANCE.register(IMana.class, new ManaStorage(), Mana::new);
		CapabilityManager.INSTANCE.register(ICooldowns.class, new CooldownsStorage(), Cooldowns::new);
		CapabilityManager.INSTANCE.register(ILearnedSongs.class, new LearnedSongsStorage(), LearnedSongs::new);
		CapabilityManager.INSTANCE.register(HookModel.class, new HookStorage(), () -> { throw new UnsupportedOperationException("No Implementation!"); });
		CapabilityManager.INSTANCE.register(SacredShieldState.class, new SacredShieldStorage(), () -> { throw new UnsupportedOperationException("No Implementation!"); });

		NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation("superslegend", "main_channel"), () -> "1.0", s -> true, s -> true);
		NETWORK.registerMessage(1, SyncToClient.class, SyncToClient::encode, SyncToClient::new, SyncToClient::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

		// This is for thread-safe operations later on such as world-gen
		event.enqueueWork(() ->
		{
			SupersLegendStructures.setupStructures();
			SupersLegendConfiguredStructures.registerConfiguredStructures();
		});
		
		DispenserBlock.registerBehavior(ItemInit.POISON_ARROW.get(), new ProjectileDispenseBehavior()
		{
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn)
			{
				PoisonArrowEntity poisonarrowentity = new PoisonArrowEntity(worldIn, position.x(), position.y(), position.z());
				poisonarrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				return poisonarrowentity;
			}
		});
	}

	// STRUCTURE GEN CODE STARTS HERE!
	// STRUCTURE GEN CODE STARTS HERE!

	//Code credited to TelepathicGrunt
	/**
	 * This is the event you will use to add anything to any biome.
	 * This includes spawns, changing the biome's looks, messing with its surfacebuilders,
	 * adding carvers, spawning new features... etc
	 *
	 * Here, we will use this to add our structure to all biomes.
	 */
	public void biomeModification(final BiomeLoadingEvent event) {
		/*
		 * Add our structure to all biomes including other modded biomes.
		 * You can skip or add only to certain biomes based on stuff like biome category,
		 * temperature, scale, precipitation, mod id, etc. All kinds of options!
		 *
		 * You can even use the BiomeDictionary as well! To use BiomeDictionary, do
		 * RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName()) to get the biome's
		 * registrykey. Then that can be fed into the dictionary to get the biome's types.
		 */
		event.getGeneration().getStructures().add(() -> SupersLegendConfiguredStructures.CONFIGURED_FAIRY_FOUNTAIN);
		if (event.getName().equals(new ResourceLocation("minecraft", "dark_forest"))||
				event.getName().equals(new ResourceLocation("minecraft", "dark_forest_hills"))) {
			event.getGeneration().getStructures().add(() -> SupersLegendConfiguredStructures.CONFIGURED_GRAVEYARD);
		}
	}

	/**
	 * Will go into the world's chunkgenerator and manually add our structure spacing.
	 * If the spacing is not added, the structure doesn't spawn.
	 *
	 * Use this for dimension blacklists for your structure.
	 * (Don't forget to attempt to remove your structure too from the map if you are blacklisting that dimension!)
	 * (It might have your structure in it already.)
	 *
	 * Basically use this to make absolutely sure the chunkgenerator can or cannot spawn your structure.
	 */
	private static Method GETCODEC_METHOD;
	public void addDimensionalSpacing(final WorldEvent.Load event) {
		if(event.getWorld() instanceof ServerWorld){
			ServerWorld serverWorld = (ServerWorld)event.getWorld();

			/*
			 * Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
			 * They will handle your structure spacing for your if you add to WorldGenRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
			 * This here is done with reflection as this tutorial is not about setting up and using Mixins.
			 * If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.
			 */
			try {
				if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
				ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
				if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
			}
			catch(Exception e){
				SupersLegendMain.LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
			}

			/*
			 * Prevent spawning our structure in Vanilla's superflat world as
			 * people seem to want their superflat worlds free of modded structures.
			 * Also that vanilla superflat is really tricky and buggy to work with in my experience.
			 */
			if(serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
					serverWorld.dimension().equals(World.OVERWORLD)){
				return;
			}

			/*
			 * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
			 * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
			 *
			 * NOTE: if you add per-dimension spacing configs, you can't use putIfAbsent as WorldGenRegistries.NOISE_GENERATOR_SETTINGS in FMLCommonSetupEvent
			 * already added your default structure spacing to some dimensions. You would need to override the spacing with .put(...)
			 * And if you want to do dimension blacklisting, you need to remove the spacing entry entirely from the map below to prevent generation safely.
			 */
			Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
			tempMap.putIfAbsent(SupersLegendStructures.FAIRY_FOUNTAIN.get(),
					DimensionStructuresSettings.DEFAULTS.get(SupersLegendStructures.FAIRY_FOUNTAIN.get()));
			tempMap.putIfAbsent(SupersLegendStructures.GRAVEYARD.get(),
					DimensionStructuresSettings.DEFAULTS.get(SupersLegendStructures.GRAVEYARD.get()));
			serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
		}
	}
	// STRUCTURE GEN CODE ENDS HERE!
	// STRUCTURE GEN CODE ENDS HERE!
	
	public static final ItemGroup RESOURCES = new SupersLegendItemGroup();
	
	// We need this code for custom bow models with pull animations
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void setModelProperties(FMLClientSetupEvent event)
		{
			register(ItemInit.LENS_OF_TRUTH.get(), new ResourceLocation("using"),
					(stack, world, entity) -> entity != null && entity.getUseItem() == stack ? 1.0F : 0.0F);

			register(ItemInit.CLAWSHOT.get(), new ResourceLocation("pulling"),
					(stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
			
			register(ItemInit.METAL_BOW.get(), new ResourceLocation("pull"), (stack, world, entity) ->
			{
				if (entity == null)
				{
					return 0.0F;
				}
				else
				{
					return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
				}
			});
			register(ItemInit.METAL_BOW.get(), new ResourceLocation("pulling"),
					(stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
			
			register(ItemInit.HEROS_BOW.get(), new ResourceLocation("pull"), (stack, world, entity) ->
			{
				if (entity == null)
				{
					return 0.0F;
				}
				else
				{
					return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
				}
			});
			register(ItemInit.HEROS_BOW.get(), new ResourceLocation("pulling"),
					(stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);

			register(ItemInit.FAIRY_BOW.get(), new ResourceLocation("pull"), (stack, world, entity) ->
			{
				if (entity == null)
				{
					return 0.0F;
				}
				else
				{
					return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
				}
			});
			register(ItemInit.FAIRY_BOW.get(), new ResourceLocation("pulling"),
					(stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);


			register(ItemInit.LYNEL_BOW_X3.get(), new ResourceLocation("pull"), (stack, world, entity) ->
			{
				if (entity == null)
				{
					return 0.0F;
				}
				else
				{
					return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
				}
			});
			register(ItemInit.LYNEL_BOW_X3.get(), new ResourceLocation("pulling"),
					(stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
			
			register(ItemInit.LYNEL_BOW_X5.get(), new ResourceLocation("pull"), (stack, world, entity) ->
			{
				if (entity == null)
				{
					return 0.0F;
				}
				else
				{
					return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
				}
			});
			register(ItemInit.LYNEL_BOW_X5.get(), new ResourceLocation("pulling"),
					(stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
			
			register(ItemInit.DEKU_SHIELD.get(), new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_,
					p_239421_2_) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F);
			
			register(ItemInit.HYLIAN_SHIELD.get(), new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_,
					p_239421_2_) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F);

			register(ItemInit.SACRED_SHIELD.get(), new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_,
					p_239421_2_) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F);

			register(ItemInit.MIRROR_SHIELD.get(), new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_,
					p_239421_2_) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F);
			
			register(ItemInit.FISHING_ROD.get(), new ResourceLocation("cast"), (p_239422_0_, p_239422_1_, p_239422_2_) ->
			{
				if (p_239422_2_ == null)
				{
					return 0.0F;
				}
				else
				{
					boolean flag = p_239422_2_.getMainHandItem() == p_239422_0_;
					boolean flag1 = p_239422_2_.getOffhandItem() == p_239422_0_;
					if (p_239422_2_.getMainHandItem().getItem() instanceof FishingRodItem)
					{
						flag1 = false;
					}
					
					return (flag || flag1) && p_239422_2_ instanceof PlayerEntity && ((PlayerEntity) p_239422_2_).fishing != null ? 1.0F : 0.0F;
				}
			});
		}


	}
	private void doClientStuff(final FMLClientSetupEvent event) {
		RendererManagerInit.init();
		ClientHandler.setupClient();

	}
}
