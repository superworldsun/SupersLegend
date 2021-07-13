package com.superworldsun.superslegend.items;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LanzaniteMultiToolItem extends ToolItem {

    //Credit goes to coolsimulations for Forge 1.16 multitool code
    private final float attackDamage;
    private final float attackSpeed;
    private final IItemTier material;
    private final Multimap<Attribute, AttributeModifier> attribute;
    private static final Set<Block> EFFECTIVE_ON;
    protected static final Map<Block, Block> BLOCK_STRIPPING_MAP;
    protected static final Map<Block, BlockState> SHOVEL_LOOKUP;
    protected static final Map<Block, BlockState> HOE_LOOKUP;

    public LanzaniteMultiToolItem(IItemTier material, float damage, float speed, Item.Properties builder) {
        super(damage, speed, material, EFFECTIVE_ON, builder.addToolType(net.minecraftforge.common.ToolType.AXE, material.getLevel()).addToolType(net.minecraftforge.common.ToolType.PICKAXE, material.getLevel()).addToolType(net.minecraftforge.common.ToolType.SHOVEL, material.getLevel()).stacksTo(1));
        this.material = material;
        this.attackSpeed = speed;
        this.attackDamage = damage;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.<Attribute, AttributeModifier>builder();
        attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        attributeBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
        this.attribute = attributeBuilder.build();
    }

    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        Block block = state.getBlock();

        if (block == Blocks.COBWEB)
        {
            return 15.0F;
        }
        else {
            Material material = state.getMaterial();
            return material != Material.WOOD && material != Material.NETHER_WOOD && material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.BAMBOO && material != Material.CORAL && material != Material.LEAVES && material != Material.VEGETABLE && material != Material.METAL && material != Material.HEAVY_METAL && material != Material.STONE ? super.getDestroySpeed(stack, state) : this.speed;
        }
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    public boolean isCorrectToolForDrops(BlockState blockIn) {
        Block block = blockIn.getBlock();
        int i = this.getTier().getLevel();
        if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
            return i >= blockIn.getHarvestLevel();
        }

        if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.AXE) {
            return i >= blockIn.getHarvestLevel();
        }

        if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.SHOVEL) {
            return i >= blockIn.getHarvestLevel();
        }

        Material material = blockIn.getMaterial();
        return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL || block == Blocks.SNOW || block == Blocks.SNOW_BLOCK || block == Blocks.COBWEB;
    }

    /**
     * Called when this item is used when targetting a Block
     */
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = world.getBlockState(blockpos);
        Block blockStrip = BLOCK_STRIPPING_MAP.get(blockstate.getBlock());

        BlockState iblockstate = world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        BlockPos blockBelowBlockPos = new BlockPos(blockpos.getX(), blockpos.getY() - 1, blockpos.getZ());

        BlockState blockStateBelow = world.getBlockState(blockBelowBlockPos);
        Block blockBelow = blockStateBelow.getBlock();
        BlockPos blockAboveBlockPos = blockpos.above();

        BlockPos blockTwiceBelowBlockPos = blockpos.below(2);

        BlockPos blockTwiceAboveBlockPos = blockpos.above(2);

        if (blockStrip != null) {
            PlayerEntity playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isClientSide) {
                world.setBlock(blockpos, (BlockState) blockStrip.defaultBlockState().setValue(RotatedPillarBlock.AXIS, blockstate.getValue(RotatedPillarBlock.AXIS)), 11);
                if (playerentity != null) {
                    context.getItemInHand().hurtAndBreak(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.broadcastBreakEvent(context.getHand());});
                }
            }

            return ActionResultType.SUCCESS;
        }

        if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT)) {
            world.levelEvent((PlayerEntity)null, 1009, blockpos, 0);
            world.setBlock(blockpos, blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false)), 11);
        }

        if(!context.getPlayer().isShiftKeyDown()) {

            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
            PlayerEntity playerentity = context.getPlayer();
            if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
            if(context.getClickedFace() != Direction.DOWN) {
                if (world.isEmptyBlock(blockpos.above())) {
                    setBlockToFarmland(context, blockpos, world);
                }

                if(block instanceof BushBlock) {
                    BlockState iblockstate2 = HOE_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());

                    if(iblockstate2 != null && world.isEmptyBlock(blockAboveBlockPos)) {
                        setBlockToFarmland(context, blockBelowBlockPos, world);
                        if(!playerentity.isCreative())
                            block.playerDestroy(world, playerentity, blockpos, iblockstate, null, context.getItemInHand());
                        world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
                        return ActionResultType.SUCCESS;
                    }
                }

                if(block instanceof DoublePlantBlock) {
                    BlockState iblockstate2_below = HOE_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());
                    BlockState iblockstate2_twice_below = HOE_LOOKUP.get(world.getBlockState(blockTwiceBelowBlockPos).getBlock());

                    if(iblockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER && iblockstate2_below != null && world.isEmptyBlock(blockTwiceAboveBlockPos)) {
                        setBlockToFarmland(context, blockBelowBlockPos, world);
                        block.playerWillDestroy(world, blockpos, iblockstate, playerentity);
                        return ActionResultType.SUCCESS;
                    } else if(iblockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER && iblockstate2_twice_below != null && world.isEmptyBlock(blockAboveBlockPos)) {
                        setBlockToFarmland(context, blockTwiceBelowBlockPos, world);
                        block.playerWillDestroy(world, blockpos, iblockstate, playerentity);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        } else {
            if(context.getClickedFace() != Direction.DOWN) {
                PlayerEntity playerentity = context.getPlayer();

                if (world.getBlockState(blockpos.above()).isAir()) {
                    setBlockToPath(context, blockpos, world);
                }

                if(block instanceof BushBlock) {
                    BlockState iblockstate2 = SHOVEL_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());

                    if(blockBelow == Blocks.GRASS || iblockstate2 != null && world.isEmptyBlock(blockAboveBlockPos)) {
                        setBlockToPath(context, blockBelowBlockPos, world);
                        if(!playerentity.isCreative())
                            block.playerDestroy(world, playerentity, blockpos, iblockstate, null, context.getItemInHand());
                        world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
                        return ActionResultType.SUCCESS;
                    }
                }

                if(block instanceof DoublePlantBlock) {
                    BlockState iblockstate2_below = SHOVEL_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());
                    BlockState iblockstate2_twice_below = SHOVEL_LOOKUP.get(world.getBlockState(blockTwiceBelowBlockPos).getBlock());

                    if(iblockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER && iblockstate2_below != null && world.isEmptyBlock(blockTwiceAboveBlockPos)) {
                        setBlockToPath(context, blockBelowBlockPos, world);
                        block.playerWillDestroy(world, blockpos, iblockstate, playerentity);
                        return ActionResultType.SUCCESS;
                    } else if(iblockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER && iblockstate2_twice_below != null && world.isEmptyBlock(blockAboveBlockPos)) {
                        setBlockToPath(context, blockTwiceBelowBlockPos, world);
                        block.playerWillDestroy(world, blockpos, iblockstate, playerentity);
                        return ActionResultType.SUCCESS;
                    }
                }

            }
        }

        return ActionResultType.PASS;
    }

    protected ActionResultType setBlockToFarmland(ItemUseContext context, BlockPos blockpos, World world) {

        BlockState iblockstate2 = HOE_LOOKUP.get(world.getBlockState(blockpos).getBlock());
        if (iblockstate2 != null) {
            PlayerEntity playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isClientSide) {
                world.setBlock(blockpos, iblockstate2, 11);
                if (playerentity != null) {
                    context.getItemInHand().hurtAndBreak(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.broadcastBreakEvent(context.getHand());});
                }
            }

            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

    protected ActionResultType setBlockToPath(ItemUseContext context, BlockPos blockpos, World world) {

        BlockState iblockstate1 = SHOVEL_LOOKUP.get(world.getBlockState(blockpos).getBlock());
        if (iblockstate1 != null) {
            PlayerEntity playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isClientSide) {
                world.setBlock(blockpos, iblockstate1, 11);
                if (playerentity != null) {
                    context.getItemInHand().hurtAndBreak(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.broadcastBreakEvent(context.getHand());});
                }
            }

            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

    /**
     * Returns the amount of damage this item will deal. One heart of damage is equal to 2 damage points.
     */
    public float getAttackDamage()
    {
        return this.material.getAttackDamageBonus();
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        stack.hurtAndBreak(1, attacker, (player) -> {player.broadcastBreakEvent(EquipmentSlotType.MAINHAND);});
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
    {
        if ((double)state.getDestroySpeed(worldIn, pos) != 0.0D)
        {
            stack.hurtAndBreak(2, entityLiving, (player) -> {player.broadcastBreakEvent(EquipmentSlotType.MAINHAND);});
        }

        return true;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getEnchantmentValue()
    {
        return this.material.getEnchantmentValue();
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        if(enchantment.category == EnchantmentType.BREAKABLE || enchantment.category == EnchantmentType.WEAPON || enchantment.category == EnchantmentType.DIGGER)
            return true;
        else
            return false;
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair)
    {
        return this.material.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot)
    {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attribute : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker)
    {
        return stack.getItem() instanceof LanzaniteMultiToolItem;
    }

    static {

        EFFECTIVE_ON = ImmutableSet.of(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL,
                Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE,
                new Block[]{Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK,
                        Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE,
                        Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE,
                        Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE,
                        Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE,
                        Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE,
                        Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB,
                        Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB,
                        Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB,
                        Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ,
                        Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON,
                        Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB,
                        Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB,
                        Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB,
                        Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB,
                        Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX,
                        Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX,
                        Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX,
                        Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX,
                        Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX,
                        Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX,
                        Blocks.YELLOW_SHULKER_BOX,
                        Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS,
                        Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.BOOKSHELF, Blocks.OAK_WOOD,
                        Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD,
                        Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG,
                        Blocks.DARK_OAK_LOG, Blocks.CHEST, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN,
                        Blocks.MELON, Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON,
                        Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON,
                        Blocks.OAK_PRESSURE_PLATE, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE,
                        Blocks.JUNGLE_PRESSURE_PLATE, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.ACACIA_PRESSURE_PLATE,
                        Blocks.CRIMSON_BUTTON, Blocks.WARPED_BUTTON,
                        Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL,
                        Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND,
                        Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER,
                        Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER,
                        Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER,
                        Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER,
                        Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER,
                        Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL,
                        Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK, Blocks.HAY_BLOCK, Blocks.DRIED_KELP_BLOCK,
                        Blocks.TARGET, Blocks.SHROOMLIGHT, Blocks.SPONGE, Blocks.WET_SPONGE,
                        Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LEAVES,Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES});

        BLOCK_STRIPPING_MAP = (new Builder()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
                .put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
                .put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
                .put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
                .put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
                .put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
                .put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
                .put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM).put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
                .put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM).put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE)
                .build();

        SHOVEL_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.defaultBlockState()));

        HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.defaultBlockState(),
                Blocks.GRASS_PATH, Blocks.FARMLAND.defaultBlockState(), Blocks.DIRT, Blocks.FARMLAND.defaultBlockState(),
                Blocks.COARSE_DIRT, Blocks.DIRT.defaultBlockState()));
    }

}

