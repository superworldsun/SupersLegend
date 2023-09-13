package com.superworldsun.superslegend.capability.magic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.api.MagicContainer;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SyncMagicMessage;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.PacketDistributor;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class MagicProvider implements ICapabilitySerializable<CompoundTag> {
	private static final ResourceLocation CAPABILITY_ID = new ResourceLocation(SupersLegendMain.MOD_ID, "magic");
	private static final Capability<MagicContainer> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
	private LazyOptional<MagicContainer> optionalCapability = LazyOptional.of(() -> new PlayerMagicContainer());

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (!(event.getObject() instanceof Player)) return;
		event.addCapability(CAPABILITY_ID, new MagicProvider());
	}

	@SubscribeEvent
	public static void persistThroughDeath(PlayerEvent.Clone event) {
		if (event.getEntity().level().isClientSide) return;
		event.getOriginal().reviveCaps();
		get(event.getEntity()).copyFrom(get(event.getOriginal()));
		event.getOriginal().invalidateCaps();
	}

	@SubscribeEvent
	public static void sync(EntityJoinLevelEvent event) {
		if (!(event.getEntity() instanceof ServerPlayer player)) return;
		sync(player);
	}

	private static void sync(ServerPlayer player) {
		NetworkDispatcher.network_channel.send(PacketDistributor.PLAYER.with(() -> player), new SyncMagicMessage(player));
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		return cap == CAPABILITY ? optionalCapability.cast() : LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		optionalCapability.ifPresent(container -> tag.putFloat("Magic", container.getMagic()));
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		optionalCapability.ifPresent(container -> container.setMagic(tag.getFloat("Magic")));
	}

	@NotNull
	private static MagicContainer get(Player player) {
		return player.getCapability(CAPABILITY).orElseThrow(NullPointerException::new);
	}

	public static float getMagic(Player player) {
		return get(player).getMagic();
	}

	public static void restoreMagic(Player player, float amount) {
		get(player).restoreMagic(amount);
		if (player instanceof ServerPlayer) sync((ServerPlayer) player);
	}

	public static boolean hasMagic(Player player, float amount) {
		if (player.getAbilities().instabuild) return true;
		return get(player).hasMagic(amount);
	}

	public static void spendMagic(Player player, float amount) {
		if (player.getAbilities().instabuild) return;
		get(player).spendMagic(amount);
		if (player instanceof ServerPlayer) sync((ServerPlayer) player);
	}

	public static void setMagic(Player player, float amount) {
		get(player).setMagic(amount);
		if (player instanceof ServerPlayer) sync((ServerPlayer) player);
	}

	public static boolean isFullMagic(Player player) {
		return get(player).getMagic() == get(player).getMaxMagic();
	}
}
