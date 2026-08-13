package com.superworldsun.superslegend.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.superworldsun.superslegend.registries.StructureTypeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * A jigsaw structure whose template origin is placed a fixed number of blocks
 * below the terrain surface.  This keeps a saved underground template intact
 * instead of projecting its bottom onto the surface like vanilla jigsaws do.
 */
public final class BuriedJigsawStructure extends Structure {
    // Covers the Nayru temple's 29 x 23 footprint, including either rotation.
    private static final int TERRAIN_SAMPLE_RADIUS = 20;
    // Keep lakes and rivers far enough away that they cannot flood an opening.
    private static final int WATER_CLEARANCE_RADIUS = 20;
    private static final int TERRAIN_SAMPLE_STEP = 4;
    private static final int WATER_SAMPLE_STEP = 2;
    private static final int MAX_SURFACE_VARIATION = 8;

    public static final Codec<BuriedJigsawStructure> CODEC = ExtraCodecs.validate(
            RecordCodecBuilder.<BuriedJigsawStructure>mapCodec(instance -> instance.group(
                    settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 7).fieldOf("size").forGetter(structure -> structure.maxDepth),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
                    Codec.intRange(0, 128).fieldOf("surface_offset").forGetter(structure -> structure.surfaceOffset),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
            ).apply(instance, BuriedJigsawStructure::new)), structure ->
                    structure.maxDistanceFromCenter + (structure.terrainAdaptation() == TerrainAdjustment.NONE ? 0 : 12) > 128
                            ? DataResult.error(() -> "Structure size including terrain adaptation must not exceed 128")
                            : DataResult.success(structure)
    ).codec();

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final int surfaceOffset;
    private final int maxDistanceFromCenter;

    public BuriedJigsawStructure(StructureSettings settings, Holder<StructureTemplatePool> startPool,
                                 Optional<ResourceLocation> startJigsawName, int maxDepth,
                                 HeightProvider startHeight, boolean useExpansionHack,
                                 int surfaceOffset, int maxDistanceFromCenter) {
        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.surfaceOffset = surfaceOffset;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        OptionalInt surfaceHeight = findDryLevelSurface(context, chunkPos);
        if (surfaceHeight.isEmpty()) {
            return Optional.empty();
        }

        BlockPos startPos = new BlockPos(chunkPos.getMinBlockX(),
                surfaceHeight.getAsInt() - this.surfaceOffset, chunkPos.getMinBlockZ());

        return JigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.maxDepth,
                startPos, this.useExpansionHack, Optional.empty(), this.maxDistanceFromCenter);
    }

    /** Avoid embedding the entrance in uneven terrain or generating near surface water. */
    private static OptionalInt findDryLevelSurface(GenerationContext context, ChunkPos chunkPos) {
        int centerX = chunkPos.getMiddleBlockX();
        int centerZ = chunkPos.getMiddleBlockZ();
        int lowestY = Integer.MAX_VALUE;
        int highestY = Integer.MIN_VALUE;

        for (int xOffset = -WATER_CLEARANCE_RADIUS; xOffset <= WATER_CLEARANCE_RADIUS; xOffset += WATER_SAMPLE_STEP) {
            for (int zOffset = -WATER_CLEARANCE_RADIUS; zOffset <= WATER_CLEARANCE_RADIUS; zOffset += WATER_SAMPLE_STEP) {
                int surfaceY = context.chunkGenerator().getFirstFreeHeight(centerX + xOffset, centerZ + zOffset,
                        Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
                int oceanFloorY = context.chunkGenerator().getFirstFreeHeight(centerX + xOffset, centerZ + zOffset,
                        Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor(), context.randomState());
                if (surfaceY != oceanFloorY) {
                    return OptionalInt.empty();
                }

                if (Math.abs(xOffset) <= TERRAIN_SAMPLE_RADIUS && Math.abs(zOffset) <= TERRAIN_SAMPLE_RADIUS
                        && xOffset % TERRAIN_SAMPLE_STEP == 0 && zOffset % TERRAIN_SAMPLE_STEP == 0) {
                    lowestY = Math.min(lowestY, surfaceY);
                    highestY = Math.max(highestY, surfaceY);
                    if (highestY - lowestY > MAX_SURFACE_VARIATION) {
                        return OptionalInt.empty();
                    }
                }
            }
        }

        return highestY == Integer.MIN_VALUE ? OptionalInt.empty() : OptionalInt.of(highestY);
    }

    @Override
    public StructureType<?> type() {
        return StructureTypeInit.BURIED_JIGSAW.get();
    }
}
