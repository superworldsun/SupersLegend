package com.superworldsun.superslegend.client.render.curio.head;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.superworldsun.superslegend.client.model.curio.head.GeoCurioMaskModel;
import com.superworldsun.superslegend.items.curios.head.masks.GeoCurioMaskItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

/** One Curios renderer shared by every static GeckoLib mask model. */
public class GeoCurioMaskRenderer extends GeoArmorRenderer<GeoCurioMaskItem> implements ICurioRenderer {
    public GeoCurioMaskRenderer() {
        super(new GeoCurioMaskModel());
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
            PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource bufferSource,
            int packedLight, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch) {
        if (!(stack.getItem() instanceof GeoCurioMaskItem) ||
                !(renderLayerParent.getModel() instanceof HumanoidModel<?> humanoidModel)) {
            return;
        }

        LivingEntity wearer = slotContext.entity();
        prepForRender(wearer, stack, EquipmentSlot.HEAD, humanoidModel);
        RenderType renderType = getRenderType(getAnimatable(), getTextureLocation(getAnimatable()),
                bufferSource, partialTick);
        VertexConsumer vertices = bufferSource.getBuffer(renderType);
        int overlay = LivingEntityRenderer.getOverlayCoords(wearer, 0.0F);

        poseStack.pushPose();
        renderToBuffer(poseStack, vertices, packedLight, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
