package com.superworldsun.superslegend;

import static net.minecraft.item.ItemModelsProperties.register;

import java.util.Optional;

import com.superworldsun.superslegend.util.events.ModEventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.superworldsun.superslegend.config.Config;
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
import com.superworldsun.superslegend.mana.IMana;
import com.superworldsun.superslegend.mana.Mana;
import com.superworldsun.superslegend.mana.ManaStorage;
import com.superworldsun.superslegend.registries.BiomeInit;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ContainerInit;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.FluidInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.LootInit;
import com.superworldsun.superslegend.registries.OcarinaSongInit;
import com.superworldsun.superslegend.registries.PaintingInit;
import com.superworldsun.superslegend.registries.RendererManagerInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TileEntityInit;
import com.superworldsun.superslegend.songs.ILearnedSongs;
import com.superworldsun.superslegend.songs.LearnedSongs;
import com.superworldsun.superslegend.songs.LearnedSongsStorage;
import com.superworldsun.superslegend.util.ClientHandler;
import com.superworldsun.superslegend.util.events.EntityEventHandler;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.IForgeRegistry;

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
		ModLoadingContext context = ModLoadingContext.get();
		// Our listener for setup, it will pick up on anything put into setup
		// and notify Forge of it
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::doClientStuff);
		context.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);

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
