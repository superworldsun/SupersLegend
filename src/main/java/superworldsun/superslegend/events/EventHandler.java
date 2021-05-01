package superworldsun.superslegend.events;

//import net.minecraft.arrows.SharedMonsterAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowAncient;

@Mod.EventBusSubscriber(modid = SupersLegend.modid, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {



	/*@SubscribeEvent
	public void livingDeath(LivingDeathEvent event)
	{
		if (event.getEntity() instanceof PlayerEntity)
		{
			event.setCanceled(true);
		}
	}*/

    /*@SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        World world = (World) event.getWorld();
        TileEntity te = world.getTileEntity(pos);
        PlayerEntity player = event.getPlayer();
        GameProfile profile = player.getGameProfile();
        UUID playerUUID = profile.getId();
        if (!world.isRemote && te instanceof SwordDisplayTile) {
            SwordDisplayTile displayTile = (SwordDisplayTile) te;
            boolean isTheOwner = playerUUID.equals(displayTile.getOwner());
            ItemStack sword = displayTile.getSword();
            if (!sword.isEmpty() && !isTheOwner && displayTile.getOwner() != null && !player.abilities.isCreativeMode) {
                event.setCanceled(true);
            }
        }
    }*/

    @SubscribeEvent
    public static void customBiomeTable(FMLCommonSetupEvent event)
    {

        //BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(LOST_WOODS, 10));

        //BiomeDictionary.addTypes(LOST_WOODS, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.OVERWORLD);


        //event.getGeneration().build().equals(new ResourceLocation(SupersLegend.modid, "worldgen/biome/lost_woods"));

    //new ResourceLocation(SupersLegend.modid, "worldgen/biome/lost_woods");

    }

    //public static RegistryKey<Biome> LOST_WOODS = RegistryKey.create(Registry.DIMENSION_REGISTRY,
      //      new ResourceLocation(SupersLegend.modid, "lost_woods"));




    /*@SubscribeEvent
    public static void customLootTable(LootTableLoadEvent event){

    if(event.getName().equals(LootTables.GAMEPLAY_FISHING_FISH))
    {
       //event.getTable().getPool(String.valueOf(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(SupersLegend.modid, "gameplay/fishing/fish"))).build()));

        //Adds Fish along next to regular fish, stacks loot
        event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(SupersLegend.modid, "gameplay/fishing/fish"))).build());
    }
        //event.getLootTableManager().getLootTableFromLocation(new ResourceLocation(SupersLegend.modid, "gameplay/fishing/fish"));
        //LootTables.getReadOnlyLootTables().add(new ResourceLocation(SupersLegend.modid, "loot_tables/gameplay/fishing/fish.json"));
    }*/

    /*@SubscribeEvent
    public void onpigjump(LivingEvent.LivingJumpEvent event)
    {
        if(event.getEntityLiving() instanceof PigEntity) {

            event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.LEVITATION, 1000, 5));
            event.getEntityLiving().playSound(SoundInit.BOMB_FUSE, 0.25F, 1.0F);
        }
    }*/

    //@SubscribeEvent
    ///public void testevent(ItemExpireEvent event){ }


    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {
        Entity sourceEntity = event.getSource().getImmediateSource();
        if (sourceEntity instanceof EntityArrowAncient) {
            event.getDrops().clear();
        }
    }

    //Crashes Game because of boolean
    /*@SubscribeEvent
    public boolean isAboutToBreak(ItemStack stack)
    {
        return stack.isDamageable() && (stack.getDamage()+1 >= stack.getMaxDamage() && (!Screen.hasControlDown()));
    }*/

	/*@SuppressWarnings("unused")
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void beforePlayerHurt(LivingAttackEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof PlayerEntity)) {
			return;
		}
		PlayerEntity player = (PlayerEntity) entity;

		if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.magic_armor_cap)&&
			player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem().equals(ItemList.magic_armor_tunic)&&
			player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem().equals(ItemList.magic_armor_leggings)&&
			player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.magic_armor_boots))
		{
			
		}
	}*/

		/*@SubscribeEvent
		public static void pickupItem(EntityItemPickupEvent event) {
			event.getPlayer().inventory.addItemStackToInventory(new ItemStack(Items.SNOWBALL));
		}

	@SubscribeEvent
	public static void OnCraftedEvent(PlayerEvent.ItemCraftedEvent event) {
		if (event.getCrafting().getItem() != Items.ANVIL) {
			event.getPlayer().inventory.addItemStackToInventory(new ItemStack(Items.SNOWBALL));
		}
	}*/



}