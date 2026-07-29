package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IHoveringEntity;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class HoverBootsArmor extends NonEnchantArmor
{
    public static final int MAX_HOVER_TICKS = 22;
    private static final int NO_NORMAL_HOVER_DEADLINE = Integer.MIN_VALUE;
    private static final double LANDING_CHECK_DEPTH = 0.03D;
    private static final double HAZARD_TOP_TOLERANCE = 0.025D;
    private static final double HAZARD_HORIZONTAL_OVERLAP = 0.001D;
    private static final double SMALL_SLIME_BOUNCE_VELOCITY = 0.10D;
    private static final UUID HOVER_STEP_HEIGHT_MODIFIER_ID = UUID.fromString("d53cd0d7-018e-487b-a771-8d6949af91c1");
    private static final AttributeModifier HOVER_STEP_HEIGHT_MODIFIER = new AttributeModifier(
            HOVER_STEP_HEIGHT_MODIFIER_ID,
            "Hover Boots active hover step",
            0.4D,
            AttributeModifier.Operation.ADDITION
    );
    //private static final Map<EquipmentSlot, BipedModel<?>> MODELS_CACHE = new HashMap<>();

    public HoverBootsArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    /**
     * Preserve vanilla slime's completely safe landing even though the boots delay the final
     * grounded tick until their hover ends. Honey still receives ordinary fall damage instead of
     * allowing the hover platform to erase the accumulated fall distance.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void restoreFallDamageOnStickyHoverLanding(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Player player) || !isWearingHoverBoots(player)) {
            return;
        }

        if (isTouchingSlimeBlockTop(player)) {
            event.setDistance(0.0F);
            event.setDamageMultiplier(0.0F);
            player.fallDistance = 0.0F;
        } else if (isTouchingHoneyBlockTop(player)) {
            event.setDamageMultiplier(1.0F);
        }
    }

    /*@OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    @Override
    public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, M _default)
    {
        if (!MODELS_CACHE.containsKey(armorSlot))
        {
            MODELS_CACHE.put(armorSlot, new HoverBootsModel());
        }

        return (M) MODELS_CACHE.get(armorSlot);
    }*/

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot armorSlot, String type)
    {
        return SupersLegendMain.MOD_ID + ":textures/models/armor/hover_boots.png";
    }

    //true = no sprinting
    //false = must be sprinting to hover
    static boolean disableSprintingChecks = true;

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            IHoveringEntity hoveringPlayer = (IHoveringEntity) player;
            if (hoveringPlayer.isStickyBouncePending()) {
                // A real jump replaces the automatic one-time slime rebound. Do not convert the
                // player's own jump into a hover when it reaches its apex.
                hoveringPlayer.setStickyBouncePending(false);
                hoveringPlayer.setJumpedFromBlock(true);
                return;
            }
            if (hoveringPlayer.isBounceHovering()) {
                // A real jump input is not the automatic rebound that this state was designed to
                // consume. End the sticky-surface hover without touching the jump impulse so the
                // player can jump from honey/slime onto a neighboring block normally.
                hoveringPlayer.setBounceHovering(false);
                hoveringPlayer.setHovering(false);
                hoveringPlayer.setHoverTime(MAX_HOVER_TICKS);
                hoveringPlayer.setJumpedFromBlock(true);
                return;
            }
            if (hoveringPlayer.isHovering() && hoveringPlayer.isHazardHovering()) {
                // Hazard hovering is a fixed platform state. Remove the jump impulse immediately
                // so landing on damage terrain cannot be used to jump-hover above it.
                player.setDeltaMovement(player.getDeltaMovement().x, 0.0D, player.getDeltaMovement().z);
                player.setPos(player.getX(), hoveringPlayer.getHoverHeight(), player.getZ());
                player.setOnGround(true);
                player.fallDistance = 0.0F;
                return;
            }
            hoveringPlayer.setJumpedFromBlock(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        // Prevent applying changes 2 times per tick
        if (event.phase == TickEvent.Phase.START)
        {
            return;
        }

        Player player = event.player;
        IHoveringEntity hoveringPlayer = (IHoveringEntity) event.player;
        boolean wearingHoverBoots = isWearingHoverBoots(player);
        boolean wasWearingHoverBoots = hoveringPlayer.wasWearingHoverBoots();
        boolean touchingSlimeBlock = isTouchingSlimeBlockTop(player);
        boolean standingOnStickyHoverBlock = isStandingOnStickyHoverBlock(player);
        // Slime changes onGround to false as it applies its rebound. Its top collision still
        // identifies the actual landing, allowing us to replace that rebound on the same tick.
        boolean contactingStickyHoverBlock = standingOnStickyHoverBlock
                || (touchingSlimeBlock && player.getDeltaMovement().y > 0.03D);
        boolean slimeReboundingFromLanding = touchingSlimeBlock
                && player.getDeltaMovement().y > 0.03D;
        boolean enteredStickyHoverBlock = contactingStickyHoverBlock
                && !hoveringPlayer.wasOnStickyHoverBlock();

        // Slime is a safe landing surface. Clear the completed fall as soon as the player's feet
        // touch its real top, rather than waiting for the hover's eventual landing event. Otherwise
        // walking off during that hover carries the old fall distance onto the next block.
        if (wearingHoverBoots && touchingSlimeBlock) {
            player.fallDistance = 0.0F;
        }

        hoveringPlayer.setWasWearingHoverBoots(wearingHoverBoots);
        if (!hoveringPlayer.isBounceHovering() && !hoveringPlayer.isStickyBouncePending()) {
            hoveringPlayer.setWasOnStickyHoverBlock(contactingStickyHoverBlock);
        }

        if (!wearingHoverBoots) {
            hoveringPlayer.setHovering(false);
            hoveringPlayer.setHazardHovering(false);
            hoveringPlayer.setBounceHovering(false);
            hoveringPlayer.setStickyBouncePending(false);
            hoveringPlayer.setNormalHoverDeadline(NO_NORMAL_HOVER_DEADLINE);
            hoveringPlayer.setWasOnStickyHoverBlock(standingOnStickyHoverBlock);
            return;
        }

        // Initialize the stored ledge height before any partial-height surface logic runs. Without
        // this transition guard, equipping the boots while already standing on soul sand can reuse
        // a stale height and create a hover that continually corrects back to that old position.
        if (!wasWearingHoverBoots) {
            hoveringPlayer.setHovering(false);
            hoveringPlayer.setHazardHovering(false);
            hoveringPlayer.setBounceHovering(false);
            hoveringPlayer.setStickyBouncePending(false);
            hoveringPlayer.setHoverTime(0);
            hoveringPlayer.setHoverHeight(player.getY());
            hoveringPlayer.setJumpedFromBlock(!player.onGround());
            hoveringPlayer.setNormalHoverDeadline(NO_NORMAL_HOVER_DEADLINE);
            hoveringPlayer.setWasOnStickyHoverBlock(standingOnStickyHoverBlock);
            return;
        }

        // At an open water gap, the first gravity step can place the player's feet in water before
        // this END-phase handler sees the missing floor. Preserve that one genuine dry-ledge entry
        // so it can reach the ordinary hover branch below. Existing water/swimming state remains
        // locked out, which prevents swimming out of water from arming a hover.
        boolean recoveringWaterGapEntry = shouldRecoverWaterGapEntry(player, hoveringPlayer);

        // Hovering is a walking-off-a-ledge ability. Swimming out of water, creative/elytra
        // flight, and riding must never arm it. Keep the jump-origin lock until genuine safe
        // ground is reached so ending one of these states in midair cannot start a late hover.
        if (player.getAbilities().flying
                || player.isFallFlying()
                || (player.isSwimming() && !recoveringWaterGapEntry)
                || (player.isInWaterOrBubble() && !recoveringWaterGapEntry)
                || player.isInLava()
                || player.isPassenger()) {
            hoveringPlayer.setHovering(false);
            hoveringPlayer.setHazardHovering(false);
            hoveringPlayer.setBounceHovering(false);
            hoveringPlayer.setStickyBouncePending(false);
            hoveringPlayer.setHoverTime(0);
            hoveringPlayer.setHoverHeight(player.getY());
            hoveringPlayer.setJumpedFromBlock(true);
            hoveringPlayer.setNormalHoverDeadline(NO_NORMAL_HOVER_DEADLINE);
            return;
        }

        // Damage knockback can launch a grounded player without firing LivingJumpEvent. Mark it
        // as a non-ledge airborne origin and preserve that mark through the complete arc.
        boolean recentlyHurt = player.hurtTime > 0;
        if (recentlyHurt) {
            hoveringPlayer.setJumpedFromBlock(true);
            hoveringPlayer.setBounceHovering(false);
            hoveringPlayer.setStickyBouncePending(false);
            if (!player.onGround()) {
                hoveringPlayer.setHovering(false);
                hoveringPlayer.setHazardHovering(false);
                hoveringPlayer.setHoverTime(MAX_HOVER_TICKS);
                hoveringPlayer.setHoverHeight(player.getY());
                return;
            }
        }

        // Slime gets one deliberately small rebound. At its apex that phase becomes the normal
        // timed hover; this keeps vanilla slime from inserting additional full-strength bounces.
        if (hoveringPlayer.isStickyBouncePending()) {
            hoveringPlayer.setWasOnStickyHoverBlock(true);
            if (player.getDeltaMovement().y <= 0.03D) {
                hoveringPlayer.setStickyBouncePending(false);
                beginBounceHover(player, hoveringPlayer);
            }
            return;
        }

        // Honey begins its stationary hover immediately. Slime first passes through the small
        // rebound phase above.
        if (!hoveringPlayer.isBounceHovering()
                && enteredStickyHoverBlock
                && (!hoveringPlayer.jumpedFromBlock() || slimeReboundingFromLanding)
                && !hoveringPlayer.isHovering()
                && !hoveringPlayer.isHazardHovering()) {
            if (slimeReboundingFromLanding) {
                beginSmallSlimeBounce(player, hoveringPlayer);
            } else {
                // Merely walking onto slime should not raise the platform. Begin at the real
                // surface height; the one-time small rebound is reserved for an actual landing.
                beginBounceHover(player, hoveringPlayer);
            }
            hoveringPlayer.setWasOnStickyHoverBlock(true);
            return;
        }
        if (hoveringPlayer.isBounceHovering()) {
            continueBounceHover(player, hoveringPlayer);
            return;
        }
        if (tryConvertBlockBounceToHover(player, hoveringPlayer)) {
            return;
        }

        // Once damage terrain has triggered a hover, run that timer independently of subsequent
        // ground/contact checks. This prevents alternating server/client landing decisions from
        // clearing and restarting the platform every tick.
        if (hoveringPlayer.isHazardHovering()) {
            // Keep the hazard flag as an exhausted-cycle marker, but do not pin or otherwise move
            // the player after its platform has ended. Safe ground clears this lockout.
            if (!hoveringPlayer.isHovering()
                    && hoveringPlayer.getHoverTime() >= MAX_HOVER_TICKS) {
                if (hasSafeWalkableGround(player)) {
                    resetHover(player, hoveringPlayer, true);
                }
                return;
            }

            if (tryStepOutOfHazard(player, hoveringPlayer)) {
                resetHover(player, hoveringPlayer, true);
                return;
            }

            if (hoveringPlayer.getHoverTime() > 1
                    && !isStandingOnHoverableSurface(player)
                    && hasSafeWalkableGround(player)) {
                resetHover(player, hoveringPlayer, true);
                return;
            }

            if (hoveringPlayer.getHoverTime() < MAX_HOVER_TICKS) {
                continueHazardHover(player, hoveringPlayer);
            } else {
                expireHazardHover(hoveringPlayer);
            }
            return;
        }

        // A hazard hover begins only after the player actually reaches that block's surface.
        // Recessed hazards such as spikes therefore receive normal downward movement first.
        if (wearingHoverBoots && player.onGround() && isStandingOnHoverableSurface(player)) {
            if (!hoveringPlayer.isHazardHovering()) {
                beginHazardHover(player, hoveringPlayer);
            }

            if (hoveringPlayer.getHoverTime() < MAX_HOVER_TICKS) {
                continueHazardHover(player, hoveringPlayer);
            } else {
                // Do not recharge while the player remains on the same hazard. Once the hover
                // expires, the boots stop protecting them until they step onto safe ground.
                expireHazardHover(hoveringPlayer);
            }
            return;
        }

        // Soul sand, mud, paths, lower slabs, and similar collision shapes can complete their
        // shallow drop in one tick. Preserve an active hover above that lower surface, or begin a
        // fresh hover from the height of the full block the player just left.
        // Continue through the normal hover branch after restoring/starting so the shared timer
        // still advances; returning here would let a shallow surface refresh the hover forever.
        restoreHoverAbovePartialSurface(player, hoveringPlayer);
        tryBeginPartialSurfaceHover(player, hoveringPlayer);

        // Minecraft may preserve onGround for a tick after leaving a partial-height block such as
        // a campfire. Require collision directly below the player's feet before clearing the
        // expired-hover lockout, otherwise walking off can incorrectly start a fresh hover cycle.
        // A jump can land with only the edge of the player's footprint supported. The center-only
        // probe used for ordinary hover activation misses that valid landing and leaves the
        // jump-origin lock set, preventing the next genuine ledge departure from hovering.
        if (player.onGround() && hasAnySafeWalkableGround(player)) {
            // Do not clear a knockback-origin marker on the same grounded tick that applied the
            // impulse. It will clear after the player finishes the arc and lands again.
            hoveringPlayer.setNormalHoverDeadline(NO_NORMAL_HOVER_DEADLINE);
            resetHover(player, hoveringPlayer, !recentlyHurt);
            return;
        }

        // Once hovering has started, finding a floor immediately below means the player has
        // returned to land. Stop pinning them so vanilla can complete the landing this tick.
        if (hoveringPlayer.isHovering() && hasWalkableGround(player)) {
            hoveringPlayer.setHovering(false);
            hoveringPlayer.setHoverTime(MAX_HOVER_TICKS);
            return;
        }

        boolean movementRequirementMet = disableSprintingChecks || player.isSprinting();
        if (!movementRequirementMet) {
            hoveringPlayer.setHovering(false);
            hoveringPlayer.setHoverTime(MAX_HOVER_TICKS);
            return;
        }

        // Preserve the original activation rule: walking off a ledge can hover, jumping cannot.
        // Requiring no nearby support and no upward movement prevents slab/stair auto-steps from
        // briefly looking like a ledge departure.
        boolean movingUpward = player.getDeltaMovement().y > 0.001D
                || player.getY() > player.yOld + 0.001D;
        if (movingUpward) {
            if (!hoveringPlayer.isHovering()) {
                hoveringPlayer.setHoverHeight(player.getY());
                return;
            }

            // LivingJumpEvent marks genuine player jumps before this check. Do not treat the
            // hover's own position correction (common at slab edges) as a new upward launch:
            // resetting here used to return the timer to zero and could create an infinite hover.
            if (hoveringPlayer.jumpedFromBlock()) {
                hoveringPlayer.setHovering(false);
                hoveringPlayer.setHoverTime(MAX_HOVER_TICKS);
                return;
            }
        }

        if (!hasHoverActivationGround(player)
                && !hoveringPlayer.jumpedFromBlock()) {
            int hoverDeadline = hoveringPlayer.getNormalHoverDeadline();
            if (hoverDeadline == NO_NORMAL_HOVER_DEADLINE) {
                hoverDeadline = player.tickCount + MAX_HOVER_TICKS;
                hoveringPlayer.setNormalHoverDeadline(hoverDeadline);
            }

            // This deadline is independent of hoverTime. Partial-block collision can briefly
            // disturb the ordinary counter, but it can never extend the complete airborne cycle.
            if (player.tickCount >= hoverDeadline) {
                hoveringPlayer.setHovering(false);
                hoveringPlayer.setHoverTime(MAX_HOVER_TICKS);
                hoveringPlayer.setJumpedFromBlock(true);
                return;
            }

            int previousHoverTime = hoveringPlayer.increaseHoverTime();
            double hoverHeight = hoveringPlayer.getHoverHeight();

            // Slabs and stairs can briefly remove the center support probe while vanilla steps
            // the player upward. A real ledge departure has already moved the player's feet below
            // their last grounded height. Waiting for that physical drop prevents the hover
            // platform from flashing during the auto-step. Water gaps are recovered separately
            // before the fluid reset, after the real downward movement has already happened.
            if (!hoveringPlayer.isHovering()
                    && player.getY() >= hoverHeight - 0.01D) {
                hoveringPlayer.setHovering(false);
                // Keep an exhausted timer exhausted while the player is still airborne. Only a
                // not-yet-started candidate may be re-armed; otherwise the hover could recharge
                // forever at the stored ledge height.
                if (previousHoverTime < MAX_HOVER_TICKS) {
                    hoveringPlayer.setHoverTime(0);
                }
                return;
            }

            if (previousHoverTime < MAX_HOVER_TICKS) {
                hoveringPlayer.setHovering(true);
                double motionY = player.getDeltaMovement().y;

                if (motionY < 0.0D && player.getY() <= hoveringPlayer.getHoverHeight()) {
                    player.setDeltaMovement(player.getDeltaMovement().x, 0.0D, player.getDeltaMovement().z);
                    // Preserve the distance accumulated before the boots caught the player.
                    // Hovering pauses the fall; it must not turn an eventual damaging landing
                    // into a zero-distance fall.

                    Vec3 movement = player.getDeltaMovement();
                    player.setDeltaMovement(movement.x, movement.y * -0.1D, movement.z);

                    if (player.tickCount % 2 == 0) {
                        player.playSound(SoundInit.HOVER_BOOTS.get(), 1.0F, 1.0F);
                    }
                }

                // Use the height stored on the last grounded tick. Replacing it here causes the
                // small initial drop that made short gaps difficult to cross.
                if (player.getY() < hoverHeight) {
                    player.setBoundingBox(player.getBoundingBox().move(0.0D, hoverHeight - player.getY(), 0.0D));
                    player.setPos(player.getX(), hoverHeight, player.getZ());
                }
            } else {
                hoveringPlayer.setHovering(false);
            }
        }
    }

    /**
     * Damaging terrain starts the boots before its damage is applied. Protection is deliberately
     * tied to the active hover state instead of merely wearing the boots.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerAttacked(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof Player player)
                || player.level().isClientSide
                || !isHoverBlockDamage(event.getSource())
                || !isWearingHoverBoots(player)) {
            return;
        }

        IHoveringEntity hoveringPlayer = (IHoveringEntity) player;
        boolean canStartFreshHazardHover = !hoveringPlayer.isHazardHovering()
                || hoveringPlayer.getHoverTime() < MAX_HOVER_TICKS;
        if (tryActivateHazardProtection(player)
                || (canStartFreshHazardHover && isApproachingHoverableTop(player))
                || hasHazardTopEdgeGrace(player, hoveringPlayer)) {
            event.setCanceled(true);
        }
    }

    /** Final damage-stage safeguard for damage handlers from blocks or other mods. */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onHoveringPlayerHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player
                && isHoverBlockDamage(event.getSource())
                && isWearingHoverBoots(player)
                && hasActiveHazardProtection(player, (IHoveringEntity) player)) {
            event.setAmount(0.0F);
        }
    }

    /** Last damage stage: active hovering must not leak terrain damage through another handler. */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHoveringPlayerDamaged(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player
                && isHoverBlockDamage(event.getSource())
                && isWearingHoverBoots(player)
                && hasActiveHazardProtection(player, (IHoveringEntity) player)) {
            event.setAmount(0.0F);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void updateHoverStepHeight(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Player player = event.player;
        IHoveringEntity hoveringPlayer = (IHoveringEntity) player;
        setHoverStepHeight(player, shouldApplyHoverStep(player, hoveringPlayer));
    }

    private static boolean isWearingHoverBoots(Player player) {
        return player.getItemBySlot(EquipmentSlot.FEET).is(ItemInit.HOVER_BOOTS.get());
    }

    /**
     * Activates one fresh hazard-hover cycle on the first damaging-block contact. Repeated
     * contact cannot reset the timer, so merely wearing the boots never grants permanent immunity.
     */
    public static boolean tryActivateHazardProtection(Player player) {
        if (!isWearingHoverBoots(player) || !isStandingOnHoverableSurface(player)) {
            return false;
        }

        IHoveringEntity hoveringPlayer = (IHoveringEntity) player;
        if (hoveringPlayer.isHazardHovering()
                && !hoveringPlayer.isHovering()
                && hoveringPlayer.getHoverTime() >= MAX_HOVER_TICKS) {
            return false;
        }

        if (hoveringPlayer.isHovering() && !hoveringPlayer.isHazardHovering()) {
            return hoveringPlayer.getHoverTime() < MAX_HOVER_TICKS;
        }

        if (!hoveringPlayer.isHazardHovering()) {
            beginHazardHover(player, hoveringPlayer);
        }

        return hoveringPlayer.isHovering() && hoveringPlayer.getHoverTime() < MAX_HOVER_TICKS;
    }

    /**
     * Spikes report an inside-block contact at their upper block boundary. While airborne, that
     * contact is neither a landing nor a reason to deal damage. Actual grounded contact starts
     * the fresh hazard hover; an expired hazard hover permits normal spike damage.
     */
    public static boolean handleSpikeContact(Player player, BlockPos spikePos) {
        if (!isWearingHoverBoots(player)) {
            return false;
        }

        IHoveringEntity hoveringPlayer = (IHoveringEntity) player;
        if (hoveringPlayer.isHovering()) {
            // A ledge hover crossing above a spike pit remains protected, but touching the side
            // of a spike block at the player's own height is not treated as standing on it.
            return isTopContactWithBlock(player, spikePos)
                    || isHoveringDirectlyAboveBlock(player, spikePos);
        }

        if (!isTopContactWithBlock(player, spikePos)) {
            return false;
        }

        if (!player.onGround()) {
            // The block can report contact on the exact landing boundary one tick before vanilla
            // marks the player grounded. Ignore that early contact without starting the hover.
            return true;
        }

        return tryActivateHazardProtection(player);
    }

    private static boolean isHoverBlockDamage(DamageSource source) {
        // Keep the data tag extensible for modded hazards, with explicit vanilla checks so the
        // core behavior remains reliable even if a world/datapack has not reloaded the new tag.
        return source.is(TagInit.HOVER_BOOTS_BLOCK_DAMAGE)
                || source.is(DamageTypes.CACTUS)
                || source.is(DamageTypes.HOT_FLOOR)
                || source.is(DamageTypes.SWEET_BERRY_BUSH)
                || source.is(DamageTypes.IN_FIRE)
                || source.is(DamageTypes.LAVA)
                || source.is(DamageTypes.STALAGMITE)
                || source.is(DamageTypes.FREEZE);
    }

    private static void setHoverStepHeight(Player player, boolean enabled) {
        AttributeInstance stepHeight = player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
        if (stepHeight == null) {
            return;
        }

        AttributeModifier currentModifier = stepHeight.getModifier(HOVER_STEP_HEIGHT_MODIFIER_ID);
        if (enabled && currentModifier == null) {
            stepHeight.addTransientModifier(HOVER_STEP_HEIGHT_MODIFIER);
        } else if (!enabled && currentModifier != null) {
            stepHeight.removeModifier(currentModifier);
        }
    }

    private static boolean shouldApplyHoverStep(Player player, IHoveringEntity hoveringPlayer) {
        if (!isWearingHoverBoots(player) || !hoveringPlayer.isHovering()) {
            return false;
        }

        Vec3 intendedMotion = getIntendedHorizontalMotion(player);
        if (intendedMotion.lengthSqr() < 1.0E-5D) {
            return !hoveringPlayer.isHazardHovering();
        }

        Vec3 direction = intendedMotion.normalize();
        double aheadX = player.getX() + direction.x * 0.65D;
        double aheadZ = player.getZ() + direction.z * 0.65D;
        double feetY = player.getBoundingBox().minY;
        BlockPos aheadAtFeet = BlockPos.containing(aheadX, feetY + 0.05D, aheadZ);
        BlockPos aheadBelowFeet = BlockPos.containing(aheadX, feetY - 0.05D, aheadZ);

        // Never use the added step height to climb onto another damage block. From a recessed
        // hazard, however, it may climb toward an ordinary safe block so the player is not trapped.
        boolean damagingTarget = isActiveHoverHazard(player.level().getBlockState(aheadAtFeet))
                || isActiveHoverHazard(player.level().getBlockState(aheadBelowFeet));
        return !damagingTarget;
    }

    /**
     * Uses movement input before velocity because collision with the ledge reduces actual motion
     * to zero. Keeping the requested direction lets the step modifier remain active while the
     * player is pressing out of a recessed hazard. Velocity remains useful for remote players and
     * other movement sources that do not populate the input fields.
     */
    private static Vec3 getIntendedHorizontalMotion(Player player) {
        double yaw = Math.toRadians(player.getYRot());
        double sinYaw = Math.sin(yaw);
        double cosYaw = Math.cos(yaw);
        Vec3 inputMotion = new Vec3(
                player.xxa * cosYaw - player.zza * sinYaw,
                0.0D,
                player.zza * cosYaw + player.xxa * sinYaw
        );
        if (inputMotion.lengthSqr() >= 1.0E-5D) {
            return inputMotion;
        }

        return player.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D);
    }

    /**
     * Vanilla stepping can fail while the hover is holding the player at a partial block height.
     * When they are actively pushing against a safe ledge, perform the same collision-checked
     * vertical placement without a jump impulse. This is intentionally limited to escaping an
     * active hazard hover and can never place the player onto another tagged damage block.
     */
    private static boolean tryStepOutOfHazard(Player player, IHoveringEntity hoveringPlayer) {
        if (!hoveringPlayer.isHovering()
                || !hoveringPlayer.isHazardHovering()
                || !player.horizontalCollision) {
            return false;
        }

        Vec3 intendedMotion = getIntendedHorizontalMotion(player);
        if (intendedMotion.lengthSqr() < 1.0E-5D) {
            return false;
        }

        Vec3 direction = intendedMotion.normalize();
        AABB bounds = player.getBoundingBox();
        double probeDistance = player.getBbWidth() * 0.5D + 0.20D;
        double aheadX = player.getX() + direction.x * probeDistance;
        double aheadZ = player.getZ() + direction.z * probeDistance;
        BlockPos ledgePos = BlockPos.containing(aheadX, bounds.minY + 0.05D, aheadZ);
        var ledgeState = player.level().getBlockState(ledgePos);

        if (isActiveHoverHazard(ledgeState)) {
            return false;
        }

        VoxelShape ledgeCollision = ledgeState.getCollisionShape(player.level(), ledgePos);
        if (ledgeCollision.isEmpty()) {
            return false;
        }

        double ledgeTop = ledgePos.getY()
                + ledgeCollision.max(net.minecraft.core.Direction.Axis.Y);
        double rise = ledgeTop - bounds.minY;
        if (rise <= 0.01D || rise > 1.01D) {
            return false;
        }

        // Move slightly over the lip as part of the step. This avoids immediately colliding with
        // the same vertical face again while remaining much smaller than normal walking motion.
        double nudge = 0.12D;
        double moveX = direction.x * nudge;
        double moveZ = direction.z * nudge;
        AABB steppedBounds = bounds.move(moveX, rise, moveZ);
        if (!player.level().noCollision(player, steppedBounds)) {
            return false;
        }

        player.setPos(player.getX() + moveX, ledgeTop, player.getZ() + moveZ);
        player.setDeltaMovement(player.getDeltaMovement().x, 0.0D, player.getDeltaMovement().z);
        player.setOnGround(true);
        return true;
    }

    private static boolean hasWalkableGround(Player player) {
        AABB feetProbe = new AABB(
                player.getX() - 0.05D, player.getBoundingBox().minY - LANDING_CHECK_DEPTH, player.getZ() - 0.05D,
                player.getX() + 0.05D, player.getBoundingBox().minY + 0.01D, player.getZ() + 0.05D);
        return !player.level().noCollision(player, feetProbe);
    }

    /**
     * Detects a genuine landing anywhere beneath the player's footprint. This is intentionally
     * used only to clear the prior airborne/jump state; new hover activation remains center-based
     * so neighboring blocks cannot suppress diagonal gap hovering.
     */
    private static boolean hasAnySafeWalkableGround(Player player) {
        AABB bounds = player.getBoundingBox();
        AABB feetProbe = new AABB(
                bounds.minX + 0.001D,
                bounds.minY - LANDING_CHECK_DEPTH,
                bounds.minZ + 0.001D,
                bounds.maxX - 0.001D,
                bounds.minY + 0.01D,
                bounds.maxZ - 0.001D);
        return !player.level().noCollision(player, feetProbe)
                && !hasHoverableTopInRange(player, -LANDING_CHECK_DEPTH,
                LANDING_CHECK_DEPTH, 0.0D);
    }

    /**
     * Uses an almost point-sized center probe only when deciding whether a new ledge hover may
     * begin. The wider landing probe intentionally remains unchanged: at diagonal one-block gaps,
     * its corner can overlap a neighboring block after the player's center has already left the
     * path and incorrectly suppress activation.
     */
    private static boolean hasHoverActivationGround(Player player) {
        double centerProbeRadius = 0.005D;
        AABB feetProbe = new AABB(
                player.getX() - centerProbeRadius,
                player.getBoundingBox().minY - LANDING_CHECK_DEPTH,
                player.getZ() - centerProbeRadius,
                player.getX() + centerProbeRadius,
                player.getBoundingBox().minY + 0.01D,
                player.getZ() + centerProbeRadius);
        return !player.level().noCollision(player, feetProbe);
    }

    /**
     * Distinguishes an actual open gap from the transient loss of the feet probe that occurs while
     * vanilla moves onto a slab, stair, or another partial collision shape. Only the block directly
     * under the player's center at the stored ledge height is considered, so neighboring blocks at
     * a diagonal corner cannot delay activation.
     */
    private static boolean isCenterOverCollisionEmptyGap(Player player, double hoverHeight) {
        BlockPos gapPos = BlockPos.containing(
                player.getX(),
                hoverHeight - 0.01D,
                player.getZ());
        BlockState gapState = player.level().getBlockState(gapPos);
        return gapState.getCollisionShape(player.level(), gapPos).isEmpty();
    }

    /**
     * Recognizes only the first shallow water contact caused by walking off dry ground. A prior
     * water tick, a jump, knockback, creative flight, or a deeper fall all fail this check, so this
     * cannot revive hovering while swimming or when leaving the water.
     */
    private static boolean shouldRecoverWaterGapEntry(Player player, IHoveringEntity hoveringPlayer) {
        if (!player.isInWaterOrBubble()
                || hoveringPlayer.jumpedFromBlock()
                || hoveringPlayer.isHovering()
                || player.hurtTime > 0
                || player.onGround()
                || player.getAbilities().flying
                || player.isFallFlying()
                || player.isPassenger()) {
            return false;
        }

        double storedHeight = hoveringPlayer.getHoverHeight();
        double dropFromLedge = storedHeight - player.getY();
        if (dropFromLedge <= 0.001D || dropFromLedge > 0.20D) {
            return false;
        }

        Vec3 movement = player.getDeltaMovement();
        return movement.horizontalDistanceSqr() > 0.0001D
                && isCenterOverCollisionEmptyGap(player, storedHeight);
    }

    private static boolean hasSafeWalkableGround(Player player) {
        return hasWalkableGround(player)
                && !hasHoverableTopInRange(player, -LANDING_CHECK_DEPTH,
                LANDING_CHECK_DEPTH, 0.0D);
    }

    /**
     * Keeps a normal ledge hover at its stored height when vanilla lands the player on a shallow
     * collision shape between ticks. Without this, very small drops such as mud can end the hover
     * before its timer has elapsed.
     */
    private static boolean restoreHoverAbovePartialSurface(Player player,
                                                            IHoveringEntity hoveringPlayer) {
        if (!hoveringPlayer.isHovering()
                || hoveringPlayer.isHazardHovering()
                || !player.onGround()
                || !isStandingOnPartialHeightSurface(player)
                || hoveringPlayer.getHoverHeight() <= player.getY() + 0.01D) {
            return false;
        }

        movePlayerToHoverHeight(player, hoveringPlayer.getHoverHeight());
        return true;
    }

    /** Starts a normal hover when a walkable surface's real collision top is below one block. */
    private static boolean tryBeginPartialSurfaceHover(Player player,
                                                       IHoveringEntity hoveringPlayer) {
        if (!player.onGround()
                || hoveringPlayer.isHovering()
                || hoveringPlayer.isHazardHovering()
                || hoveringPlayer.jumpedFromBlock()
                || hoveringPlayer.getHoverTime() >= MAX_HOVER_TICKS
                || !isStandingOnPartialHeightSurface(player)) {
            return false;
        }

        double drop = hoveringPlayer.getHoverHeight() - player.getY();
        if (drop <= 0.01D || drop >= 1.0D) {
            return false;
        }

        hoveringPlayer.setHovering(true);
        hoveringPlayer.setHoverTime(0);
        movePlayerToHoverHeight(player, hoveringPlayer.getHoverHeight());
        return true;
    }

    private static void movePlayerToHoverHeight(Player player, double hoverHeight) {
        player.setDeltaMovement(player.getDeltaMovement().x, 0.0D,
                player.getDeltaMovement().z);
        player.setPos(player.getX(), hoverHeight, player.getZ());
        // The shallow surface was reached and corrected within one game tick. Keep both render
        // interpolation positions at the platform height so that correction is not displayed as
        // a brief downward/upward jitter on the first soul-sand or mud step.
        player.yOld = hoverHeight;
        player.yo = hoverHeight;
        player.setOnGround(false);
    }

    /**
     * Checks the actual supporting collision box rather than a hardcoded block list. This covers
     * vanilla and modded blocks such as soul sand and mud. An unlit tagged block is deliberately
     * ignored so an extinguished campfire does not regain damage-hover behavior through this path.
     */
    private static boolean isStandingOnPartialHeightSurface(Player player) {
        AABB bounds = player.getBoundingBox();
        double feetY = bounds.minY;
        int minX = (int) Math.floor(bounds.minX + 0.001D);
        int maxX = (int) Math.floor(bounds.maxX - 0.001D);
        int minZ = (int) Math.floor(bounds.minZ + 0.001D);
        int maxZ = (int) Math.floor(bounds.maxZ - 0.001D);
        int blockY = (int) Math.floor(feetY - 0.01D);

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                BlockPos position = new BlockPos(x, blockY, z);
                BlockState state = player.level().getBlockState(position);
                if (state.hasProperty(BlockStateProperties.LIT)
                        && !state.getValue(BlockStateProperties.LIT)) {
                    continue;
                }

                for (AABB localBox : state.getCollisionShape(player.level(), position).toAabbs()) {
                    if (localBox.maxY >= 0.999D) {
                        continue;
                    }

                    AABB worldBox = localBox.move(position.getX(), position.getY(), position.getZ());
                    if (Math.abs(feetY - worldBox.maxY) > HAZARD_TOP_TOLERANCE) {
                        continue;
                    }

                    double overlapX = Math.min(bounds.maxX, worldBox.maxX)
                            - Math.max(bounds.minX, worldBox.minX);
                    double overlapZ = Math.min(bounds.maxZ, worldBox.maxZ)
                            - Math.max(bounds.minZ, worldBox.minZ);
                    if (overlapX > HAZARD_HORIZONTAL_OVERLAP
                            && overlapZ > HAZARD_HORIZONTAL_OVERLAP) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isStandingOnHoverableSurface(Player player) {
        AABB bounds = player.getBoundingBox();
        double feetY = bounds.minY;
        int minX = (int) Math.floor(bounds.minX + 0.001D);
        int maxX = (int) Math.floor(bounds.maxX - 0.001D);
        int minZ = (int) Math.floor(bounds.minZ + 0.001D);
        int maxZ = (int) Math.floor(bounds.maxZ - 0.001D);
        int blockY = (int) Math.floor(feetY - 0.01D);

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                BlockPos position = new BlockPos(x, blockY, z);
                var state = player.level().getBlockState(position);
                if (!isActiveHoverHazard(state)) {
                    continue;
                }

                if (isTopContactWithBlock(player, position)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Some damaging blocks report their contact immediately before vanilla finishes snapping a
     * falling player onto the collision surface. Suppress only that early landing hit; the hover
     * itself still waits for the exact top contact and grounded state in onPlayerTick().
     */
    private static boolean isApproachingHoverableTop(Player player) {
        return player.getDeltaMovement().y <= 0.0D
                // Campfire flames can report contact before the player's feet reach the short
                // collision shape. This wider descending-only guard prevents that one early hit;
                // the visible hover still begins only after real top contact is established.
                && hasHoverableTopInRange(player, -0.08D, 0.75D, 0.0D);
    }

    private static boolean hasActiveHazardProtection(Player player, IHoveringEntity hoveringPlayer) {
        return hoveringPlayer.isHovering()
                && (isStandingOnHoverableSurface(player)
                || hasHazardTopEdgeGrace(player, hoveringPlayer));
    }

    /**
     * Keeps protection for the tiny boundary interval when walking off a verified hazard top.
     * This grace is available only to an already-active hazard hover, so approaching a cactus from
     * the side still hurts normally.
     */
    private static boolean hasHazardTopEdgeGrace(Player player, IHoveringEntity hoveringPlayer) {
        return hoveringPlayer.isHovering()
                && hoveringPlayer.isHazardHovering()
                && hasHoverableTopInRange(player, -0.03D, 0.06D, 0.12D);
    }

    /**
     * Finds a tagged block top using each collision box's real height and horizontal bounds.
     * verticalMin/verticalMax describe feetY - surfaceY; horizontalMargin is used only for the
     * short leave-edge grace and never for starting a hover.
     */
    private static boolean hasHoverableTopInRange(Player player, double verticalMin,
                                                   double verticalMax, double horizontalMargin) {
        AABB playerBounds = player.getBoundingBox();
        double feetY = playerBounds.minY;
        int minX = (int) Math.floor(playerBounds.minX - horizontalMargin);
        int maxX = (int) Math.floor(playerBounds.maxX + horizontalMargin);
        int minZ = (int) Math.floor(playerBounds.minZ - horizontalMargin);
        int maxZ = (int) Math.floor(playerBounds.maxZ + horizontalMargin);
        int minY = (int) Math.floor(feetY) - 1;
        int maxY = (int) Math.floor(feetY);
        AABB horizontalProbe = playerBounds.inflate(horizontalMargin, 0.0D, horizontalMargin);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos position = new BlockPos(x, y, z);
                    var state = player.level().getBlockState(position);
                    if (!isActiveHoverHazard(state)) {
                        continue;
                    }

                    VoxelShape collision = state.getCollisionShape(player.level(), position);
                    for (AABB localBox : collision.toAabbs()) {
                        AABB worldBox = localBox.move(position.getX(), position.getY(), position.getZ());
                        double verticalOffset = feetY - worldBox.maxY;
                        if (verticalOffset < verticalMin || verticalOffset > verticalMax) {
                            continue;
                        }

                        double overlapX = Math.min(horizontalProbe.maxX, worldBox.maxX)
                                - Math.max(horizontalProbe.minX, worldBox.minX);
                        double overlapZ = Math.min(horizontalProbe.maxZ, worldBox.maxZ)
                                - Math.max(horizontalProbe.minZ, worldBox.minZ);
                        if (overlapX > HAZARD_HORIZONTAL_OVERLAP
                                && overlapZ > HAZARD_HORIZONTAL_OVERLAP) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Tests the real collision boxes instead of treating the block as a full cube. A valid top
     * contact requires the player's feet to match a collision box's upper face and overlap it in
     * both horizontal axes. Merely touching a cactus or another hazard from the side fails.
     */
    private static boolean isTopContactWithBlock(Player player, BlockPos position) {
        var state = player.level().getBlockState(position);
        if (!isActiveHoverHazard(state)) {
            return false;
        }

        AABB playerBounds = player.getBoundingBox();
        double feetY = playerBounds.minY;
        VoxelShape collision = state.getCollisionShape(player.level(), position);
        if (collision.isEmpty()) {
            return false;
        }

        for (AABB localBox : collision.toAabbs()) {
            AABB worldBox = localBox.move(position.getX(), position.getY(), position.getZ());
            if (Math.abs(feetY - worldBox.maxY) > HAZARD_TOP_TOLERANCE) {
                continue;
            }

            double overlapX = Math.min(playerBounds.maxX, worldBox.maxX)
                    - Math.max(playerBounds.minX, worldBox.minX);
            double overlapZ = Math.min(playerBounds.maxZ, worldBox.maxZ)
                    - Math.max(playerBounds.minZ, worldBox.minZ);
            if (overlapX > HAZARD_HORIZONTAL_OVERLAP
                    && overlapZ > HAZARD_HORIZONTAL_OVERLAP) {
                return true;
            }
        }
        return false;
    }

    /**
     * A tagged block is hazardous only while its current state can actually hurt the player.
     * This makes extinguished campfires behave like ordinary short blocks while preserving the
     * tag-based support for modded hazards that do not expose a vanilla lit property.
     */
    private static boolean isActiveHoverHazard(BlockState state) {
        return state.is(TagInit.HOVER_BOOTS_HOVERABLE)
                && (!state.hasProperty(BlockStateProperties.LIT)
                || state.getValue(BlockStateProperties.LIT));
    }

    /** Keeps an already-active ledge hover safe while it passes over recessed spikes. */
    private static boolean isHoveringDirectlyAboveBlock(Player player, BlockPos position) {
        AABB playerBounds = player.getBoundingBox();
        if (position.getY() >= playerBounds.minY) {
            return false;
        }

        VoxelShape collision = player.level().getBlockState(position)
                .getCollisionShape(player.level(), position);
        for (AABB localBox : collision.toAabbs()) {
            AABB worldBox = localBox.move(position.getX(), position.getY(), position.getZ());
            double overlapX = Math.min(playerBounds.maxX, worldBox.maxX)
                    - Math.max(playerBounds.minX, worldBox.minX);
            double overlapZ = Math.min(playerBounds.maxZ, worldBox.maxZ)
                    - Math.max(playerBounds.minZ, worldBox.minZ);
            if (overlapX > HAZARD_HORIZONTAL_OVERLAP
                    && overlapZ > HAZARD_HORIZONTAL_OVERLAP) {
                return true;
            }
        }
        return false;
    }

    private static void beginHazardHover(Player player, IHoveringEntity hoveringPlayer) {
        hoveringPlayer.setBounceHovering(false);
        hoveringPlayer.setStickyBouncePending(false);
        hoveringPlayer.setHazardHovering(true);
        hoveringPlayer.setHoverTime(0);
        // Hazard hovering begins at the surface where the player actually landed. It must not
        // preserve the previous ledge height, otherwise recessed spikes behave like a bridge.
        hoveringPlayer.setHoverHeight(player.getY());
        hoveringPlayer.setHovering(true);
        hoveringPlayer.setJumpedFromBlock(false);
        player.setDeltaMovement(player.getDeltaMovement().x, 0.0D, player.getDeltaMovement().z);
        // Do not clear fallDistance here. Magma and other blocks may start protection from their
        // contact-damage callback before vanilla processes the landing. Hover movement preserves
        // that value until Minecraft processes the eventual real landing.
    }

    private static void continueHazardHover(Player player, IHoveringEntity hoveringPlayer) {
        hoveringPlayer.setHovering(true);
        hoveringPlayer.increaseHoverTime();
        player.setDeltaMovement(player.getDeltaMovement().x, 0.0D, player.getDeltaMovement().z);

        double hoverHeight = hoveringPlayer.getHoverHeight();
        if (player.getY() < hoverHeight) {
            player.setBoundingBox(player.getBoundingBox().move(0.0D, hoverHeight - player.getY(), 0.0D));
            player.setPos(player.getX(), hoverHeight, player.getZ());
        }

        if (player.tickCount % 2 == 0) {
            player.playSound(SoundInit.HOVER_BOOTS.get(), 1.0F, 1.0F);
        }
    }

    /**
     * Ends the platform permanently for this airborne cycle. jumpedFromBlock prevents the normal
     * ledge-hover branch from taking over, while MAX_HOVER_TICKS prevents damage callbacks from
     * recharging the hazard hover before the player reaches safe ground.
     */
    private static void expireHazardHover(IHoveringEntity hoveringPlayer) {
        hoveringPlayer.setHovering(false);
        hoveringPlayer.setBounceHovering(false);
        hoveringPlayer.setHazardHovering(true);
        hoveringPlayer.setHoverTime(MAX_HOVER_TICKS);
        hoveringPlayer.setJumpedFromBlock(true);
    }

    private static void resetHover(Player player, IHoveringEntity hoveringPlayer, boolean resetJumpState) {
        hoveringPlayer.setHovering(false);
        hoveringPlayer.setHazardHovering(false);
        hoveringPlayer.setBounceHovering(false);
        hoveringPlayer.setStickyBouncePending(false);
        hoveringPlayer.setHoverTime(0);
        // Preserve the exact surface height. Rounding launches the player upward when they
        // walk off partial-height blocks such as beds, slabs, stairs, or modded shapes.
        hoveringPlayer.setHoverHeight(player.getY());
        if (resetJumpState) {
            hoveringPlayer.setJumpedFromBlock(false);
        }
    }

    /** Converts a block-generated upward rebound into a stationary hover at the landing height. */
    private static boolean tryConvertBlockBounceToHover(Player player,
                                                         IHoveringEntity hoveringPlayer) {
        if (hoveringPlayer.isHovering()
                || hoveringPlayer.isHazardHovering()
                || hoveringPlayer.jumpedFromBlock()
                || player.getDeltaMovement().y <= 0.03D
                || !hasWalkableGround(player)) {
            return false;
        }

        beginBounceHover(player, hoveringPlayer);
        return true;
    }

    private static void beginBounceHover(Player player, IHoveringEntity hoveringPlayer) {
        hoveringPlayer.setStickyBouncePending(false);
        hoveringPlayer.setBounceHovering(true);
        // Keep the entry latched through the complete timer. Otherwise landing on slime could
        // finish one converted bounce and immediately begin another surface-hover cycle.
        hoveringPlayer.setWasOnStickyHoverBlock(true);
        hoveringPlayer.setHazardHovering(false);
        hoveringPlayer.setHovering(true);
        hoveringPlayer.setHoverTime(0);
        hoveringPlayer.setHoverHeight(player.getY());
        hoveringPlayer.setJumpedFromBlock(false);
        player.setDeltaMovement(player.getDeltaMovement().x, 0.0D,
                player.getDeltaMovement().z);
        // Honey is 15/16 of a block tall. Keeping its stationary hover grounded lets vanilla's
        // normal step logic climb the remaining 1/16 onto an adjacent full-height block. Slime
        // remains airborne here so its automatic rebound cannot fire during the active hover.
        player.setOnGround(isTouchingHoneyBlockTop(player));
    }

    /** Replaces slime's full rebound with one short hop before the hover platform appears. */
    public static void beginSmallSlimeBounce(Player player, IHoveringEntity hoveringPlayer) {
        hoveringPlayer.setStickyBouncePending(true);
        hoveringPlayer.setBounceHovering(false);
        hoveringPlayer.setHazardHovering(false);
        hoveringPlayer.setHovering(false);
        hoveringPlayer.setHoverTime(0);
        hoveringPlayer.setWasOnStickyHoverBlock(true);
        hoveringPlayer.setJumpedFromBlock(false);
        player.setDeltaMovement(player.getDeltaMovement().x, SMALL_SLIME_BOUNCE_VELOCITY,
                player.getDeltaMovement().z);
        player.setOnGround(false);
    }

    /** True only while the player's feet are supported by the real top of slime or honey. */
    private static boolean isStandingOnStickyHoverBlock(Player player) {
        return player.onGround() && isTouchingStickyHoverBlockTop(player);
    }

    /** Also works during LivingFallEvent, before vanilla has finalized the on-ground flag. */
    private static boolean isTouchingStickyHoverBlockTop(Player player) {
        return isTouchingBlockTop(player, null);
    }

    private static boolean isTouchingHoneyBlockTop(Player player) {
        return isTouchingBlockTop(player, Blocks.HONEY_BLOCK);
    }

    private static boolean isTouchingSlimeBlockTop(Player player) {
        return isTouchingBlockTop(player, Blocks.SLIME_BLOCK);
    }

    private static boolean isTouchingBlockTop(Player player, Block requiredBlock) {
        AABB bounds = player.getBoundingBox();
        double feetY = bounds.minY;
        int minX = (int) Math.floor(bounds.minX + 0.001D);
        int maxX = (int) Math.floor(bounds.maxX - 0.001D);
        int minZ = (int) Math.floor(bounds.minZ + 0.001D);
        int maxZ = (int) Math.floor(bounds.maxZ - 0.001D);
        int blockY = (int) Math.floor(feetY - 0.01D);

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                BlockPos position = new BlockPos(x, blockY, z);
                BlockState state = player.level().getBlockState(position);
                if (requiredBlock != null
                        ? !state.is(requiredBlock)
                        : (!state.is(Blocks.SLIME_BLOCK) && !state.is(Blocks.HONEY_BLOCK))) {
                    continue;
                }

                for (AABB localBox : state.getCollisionShape(player.level(), position).toAabbs()) {
                    AABB worldBox = localBox.move(position.getX(), position.getY(), position.getZ());
                    if (Math.abs(feetY - worldBox.maxY) <= HAZARD_TOP_TOLERANCE) {
                        double overlapX = Math.min(bounds.maxX, worldBox.maxX)
                                - Math.max(bounds.minX, worldBox.minX);
                        double overlapZ = Math.min(bounds.maxZ, worldBox.maxZ)
                                - Math.max(bounds.minZ, worldBox.minZ);
                        if (overlapX > HAZARD_HORIZONTAL_OVERLAP
                                && overlapZ > HAZARD_HORIZONTAL_OVERLAP) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Detects slime/honey just below the feet before exact top contact. This is used only while an
     * exhausted bounce hover is descending, so its lock cannot clear one tick before slime applies
     * another rebound.
     */
    private static boolean hasStickyBlockTopImmediatelyBelow(Player player) {
        AABB bounds = player.getBoundingBox();
        double feetY = bounds.minY;
        int minX = (int) Math.floor(bounds.minX + 0.001D);
        int maxX = (int) Math.floor(bounds.maxX - 0.001D);
        int minZ = (int) Math.floor(bounds.minZ + 0.001D);
        int maxZ = (int) Math.floor(bounds.maxZ - 0.001D);
        int minY = (int) Math.floor(feetY) - 1;
        int maxY = (int) Math.floor(feetY);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos position = new BlockPos(x, y, z);
                    BlockState state = player.level().getBlockState(position);
                    if (!state.is(Blocks.SLIME_BLOCK) && !state.is(Blocks.HONEY_BLOCK)) {
                        continue;
                    }

                    for (AABB localBox : state.getCollisionShape(player.level(), position).toAabbs()) {
                        AABB worldBox = localBox.move(position.getX(), position.getY(), position.getZ());
                        double distanceToTop = feetY - worldBox.maxY;
                        if (distanceToTop < -HAZARD_TOP_TOLERANCE || distanceToTop > 0.12D) {
                            continue;
                        }

                        double overlapX = Math.min(bounds.maxX, worldBox.maxX)
                                - Math.max(bounds.minX, worldBox.minX);
                        double overlapZ = Math.min(bounds.maxZ, worldBox.maxZ)
                                - Math.max(bounds.minZ, worldBox.minZ);
                        if (overlapX > HAZARD_HORIZONTAL_OVERLAP
                                && overlapZ > HAZARD_HORIZONTAL_OVERLAP) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /** Runs independently of ground contact so the bounce block cannot immediately end the hover. */
    private static void continueBounceHover(Player player, IHoveringEntity hoveringPlayer) {
        if (hoveringPlayer.jumpedFromBlock()
                || hoveringPlayer.getHoverTime() >= MAX_HOVER_TICKS) {
            hoveringPlayer.setHovering(false);
            hoveringPlayer.setHoverTime(MAX_HOVER_TICKS);
            hoveringPlayer.setJumpedFromBlock(true);

            // Keep the exhausted marker while descending from the small hop's hover height. Once
            // the player reaches slime, consume its automatic rebound on that same tick and leave
            // them grounded. Clearing this marker in midair would allow a second full bounce.
            if (isTouchingStickyHoverBlockTop(player)) {
                player.setDeltaMovement(player.getDeltaMovement().x, 0.0D,
                        player.getDeltaMovement().z);
                hoveringPlayer.setBounceHovering(true);
                hoveringPlayer.setWasOnStickyHoverBlock(true);
                player.setOnGround(true);
                return;
            }

            if (hasSafeWalkableGround(player) && !hasStickyBlockTopImmediatelyBelow(player)) {
                hoveringPlayer.setBounceHovering(false);
                resetHover(player, hoveringPlayer, true);
            } else {
                hoveringPlayer.setBounceHovering(true);
            }
            return;
        }

        hoveringPlayer.setHovering(true);
        hoveringPlayer.increaseHoverTime();
        player.setDeltaMovement(player.getDeltaMovement().x, 0.0D,
                player.getDeltaMovement().z);
        double hoverHeight = hoveringPlayer.getHoverHeight();
        if (player.getY() < hoverHeight) {
            player.setPos(player.getX(), hoverHeight, player.getZ());
        }
        player.setOnGround(isTouchingHoneyBlockTop(player));

        if (player.tickCount % 2 == 0) {
            player.playSound(SoundInit.HOVER_BOOTS.get(), 1.0F, 1.0F);
        }
    }
}
