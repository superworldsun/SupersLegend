package com.superworldsun.superslegend.client.render.armor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.superworldsun.superslegend.client.model.armor.ArmorModelPart;
import com.superworldsun.superslegend.client.model.armor.GeoArmorModel;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ExtendedGeoArmorRenderer<T extends Item & GeoItem> extends GeoArmorRenderer<T> {
    private final GeoArmorRendererExtension<T> extension;
    protected GeoBone pelvis = null;

    public ExtendedGeoArmorRenderer(GeoArmorRendererExtension<T> extension) {
        super(new GeoArmorModel<>(extension));
        this.extension = extension;
    }

    @Override
    protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
        super.applyBoneVisibilityBySlot(currentSlot);
        getAllBones().forEach(this::overrideModelPartVisibility);
    }

    @Override
    public void applyBoneVisibilityByPart(EquipmentSlot currentSlot, ModelPart currentPart, HumanoidModel<?> model) {
        super.applyBoneVisibilityByPart(currentSlot, currentPart, model);
        getAllBones().forEach(this::overrideModelPartVisibility);
    }

    @Override
    protected void grabRelevantBones(BakedGeoModel bakedModel) {
        if (this.lastModel != bakedModel) {
            this.pelvis = getPelvisBone();
        }
        super.grabRelevantBones(bakedModel);
    }

    @Override
    protected void applyBaseTransformations(HumanoidModel<?> baseModel) {
        super.applyBaseTransformations(baseModel);
        if (this.pelvis != null) {
            ModelPart rightLegPart = baseModel.rightLeg;
            this.pelvis.updatePosition(pelvis.getPosX(), 12 - rightLegPart.y, rightLegPart.z);
        }
    }

    @Override
    public void setAllVisible(boolean visible) {
        super.setAllVisible(visible);
        setBoneVisible(this.pelvis, visible);
    }

    /**
     * Returns a list of all existing bones in the model.
     * 
     * @return list of existing model parts.
     */
    private NonNullList<GeoBone> getAllBones() {
        NonNullList<GeoBone> bones = NonNullList.create();
        if (body != null)
            bones.add(body);
        if (head != null)
            bones.add(head);
        if (leftArm != null)
            bones.add(leftArm);
        if (leftBoot != null)
            bones.add(leftBoot);
        if (leftLeg != null)
            bones.add(leftLeg);
        if (rightArm != null)
            bones.add(rightArm);
        if (rightBoot != null)
            bones.add(rightBoot);
        if (rightLeg != null)
            bones.add(rightLeg);
        if (pelvis != null)
            bones.add(pelvis);
        return bones;
    }

    /**
     * Returns the 'pelvis' GeoBone from this model.<br>
     * 
     * @return The bone for the pelvis model piece, or null if not using it
     */
    @Nullable
    public GeoBone getPelvisBone() {
        return this.model.getBone("armorPelvis").orElse(null);
    }

    /**
     * Overrides the visibility of a model part based on the extension's
     * configuration.
     * 
     * @param bone part of the model whose visibility should be overridden.
     */
    private void overrideModelPartVisibility(@Nullable GeoBone bone) {
        if (bone == null)
            return;
        ArmorModelPart part = getModelPartFromBone(bone);
        if (extension.overridesModelPartVisibility(part)) {
            bone.setHidden(!extension.getModelPartVisibility(part));
        }
    }

    /**
     * Returns corresponding {@link ArmorModelPart} based on the geo model part.
     * 
     * @param bone geo model part.
     * @return corresponding {@link ArmorModelPart}.
     */
    private ArmorModelPart getModelPartFromBone(@Nonnull GeoBone bone) {
        if (bone == this.head)
            return ArmorModelPart.HEAD;
        if (bone == this.body)
            return ArmorModelPart.BODY;
        if (bone == this.leftArm)
            return ArmorModelPart.LEFT_ARM;
        if (bone == this.rightArm)
            return ArmorModelPart.RIGHT_ARM;
        if (bone == this.leftBoot || bone == this.leftLeg)
            return ArmorModelPart.LEFT_LEG;
        if (bone == this.rightBoot || bone == this.rightLeg)
            return ArmorModelPart.RIGHT_LEG;
        if (bone == this.pelvis)
            return ArmorModelPart.PELVIS;
        throw new IllegalArgumentException("Unrecognized geo model part " + bone);
    }
}