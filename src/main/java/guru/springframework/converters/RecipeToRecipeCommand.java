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
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private NotesToNotesCommand notesToNotesCommand;



    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if(recipe==null)return null;
        RecipeCommand recipeCommand=RecipeCommand.builder()
                .id(recipe.getId())
                .description(recipe.getDescription())
                .prepTime(recipe.getPrepTime())
                .cookTime(recipe.getCookTime())
                .servings(recipe.getServings())
                .source(recipe.getSource())
                .url(recipe.getUrl())
                .directions(recipe.getDirections())
                .image(recipe.getImage())
                .difficulty(recipe.getDifficulty())
                .ingredients(new LinkedHashSet<>())
                .categories(new LinkedHashSet<>())
                .build();
        if(recipe.getIngredients()!=null)recipe.getIngredients().forEach(i->recipeCommand.getIngredients()
                .add(this.ingredientToIngredientCommand.convert(i)));
        if(recipe.getCategories()!=null)recipe.getCategories().forEach(c->recipeCommand.getCategories()
                .add(this.categoryToCategoryCommand.convert(c)));
        recipeCommand.setNotes(this.notesToNotesCommand.convert(recipe.getNotes()));
        return recipeCommand;
    }
}
