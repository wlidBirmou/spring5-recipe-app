package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

@Component
@AllArgsConstructor
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {





    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if(recipeCommand==null)return null;
        Recipe recipe=Recipe.builder()
                .id(recipeCommand.getId())
                .description(recipeCommand.getDescription())
                .prepTime(recipeCommand.getPrepTime())
                .cookTime(recipeCommand.getCookTime())
                .servings(recipeCommand.getServings())
                .source(recipeCommand.getSource())
                .url(recipeCommand.getUrl())
                .directions(recipeCommand.getDirections())
                .image(recipeCommand.getImage())
                .difficulty(recipeCommand.getDifficulty())
                .ingredients(new LinkedHashSet<>())
                .categories(new LinkedHashSet<>())
                .build();
        if(recipeCommand.getIngredients()!=null)recipe.setIngredients(recipeCommand.getIngredients());
        if(recipeCommand.getCategories()!=null)recipe.setCategories(recipeCommand.getCategories());
        recipe.setNotes(recipeCommand.getNotes());
        return recipe;
    }
}
