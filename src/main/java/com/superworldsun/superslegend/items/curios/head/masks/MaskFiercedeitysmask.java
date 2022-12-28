package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MaskFiercedeitysmask extends NonEnchantItem implements IEntityResizer, ICurioItem
{

    public MaskFiercedeitysmask(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.RED + "Contains a dark, godlike power.."));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Grants Strength and removes some negative effects"));
        list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Magic upon use"));
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        PlayerEntity player = (PlayerEntity) livingEntity;
        //boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
        if (!player.abilities.instabuild) {
            float manaCost = 0.015F;
            ManaProvider.get(player).spendMana(manaCost);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        // Prevent applying changes 2 times per tick
        if (event.phase == TickEvent.Phase.START)
        {
            return;
        }
        // Only if we have the mask
        ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_FIERCEDEITYSMASK.get(), event.player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        float manaCost = 0.015F;
        boolean hasMana = ManaProvider.get(event.player).getMana() >= manaCost || event.player.abilities.instabuild;
        // Check if player is wearing it.
        if (!maskStack.isEmpty() && hasMana)
        {
                //player.causeFoodExhaustion(0.0175f);
                event.player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 10, 1, false, false, false));
                event.player.addEffect(new EffectInstance(Effects.JUMP, 10, 0, false, false, false));
                event.player.addEffect(new EffectInstance(Effects.LUCK, 10, 1, false, false, false));
                event.player.removeEffect(Effects.MOVEMENT_SLOWDOWN);
                event.player.removeEffect(Effects.CONFUSION);
                event.player.removeEffect(Effects.WEAKNESS);
                event.player.removeEffect(Effects.UNLUCK);
                event.player.removeEffect(Effects.BAD_OMEN);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        // Check if it is the Player who takes damage.
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            // Get the Mask as an ItemStack
            ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_FIERCEDEITYSMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
            float manaCost = 0.015F;
            boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
            // Check if player is wearing it.
            if (!stack.isEmpty() && hasMana)
            {
                // Don't do a Check to see if the damage comes from DamageSource.GENERIC. I don't know what mob/block uses the "GENERIC" damage in the game so I normally do a (event.getSource !=
                // DamageSource.*Type*) if I don't want it to take less damage from something in particular.
                if (event.getSource() == DamageSource.FALL)
                {
                    event.isCanceled();
                }
            }
        }
    }

    @Override
    public float getScale(PlayerEntity player) {
        float manaCost = 0.015F;
        boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
        return hasMana ? 1.4F : 1.0F;
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
    {
        return false;
    }
}
