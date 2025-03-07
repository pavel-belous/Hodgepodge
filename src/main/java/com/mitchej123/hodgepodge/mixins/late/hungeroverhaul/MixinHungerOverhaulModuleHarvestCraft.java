package com.mitchej123.hodgepodge.mixins.late.hungeroverhaul;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import squeek.applecore.api.food.FoodValues;

import com.pam.harvestcraft.ItemRegistry;

import iguanaman.hungeroverhaul.food.FoodModifier;
import iguanaman.hungeroverhaul.module.ModuleHarvestCraft;

@Mixin(ModuleHarvestCraft.class)
public class MixinHungerOverhaulModuleHarvestCraft {

    private static final Map<Item, FoodValues> foodValuesMap = new HashMap<Item, FoodValues>() {

        {
            put(ItemRegistry.coffeeItem, new FoodValues(2, 0.1F));
            put(ItemRegistry.chaiteaItem, new FoodValues(2, 0.1F));
        }
    };

    @Redirect(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Liguanaman/hungeroverhaul/food/FoodModifier;setModifiedFoodValues(Lnet/minecraft/item/Item;Lsqueek/applecore/api/food/FoodValues;)V"),
            remap = false)
    private static void patchFoodValue(Item item, FoodValues foodValue) {
        FoodValues patchedFoodValue = foodValuesMap.get(item);
        FoodModifier.setModifiedFoodValues(item, patchedFoodValue != null ? patchedFoodValue : foodValue);
    }
}
