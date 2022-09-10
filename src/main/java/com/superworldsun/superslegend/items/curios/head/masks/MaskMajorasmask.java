package com.superworldsun.superslegend.items.curios.head.masks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.AllNightMaskModel;
import com.superworldsun.superslegend.client.model.armor.MajorasMaskModel;
import com.superworldsun.superslegend.client.model.armor.MaskOfScentsModel;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class MaskMajorasmask extends Item implements ICurioItem
{
    @OnlyIn(Dist.CLIENT)
    private Object model;
    // put your texture here
    private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/armor/majoras_mask.png");

    public MaskMajorasmask(Properties properties) {
        super(properties);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.RED + "This Mask gives off a strong evil aura"));
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        World world = livingEntity.level;
        PlayerEntity player = (PlayerEntity) livingEntity;


        if (!world.isClientSide) {
            ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_MAJORASMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
            if (!stack0.isEmpty()) {
                player.addEffect(new EffectInstance(Effect.byId(20), 120, 0, false, true));
                player.addEffect(new EffectInstance(Effect.byId(5), 10, 1, false, false));
                player.addEffect(new EffectInstance(Effect.byId(11), 10, 1, false, false));
                player.addEffect(new EffectInstance(Effect.byId(1), 10, 1, false, false));
                player.addEffect(new EffectInstance(Effect.byId(31), 10, 0, false, false));
            } else {
                player.removeEffect(Effect.byId(20));

            }
        }
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        return true;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
    {
        // put your model here
        if (!(this.model instanceof MajorasMaskModel))
        {
            model = new MajorasMaskModel<>();
        }

        MajorasMaskModel<?> maskModel = (MajorasMaskModel<?>) this.model;
        ICurio.RenderHelper.followHeadRotations(livingEntity, maskModel.base);
        IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, maskModel.renderType(TEXTURE), false, stack.hasFoil());
        maskModel.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
