package com.superworldsun.superslegend.items.curios.head.masks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.GiantsMaskModel;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GiantsMask extends Item implements IEntityResizer, ICurioItem
{
    private static final UUID GIANTS_MASK_REACH_MODIFIER_ID = UUID.fromString("dfb43a2f-8a3f-476b-8e4c-89f48601cda6");
    private static final UUID GIANTS_MASK_SPEED_MODIFIER_ID = UUID.fromString("076fe501-e233-47e1-a568-8368c8a0f8b6");
    private static final UUID GIANTS_MASK_SWIM_MODIFIER_ID = UUID.fromString("47cd763b-9c10-4cc1-b77a-b4bf07ece942");

    @OnlyIn(Dist.CLIENT)
    private Object model;
    // put your texture here
    private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/armor/giants_mask.png");

    public GiantsMask(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.GRAY + "Within this mask lies the might of a giant"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Awaken the giants power and abilities"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "at the cost of magic"));
    }

    //TODO add so giants can have further attack distance (REACH_DISTANCE only affects blocks placed/break)
    // ,dont take any fall damage when they should be, when swiming straight up or down the speed is alot faster than it should be
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        PlayerEntity player = (PlayerEntity) livingEntity;
        //boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
        if (!player.abilities.instabuild) {
            float manaCost = 0.01F;
            ManaProvider.get(player).spendMana(manaCost);
        }
    }

    //TODO when bug is fixed for not taking fall damage, undo this.
    /*@SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        // Check if it is the Player who takes damage.
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            // Get the Mask as an ItemStack
            ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GIANTSMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
            float manaCost = 0.01F;
            boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
            // Check if player is wearing it.
            if (!stack.isEmpty() && hasMana)
            {
                // Don't do a Check to see if the damage comes from DamageSource.GENERIC. I don't know what mob/block uses the "GENERIC" damage in the game so I normally do a (event.getSource !=
                // DamageSource.*Type*) if I don't want it to take less damage from something in particular.
                if (event.getSource() == DamageSource.FALL)
                {
                    event.setAmount(event.getAmount() / 4);
                }
            }
        }
    }*/

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        float manaCost = 0.01F;
        // Prevent applying changes 2 times per tick
        boolean hasMana = ManaProvider.get(event.player).getMana() >= manaCost || event.player.abilities.instabuild;
        if (event.phase == TickEvent.Phase.START)
        {
            return;
        }
        // Only if we have the mask
        ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GIANTSMASK.get(), event.player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        if (!maskStack.isEmpty())
        {
            if (event.player.isAlive() && hasMana)
            {
                addOrReplaceModifier(event.player, ForgeMod.REACH_DISTANCE.get(), GIANTS_MASK_REACH_MODIFIER_ID, 4.0F, AttributeModifier.Operation.ADDITION);
                //addOrReplaceModifier(event.player, ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "tool", -2D, AttributeModifier.Operation.ADDITION)
                addOrReplaceModifier(event.player, Attributes.MOVEMENT_SPEED, GIANTS_MASK_SPEED_MODIFIER_ID, -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
                addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), GIANTS_MASK_SWIM_MODIFIER_ID, -0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
                event.player.maxUpStep = 2.0f;
            }
            else
            {
                removeModifier(event.player, ForgeMod.REACH_DISTANCE.get(), GIANTS_MASK_REACH_MODIFIER_ID);
                removeModifier(event.player, Attributes.MOVEMENT_SPEED, GIANTS_MASK_SPEED_MODIFIER_ID);
                removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), GIANTS_MASK_SWIM_MODIFIER_ID);
                event.player.maxUpStep = 0.6f;
            }
        }
        else
        {
            removeModifier(event.player, ForgeMod.REACH_DISTANCE.get(), GIANTS_MASK_REACH_MODIFIER_ID);
            removeModifier(event.player, Attributes.MOVEMENT_SPEED, GIANTS_MASK_SPEED_MODIFIER_ID);
            removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), GIANTS_MASK_SWIM_MODIFIER_ID);
            event.player.maxUpStep = 0.6f;
        }
    }

    private static void removeModifier(PlayerEntity player, Attribute attribute, UUID id)
    {
        ModifiableAttributeInstance attributeInstance = player.getAttribute(attribute);
        AttributeModifier modifier = attributeInstance.getModifier(id);

        if (modifier != null)
        {
            attributeInstance.removeModifier(modifier);
        }
    }

    private static void addOrReplaceModifier(PlayerEntity player, Attribute attribute, UUID id, float amount, AttributeModifier.Operation operation)
    {
        ModifiableAttributeInstance attributeInstance = player.getAttribute(attribute);
        AttributeModifier modifier = attributeInstance.getModifier(id);

        if (modifier != null && modifier.getAmount() != amount)
        {
            attributeInstance.removeModifier(modifier);
            modifier = new AttributeModifier(id, id.toString(), amount, operation);
        }
        else if (modifier == null)
        {
            modifier = new AttributeModifier(id, id.toString(), amount, operation);
        }

        if (modifier != null && !attributeInstance.hasModifier(modifier))
        {
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    @Override
    public float getScale(PlayerEntity player) {
        float manaCost = 0.01F;
        boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
        return hasMana ? 4.0F : 1.0F;
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
    {
        // put your model here
        if (!(this.model instanceof GiantsMaskModel))
        {
            model = new GiantsMaskModel<>();
        }

        GiantsMaskModel<?> maskModel = (GiantsMaskModel<?>) this.model;
        ICurio.RenderHelper.followHeadRotations(livingEntity, maskModel.base);
        IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, maskModel.renderType(TEXTURE), false, stack.hasFoil());
        maskModel.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
