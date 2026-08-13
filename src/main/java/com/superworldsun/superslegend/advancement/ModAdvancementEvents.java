package com.superworldsun.superslegend.advancement;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.magic.MagicProvider;
import com.superworldsun.superslegend.entities.projectiles.arrows.BombArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.FireArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.IceArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.MagicFireArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.MagicIceArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.MagicLightArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.ShockArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.bombs.AbstractBombEntity;
import com.superworldsun.superslegend.entities.projectiles.bombs.AbstractWaterBombEntity;
import com.superworldsun.superslegend.interfaces.IHoveringEntity;
import com.superworldsun.superslegend.interfaces.TameableEntity;
import com.superworldsun.superslegend.items.curios.head.masks.GibdoMask;
import com.superworldsun.superslegend.items.item.MagneticGlove;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TagInit;
import com.superworldsun.superslegend.items.wallet.RupeeWalletItem;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.RupeeWalletUtil;
import com.superworldsun.superslegend.events.TemperatureEvents;
import com.superworldsun.superslegend.util.HylianTextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ModAdvancementEvents {
    private static final String SEEN_MASKS_TAG = SupersLegendMain.MOD_ID + ":SeenAdvancementMasks";
    private static final String SEEN_SONGS_TAG = SupersLegendMain.MOD_ID + ":SeenAdvancementSongs";
    private static final String SEEN_RINGS_TAG = SupersLegendMain.MOD_ID + ":SeenAdvancementRings";
    private static final String ELEMENTAL_ARROWS_TAG = SupersLegendMain.MOD_ID + ":ElementalArrowHits";
    private static final String CURSED_RING_TICKS_TAG = SupersLegendMain.MOD_ID + ":CursedRingTicks";
    private static final String SPEED_TICKS_TAG = SupersLegendMain.MOD_ID + ":PegasusSpeedTicks";
    private static final String ZORA_SWIM_DISTANCE_TAG = SupersLegendMain.MOD_ID + ":ZoraSwimDistance";
    private static final String ZORA_UNDERWATER_TICKS_TAG = SupersLegendMain.MOD_ID + ":ZoraUnderwaterTicks";
    private static final String BUNNY_SPRINT_DISTANCE_TAG = SupersLegendMain.MOD_ID + ":BunnySprintDistance";
    private static final int MASK_GOAL = 5;
    private static final int SONG_SHEET_GOAL = 23;

    private ModAdvancementEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)
                || player.tickCount % 20 != 0) {
            return;
        }

        checkWallet(player);
        checkEquipment(player);
        checkInventoryGoals(player);
        checkLensOfTruth(player);
        checkSpecialEquipment(player);
        checkTransformationMasks(player);
        checkMaskChallenges(player);
        checkHylianTranslation(player);
    }

    private static void checkHylianTranslation(ServerPlayer player) {
        if (!HylianTextUtil.isHoldingBookOfMudora(player)) {
            return;
        }

        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getViewVector(1.0F).scale(7.0D));
        BlockHitResult blockHit = player.level().clip(new ClipContext(
                start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        if (blockHit.getType() != HitResult.Type.MISS
                && player.level().getBlockEntity(blockHit.getBlockPos()) instanceof SignBlockEntity sign
                && (HylianTextUtil.containsHylianText(sign.getFrontText())
                || HylianTextUtil.containsHylianText(sign.getBackText()))) {
            ModAdvancementHelper.award(player, "obtain_book_of_mudora", "translated_text");
            return;
        }

        AABB searchBox = player.getBoundingBox().expandTowards(player.getViewVector(1.0F).scale(7.0D))
                .inflate(1.0D);
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player.level(), player, start, end, searchBox,
                entity -> entity != player && entity.isPickable() && entity.hasCustomName());
        if (entityHit != null && entityHit.getEntity().getCustomName() != null
                && HylianTextUtil.usesHylianFont(entityHit.getEntity().getCustomName())) {
            ModAdvancementHelper.award(player, "obtain_book_of_mudora", "translated_text");
        }
    }

    private static void checkMaskChallenges(ServerPlayer player) {
        CompoundTag root = player.getPersistentData();
        CompoundTag data = root.getCompound(Player.PERSISTED_NBT_TAG);
        boolean bunny = hasCurio(player, ItemInit.MASK_BUNNYHOOD.get());
        if (bunny && player.isSprinting()) {
            if (data.getBoolean("BunnyHadPosition")) {
                double dx = player.getX() - data.getDouble("BunnyLastX");
                double dz = player.getZ() - data.getDouble("BunnyLastZ");
                double step = Math.sqrt(dx * dx + dz * dz);
                if (step <= 25.0D) data.putDouble(BUNNY_SPRINT_DISTANCE_TAG,
                        data.getDouble(BUNNY_SPRINT_DISTANCE_TAG) + step);
            }
            data.putBoolean("BunnyHadPosition", true);
            data.putDouble("BunnyLastX", player.getX());
            data.putDouble("BunnyLastZ", player.getZ());
            if (data.getDouble(BUNNY_SPRINT_DISTANCE_TAG) >= 1_000.0D)
                ModAdvancementHelper.award(player, "bunny_marathon", "sprinted_far");
        } else data.putBoolean("BunnyHadPosition", false);

        if (hasCurio(player, ItemInit.MASK_TROUPELEADERSMASK.get())
                && player.level().isRainingAt(player.blockPosition())
                && player.level().canSeeSky(player.blockPosition()))
            ModAdvancementHelper.award(player, "rainy_rehearsal", "stood_in_rain");

        if (hasCurio(player, ItemInit.MASK_GIBDOMASK.get()) && !player.level().getEntitiesOfClass(
                Mob.class, player.getBoundingBox().inflate(1.5D),
                mob -> mob.isAlive() && GibdoMask.isEntityAffected(mob)).isEmpty())
            ModAdvancementHelper.award(player, "among_the_dead", "walked_unnoticed");

        if (hasCurio(player, ItemInit.MASK_CAPTAINSHAT.get())) {
            java.util.List<AbstractSkeleton> followers = player.level().getEntitiesOfClass(
                    AbstractSkeleton.class, player.getBoundingBox().inflate(48.0D),
                    skeleton -> skeleton instanceof TameableEntity tameable
                            && tameable.getOwnerUniqueId().filter(player.getUUID()::equals).isPresent());
            if (followers.size() >= 3)
                ModAdvancementHelper.award(player, "captains_company", "three_skeletons");
            if (followers.stream().map(Entity::getType).distinct().count() >= 3)
                ModAdvancementHelper.award(player, "bone_battalion", "three_skeleton_types");
        }
        root.put(Player.PERSISTED_NBT_TAG, data);
    }

    private static boolean hasCurio(ServerPlayer player, Item item) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(item, player).isPresent();
    }

    @SubscribeEvent
    public static void onAnimalBred(BabyEntitySpawnEvent event) {
        if (event.getCausedByPlayer() instanceof ServerPlayer player
                && hasCurio(player, ItemInit.MASK_COUPLESMASK.get()))
            ModAdvancementHelper.award(player, "perfect_match", "bred_animals");
    }

    private static void checkWallet(ServerPlayer player) {
        RupeeWalletUtil.findEquippedWallet(player).ifPresent(result -> {
            if (!(result.stack().getItem() instanceof RupeeWalletItem wallet)) {
                return;
            }

            int balance = wallet.getStoredRupees(result.stack());
            if (balance >= 99) {
                ModAdvancementHelper.award(player, "wallet_99_rupees", "balance");
            }
            if (balance >= 300) {
                ModAdvancementHelper.award(player, "wallet_300_rupees", "balance");
            }
            if (balance >= 500) {
                ModAdvancementHelper.award(player, "wallet_500_rupees", "balance");
            }
            if (balance >= 1_000) {
                ModAdvancementHelper.award(player, "wallet_1000_rupees", "balance");
            }
            if (balance >= 2_000) {
                ModAdvancementHelper.award(player, "wallet_2000_rupees", "balance");
            }
            if (balance >= 5_000) {
                ModAdvancementHelper.award(player, "wallet_5000_rupees", "balance");
            }
            if (balance >= 9_999) {
                ModAdvancementHelper.award(player, "hyrules_richest_hero", "filled_wallet");
            }
        });
    }

    private static void checkEquipment(ServerPlayer player) {
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ItemInit.ANCIENT_HELMET.get())
                && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemInit.ANCIENT_CUIRASS.get())
                && player.getItemBySlot(EquipmentSlot.LEGS).is(ItemInit.ANCIENT_GREAVES.get())
                && player.getItemBySlot(EquipmentSlot.FEET).is(ItemInit.ANCIENT_BOOTS.get())) {
            ModAdvancementHelper.award(player, "ancient_arsenal", "equipped_set");
        }

        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ItemInit.CLIMBERS_BANDANNA.get())
                && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemInit.CLIMBING_GEAR.get())
                && player.getItemBySlot(EquipmentSlot.LEGS).is(ItemInit.CLIMBING_PANTS.get())
                && player.getItemBySlot(EquipmentSlot.FEET).is(ItemInit.CLIMBING_BOOTS.get())) {
            ModAdvancementHelper.award(player, "built_for_the_climb", "equipped_set");
        }

        if (isWearingSet(player, ItemInit.DARK_CAP.get(), ItemInit.DARK_TUNIC.get(),
                ItemInit.DARK_TROUSERS.get(), ItemInit.DARK_BOOTS.get())) {
            ModAdvancementHelper.award(player, "full_dark_tunic_set", "equipped_set");
        }
        if (isWearingSet(player, ItemInit.BARBARIAN_HELMET.get(), ItemInit.BARBARIAN_ARMOR.get(),
                ItemInit.BARBARIAN_LEG_WRAPS.get(), ItemInit.BARBARIAN_BOOTS.get())) {
            ModAdvancementHelper.award(player, "full_barbarian_set", "equipped_set");
        }
        if (isWearingSet(player, ItemInit.DESERT_VOE_HEADBAND.get(), ItemInit.DESERT_VOE_SPAULDER.get(),
                ItemInit.DESERT_VOE_TROUSERS.get(), ItemInit.DESERT_VOE_BOOTS.get())) {
            ModAdvancementHelper.award(player, "full_desert_voe_set", "equipped_set");
        }
        if (isWearingSet(player, ItemInit.SNOWQUILL_HEADDRESS.get(), ItemInit.SNOWQUILL_TUNIC.get(),
                ItemInit.SNOWQUILL_TROUSERS.get(), ItemInit.SNOWQUILL_BOOTS.get())) {
            ModAdvancementHelper.award(player, "full_snowquill_set", "equipped_set");
        }
    }

    private static boolean isWearingSet(ServerPlayer player, Item head, Item chest, Item legs, Item feet) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(head)
                && player.getItemBySlot(EquipmentSlot.CHEST).is(chest)
                && player.getItemBySlot(EquipmentSlot.LEGS).is(legs)
                && player.getItemBySlot(EquipmentSlot.FEET).is(feet);
    }

    private static void checkInventoryGoals(ServerPlayer player) {
        CompoundTag forgeData = player.getPersistentData();
        CompoundTag persistentData = forgeData.getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag seenMasks = persistentData.getCompound(SEEN_MASKS_TAG);
        CompoundTag seenSongs = persistentData.getCompound(SEEN_SONGS_TAG);
        CompoundTag seenRings = persistentData.getCompound(SEEN_RINGS_TAG);
        boolean hasSword = false;
        boolean hasShield = false;
        boolean hasBow = false;
        boolean hasBombs = false;
        boolean hasOcarina = false;

        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.isEmpty()) {
                continue;
            }

            Item item = stack.getItem();
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
            if (SupersLegendMain.MOD_ID.equals(itemId.getNamespace())) {
                String path = itemId.getPath();
                if (path.startsWith("mask_")) {
                    seenMasks.putBoolean(path, true);
                }
                if (path.endsWith("_sheet")) {
                    seenSongs.putBoolean(path, true);
                }
                if (stack.is(TagInit.APPRAISAL_LIST)) {
                    seenRings.putBoolean(path, true);
                }
            }

            hasSword |= item instanceof SwordItem;
            hasShield |= item instanceof ShieldItem;
            hasBow |= item instanceof BowItem;
            hasBombs |= stack.is(ItemInit.BOMB.get()) || stack.is(ItemInit.WATER_BOMB.get());
            hasOcarina |= stack.is(ItemInit.FAIRY_OCARINA.get()) || stack.is(ItemInit.OCARINA_OF_TIME.get());
        }

        persistentData.put(SEEN_MASKS_TAG, seenMasks);
        persistentData.put(SEEN_SONGS_TAG, seenSongs);
        persistentData.put(SEEN_RINGS_TAG, seenRings);
        forgeData.put(Player.PERSISTED_NBT_TAG, persistentData);

        if (seenMasks.getAllKeys().size() >= MASK_GOAL) {
            ModAdvancementHelper.award(player, "happy_mask_salesman", "five_masks");
        }
        if (!seenSongs.isEmpty()) {
            ModAdvancementHelper.award(player, "the_song_remains_the_same", "first_song");
        }
        if (seenSongs.getAllKeys().size() >= SONG_SHEET_GOAL) {
            ModAdvancementHelper.award(player, "maestro_of_time", "all_songs");
        }
        if (seenRings.getAllKeys().size() >= 10) {
            ModAdvancementHelper.award(player, "ring_collector", "ten_rings");
        }
        if (hasSword) {
            ModAdvancementHelper.award(player, "its_dangerous_to_go_alone", "obtained_sword");
        }
        if (hasSword && hasShield && hasBow && hasBombs && hasOcarina) {
            ModAdvancementHelper.award(player, "fully_equipped", "full_loadout");
        }
    }

    private static void checkSpecialEquipment(ServerPlayer player) {
        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        double horizontalSpeed = player.getDeltaMovement().horizontalDistanceSqr();

        if (boots.is(ItemInit.IRON_BOOTS.get()) && player.isUnderWater() && horizontalSpeed > 0.0025D) {
            ModAdvancementHelper.award(player, "heavy_metal", "walked_underwater");
        }
        if (boots.is(ItemInit.HOVER_BOOTS.get()) && ((IHoveringEntity) player).isHovering()
                && horizontalSpeed > 0.0025D) {
            ModAdvancementHelper.award(player, "walking_on_air", "crossed_gap");
        }

        int speedTicks = boots.is(ItemInit.PEGASUS_BOOTS.get()) && player.isSprinting()
                && horizontalSpeed > 0.04D ? data.getInt(SPEED_TICKS_TAG) + 20 : 0;
        data.putInt(SPEED_TICKS_TAG, speedTicks);
        if (speedTicks >= 100) {
            ModAdvancementHelper.award(player, "speed_demon", "sustained_speed");
        }

        boolean cursed = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.CURSED_RING.get(), player).isPresent();
        int cursedTicks = cursed ? data.getInt(CURSED_RING_TICKS_TAG) + 20 : 0;
        data.putInt(CURSED_RING_TICKS_TAG, cursedTicks);
        if (cursedTicks >= 24_000) {
            ModAdvancementHelper.award(player, "curse_bearer", "full_day");
        }

        boolean nearSacredPedestal = BlockPos.betweenClosedStream(new AABB(player.blockPosition()).inflate(5.0D))
                .anyMatch(pos -> player.level().getBlockState(pos).is(BlockInit.DINS_SACRED_PEDESTAL.get())
                        || player.level().getBlockState(pos).is(BlockInit.FARORES_SACRED_PEDESTAL.get())
                        || player.level().getBlockState(pos).is(BlockInit.NAYRUS_SACRED_PEDESTAL.get()));
        if (nearSacredPedestal) {
            ModAdvancementHelper.award(player, "sacred_ground", "found_pedestal");
        }
        if (player.level().dimension() == Level.NETHER && TemperatureEvents.getHeatResistance(player) >= 1.0F) {
            ModAdvancementHelper.award(player, "hell_heat_proof", "withstood_hell_heat");
        }
        if (cursed) {
            // Persist changes even when this is the only active tracker.
        }
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
    }

    private static void checkTransformationMasks(ServerPlayer player) {
        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        boolean deku = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_DEKUMASK.get(), player).isPresent();
        boolean goron = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GORONMASK.get(), player).isPresent();
        boolean zora = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_ZORAMASK.get(), player).isPresent();
        if (deku) ModAdvancementHelper.award(player, "wear_deku_mask", "wore_mask");
        if (goron) ModAdvancementHelper.award(player, "wear_goron_mask", "wore_mask");
        if (zora) ModAdvancementHelper.award(player, "wear_zora_mask", "wore_mask");

        int underwaterTicks = zora && player.isUnderWater() ? data.getInt(ZORA_UNDERWATER_TICKS_TAG) + 20 : 0;
        data.putInt(ZORA_UNDERWATER_TICKS_TAG, underwaterTicks);
        if (underwaterTicks >= 6_000) ModAdvancementHelper.award(player, "zora_five_minutes", "five_minutes");

        if (zora && player.isSwimming()) {
            if (data.getBoolean("ZoraHadPosition")) {
                double dx = player.getX() - data.getDouble("ZoraLastX");
                double dy = player.getY() - data.getDouble("ZoraLastY");
                double dz = player.getZ() - data.getDouble("ZoraLastZ");
                double step = Math.sqrt(dx * dx + dy * dy + dz * dz);
                if (step <= 25.0D) data.putDouble(ZORA_SWIM_DISTANCE_TAG,
                        data.getDouble(ZORA_SWIM_DISTANCE_TAG) + step);
            }
            data.putBoolean("ZoraHadPosition", true);
            data.putDouble("ZoraLastX", player.getX());
            data.putDouble("ZoraLastY", player.getY());
            data.putDouble("ZoraLastZ", player.getZ());
            if (data.getDouble(ZORA_SWIM_DISTANCE_TAG) >= 1_000.0D)
                ModAdvancementHelper.award(player, "zora_long_distance_swim", "long_swim");
        } else data.putBoolean("ZoraHadPosition", false);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
    }

    @SubscribeEvent
    public static void onPegHit(PlayerInteractEvent.LeftClickBlock event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
                || !(player.getMainHandItem().is(ItemInit.MAGIC_HAMMER.get())
                || player.getMainHandItem().is(ItemInit.MEGATON_HAMMER.get())
                || player.getMainHandItem().is(ItemInit.SKULL_HAMMER.get()))) return;
        BlockState state = event.getLevel().getBlockState(event.getPos());
        if (state.is(BlockInit.OAK_PEG_BLOCK.get()) || state.is(BlockInit.SPRUCE_PEG_BLOCK.get())
                || state.is(BlockInit.BIRCH_PEG_BLOCK.get()) || state.is(BlockInit.JUNGLE_PEG_BLOCK.get())
                || state.is(BlockInit.ACACIA_PEG_BLOCK.get()) || state.is(BlockInit.DARK_OAK_PEG_BLOCK.get())
                || state.is(BlockInit.RUSTED_PEG_BLOCK.get()) || state.is(BlockInit.SPIKED_PEG_BLOCK.get()))
            ModAdvancementHelper.award(player, "hammer_time", "hammered_peg");
        if (state.is(BlockInit.SPIKED_PEG_BLOCK.get()) && player.getMainHandItem().is(ItemInit.SKULL_HAMMER.get()))
            ModAdvancementHelper.award(player, "skull_crusher", "skull_hammer_spiked_peg");
        if (state.is(BlockInit.RUSTED_FLOOR_SWITCH.get()) && player.getMainHandItem().is(ItemInit.MEGATON_HAMMER.get()))
            ModAdvancementHelper.award(player, "heavy_switch", "megaton_rusted_switch");
    }

    @SubscribeEvent
    public static void onItemDestroyed(PlayerDestroyItemEvent event) {
        if (event.getEntity() instanceof ServerPlayer player
                && event.getOriginal().is(ItemInit.DEKU_SHIELD.get()) && player.isOnFire())
            ModAdvancementHelper.award(player, "ashes_to_ashes", "shield_burned");
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && event.getCrafting().is(ItemInit.BIGGORONS_SWORD.get()))
            ModAdvancementHelper.award(player, "forge_biggorons_sword", "crafted_sword");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAncientGroundItemPickup(EntityItemPickupEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        CompoundTag itemData = event.getItem().getPersistentData();
        if (itemData.getBoolean(TimeSongSavedData.ITEM_INVERTED_EXTENDED_TAG)
                && itemData.getInt(TimeSongSavedData.ITEM_REAL_LIFETIME_TAG) > 6_000) {
            ModAdvancementHelper.award(player, "against_the_clock", "rescued_old_item");
        }
    }

    @SubscribeEvent
    public static void onItemSmelted(PlayerEvent.ItemSmeltedEvent event) {
        if (event.getEntity() instanceof ServerPlayer player
                && TimeSongSavedData.getMode(player.level()) == TimeSongSavedData.Mode.DOUBLE
                && player.getPersistentData().getBoolean("SupersLegendUsedDoubleTime")) {
            ModAdvancementHelper.award(player, "time_well_spent", "collected_result");
            player.getPersistentData().remove("SupersLegendUsedDoubleTime");
        }
    }

    @SubscribeEvent
    public static void onPotionBrewed(PlayerBrewedPotionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player
                && TimeSongSavedData.getMode(player.level()) == TimeSongSavedData.Mode.DOUBLE
                && player.getPersistentData().getBoolean("SupersLegendUsedDoubleTime")) {
            ModAdvancementHelper.award(player, "time_well_spent", "collected_result");
            player.getPersistentData().remove("SupersLegendUsedDoubleTime");
        }
    }

    @SubscribeEvent
    public static void onLynelVolleyImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)
                || !(arrow.getOwner() instanceof ServerPlayer player)
                || !(event.getRayTraceResult() instanceof EntityHitResult hit)
                || !arrow.getPersistentData().hasUUID("SupersLegendLynelVolley")) return;
        CompoundTag root = player.getPersistentData();
        CompoundTag data = root.getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag volleys = data.getCompound("SupersLegendLynelVolleyHits");
        String volley = arrow.getPersistentData().getUUID("SupersLegendLynelVolley").toString();
        CompoundTag targets = volleys.getCompound(volley);
        targets.putBoolean(hit.getEntity().getUUID().toString(), true);
        volleys.put(volley, targets);
        data.put("SupersLegendLynelVolleyHits", volleys);
        root.put(Player.PERSISTED_NBT_TAG, data);
        if (targets.getAllKeys().size() >= 3)
            ModAdvancementHelper.award(player, "lynel_barrage", "three_targets_one_volley");
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (event.getEntity() instanceof Monster && CuriosApi.getCuriosHelper()
                .findEquippedCurio(ItemInit.MASK_STONEMASK.get(), player).isPresent()) {
            ModAdvancementHelper.award(player, "stone_cold_stealth", "surprise_attack");
        }
        Object direct = event.getSource().getDirectEntity();
        String element = direct instanceof FireArrowEntity || direct instanceof MagicFireArrowEntity ? "fire"
                : direct instanceof IceArrowEntity || direct instanceof MagicIceArrowEntity ? "ice"
                : direct instanceof ShockArrowEntity ? "shock"
                : direct instanceof MagicLightArrowEntity ? "light" : null;
        if (element != null) {
            CompoundTag root = player.getPersistentData();
            CompoundTag data = root.getCompound(Player.PERSISTED_NBT_TAG);
            CompoundTag hits = data.getCompound(ELEMENTAL_ARROWS_TAG);
            hits.putBoolean(element, true);
            data.put(ELEMENTAL_ARROWS_TAG, hits);
            root.put(Player.PERSISTED_NBT_TAG, data);
            if (hits.getAllKeys().size() >= 4) {
                ModAdvancementHelper.award(player, "elemental_arsenal", "all_elements");
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        CompoundTag victimData = event.getEntity().getPersistentData();
        if (victimData.hasUUID(MagneticGlove.PULLER_TAG)
                && event.getEntity().level().getGameTime()
                - victimData.getLong(MagneticGlove.LAST_PULL_TIME_TAG) <= 200L
                && !(event.getSource().getEntity() instanceof Player)) {
            ServerPlayer magneticPlayer = event.getEntity().getServer() == null ? null
                    : event.getEntity().getServer().getPlayerList()
                    .getPlayer(victimData.getUUID(MagneticGlove.PULLER_TAG));
            if (magneticPlayer != null) {
                ModAdvancementHelper.award(magneticPlayer, "magnetic_demise", "environmental_kill");
            }
        }

        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) {
            return;
        }
        ItemStack weapon = player.getMainHandItem();
        if (event.getEntity().getMobType() == MobType.UNDEAD && event.getEntity().getMaxHealth() >= 40.0F
                && (weapon.is(ItemInit.MASTER_SWORD.get()) || weapon.is(ItemInit.MASTER_SWORD_V2.get())
                || weapon.is(ItemInit.TRUE_MASTER_SWORD.get()))) {
            ModAdvancementHelper.award(player, "evils_bane", "powerful_undead");
        }
        if (event.getSource().getDirectEntity() instanceof BombArrowEntity) {
            ModAdvancementHelper.award(player, "bombs_away", "bomb_arrow_kill");
        }
        if (player.getCooldowns().isOnCooldown(ItemInit.MASK_BLASTMASK.get())) {
            ModAdvancementHelper.award(player, "blast_radius", "blast_mask_kill");
        }
        if (event.getEntity() instanceof Fox && hasCurio(player, ItemInit.MASK_KEATONMASK.get()))
            ModAdvancementHelper.award(player, "false_fox", "killed_fox");

        Entity direct = event.getSource().getDirectEntity();
        if (direct instanceof AbstractBombEntity || direct instanceof AbstractWaterBombEntity) {
            int kills = direct.getPersistentData().getInt("SupersLegendAdvancementKills") + 1;
            direct.getPersistentData().putInt("SupersLegendAdvancementKills", kills);
            if (kills >= 3) ModAdvancementHelper.award(player, "triple_blast", "three_kills");
        }
        if (direct instanceof AbstractWaterBombEntity waterBomb
                && player.isUnderWater() && waterBomb.isUnderWater() && event.getEntity().isUnderWater()
                && (event.getEntity().getType() == net.minecraft.world.entity.EntityType.DROWNED
                || event.getEntity().getType() == net.minecraft.world.entity.EntityType.GUARDIAN
                || event.getEntity().getType() == net.minecraft.world.entity.EntityType.ELDER_GUARDIAN))
            ModAdvancementHelper.award(player, "depth_charge", "underwater_kill");
    }

    private static void checkLensOfTruth(ServerPlayer player) {
        if (!player.isUsingItem() || !player.getUseItem().is(ItemInit.LENS_OF_TRUTH.get())) {
            return;
        }

        boolean revealedInvisibleMob = !player.level().getEntitiesOfClass(
                Mob.class,
                player.getBoundingBox().inflate(32.0D),
                mob -> mob.isAlive() && mob.isInvisible() && player.hasLineOfSight(mob)
        ).isEmpty();
        if (revealedInvisibleMob) {
            ModAdvancementHelper.award(player, "truth_revealed", "revealed_mob");
        }
    }
}
