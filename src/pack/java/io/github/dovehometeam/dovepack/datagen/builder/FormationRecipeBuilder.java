package io.github.dovehometeam.dovepack.datagen.builder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tterrag.registrate.util.DataIngredient;
import io.github.dovehometeam.dovepack.common.recipe.FormationRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FormationRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final int count;
    private final ItemStack resultStack;
    private final List<String> rows;
    private final Map<Character, Ingredient> key;

    @javax.annotation.Nullable
    private String group;

    public FormationRecipeBuilder(ItemLike result, int count) {
        this(new ItemStack(result, count));
    }

    public FormationRecipeBuilder(ItemStack result) {
        this.rows = Lists.newArrayList();
        this.key = Maps.newLinkedHashMap();
        this.result = result.getItem();
        this.count = result.getCount();
        this.resultStack = result;
    }

    public static FormationRecipeBuilder formation(ItemLike result) {
        return formation(result, 1);
    }

    public static FormationRecipeBuilder formation(ItemLike result, int count) {
        return new FormationRecipeBuilder(result, count);
    }

    public static FormationRecipeBuilder formation(ItemStack result) {
        return new FormationRecipeBuilder(result);
    }

    public FormationRecipeBuilder define(Character symbol, TagKey<Item> tag) {
        return this.define(symbol, Ingredient.of(tag));
    }

    public FormationRecipeBuilder define(Character symbol, ItemLike item) {
        return this.define(symbol, Ingredient.of(item));
    }

    public FormationRecipeBuilder define(Character symbol, Ingredient ingredient) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }

    public FormationRecipeBuilder pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != this.rows.getFirst().length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pattern);
            return this;
        }
    }

    @Override
    @Deprecated(forRemoval = true)
    public FormationRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        return this;//不需要触发器
    }

    @Override
    public FormationRecipeBuilder group(@Nullable String s) {
        this.group = s;
        return this;
    }

    @Override
    public Item getResult() {
        return result;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {

        ShapedRecipePattern pattern = ShapedRecipePattern.of(this.key, this.rows);
        FormationRecipe recipe = new FormationRecipe(Objects.requireNonNullElse(this.group, ""), pattern, this.resultStack);
        recipeOutput.accept(id, recipe, null);
    }


}
