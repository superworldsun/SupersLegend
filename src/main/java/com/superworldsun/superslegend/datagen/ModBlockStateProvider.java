package com.superworldsun.superslegend.datagen;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SupersLegendMain.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(BlockInit.DEEPSLATE_MASTER_ORE_BLOCK);

        doorBlockWithRenderType(((DoorBlock) BlockInit.DUNGEON_DOOR.get()), modLoc("block/dungeon_door_bottom"), modLoc("block/dungeon_door_top"),"cutout");
        doorBlockWithRenderType(((DoorBlock) BlockInit.LOCKED_DUNGEON_DOOR.get()), modLoc("block/locked_dungeon_door_bottom"), modLoc("block/locked_dungeon_door_top"),"cutout");
        doorBlockWithRenderType(((DoorBlock) BlockInit.BOSS_DOOR.get()), modLoc("block/boss_door_bottom"), modLoc("block/boss_door_top"),"cutout");
        doorBlockWithRenderType(((DoorBlock) BlockInit.LOCKED_BOSS_DOOR.get()), modLoc("block/locked_boss_door_bottom"), modLoc("block/locked_boss_door_top"),"cutout");
        doorBlockWithRenderType(((DoorBlock) BlockInit.LOCKED_WOODEN_DOOR.get()), modLoc("block/locked_wooden_door_bottom"), modLoc("block/locked_wooden_door_top"),"cutout");
        doorBlockWithRenderType(((DoorBlock) BlockInit.WOODEN_BOSS_DOOR.get()), modLoc("block/wooden_boss_door_bottom"), modLoc("block/wooden_boss_door_top"),"cutout");
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
