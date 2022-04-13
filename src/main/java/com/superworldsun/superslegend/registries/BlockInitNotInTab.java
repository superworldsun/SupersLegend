package com.superworldsun.superslegend.registries;


import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.FluidBlock;
import com.superworldsun.superslegend.blocks.HammeredRustedPegBlock;
import com.superworldsun.superslegend.blocks.HammeredSpikedPegBlock;
import com.superworldsun.superslegend.blocks.HammeredWoodenPegBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInitNotInTab
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SupersLegendMain.MOD_ID);

    //Blocks
    public static final RegistryObject<Block> HAMMERED_WOODEN_PEG_BLOCK = BLOCKS.register("hammered_wooden_peg_block", () -> new HammeredWoodenPegBlock(PropertiesInit.HAMMERED_WOODEN_PEG));
    public static final RegistryObject<Block> HAMMERED_RUSTED_PEG_BLOCK = BLOCKS.register("hammered_rusted_peg_block", () -> new HammeredRustedPegBlock(PropertiesInit.HAMMERED_RUSTED_PEG));
    public static final RegistryObject<Block> HAMMERED_SPIKED_PEG_BLOCK = BLOCKS.register("hammered_spiked_peg_block", () -> new HammeredSpikedPegBlock(PropertiesInit.HAMMERED_SPIKED_PEG));

    //Liquids
    //public static final RegistryObject<FluidBlock> LIQUID_POISON = BLOCKS.register("liquid_poison", () -> new FluidBlock(FluidInit.POISON_SOURCE));
    //public static final RegistryObject<FluidBlock> LIQUID_MUD = BLOCKS.register("liquid_mud", () -> new FluidBlock(FluidInit.MUD_SOURCE));

}
