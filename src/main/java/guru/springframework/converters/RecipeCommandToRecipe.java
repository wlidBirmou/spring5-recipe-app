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


    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final CategoryCommandToCategory categoryCommandToCategory;
    private final NotesCommandToNotes notesCommandToNotes;


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
        if(recipeCommand.getIngredientCommands()!=null)recipeCommand.getIngredientCommands().forEach(i->recipe.getIngredients()
                .add(this.ingredientCommandToIngredient.convert(i)));
        if(recipeCommand.getCategoryCommands()!=null)recipeCommand.getCategoryCommands().forEach(c->recipe.getCategories()
                .add(this.categoryCommandToCategory.convert(c)));
        recipe.setNotes(this.notesCommandToNotes.convert(recipeCommand.getNotesCommand()));
        return recipe;
    }
}
