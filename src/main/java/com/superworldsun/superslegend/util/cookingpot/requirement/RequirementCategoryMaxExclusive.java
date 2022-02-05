package com.superworldsun.superslegend.util.cookingpot.requirement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.superworldsun.superslegend.util.cookingpot.FoodCategory;
import com.superworldsun.superslegend.util.cookingpot.CookingPotCookingRecipeInput;
import com.superworldsun.superslegend.util.cookingpot.JsonUtils;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;

public class RequirementCategoryMaxExclusive implements IRequirement {
    //Code credited to Si_hen, this code is a modified version of their crockpot mod
    private final FoodCategory category;
    private final float max;

    public RequirementCategoryMaxExclusive(FoodCategory category, float max) {
        this.category = category;
        this.max = max;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public float getMax() {
        return max;
    }

    @Override
    public boolean test(CookingPotCookingRecipeInput recipeInput) {
        return recipeInput.mergedFoodValues.get(category) < max;
    }

    public static RequirementCategoryMaxExclusive fromJson(JsonObject object) {
        return new RequirementCategoryMaxExclusive(JsonUtils.getAsEnum(object, "category", FoodCategory.class), JSONUtils.getAsFloat(object, "max"));
    }

    @Override
    public JsonElement toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", RequirementType.CATEGORY_MAX.name());
        obj.addProperty("category", category.name());
        obj.addProperty("max", max);
        return obj;
    }

    public static RequirementCategoryMaxExclusive fromNetwork(PacketBuffer buffer) {
        return new RequirementCategoryMaxExclusive(buffer.readEnum(FoodCategory.class), buffer.readFloat());
    }

    @Override
    public void toNetwork(PacketBuffer buffer) {
        buffer.writeEnum(RequirementType.CATEGORY_MAX_EXCLUSIVE);
        buffer.writeEnum(category);
        buffer.writeFloat(max);
    }
}
