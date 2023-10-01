package com.superworldsun.superslegend.client.render.armor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.ArmorModelPart;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;

/**
 * Provides methods for rendering armor models with customizable visibility of
 * different parts.
 */
public class GeoArmorRendererExtension<T extends Item & GeoItem> implements IClientItemExtensions {
    private final Map<ArmorModelPart, Boolean> modelPartsVisibility = new HashMap<>();
    private ExtendedGeoArmorRenderer<T> renderer;
    private ResourceLocation modelResource;
    private ResourceLocation textureResource;
    private ResourceLocation animationResource;
    private @Nullable GeoArmorResourceProvider textureProvider;

    public GeoArmorRendererExtension(String filesNames) {
        setModelName(filesNames);
        setTextureName(filesNames);
        setAnimationName(filesNames);
    }

    @Override
    public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot,
            HumanoidModel<?> original) {
        if (renderer == null) {
            renderer = new ExtendedGeoArmorRenderer<>(this);
        }
        if (textureProvider != null) {
            textureResource = textureProvider.getTexture(entity, stack, slot);
        }
        renderer.prepForRender(entity, stack, slot, original);
        return renderer;
    }

    /**
     * Sets the texture provider.
     * 
     * @param provider a function that dynamically provides texture based on the
     *                 {@link LivingEntity} wearer, {@link ItemStack} and
     *                 {@link EquipmentSlot}.
     */
    public void setTextureProvider(GeoArmorResourceProvider provider) {
        this.textureProvider = provider;
    }

    /**
     * Forces specified armor model parts to be rendered.
     */
    public void showModelParts(ArmorModelPart... parts) {
        for (ArmorModelPart part : parts) {
            modelPartsVisibility.put(part, true);
        }
    }

    /**
     * Forces specified armor model parts to be hidden.
     */
    public void hideModelParts(ArmorModelPart... parts) {
        for (ArmorModelPart part : parts) {
            modelPartsVisibility.put(part, false);
        }
    }

    /**
     * Returns the visibility status of a specific armor model part.
     * 
     * @param part representation of a geo armor model part.
     * @return visibility of the the model part.
     */
    public boolean getModelPartVisibility(ArmorModelPart part) {
        return modelPartsVisibility.getOrDefault(part, false);
    }

    /**
     * Checks if a given armor model part has its visibility overridden.
     * 
     * @param part representation of a geo armor model part.
     * @return true if armor model part visiblity should be overridden. Otherwise,
     *         false.
     */
    public boolean overridesModelPartVisibility(ArmorModelPart part) {
        return modelPartsVisibility.containsKey(part);
    }

    /**
     * @return {@link ResourceLocation} of the armor model.
     */
    public ResourceLocation getModelResource() {
        return modelResource;
    }

    /**
     * Sets the armor model file name.
     * 
     * @param model a {@link String} that represents the name of the model file.
     * @return Current instance of the GeoArmorRendererExtension class.
     */
    public GeoArmorRendererExtension<T> setModelName(String model) {
        this.modelResource = new ResourceLocation(SupersLegendMain.MOD_ID,
                "geo/" + model + ".geo.json");
        return this;
    }

    /**
     * @return {@link ResourceLocation} of the armor texture.
     */
    public ResourceLocation getTextureResource() {
        return textureResource;
    }

    /**
     * Sets the armor texture file name.
     * 
     * @param model a {@link String} that represents the name of the texture file.
     * @return Current instance of the GeoArmorRendererExtension class.
     */
    public GeoArmorRendererExtension<T> setTextureName(String texture) {
        this.textureResource = new ResourceLocation(SupersLegendMain.MOD_ID,
                "textures/armor/" + texture + ".png");
        return this;
    }

    /**
     * @return {@link ResourceLocation} of the armor animation.
     */
    public ResourceLocation getAnimationResource() {
        return animationResource;
    }

    /**
     * Sets the armor animation file name.
     * 
     * @param model a {@link String} that represents the name of the animation file.
     * @return Current instance of the GeoArmorRendererExtension class.
     */
    public GeoArmorRendererExtension<T> setAnimationName(String animation) {
        this.animationResource = new ResourceLocation(SupersLegendMain.MOD_ID,
                "animations/" + animation + ".animation.json");
        return this;
    }
}
