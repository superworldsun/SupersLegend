package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PaintingInit {

    public static final DeferredRegister<PaintingType> PAINTING_TYPES = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, SupersLegendMain.MOD_ID);

    //Notes credited to Mr. Pineapple
    /* Because all painting and other textures are stiched together on startup, they are now in their own texture files
     * This means we can now easily add as many paintings as we want to the game, without having the possibility
     * of overriding vanilla or other mods paintings.
     * To do this we get the DeferredRegistry and we add a new Painting Type, this takes the dimensions of the texture (x, y)
     * A 16x16 painting would take up a 1x1 block, meaning that the picture below takes up a 1x2 block as it is (16x2)
     */

    public static final RegistryObject<PaintingType> SOMARI = PAINTING_TYPES.register("somari", () -> new PaintingType(16,32));
}
