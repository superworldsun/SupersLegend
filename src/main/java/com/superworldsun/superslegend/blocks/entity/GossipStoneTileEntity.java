package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import top.theillusivec4.curios.api.CuriosApi;

public class GossipStoneTileEntity extends TileEntity {
	private String message = "";

	public GossipStoneTileEntity() {
		super(TileEntityInit.GOSSIP_STONE.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.putString("message", message);
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		message = compound.getString("message");
		super.load(state, compound);
	}

	public ITextComponent getMessage(PlayerEntity player) {
		boolean hasMaskOfTruth = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_MASKOFTRUTH.get(), player).isPresent();

		if (message.isEmpty() || !hasMaskOfTruth) {
			return new TranslationTextComponent("block.superslegend.gossip_stone_block.silent");
		} else {
			return new StringTextComponent(message);
		}
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static TileEntityType<GossipStoneTileEntity> createType() {
		return Builder.of(GossipStoneTileEntity::new, BlockInit.GOSSIP_STONE_BLOCK.get()).build(null);
	}
}
