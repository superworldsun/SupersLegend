package com.superworldsun.superslegend;

import com.superworldsun.superslegend.entities.projectiles.arrows.EntityPoisonArrow;
import com.superworldsun.superslegend.registries.*;
import com.superworldsun.superslegend.util.events.EntityEventHandler;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.item.ItemModelsProperties.register;

@Mod(SupersLegendMain.MOD_ID)
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SupersLegendMain {
    //Our instance, referenced in the below sub-class
    public static SupersLegendMain instance;
    //The strings for our name and modid + logger
    public static final String NAME = "SupersLegend";
    public static final String MOD_ID = "superslegend";
    public static final Logger LOGGER = LogManager.getLogger();

    //This sub-class below is the start where we'll add registry and stuff later on
    public SupersLegendMain() {
        instance = this;

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext context = ModLoadingContext.get();
        //Our listener for setup, it will pick up on anything put into setup and notify Forge of it
        modEventBus.addListener(this::setup);

        //Remember to register items before blocks, problems can occur otherwise if you don't
        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        SoundInit.SOUNDS.register(modEventBus);
        BiomeInit.BIOMES.register(modEventBus);
        PaintingInit.PAINTING_TYPES.register(modEventBus);
        EntityTypeInit.ENTITIES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
    }

    public static ResourceLocation locate(String name)
    {
        return new ResourceLocation(SupersLegendMain.MOD_ID, name);
    }

    @SubscribeEvent
    public static void createBlockItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            final Item.Properties properties = new Item.Properties().tab(SupersLegendMain.RESOURCES);
            final BlockItem blockItem = new BlockItem(block, properties);
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });
    }


    /* The FMLCommonSetupEvent (FML - Forge Mod Loader) */
    private void setup(final FMLCommonSetupEvent event) {
        //This is for thread-safe operations later on such as world-gen
        event.enqueueWork(() -> {

        });

        DispenserBlock.registerBehavior(ItemInit.POISON_ARROW.get(), new ProjectileDispenseBehavior() {
            @Override
            protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
                EntityPoisonArrow poisonarrowentity = new EntityPoisonArrow(worldIn, position.x(), position.y(),
                        position.z());
                poisonarrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
                return poisonarrowentity;
            }
        });
    }

    public static final ItemGroup RESOURCES = new SupersLegendItemGroup();

    //We need this code for custom bow models with pull animations
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void setModelProperties(FMLClientSetupEvent event) {
            register(ItemInit.METAL_BOW.get(), new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
                if (p_239429_2_ == null) {
                    return 0.0F;
                } else {
                    return p_239429_2_.getUseItem() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getUseItemRemainingTicks()) / 20.0F;
                }
            });
            register(ItemInit.METAL_BOW.get(), new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isUsingItem() && p_239428_2_.getUseItem() == p_239428_0_ ? 1.0F : 0.0F);

            register(ItemInit.HEROS_BOW.get(), new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
                if (p_239429_2_ == null) {
                    return 0.0F;
                } else {
                    return p_239429_2_.getUseItem() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getUseItemRemainingTicks()) / 20.0F;
                }
            });
            register(ItemInit.HEROS_BOW.get(), new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isUsingItem() && p_239428_2_.getUseItem() == p_239428_0_ ? 1.0F : 0.0F);

            register(ItemInit.LYNEL_BOW_X3.get(), new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
                if (p_239429_2_ == null) {
                    return 0.0F;
                } else {
                    return p_239429_2_.getUseItem() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getUseItemRemainingTicks()) / 20.0F;
                }
            });
            register(ItemInit.LYNEL_BOW_X3.get(), new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isUsingItem() && p_239428_2_.getUseItem() == p_239428_0_ ? 1.0F : 0.0F);

            register(ItemInit.LYNEL_BOW_X5.get(), new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
                if (p_239429_2_ == null) {
                    return 0.0F;
                } else {
                    return p_239429_2_.getUseItem() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getUseItemRemainingTicks()) / 20.0F;
                }
            });
            register(ItemInit.LYNEL_BOW_X5.get(), new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isUsingItem() && p_239428_2_.getUseItem() == p_239428_0_ ? 1.0F : 0.0F);


            register(ItemInit.DEKU_SHIELD.get(), new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F);

            register(ItemInit.HYLIAN_SHIELD.get(), new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F);

            register(ItemInit.FISHING_ROD.get(), new ResourceLocation("cast"), (p_239422_0_, p_239422_1_, p_239422_2_) -> {
                if (p_239422_2_ == null) {
                    return 0.0F;
                } else {
                    boolean flag = p_239422_2_.getMainHandItem() == p_239422_0_;
                    boolean flag1 = p_239422_2_.getOffhandItem() == p_239422_0_;
                    if (p_239422_2_.getMainHandItem().getItem() instanceof FishingRodItem) {
                        flag1 = false;
                    }

                    return (flag || flag1) && p_239422_2_ instanceof PlayerEntity && ((PlayerEntity)p_239422_2_).fishing != null ? 1.0F : 0.0F;
                }
            });
        }
    }

}
