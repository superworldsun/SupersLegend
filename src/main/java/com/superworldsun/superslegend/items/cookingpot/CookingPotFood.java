package com.superworldsun.superslegend.items.cookingpot;

import com.google.common.collect.ImmutableList;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.util.cookingpot.MathUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CookingPotFood extends Item {
    //Code credited to Si_hen, this code is a modified version of their crockpot mod
    private final int duration;
    private final boolean isDrink;
    private final int cooldown;
    private final float heal;
    private final Pair<DamageSource, Float> damage;
    private final List<Effect> removedEffects;
    private final List<Supplier<ITextComponent>> tooltips;
    private final boolean hideEffects;
    private final List<Supplier<ITextComponent>> effectTooltips;

    protected CookingPotFood(CookingPotFoodBuilder builder) {
        super(builder.properties.food(builder.foodBuilder.build()));
        this.duration = builder.duration;
        this.isDrink = builder.isDrink;
        this.cooldown = builder.cooldown;
        this.heal = builder.heal;
        this.damage = builder.damage;
        this.removedEffects = builder.removedEffects;
        this.tooltips = builder.tooltips;
        this.hideEffects = builder.hideEffects;
        this.effectTooltips = builder.effectTooltips;
    }

    public static CookingPotFoodBuilder builder() {
        return new CookingPotFoodBuilder();
    }

    public List<Pair<EffectInstance, Float>> getEffects() {
        Food foodProperties = this.getFoodProperties();
        return foodProperties == null ? ImmutableList.of() : foodProperties.getEffects().stream().map(p -> Pair.of(p.getFirst(), p.getSecond())).collect(ImmutableList.toImmutableList());
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide) {
            if (damage != null && damage.getValue() > 0.0F) {
                entityLiving.hurt(damage.getKey(), damage.getValue());
            }
            if (heal > 0.0F) {
                entityLiving.heal(heal);
            }
            removedEffects.forEach(entityLiving::removeEffect);
            if (cooldown > 0 && entityLiving instanceof PlayerEntity) {
                ((PlayerEntity) entityLiving).getCooldowns().addCooldown(this, cooldown);
            }
        }
        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return this.duration;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        if (this.isDrink) {
            return UseAction.DRINK;
        } else {
            return super.getUseAnimation(stack);
        }
    }

    @Override
    public SoundEvent getEatingSound() {
        if (this.isDrink) {
            return SoundEvents.GENERIC_DRINK;
        } else {
            return super.getEatingSound();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World level, List<ITextComponent> tooltipComponents, ITooltipFlag isAdvanced) {
        if (!this.tooltips.isEmpty()) {
            this.tooltips.forEach(tip -> tooltipComponents.add(tip.get()));
        }
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    public static class CookingPotFoodBuilder {
        private Properties properties = new Properties().tab(SupersLegendMain.RESOURCES);
        private int maxStackSize = 64;
        private Rarity rarity = Rarity.COMMON;
        private Food.Builder foodBuilder = new Food.Builder();
        private int duration = FoodUseDuration.NORMAL.val;
        private boolean isDrink;
        private int cooldown;
        private float heal;
        private Pair<DamageSource, Float> damage;
        private final List<Effect> removedEffects = new ArrayList<>();
        private final List<Supplier<ITextComponent>> tooltips = new ArrayList<>();
        private boolean hideEffects;
        private final List<Supplier<ITextComponent>> effectTooltips = new ArrayList<>();

        public CookingPotFoodBuilder nutrition(int nutrition) {
            this.foodBuilder = this.foodBuilder.nutrition(nutrition);
            return this;
        }

        public CookingPotFoodBuilder saturationMod(float saturationModifier) {
            this.foodBuilder = this.foodBuilder.saturationMod(saturationModifier);
            return this;
        }

        public CookingPotFoodBuilder meat() {
            this.foodBuilder = this.foodBuilder.meat();
            return this;
        }

        public CookingPotFoodBuilder alwaysEat() {
            this.foodBuilder = this.foodBuilder.alwaysEat();
            return this;
        }

        public CookingPotFoodBuilder duration(FoodUseDuration durationIn) {
            this.duration = durationIn.val;
            return this;
        }

        public CookingPotFoodBuilder effect(Supplier<EffectInstance> effect, float probability) {
            this.foodBuilder = this.foodBuilder.effect(effect, probability);
            return this;
        }

        public CookingPotFoodBuilder effect(Supplier<EffectInstance> effect) {
            this.foodBuilder = this.foodBuilder.effect(effect, 1.0F);
            return this;
        }

        public CookingPotFoodBuilder effect(Effect effect, int duration, int amplifier, float probability) {
            return this.effect(() -> new EffectInstance(effect, duration, amplifier), probability);
        }

        public CookingPotFoodBuilder effect(Effect effect, int duration, int amplifier) {
            return this.effect(effect, duration, amplifier, 1.0F);
        }

        public CookingPotFoodBuilder effect(Effect effect, int duration, float probability) {
            return this.effect(() -> new EffectInstance(effect, duration), probability);
        }

        public CookingPotFoodBuilder effect(Effect effect, int duration) {
            return this.effect(effect, duration, 1.0F);
        }

        public CookingPotFoodBuilder drink() {
            this.isDrink = true;
            return this;
        }

        public CookingPotFoodBuilder cooldown(int cooldown) {
            this.cooldown = cooldown;
            return this;
        }

        public CookingPotFoodBuilder heal(float heal) {
            this.heal = heal;
            return this;
        }

        public CookingPotFoodBuilder damage(DamageSource damageSource, float damageAmount) {
            this.damage = Pair.of(damageSource, damageAmount);
            return this;
        }

        public CookingPotFoodBuilder removeEffect(Effect effect) {
            this.removedEffects.add(effect);
            return this;
        }

        public CookingPotFoodBuilder tooltip(String key) {
            this.tooltips.add(() -> new TranslationTextComponent("tooltip.crockpot." + key));
            return this;
        }

        public CookingPotFoodBuilder tooltip(String key, TextFormatting... formats) {
            this.tooltips.add(() -> new TranslationTextComponent("tooltip.crockpot." + key).withStyle(formats));
            return this;
        }

        public CookingPotFoodBuilder hideEffects() {
            this.hideEffects = true;
            return this;
        }

        public CookingPotFoodBuilder effectTooltip(Supplier<ITextComponent> tooltip) {
            this.effectTooltips.add(tooltip);
            return this;
        }

        public CookingPotFoodBuilder effectTooltip(ITextComponent tooltip) {
            return this.effectTooltip(() -> tooltip);
        }

        public CookingPotFoodBuilder effectTooltip(String key, TextFormatting... formats) {
            return this.effectTooltip(new TranslationTextComponent("tooltip.crockpot.effect." + key).withStyle(formats));
        }

        public CookingPotFoodBuilder effectTooltip(String key) {
            return this.effectTooltip(new TranslationTextComponent("tooltip.crockpot.effect." + key));
        }

        public CookingPotFoodBuilder hide() {
            this.properties = new Item.Properties();
            if (this.maxStackSize != 64) {
                this.properties = this.properties.stacksTo(this.maxStackSize);
            }
            if (this.rarity != Rarity.COMMON) {
                this.properties = this.properties.rarity(this.rarity);
            }
            return this;
        }

        public CookingPotFoodBuilder stacksTo(int maxStackSize) {
            this.maxStackSize = maxStackSize;
            this.properties = this.properties.stacksTo(this.maxStackSize);
            return this;
        }

        public CookingPotFoodBuilder rarity(Rarity rarity) {
            this.rarity = rarity;
            this.properties = this.properties.rarity(this.rarity);
            return this;
        }

        public CookingPotFood build() {
            return new CookingPotFood(this);
        }
    }
}
