package io.github.dovehometeam.dovepack.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

import static io.github.dovehometeam.dovepack.init.PackRecipes.FORMATION_RECIPE_SERIALIZER;
import static io.github.dovehometeam.dovepack.init.PackRecipes.FORMATION_RECIPE_TYPE;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FormationRecipe implements Recipe<CraftingInput> {
    @Getter
    public final ShapedRecipePattern pattern;
    final ItemStack result;
    final String group;



    public FormationRecipe(String group,ShapedRecipePattern pattern, ItemStack result) {
        this.pattern = pattern;
        this.result = result;
        this.group = group;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return pattern.ingredients();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return pattern.matches(input);
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return this.getResultItem(registries).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.pattern.width() && height >= this.pattern.height();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result;
    }

    @Override
    public boolean isIncomplete() {
        NonNullList<Ingredient> nonnulllist = this.getIngredients();
        return nonnulllist.isEmpty() || nonnulllist.stream().filter(p_151277_ -> !p_151277_.isEmpty()).anyMatch(Ingredient::hasNoItems);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FORMATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return FORMATION_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<FormationRecipe> {

        public static final MapCodec<FormationRecipe> CODEC
                = RecordCodecBuilder.mapCodec(
                        instance -> instance
                                .group(
                                        Codec.STRING.optionalFieldOf("group", "").forGetter(f -> f.group),
                                        ShapedRecipePattern.MAP_CODEC.forGetter(f -> f.pattern),
                                        ItemStack.CODEC.fieldOf("result").forGetter(f -> f.result)
                                )
                                .apply(instance, FormationRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, FormationRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork, Serializer::fromNetwork
        );

        @Override
        public MapCodec<FormationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FormationRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static final StreamCodec<ByteBuf, ItemStack> STACK_STREAM_CODEC = ByteBufCodecs.fromCodec(ItemStack.SINGLE_ITEM_CODEC);

        private static FormationRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            String group = buf.readUtf();
            ShapedRecipePattern pattern = ShapedRecipePattern.STREAM_CODEC.decode(buf);

            ItemStack result = STACK_STREAM_CODEC.decode(buf);
            return new FormationRecipe(group, pattern, result);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, FormationRecipe recipe) {
            buf.writeUtf(recipe.group);
            ShapedRecipePattern.STREAM_CODEC.encode(buf, recipe.pattern);
            STACK_STREAM_CODEC.encode(buf, recipe.result);
        }
    }
}
