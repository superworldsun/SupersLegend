package com.superworldsun.superslegend;

import com.mojang.serialization.Codec;
import com.superworldsun.superslegend.client.config.SupersLegendConfig;
import com.superworldsun.superslegend.entities.projectiles.arrows.*;
import com.superworldsun.superslegend.hookshotCap.SyncToClient;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookStorage;
import com.superworldsun.superslegend.items.capabilities.SacredShieldState;
import com.superworldsun.superslegend.items.capabilities.SacredShieldStorage;
import com.superworldsun.superslegend.loot.VanillaMobDrops;
import com.superworldsun.superslegend.mana.IMana;
import com.superworldsun.superslegend.mana.Mana;
import com.superworldsun.superslegend.mana.ManaStorage;
import com.superworldsun.superslegend.registries.*;
import com.superworldsun.superslegend.songs.ILearnedSongs;
import com.superworldsun.superslegend.songs.LearnedSongs;
import com.superworldsun.superslegend.songs.LearnedSongsStorage;
import com.superworldsun.superslegend.waypoints.IWaypoints;
import com.superworldsun.superslegend.waypoints.WaypointsStorage;
import com.superworldsun.superslegend.waypoints.Waypoints;
import com.superworldsun.superslegend.worldgen.world.OreGen;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.*;

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
	public static final ArrayList<BlockPos> toRemove = new ArrayList<BlockPos>();
	// This sub-class below is the start where we'll add registry and stuff
	// later on
	public SupersLegendMain()
	{
		instance = this;

		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		// Our listener for setup, it will pick up on anything put into setup
		// and notify Forge of it
		//SupersLegendStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		modEventBus.addListener(this::setup);
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SupersLegendConfig.SPEC, "superslegend.toml");

		// Remember to register items before blocks, problems can occur
		// otherwise if you don't
		VanillaMobDrops customloot = new VanillaMobDrops();
		MinecraftForge.EVENT_BUS.register(customloot);
		ItemInit.ITEMS.register(modEventBus);
		BlockInit.BLOCKS.register(modEventBus);
		//BlockItemInit.BLOCKS.register(modEventBus);
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
		EffectInit.REGISTRY.register(modEventBus);
		RecipeSerializerInit.RECIPE_SERIALIZERS.register(modEventBus);
		AttributeInit.ATTRIBUTES.register(modEventBus);
		
		//The structure gen listeners must use MinecraftForge.EVENT_BUS and
		// not MLJavaModLoadingContext.get().getModEventBus(), otherwise, you will cause MC to crash
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);

		// The comments for BiomeLoadingEvent and StructureSpawnListGatherEvent says to do HIGH for additions.
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGen::generateOres);
		forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
	}
	
	public static ResourceLocation locate(String name)
	{
		return new ResourceLocation(SupersLegendMain.MOD_ID, name);
	}
	
	/* The FMLCommonSetupEvent (FML - Forge Mod Loader) */
	private void setup(final FMLCommonSetupEvent event)
	{
		CapabilityManager.INSTANCE.register(IMana.class, new ManaStorage(), Mana::new);
		CapabilityManager.INSTANCE.register(ILearnedSongs.class, new LearnedSongsStorage(), LearnedSongs::new);
		CapabilityManager.INSTANCE.register(HookModel.class, new HookStorage(), () -> { throw new UnsupportedOperationException("No Implementation!"); });
		CapabilityManager.INSTANCE.register(SacredShieldState.class, new SacredShieldStorage(), () -> { throw new UnsupportedOperationException("No Implementation!"); });
		CapabilityManager.INSTANCE.register(IWaypoints.class, new WaypointsStorage(), Waypoints::new);

		NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation("superslegend", "main_channel"), () -> "1.0", s -> true, s -> true);
		NETWORK.registerMessage(1, SyncToClient.class, SyncToClient::encode, SyncToClient::new, SyncToClient::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

		// This is for thread-safe operations later on such as world-gen
		event.enqueueWork(() ->
		{
			//SupersLegendStructures.setupStructures();
			//SupersLegendConfiguredStructures.registerConfiguredStructures();
		});

		//HOW TO DISPENCER ARROWS
		DispenserBlock.registerBehavior(ItemInit.FIRE_ARROW.get(), new ProjectileDispenseBehavior()
		{
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn)
			{
				FireArrowEntity firearrowentity = new FireArrowEntity(worldIn, position.x(), position.y(), position.z());
				firearrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				return firearrowentity;
			}
		});
		DispenserBlock.registerBehavior(ItemInit.ICE_ARROW.get(), new ProjectileDispenseBehavior()
		{
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn)
			{
				IceArrowEntity icearrowentity = new IceArrowEntity(worldIn, position.x(), position.y(), position.z());
				icearrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				return icearrowentity;
			}
		});
		DispenserBlock.registerBehavior(ItemInit.SHOCK_ARROW.get(), new ProjectileDispenseBehavior()
		{
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn)
			{
				ShockArrowEntity shockarrowentity = new ShockArrowEntity(worldIn, position.x(), position.y(), position.z());
				shockarrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				return shockarrowentity;
			}
		});
		DispenserBlock.registerBehavior(ItemInit.ANCIENT_ARROW.get(), new ProjectileDispenseBehavior()
		{
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn)
			{
				AncientArrowEntity ancientarrowentity = new AncientArrowEntity(worldIn, position.x(), position.y(), position.z());
				ancientarrowentity.pickup = AncientArrowEntity.PickupStatus.ALLOWED;
				return ancientarrowentity;
			}
		});
		DispenserBlock.registerBehavior(ItemInit.SILVER_ARROW.get(), new ProjectileDispenseBehavior()
		{
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn)
			{
				SilverArrowEntity silverarrowentity = new SilverArrowEntity(worldIn, position.x(), position.y(), position.z());
				silverarrowentity.pickup = SilverArrowEntity.PickupStatus.ALLOWED;
				return silverarrowentity;
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
		/*event.getGeneration().getStructures().add(() -> SupersLegendConfiguredStructures.CONFIGURED_FAIRY_FOUNTAIN);
		if (event.getName().equals(new ResourceLocation("minecraft", "dark_forest"))||
				event.getName().equals(new ResourceLocation("minecraft", "dark_forest_hills"))) {
			event.getGeneration().getStructures().add(() -> SupersLegendConfiguredStructures.CONFIGURED_GRAVEYARD);
		}*/
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
				@SuppressWarnings("unchecked")
				Codec<? extends ChunkGenerator> codec = (Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().getGenerator());
				ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey(codec);
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
			/*Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().getGenerator().getSettings().structureConfig());
			tempMap.putIfAbsent(SupersLegendStructures.FAIRY_FOUNTAIN.get(),
					DimensionStructuresSettings.DEFAULTS.get(SupersLegendStructures.FAIRY_FOUNTAIN.get()));
			tempMap.putIfAbsent(SupersLegendStructures.GRAVEYARD.get(),
					DimensionStructuresSettings.DEFAULTS.get(SupersLegendStructures.GRAVEYARD.get()));
			serverWorld.getChunkSource().getGenerator().getSettings().structureConfig = tempMap;*/
		}
	}
	// STRUCTURE GEN CODE ENDS HERE!
	// STRUCTURE GEN CODE ENDS HERE!

	//GROUPS
	public static final ItemGroup RESOURCES = new SupersLegendItemGroup();
	public static final ItemGroup APPAREL = new SupersLegendItemGroupApparel();
	public static final ItemGroup BLOCKS = new SupersLegendItemGroupBlocks();
}
