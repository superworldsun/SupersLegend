package com.superworldsun.superslegend.client.render.curio.head;

import com.mojang.blaze3d.vertex.PoseStack;
import com.superworldsun.superslegend.client.model.curio.head.AllNightMaskModel;
import com.superworldsun.superslegend.items.curios.head.masks.AllNightMask;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class AllNightMaskRenderer extends GeoArmorRenderer<AllNightMask> implements ICurioRenderer {

    //TODO get mask to render using Geckolib
    public AllNightMaskRenderer() {
        super(new AllNightMaskModel());
    }

    // @Override
    // public void setupAnim(Entity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    // }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
